package com.nag.taskmanager.dto;

import com.nag.taskmanager.model.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomDTO {

    // Getters and setters
    private Long id;

    @NotBlank(message="Room name is required. Enter a room name.") // Exception handling for validation
    private String name;
    // Description is not needed
    private String description;
    @Positive(message="Capacity must be a positive number. Enter a positive number.") // Exception handling for validation
    private Integer capacity;

    // Default constructor needed for JPA
    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.capacity = room.getCapacity();

    }

    // Constructor with fields
    public RoomDTO(Long id, String name, String description, Integer capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

}
