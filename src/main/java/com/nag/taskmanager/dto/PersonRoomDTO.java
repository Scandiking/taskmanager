package com.nag.taskmanager.dto;
import jakarta.validation.constraints.NotNull;

public class PersonRoomDTO {

    private Long id;

    @NotNull(message="Person ID is required. Enter a person ID.") // Exception handling for validation
    private Long personId;

    @NotNull(message="Room ID is required. Enter a room ID.") // Exception handling for validation
    private Long roomId;

    private String role; // Optional field

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
