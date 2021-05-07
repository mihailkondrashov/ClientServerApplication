package com.kondrashov.client.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kondrashov.client.ClientApp;
import com.kondrashov.client.Controllers.dto.PersonRequestDTO;
import com.kondrashov.client.Controllers.dto.PersonResponseDTO;
import com.kondrashov.client.services.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ClientController{

    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ClientController(Socket socket) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public Collection<PersonResponseDTO> getPeople() throws IOException, ExecutionException, InterruptedException {
        FutureTask<String> getPeopleFuture = new FutureTask<>(new ClientService(bufferedWriter,bufferedReader, "GET", "/api/v1/people", null, null ));
        new Thread(getPeopleFuture).start();
        return objectMapper.readValue(getPeopleFuture.get(), new TypeReference<Collection<PersonResponseDTO>>() {});
    }

    public PersonResponseDTO getPersonById(Long id) throws IOException, ExecutionException, InterruptedException {
        FutureTask<String> getPersonFuture = new FutureTask(new ClientService(bufferedWriter,bufferedReader, "GET", "/api/v1/people/"+id, null, null ));
        new Thread(getPersonFuture).start();
        return objectMapper.readValue(getPersonFuture.get(),PersonResponseDTO.class);
    }

    public Long savePerson(PersonRequestDTO personRequestDTO) throws IOException, ExecutionException, InterruptedException {
        FutureTask<String> savePersonFuture = new FutureTask(new ClientService(bufferedWriter,bufferedReader, "POST", "/api/v1/people", null, personRequestDTO ));
        new Thread(savePersonFuture).start();
        return objectMapper.readValue(savePersonFuture.get(), Long.class);
    }

    public PersonResponseDTO updatePerson(Long id, PersonRequestDTO personRequestDTO) throws IOException, ExecutionException, InterruptedException {
        FutureTask<String> updatePersonFuture = new FutureTask(new ClientService(bufferedWriter,bufferedReader, "PATCH", "/api/v1/people/"+id, null, personRequestDTO ));
        new Thread(updatePersonFuture).start();
        return objectMapper.readValue(updatePersonFuture.get(),PersonResponseDTO.class);
    }

    public boolean deletePerson(Long id) throws IOException, ExecutionException, InterruptedException {
        FutureTask<String> deletePersonFuture = new FutureTask(new ClientService(bufferedWriter,bufferedReader, "DELETE", "/api/v1/people/"+id, null, null ));
        new Thread(deletePersonFuture).start();
        return objectMapper.readValue(deletePersonFuture.get(),Boolean.class);
    }
}
