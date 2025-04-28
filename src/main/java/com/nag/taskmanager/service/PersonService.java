// Service class with business logic

package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.PersonDTO;
import com.nag.taskmanager.dto.RoomDTO;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.exception.ResourceNotFoundException;

import com.nag.taskmanager.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;

    // Constructor-based dependency injection
    @Autowired
    public PersonService(PersonRepository personRepository, RoomRepository roomRepository) {
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
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

    // Assign a room to a person
    @Transactional
    public void assignRoomToPerson(Long personId, Long roomId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        person.getRooms().add(room);
        personRepository.save(person);
    }

    // Remove a room from a person
    @Transactional
    public void removeRoomFromPerson(Long personId, Long roomId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        person.getRooms().remove(room);
        personRepository.save(person);
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

    // Get all rooms for a person
    public List<RoomDTO> getPersonRooms(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personId));

        return person.getRooms().stream()
                .map(room -> new RoomDTO(room))
                .collect(Collectors.toList());
    }


}
