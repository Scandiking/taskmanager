package com.nag.taskmanager.controller;

import com.nag.taskmanager.model.TaskPerson;
import com.nag.taskmanager.service.TaskPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-persons")
public class TaskPersonController {

    private final TaskPersonService taskPersonService;

    @Autowired
    public TaskPersonController(TaskPersonService taskPersonService) {
        this.taskPersonService = taskPersonService;
    }

    @GetMapping
    public List<TaskPerson> getAllTaskPersons() {
        return taskPersonService.getAllTaskPersons();
    }

    @GetMapping("/{id}")
    public TaskPerson getTaskPersonById(@PathVariable Long id) {
        return taskPersonService.getTaskPersonById(id);
    }

    @GetMapping("/person/{personId}")
    public List<TaskPerson> getTaskPersonsByPersonId(@PathVariable Long personId) {
        return taskPersonService.getTaskPersonsByPersonId(personId);
    }

    @GetMapping("/task/{taskId}")
    public List<TaskPerson> getTaskPersonsByTaskId(@PathVariable Long taskId) {
        return taskPersonService.getTaskPersonsByTaskId(taskId);
    }

    @PostMapping
    public ResponseEntity<TaskPerson> createTaskPerson(@RequestBody TaskPerson taskPerson) {
        TaskPerson newTaskPerson = taskPersonService.createTaskPerson(taskPerson);
        return ResponseEntity.ok(newTaskPerson);
    }

    @PutMapping("/{id}")
    public TaskPerson updateTaskPerson(@PathVariable Long id, @RequestBody TaskPerson taskPerson) {
        return taskPersonService.updateTaskPerson(id, taskPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskPerson(@PathVariable Long id) {
        taskPersonService.deleteTaskPerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
