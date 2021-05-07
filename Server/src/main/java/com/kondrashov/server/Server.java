package com.kondrashov.server;

import com.kondrashov.server.controllers.ServerController;
import com.kondrashov.server.repositories.PersonRepositoryImpl;
import com.kondrashov.server.services.DBService;
import com.kondrashov.server.services.PeopleServiceImpl;
import com.kondrashov.server.utils.PersonMapperImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {

    private static final Logger logger = LogManager.getLogger(ServerApp.class);
    private static final int PORT = 8087;
    private static final Properties properties = new Properties();


    static {try {
        properties.load(new FileInputStream("Server/src/main/resources/Server.properties"));
    } catch (IOException e) {
        e.printStackTrace();
    }}

    public static void main(String[] args) {
        try {
            logger.info("Server is started.");
            DBService dbService = DBService.getInstance();
            PersonMapperImpl personMapper = new PersonMapperImpl();
            PersonRepositoryImpl personRepository = new PersonRepositoryImpl(dbService.getConnection());
            PeopleServiceImpl personService = new PeopleServiceImpl(personRepository, personMapper);
            ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("countSupportedClients")));
            try(ServerSocket serverSocket = new ServerSocket(PORT, Integer.parseInt(properties.getProperty("countSupportedClients")));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                logger.info("Listening for connection on port " + PORT);
                while(!serverSocket.isClosed()){
                    try {
                        if (bufferedReader.ready()) {
                            String command = bufferedReader.readLine();
                            if (command.equalsIgnoreCase("exit")) {
                                logger.info("Server's socket is close");
                                serverSocket.close();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e);
                    }
                    Socket client = serverSocket.accept();
                    executorService.execute(new ServerController(client, personService));
                }
                logger.info("Connection with client is closed.");
            }
            logger.info("Application is stopped");
        } catch (SQLException | IOException exception) {
            logger.error(exception.getLocalizedMessage(), exception);
        }
    }
}
