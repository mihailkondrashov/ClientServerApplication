package com.kondrashov.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kondrashov.server.controllers.dto.PersonRequestDTO;
import com.kondrashov.server.controllers.dto.PersonResponseDTO;
import com.kondrashov.server.controllers.dto.RequestDTO;
import com.kondrashov.server.controllers.dto.ResponseDTO;
import com.kondrashov.server.services.interfaces.PeopleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ServerController implements Runnable{

    private final Socket clientSocket;
    private final BufferedReader serverReader;
    private final BufferedWriter serverWriter;
    private final Logger logger = LogManager.getRootLogger();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final PeopleService personService;

    public ServerController(Socket socket, PeopleService peopleService) throws IOException {
        this.clientSocket = socket;
        this.serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.serverWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.personService = peopleService;
    }

    @Override
    public void run() {
        try {
            while(!clientSocket.isClosed()) {
                String request = serverReader.readLine();
                RequestDTO requestDTO = objectMapper.readValue(request, RequestDTO.class);
                String[] array = requestDTO.getStartLine().split(" ");
                String httpMethod = array[0];
                String path = array[1];
                PersonRequestDTO person = objectMapper.readValue(objectMapper.writeValueAsString(requestDTO.getBody()), PersonRequestDTO.class);

                if (httpMethod.equals("GET") && path.equals("/api/v1/people")) {
                    logger.info("The controller " + "\"" + path + "\"" + " received a GET request");
                    sendResponse(serverWriter, "200 OK", null, getPeople());
                } else if (httpMethod.equals("GET") && path.contains("/api/v1/people/")) {
                    logger.info("The controller " + "\"" + path + "\"" + " received a GET request");
                    String[] pathsElement = path.split("/");
                    Long id = Long.valueOf(pathsElement[pathsElement.length - 1]);
                    sendResponse(serverWriter, "200 OK", null, getPersonById(id).get());
                } else if (httpMethod.equals("POST") && path.contains("/api/v1/people")) {
                    logger.info("The controller " + "\"" + path + "\"" + " received a POST request");
                    sendResponse(serverWriter, "201 Created", null, savePerson(person));
                } else if (httpMethod.equals("PATCH") && path.contains("/api/v1/people/")) {
                    logger.info("The controller " + "\"" + path + "\"" + " received a PATH request");
                    String[] pathsElement = path.split("/");
                    Long id = Long.valueOf(pathsElement[pathsElement.length - 1]);
                    sendResponse(serverWriter, "202 Accepted", null, update(id, person).get());
                } else if (httpMethod.equals("DELETE") && path.contains("/api/v1/people/")) {
                    logger.info("The controller " + "\"" + path + "\"" + " received a DELETE request");
                    String[] pathsElement = path.split("/");
                    Long id = Long.valueOf(pathsElement[pathsElement.length - 1]);
                    if (delete(id)) {
                        sendResponse(serverWriter, "204 No Content", null, true);
                    } else {
                        logger.error("Receive incorrect request: " + requestDTO.getStartLine());
                        sendResponse(serverWriter, "400 Bad Request", null, false);
                    }
                } else {
                    logger.error("Receive incorrect request: " + requestDTO.getStartLine());
                    sendResponse(serverWriter, "400 Bad Request", null, null);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(BufferedWriter writer, String status, Map<String, String> headers, Object body) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO("HTTP/1.1 "+status, headers, body);
        writer.write(objectMapper.writeValueAsString(responseDTO)+"\n");
        writer.flush();
        logger.info("Response is send");
    }


    private Collection<PersonResponseDTO> getPeople () throws SQLException {
        return personService.findAll();
    }

    private Optional<PersonResponseDTO> getPersonById(Long id){
        return personService.find(id);
    }

    private Long savePerson(PersonRequestDTO requestDTO) throws SQLException {
        return personService.save(requestDTO);
    }

    private Optional<PersonResponseDTO> update(Long id, PersonRequestDTO personRequestDTO){
        return personService.update(id, personRequestDTO);
    }

    private boolean delete(Long id){
        return personService.delete(id);
    }
}
