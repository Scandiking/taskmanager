package com.nag.taskmanager.service;

import com.nag.taskmanager.model.TaskPerson;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.repository.TaskPersonRepository;
import com.nag.taskmanager.repository.TaskRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

@Service
public class TaskPersonService {

    private final TaskPersonRepository taskPersonRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    @Setter
    private String status;

    @Autowired
    public TaskPersonService(TaskPersonRepository taskPersonRepository, PersonRepository personRepository, TaskRepository taskRepository) {
        this.taskPersonRepository = taskPersonRepository;
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskPerson> getAllTaskPersons() {
        return taskPersonRepository.findAll();
    }

    public TaskPerson getTaskPersonById(Long id) {
        return taskPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskPerson not found with id: " + id));
    }

    public List<TaskPerson> getTaskPersonsByPersonId(Long personId) {
        if (!personRepository.existsById(personId)) {

            throw new ResourceNotFoundException("Person not found with id: " + personId);
        }
        return taskPersonRepository.findByPerson_Id(personId);
    }

    public TaskPerson createTaskPerson(TaskPerson taskPerson) {
        if (!personRepository.existsById(taskPerson.getPersonId())) {
            throw new ResourceNotFoundException("Person not found with id: " + taskPerson.getPersonId());
        }
        if (!taskRepository.existsById(taskPerson.getTaskId())) {
            throw new ResourceNotFoundException("Task not found with id: " + taskPerson.getTaskId());
        }

        // Check if the association already exists
        Optional<TaskPerson> existing = taskPersonRepository.findByPerson_IdAndTask_Id(taskPerson.getPersonId(), taskPerson.getTaskId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("This person is already assigned to this task");
        }

        // Set assignment date if not provided
        if (taskPerson.getAssignmentDate() == null) {
            taskPerson.setAssignmentDate(LocalDate.now());
        }

        return taskPersonRepository.save(taskPerson);
    }

    public TaskPerson updateTaskPerson(Long id, TaskPerson taskPersonDetails) {
        TaskPerson existingTaskPerson = getTaskPersonById(id);

        existingTaskPerson.setStatus(taskPersonDetails.getStatus());

        return taskPersonRepository.save(existingTaskPerson);
    }

    public void deleteTaskPerson(Long id) {
        TaskPerson taskPerson = getTaskPersonById(id);
        taskPersonRepository.delete(taskPerson);
    }


    public List<TaskPerson> getTaskPersonsByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        return taskPersonRepository.findByTask_Id(taskId);
    }

    // Setters getters
    public String getStatus() {
        String status = "";
        return status;
    }

}
