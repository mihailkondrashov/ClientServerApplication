package com.kondrashov.client.utils;

import com.kondrashov.client.Controllers.dto.PersonRequestDTO;
import com.kondrashov.client.Controllers.dto.PersonResponseDTO;

public class PersonMapping {

    public static PersonRequestDTO mapToPersonRequest(PersonResponseDTO responseDTO){
        return new PersonRequestDTO(responseDTO.getFirstName(), responseDTO.getLastName(), responseDTO.getBirthday(), responseDTO.getPhoneNumber());

    }
}
