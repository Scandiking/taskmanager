package com.nag.taskmanager.controller;

import com.nag.taskmanager.dto.TaskDTO;
import com.nag.taskmanager.exception.ResourceNotFoundException;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.model.Task;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.repository.RoomRepository;
import com.nag.taskmanager.repository.TaskRepository;
import com.nag.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository, PersonRepository personRepository, RoomRepository roomRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        // Response status code 200 OK
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        // Response status code 200 OK
        return ResponseEntity.ok(task);
    }

    /*
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        // Response status code 201 Created
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
     */

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());

        // Set creator
        Person creator = personRepository.findById(taskDTO.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        task.setCreator(creator);

        // Set room if needed
        if (taskDTO.getRoomId() != null) {
            Room room = roomRepository.findById(taskDTO.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
            task.setRoom(room);
        }

        // Set assignees before saving
        Set<Person> assignees = new HashSet<>();
        for (Long assigneeId : taskDTO.getAssigneeIds()) {
            Person assignee = personRepository.findById(assigneeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            assignees.add(assignee);
        }
        task.setAssignees(assignees);

        // Save task first to get ID
        Task savedTask = taskRepository.save(task);

        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        // Response status code 200 OK
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        // Response status code 204 No Content
        return ResponseEntity.noContent().build();
    }
}