/*
Person entity.
 */

package com.nag.taskmanager.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "person_room",
            joinColumns = @JoinColumn(name="person_id"),
            inverseJoinColumns = @JoinColumn(name="room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "assignees")
    private Set<Task> tasks = new HashSet<>();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id != null ? id.equals(person.id) : person.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

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
