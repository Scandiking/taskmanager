package com.nag.taskmanager.controller;

import com.nag.taskmanager.dto.RoomDTO;
import com.nag.taskmanager.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rooms") // Base path for all endpoints in the Room controller
public class RoomController {

    // Field-based dependency injection
    private final RoomService roomService;

    // Constructor-based dependency injection
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Endpoint to get all rooms
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        // Get all Room entities
        List<RoomDTO> rooms = roomService.getAllRooms();
        // Response status code 200 OK
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id);
        // Response status code 200 OK
        return ResponseEntity.ok(room);
    }

    // Endpoint to create a room
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        RoomDTO createdRoom = roomService.createRoom(roomDTO);
        // Response status code 201 Created
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(
            @PathVariable Long id,
            @RequestBody @Valid RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        // Response status code 200 OK
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        // Response status code 204 No Content
        return ResponseEntity.noContent().build();
    }
}
