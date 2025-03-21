package com.nag.taskmanager.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="task_person")
public class TaskPerson {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name="task_id", nullable = false)
    private Task task;

    private String status;
    private LocalDate assignmentDate;

    // Defualt constructor required by JPA
    public TaskPerson() {

    }

    public Long getPersonId() {
        return person != null ? person.getId() : null;
    }

    public Long getTaskId() {
        return task != null ? task.getId() : null;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate now) {
        // this.assignmentDate = now;
        this.assignmentDate = assignmentDate;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = (String) status;
    }
}
