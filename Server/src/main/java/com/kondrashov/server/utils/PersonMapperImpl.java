package com.kondrashov.server.utils;

import com.kondrashov.server.controllers.dto.PersonRequestDTO;
import com.kondrashov.server.controllers.dto.PersonResponseDTO;
import com.kondrashov.server.entities.Person;

public class PersonMapperImpl implements PersonMapper{
    @Override
    public Person mapToPerson(PersonRequestDTO requestDTO) {
        return new Person(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getBirthday(),
                requestDTO.getPhoneNumber()
        );
    }

    @Override
    public PersonResponseDTO mapToPersonResponseDTO(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getBirthday(),
                person.getPhoneNumber()
        );
    }
}
