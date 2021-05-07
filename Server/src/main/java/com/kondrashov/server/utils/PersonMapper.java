package com.kondrashov.server.utils;

import com.kondrashov.server.controllers.dto.PersonRequestDTO;
import com.kondrashov.server.controllers.dto.PersonResponseDTO;
import com.kondrashov.server.entities.Person;

public interface PersonMapper {

    Person mapToPerson(PersonRequestDTO requestDTO);
    PersonResponseDTO mapToPersonResponseDTO(Person person);
}
