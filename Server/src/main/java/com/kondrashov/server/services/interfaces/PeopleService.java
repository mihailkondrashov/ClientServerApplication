package com.kondrashov.server.services.interfaces;

import com.kondrashov.server.controllers.dto.PersonRequestDTO;
import com.kondrashov.server.controllers.dto.PersonResponseDTO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface PeopleService {

    Collection<PersonResponseDTO> findAll() throws SQLException;
    Optional<PersonResponseDTO> find(Long id);
    Long save(PersonRequestDTO entity) throws SQLException;
    Optional<PersonResponseDTO> update(Long id, PersonRequestDTO entity);
    boolean delete(Long id);

}
