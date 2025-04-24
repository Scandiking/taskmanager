package com.nag.taskmanager.controller;

import com.nag.taskmanager.dto.PersonDTO;
import com.nag.taskmanager.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get all persons", description = "Retrieve a list of all persons")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        // Response status code 200 OK
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID", description = "Retrieve a person by their ID")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO person = personService.getPersonById(id);
        // Response status code 200 OK
        return ResponseEntity.ok(person);
    }

    @PostMapping
    @Operation(summary = "Create a new person", description = "Create a new person with the provided details")
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.createPerson(personDTO);
        // Response status code 201 Created
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing person", description = "Update the details of an existing person")
    public ResponseEntity<PersonDTO> updatePerson(
            @PathVariable Long id,
            @RequestBody @Valid PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        // Response status code 200 OK
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person", description = "Delete a person by using their ID")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        // Response status code 204 No Content
        return ResponseEntity.noContent().build();
    }
}