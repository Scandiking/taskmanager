// Service class with business logic

package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.PersonRoomDTO;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.model.PersonRoom;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.repository.PersonRoomRepository;
import com.nag.taskmanager.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonRoomService {

    private final PersonRoomRepository personRoomRepository;
    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;

    public PersonRoomService(
            PersonRoomRepository personRoomRepository,
            PersonRepository personRepository,
            RoomRepository roomRepository) {
        this.personRoomRepository = personRoomRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    // Business logic to assign a person to a room
    public PersonRoom assignPersonToRoom(PersonRoomDTO dto) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        PersonRoom personRoom = new PersonRoom();
        personRoom.setPerson(person);
        personRoom.setRoom(room);
        personRoom.setRole(dto.getRole());

        return personRoomRepository.save(personRoom);
    }

    // Business logic to get all person rooms
    public List<PersonRoom> getAllPersonRooms() {
        return personRoomRepository.findAll();
    }
}
