// Service class with business logic

package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.RoomDTO;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    // Constructor-based dependency injection
    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Business logic to get all rooms
    @Transactional(readOnly=true)
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Business logic to get a room by id
    @Transactional(readOnly=true)
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
        return convertToDTO(room);
    }

    // Business logic to create a room
    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    // Business logic to update a room
    @Transactional
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        // Check if room ecists
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with id: " + id);
        }

        Room room = convertToEntity(roomDTO);
        room.setId(id);
        Room updatedRoom = roomRepository.save(room);
        return convertToDTO(updatedRoom);
    }

    // Business logic to delete a room
    @Transactional
    public void deleteRoom(Long id) {
        // Check if room exists
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    // Helper methods to convert between DTO and entity
    private RoomDTO convertToDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getCapacity()
        );
    }

    // Helper methods to convert between DTO and entity
    private Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setName(roomDTO.getName());
        room.setDescription(roomDTO.getDescription());
        room.setCapacity(roomDTO.getCapacity());
        return room;
    }


}
