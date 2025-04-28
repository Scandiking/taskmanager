// Entity class that represent data model for a person.
/*
Person entity.
 */

package com.nag.taskmanager.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

// Entity class that represents Person entity
@Entity
@Table(name = "persons")
public class Person {

    // Getters and setters
    @Setter
    @Getter
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // no need for @notblank because it's auto-generated
    private Long id;

    @Setter
    @Getter
    @Column
    @NotBlank
    private String firstName;

    @Setter
    @Getter
    @Column
    @NotBlank
    private String lastName;

    @Setter
    @Getter
    @Column
    @NotBlank
    private String email;

    @Setter
    @Getter
    @Column
    @NotBlank
    private String phone;

    @ManyToMany(mappedBy = "assignees")
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="person_room",
            joinColumns = @JoinColumn(name="person_id"),
            inverseJoinColumns = @JoinColumn(name="room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    public Set<Room> getRooms() {
        return rooms;
    }



    // Default constructor required by JPA
    public Person() {

    }

    // Constructor with fields
    public Person(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Equals, hashCode and toString methods
    @Override
    // Check if record are already in the database
    public boolean equals(Object o) {
        // Check if the object is the same
        if (this == o) return true;
        // Check if the object is null or the class is different or the id is null
        if (o == null || getClass() != o.getClass()) return false;

        // Cast the object to a person
        Person person = (Person) o;

        // then return the id if it's not null, else return null
        return id != null ? id.equals(person.id) : person.id == null;
    }

    // Hashcode method
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // toString method
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
