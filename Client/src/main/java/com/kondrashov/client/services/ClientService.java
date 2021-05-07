package com.kondrashov.client.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kondrashov.client.ClientApp;
import com.kondrashov.client.Controllers.dto.PersonRequestDTO;
import com.kondrashov.client.Controllers.dto.RequestDTO;
import com.kondrashov.client.Controllers.dto.ResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Map;
import java.util.concurrent.Callable;

public class ClientService implements Callable {

    private final Logger logger = LogManager.getLogger(ClientApp.class);
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String httpMethod;
    private String path;
    private Map<String,String> headers;
    private PersonRequestDTO body;

    public ClientService(BufferedWriter bufferedWriter, BufferedReader bufferedReader, String method, String path, Map<String,String> headers, PersonRequestDTO body) {
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
        this.httpMethod = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public Object call() throws Exception {
        RequestDTO requestDTO = new RequestDTO(httpMethod+" "+path+" HTTP/1.1", headers, body);
        bufferedWriter.write(objectMapper.writeValueAsString(requestDTO)+"\n");
        bufferedWriter.flush();
        logger.info("Request  to server is send");
        ResponseDTO responseDTO = objectMapper.readValue(bufferedReader.readLine(), ResponseDTO.class);
        logger.info("Response from server is receive");
        return objectMapper.writeValueAsString(responseDTO.getBody());
    }
}
