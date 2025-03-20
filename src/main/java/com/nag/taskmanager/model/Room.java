package com.nag.taskmanager.model;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    private Set<PersonRoom> personRooms = new HashSet<>();


    public Set<Person> getPersons() {
        Set<Person> result = new HashSet<>();
        for (PersonRoom pr : personRooms) {
            result.add(pr.getPerson());
        }
        return result;
    }

    // Add a person through the join relationship
    public void addPerson(Person person) {
        PersonRoom personRoom = new PersonRoom();
        personRoom.setPerson(person);
        personRoom.setRoom(this);
        this.personRooms.add(personRoom);
    }

    // Default construtor required by JPA
    public Room() {

    }

    // Constructor with fields
    public Room(String name, String description, Integer capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }



    // equals, hashCode and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public void getID() {

    }
}
