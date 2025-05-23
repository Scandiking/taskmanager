// Service class with business logic

package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.TaskDTO;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.model.Task;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.repository.RoomRepository;
import com.nag.taskmanager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;

    // Constructor-based dependency injection
    @Autowired
    public TaskService(TaskRepository taskRepository, PersonRepository personRepository,
                       RoomRepository roomRepository) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    // Business logic to get all tasks
    @Transactional(readOnly=true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Business logic to get a task by id
    @Transactional(readOnly=true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return convertToDTO(task);
    }

    // Business logic to create a task
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        // Validate creator exists
        Person creator = personRepository.findById(taskDTO.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + taskDTO.getCreatorId()));

        // Validate room exists
        Room room = null;
        if (taskDTO.getRoomId() != null) {
            room = roomRepository.findById(taskDTO.getRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + taskDTO.getRoomId()));
        }

        // Get and validate assignees
        Set<Person> assignees = new HashSet<>();
        if (taskDTO.getAssigneeIds() != null) {
            for (Long assigneeId : taskDTO.getAssigneeIds()) {
                Person assignee = personRepository.findById(assigneeId)
                        .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + assigneeId));
                assignees.add(assignee);
            }
        }

        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCreator(creator);
        task.setAssignees(assignees);

        if (room != null) {
            task.setRoom(room);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    // Business logic to update a task
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }

        Task task = convertToEntity(taskDTO);
        task.setId(id);
        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    // Business logic to delete a task
    @Transactional
    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Helper methods to convert between DTO and entity
    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());

        if (task.getCreator() != null) {
            dto.setCreatorId(task.getCreator().getId());
        }

        if (task.getRoom() != null) {
            dto.setRoomId(task.getRoom().getId());
        }

        if (task.getAssignees() != null) {
            dto.setAssigneeIds(task.getAssignees().stream()
                    .map(Person::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // Business logic to assign a task to a room
    @Transactional
    public TaskDTO assignTaskToRoom(Long taskId, Long roomId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    // Helper method to convert DTO to entity
    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        return task;
    }

    // Business logic to delete a task
    public void deleteTask(Long id) {
        deleteTaskById(id);
    }
}
