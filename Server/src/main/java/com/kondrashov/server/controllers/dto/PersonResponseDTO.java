package com.kondrashov.server.controllers.dto;

import com.kondrashov.server.exceptions.PersonNotValidException;

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

    public void setFirstName(String firstName) throws PersonNotValidException {
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
