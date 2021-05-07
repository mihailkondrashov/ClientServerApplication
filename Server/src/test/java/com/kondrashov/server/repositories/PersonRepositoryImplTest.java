package com.kondrashov.server.repositories;

import com.kondrashov.server.entities.Person;
import com.kondrashov.server.services.DBService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryImplTest {

    private static DBService dbService ;
    private static PersonRepositoryImpl personRepository;

    @BeforeAll
    static void init() throws IOException, SQLException {
        dbService = DBService.getInstance();
        personRepository = new PersonRepositoryImpl(dbService.getConnection());
    }

    @Test
    void save(){

        String firstName = "Mike";
        String lastName = "Johnson";
        LocalDate birthday = LocalDate.of(2009,12,31);
        String phone = "+789452";

        Person person = new Person(firstName,lastName,birthday,phone);
        Long id = personRepository.save(person);

        Person actualPerson = personRepository.find(id).get();

        assertThat(actualPerson.getId()).isEqualTo(id);
        assertThat(actualPerson.getFirstName()).isEqualTo(firstName);
        assertThat(actualPerson.getLastName()).isEqualTo(lastName);
        assertThat(actualPerson.getBirthday()).isEqualTo(birthday);
        assertThat(actualPerson.getPhoneNumber()).isEqualTo(phone);
        personRepository.delete(id);
    }

    @Test
    void delete(){
        String firstName = "Mike";
        String lastName = "Johnson";
        LocalDate birthday = LocalDate.of(2009,12,31);
        String phone = "+789452";

        Person person = new Person(firstName,lastName,birthday,phone);
        Long id = personRepository.save(person);

        boolean isDelete = personRepository.delete(id);
        Person actualPerson = personRepository.find(id).get();
        assertThat(isDelete).isTrue();
        assertThat(actualPerson.getId()).isNull();
        assertThat(actualPerson.getFirstName()).isNull();
        assertThat(actualPerson.getLastName()).isNull();
        assertThat(actualPerson.getBirthday()).isNull();
        assertThat(actualPerson.getPhoneNumber()).isNull();
    }

    @Test
    void update(){
        String firstName = "Mike";
        String lastName = "Johnson";
        LocalDate birthday = LocalDate.of(2009,12,31);
        String phone = "+789452";

        Person person = new Person(firstName,lastName,birthday,phone);
        Long id = personRepository.save(person);

        String      newFirstName = "Mike12";
        String      newLastName = "Johnson12";
        LocalDate   newBirthday = LocalDate.of(2010,12,31);
        String      newPhone = "123458";

        person.setPhoneNumber(newPhone);
        person.setLastName(newLastName);
        person.setFirstName(newFirstName);
        person.setBirthday(newBirthday);

        Person updatedPerson = personRepository.update(person).get();
        assertThat(updatedPerson.getFirstName()).isEqualTo(newFirstName);
        assertThat(updatedPerson.getLastName()).isEqualTo(newLastName);
        assertThat(updatedPerson.getBirthday()).isEqualTo(newBirthday);
        assertThat(updatedPerson.getPhoneNumber()).isEqualTo(newPhone);
        personRepository.delete(id);
    }


    @AfterAll
    static void close() throws SQLException {
        dbService.closeConnection();
    }
}