package com.kondrashov.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DBService {

    private static Properties properties;
    private final Connection connection;
    private static volatile DBService instance;
    private final Logger logger = LogManager.getLogger();

    public static DBService getInstance() throws SQLException, IOException {
        if (instance == null){
            synchronized (DBService.class){
                if(instance == null){
                    instance = new DBService();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if(!connection.isClosed()){
        return connection;}
        else{
            logger.error("Connection is closed");
            throw new RuntimeException("Connection is closed");
        }
    }

    public void closeConnection() throws SQLException {connection.close();logger.info("Connection to the database is closed");}

    private DBService() throws SQLException, IOException {
        this.properties = new Properties();
        logger.info("File config setup");
        this.connection = createConnection();
        logger.info("The connection to the database is established.");
        initializationDB();
        logger.info("Database setup completed.");
    }

    private Connection createConnection() throws IOException, SQLException {
        properties.load(new FileInputStream("Server/src/main/resources/DBProperties.properties"));

        return DriverManager.getConnection(
                properties.getProperty("DBUrl"),
                properties.getProperty("DBUser"),
                properties.getProperty("DBPassword")
                );
    }

    private void initializationDB() throws SQLException {
        String CREATE_PERSON_DB = "CREATE TABLE IF NOT EXISTS People (" +
                "ID bigint UNIQUE NOT NULL GENERATED BY DEFAULT AS IDENTITY, " +
                "FIRSTNAME VARCHAR(255) NOT NULL , " +
                "LASTNAME VARCHAR(255) NOT NULL ," +
                "birthday DATE NOT NULL," +
                "PHONE_NUMBER VARCHAR(255) NOT NULL," +
                " PRIMARY KEY (ID))";

        Statement statement = connection.createStatement();
        statement.execute(CREATE_PERSON_DB);
        logger.info("The Person table is created.");
    }
}
