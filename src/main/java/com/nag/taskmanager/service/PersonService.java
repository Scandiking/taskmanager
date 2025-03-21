// Service class with business logic

package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.PersonDTO;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    // Constructor-based dependency injection
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Business logic to get all persons
    @Transactional(readOnly = true)
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Business logic to get a person by id
    @Transactional(readOnly = true)
    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));
    return convertToDTO(person);
    }

    // Business logic to create a person
    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = convertToEntity(personDTO);
        Person savedPerson = personRepository.save(person);
        return convertToDTO(savedPerson);
    }

    // Business logic to update a person
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }

        Person person = convertToEntity(personDTO);
        person.setId(id);
        Person updatedPerson = personRepository.save(person);
        return convertToDTO(updatedPerson);
    }

    // Business logic to delete a person
    @Transactional
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
        personRepository.deleteById(id);
    }

    // Helper methods to convert between DTO and entity
    private PersonDTO convertToDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPhone()
        );
    }

    // Helper methods to convert between DTO and entity
    private Person convertToEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setPhone(personDTO.getPhone());
        return person;
    }
}
