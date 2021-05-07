package com.kondrashov.client.Controllers.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class PersonResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;

    public PersonResponseDTO() {
    }

    public PersonResponseDTO(Long id, String firstName, String lastName, LocalDate birthday, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
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
        PersonResponseDTO that = (PersonResponseDTO) o;
        return id.equals(that.id) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && birthday.equals(that.birthday) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday, phoneNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersonResponseDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("birthday=" + birthday)
                .add("phoneNumber='" + phoneNumber + "'")
                .toString();
    }
}
