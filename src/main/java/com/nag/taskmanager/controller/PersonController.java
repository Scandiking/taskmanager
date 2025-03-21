package com.nag.taskmanager.controller;

import com.nag.taskmanager.dto.PersonDTO;
import com.nag.taskmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/persons") // Base path for all endpoints in the Person Controller
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        // Response status code 200 OK
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO person = personService.getPersonById(id);
        // Response status code 200 OK
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.createPerson(personDTO);
        // Response status code 201 Created
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(
            @PathVariable Long id,
            @RequestBody @Valid PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        // Response status code 200 OK
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        // Response status code 204 No Content
        return ResponseEntity.noContent().build();
    }
}