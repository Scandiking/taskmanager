package com.nag.taskmanager.model;
import jakarta.persistence.*;

@Entity
public class PersonRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;

    // Optional fields
    private String role;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return person.getId();
    }

    public Long getRoomId() {
        return room.getId();
    }

    public String getRole() {
        return role;
    }

    // Getters, setters, constructors
}
