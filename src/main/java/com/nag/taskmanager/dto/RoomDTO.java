package com.nag.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomDTO {

    // Getters and setters
    private Long id;

    @NotBlank(message="Room name is required. Enter a room name.")
    private String name;
    // Description is not needed
    private String description;
    @Positive(message="Capacity must be a positive number. Enter a positive number.")
    private Integer capacity;

    // Constructors, getters and setters
    public RoomDTO() {

    }

    public RoomDTO(Long id, String name, String description, Integer capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

}
