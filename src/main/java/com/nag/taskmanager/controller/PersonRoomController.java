package com.nag.taskmanager.controller;

import com.nag.taskmanager.dto.PersonRoomDTO;
import com.nag.taskmanager.model.PersonRoom;
import com.nag.taskmanager.service.PersonRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person-rooms")
public class PersonRoomController {

    private final PersonRoomService personRoomService;

    public PersonRoomController(PersonRoomService personRoomService) {
        this.personRoomService = personRoomService;
    }

    @PostMapping
    public ResponseEntity<PersonRoomDTO> createPersonRoom(@Valid @RequestBody PersonRoomDTO personRoomDTO) {
        PersonRoom created = personRoomService.assignPersonToRoom(personRoomDTO);
        PersonRoomDTO response = convertToDTO(created);
        // Response status code 201 Created
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PersonRoomDTO> getAllPersonRooms() {
        // Get all PersonRoom entities
        return personRoomService.getAllPersonRooms().stream()
                // Convert each PersonRoom entity to a PersonRoomDTO
                .map(this::convertToDTO)
                // Collect all PersonRoomDTOs into a list
                .collect(Collectors.toList());
    }

    private PersonRoomDTO convertToDTO(PersonRoom personRoom) {
        PersonRoomDTO dto = new PersonRoomDTO();
        dto.setId(personRoom.getId());
        dto.setPersonId(personRoom.getPersonId());
        dto.setRoomId(personRoom.getRoomId());
        dto.setRole(personRoom.getRole());
        return dto;
    }

}
