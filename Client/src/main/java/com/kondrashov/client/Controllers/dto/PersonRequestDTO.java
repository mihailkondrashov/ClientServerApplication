package com.kondrashov.client.Controllers.dto;

import java.time.LocalDate;
import java.util.Objects;

public class PersonRequestDTO {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;

    public PersonRequestDTO() {
    }

    public PersonRequestDTO(String firstName, String lastName, LocalDate birthday, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRequestDTO personRequestDTO = (PersonRequestDTO) o;
        return firstName.equals(personRequestDTO.firstName) && lastName.equals(personRequestDTO.lastName) && birthday.equals(personRequestDTO.birthday) && phoneNumber.equals(personRequestDTO.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthday, phoneNumber);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
