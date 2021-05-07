package com.kondrashov.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kondrashov.client.Controllers.ClientController;
import com.kondrashov.client.Controllers.dto.PersonRequestDTO;
import com.kondrashov.client.Controllers.dto.PersonResponseDTO;
import com.kondrashov.client.utils.PersonMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientApp {

    private static final Logger logger = LogManager.getLogger(ClientApp.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        logger.info("Application is started");
        logger.info("Connecting to server...");
        Socket socket =  new Socket("localhost", 8087);
        ClientController clientController = new ClientController(socket);
        logger.info("Client connected to server");
        Scanner scanner = new Scanner(System.in);
        while(!socket.isClosed()) {
            System.out.println("\nHello!\n" +
                    "Select operation:\n" +
                    "1 - Get all people;\n" +
                    "2 - Get person by id;\n" +
                    "3 - Save new person;\n" +
                    "4 - Update person by id;\n" +
                    "5 - Delete person by id\n" +
                    "or exit for client stop.");
            switch (scanner.next()) {
                case "1":
                    Collection<PersonResponseDTO> people = clientController.getPeople();
                    System.out.println("Result request:");
                    System.out.println(people);
                    break;
                case "2":
                    System.out.println("Write id:");
                    Long id = scanner.nextLong();
                    PersonResponseDTO responseDTO = clientController.getPersonById(id);
                    System.out.println("Result request:");
                    System.out.println(responseDTO);
                    break;
                case "3":
                    PersonRequestDTO person = new PersonRequestDTO();
                    System.out.println("Write name:");
                    person.setFirstName(scanner.next());
                    System.out.println("Write last name:");
                    person.setLastName(scanner.next());
                    System.out.println("Write birthdate. Format (yyyy-MM-dd):");
                    person.setBirthday(LocalDate.parse(scanner.next()));
                    System.out.println("Write phone number:");
                    person.setPhoneNumber(scanner.next());
                    Long savePersonId = clientController.savePerson(person);
                    System.out.println("Id saved person is "+ savePersonId);
                    break;
                case "4":
                    System.out.println("Write id:");
                    Long updatePersonId = scanner.nextLong();
                    PersonRequestDTO actualPerson = PersonMapping.mapToPersonRequest(clientController.getPersonById(updatePersonId));
                    System.out.println("Will first name be corrected?\n1 - Yes\n2 - No");
                    if(scanner.next().equals("1")) {
                        System.out.println("Actual name is " + actualPerson.getFirstName() + ". Make your changes:");
                        actualPerson.setFirstName(scanner.next());
                    }
                    System.out.println("Will last name be corrected?\n1 - Yes\n2 - No");
                    if(scanner.next().equals("1")) {
                        System.out.println("Actual last name is " + actualPerson.getLastName() + ". Make your changes or click to Enter");
                        actualPerson.setLastName(scanner.next());
                    }
                    System.out.println("Will birthdate be corrected?\n1 - Yes\n2 - No");
                    if(scanner.next().equals("1")) {
                        System.out.println("Actual birthdate is " + actualPerson.getBirthday() + ". Make your changes or click to Enter");
                        actualPerson.setBirthday(LocalDate.parse(scanner.next()));
                    }
                    System.out.println("Will phone number be corrected?\n1 - Yes\n2 - No");
                    if(scanner.next().equals("1")) {
                        System.out.println("Actual phone number is " + actualPerson.getPhoneNumber() + ". Make your changes or click to Enter");
                        actualPerson.setPhoneNumber(scanner.next());
                    }
                    PersonResponseDTO updatedPerson = clientController.updatePerson(updatePersonId, actualPerson);
                    System.out.println(updatedPerson);
                    break;
                case "5":
                    System.out.println("Write id:");
                    Long deletePersonId = scanner.nextLong();
                    if(clientController.deletePerson(deletePersonId)){
                        System.out.println("Person delete complete.");
                    }
                    else{
                        System.out.println("Deleting a person is unsuccessful.");
                    }
                    break;
                case "exit":
                    socket.close();
                    break;
                default:
                    System.out.println("Select operation not supported. Select correct operation.");
                    break;
            }
        }
    }
}
