package com.kondrashov.server.entities;

import com.kondrashov.server.exceptions.PersonNotValidException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;

    public Person() {
    }

    public Person(String firstName, String lastName, LocalDate birthday, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setPhoneNumber(phoneNumber);
    }

    public Person(Long id, String firstName, String lastName, LocalDate birthday, String phoneNumber) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setPhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws PersonNotValidException{
        if(firstName != null && !firstName.isEmpty() && firstName.length() > 1) {
            this.firstName = firstName;
        }
        else
        {
            throw new PersonNotValidException("Field FirstName should be not empty, not null and characters is greater than 1");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(firstName != null && !firstName.isEmpty() && firstName.length() > 1) {
            this.lastName = lastName;
        }
        else
        {
            throw new PersonNotValidException("Field LastName should be not empty, not null and characters is greater than 1");
        }
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if(birthday != null && birthday.isAfter(LocalDate.of(1870, 01, 01))) {
            this.birthday = birthday;
        }
        else
        {
            throw new PersonNotValidException("Field Birthday should not null and after 01/01/1870");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
        else
        {
            throw new PersonNotValidException("Field Phone Number should be not empty, not null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && birthday.equals(person.birthday) && phoneNumber.equals(person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday, phoneNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("birthday=" + birthday)
                .add("phoneNumber='" + phoneNumber + "'")
                .toString();
    }
}
