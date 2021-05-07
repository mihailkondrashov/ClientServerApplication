package com.kondrashov.server.services;

import com.kondrashov.server.controllers.dto.PersonRequestDTO;
import com.kondrashov.server.controllers.dto.PersonResponseDTO;
import com.kondrashov.server.entities.Person;
import com.kondrashov.server.exceptions.PersonNotFoundException;
import com.kondrashov.server.repositories.interfaces.PersonRepository;
import com.kondrashov.server.services.interfaces.PeopleService;
import com.kondrashov.server.utils.PersonMapper;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class PeopleServiceImpl implements PeopleService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PeopleServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public Collection<PersonResponseDTO> findAll() throws SQLException {
        return personRepository.
                findAll().
                stream().
                map(personMapper::mapToPersonResponseDTO).
                collect(Collectors.toList());
    }

    @Override
    public Optional<PersonResponseDTO> find(Long id) {

        return Optional.of(personMapper.mapToPersonResponseDTO(personRepository.find(id).orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found."))));
    }

    @Override
    public Optional<PersonResponseDTO> update(Long id, PersonRequestDTO entity) {
        Person person = personRepository.find(id).orElseThrow(() -> new PersonNotFoundException("Person with id "+id+" not found."));
        person.setFirstName(entity.getFirstName());
        person.setLastName(entity.getLastName());
        person.setBirthday(entity.getBirthday());
        person.setPhoneNumber(entity.getPhoneNumber());
        PersonResponseDTO responseDTO = personMapper.mapToPersonResponseDTO(personRepository.update(person).get());
        return Optional.of(responseDTO);
    }

    @Override
    public boolean delete(Long id) {
        return personRepository.delete(id);
    }

    @Override
    public Long save(PersonRequestDTO entity) throws SQLException {
        return personRepository.save(personMapper.mapToPerson(entity));
    }
}
