package com.kondrashov.server.repositories;

import com.kondrashov.server.entities.Person;
import com.kondrashov.server.repositories.interfaces.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {

    private final Connection connection;
    private final Logger logger = LogManager.getRootLogger();
    private final String SAVE_PERSON = "Insert into People (firstName, lastName, birthday, phone_number) values (?,?,?,?)";
    private final String GET_PERSON_BY_ID = "Select * from People where id = ?";
    private final String GET_PEOPLE = "Select * from people";
    private final String DELETE_PERSON_BY_ID = "Delete from people where id = ?";
    private final String UPDATE_PERSON = "Update people set firstName = ?, lastName = ?, birthday = ?, phone_number = ? where id = ?";

    public PersonRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Person> findAll() {
        Collection<Person> people = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet people_result = statement.executeQuery(GET_PEOPLE);
            while (people_result.next()) {
                people.add(new Person(
                        people_result.getLong(1),
                        people_result.getString(2),
                        people_result.getString(3),
                        people_result.getObject(4, LocalDate.class),
                        people_result.getString(5)
                ));
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
        }
        logger.info(GET_PEOPLE);
        return people;
    }

    @Override
    public Optional<Person> find(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_PERSON_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Person person = new Person();
            while(resultSet.next()) {
                person= new Person(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDate.class),
                        resultSet.getString(5)
                );
            }
            logger.info(GET_PERSON_BY_ID);
            return Optional.of(person);

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Person> update(Person entity) {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_PERSON)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setObject(3, entity.getBirthday());
            statement.setString(4, entity.getPhoneNumber());
            statement.setLong(5, entity.getId());
            boolean isUpdate = statement.execute();
            if(!isUpdate){
                logger.info(UPDATE_PERSON);
                return find(entity.getId());
            }
            else{
                throw new SQLException("Person is not updated");
            }
        }
        catch(SQLException exp){
            logger.error(exp.getMessage(), exp);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {

        try(PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_BY_ID)){
            statement.setLong(1, id);
            logger.info(DELETE_PERSON_BY_ID);
            return !statement.execute();
        }
        catch(SQLException exp){
            logger.error(exp.getMessage(), exp);
            return false;
        }
    }

    @Override
    public Long save(Person entity) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_PERSON, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setObject(3, entity.getBirthday());
            statement.setString(4, entity.getPhoneNumber());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
        }
        logger.info(SAVE_PERSON);
        return entity.getId();
    }
}
