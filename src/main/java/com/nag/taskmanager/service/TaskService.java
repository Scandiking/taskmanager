package com.nag.taskmanager.service;

import com.nag.taskmanager.dto.TaskDTO;
import com.nag.taskmanager.model.Person;
import com.nag.taskmanager.model.PersonRoom;
import com.nag.taskmanager.model.Room;
import com.nag.taskmanager.model.Task;
import com.nag.taskmanager.repository.PersonRepository;
import com.nag.taskmanager.repository.PersonRoomRepository;
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
    private final PersonRoomRepository personRoomRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, PersonRepository personRepository,
                       RoomRepository roomRepository, PersonRoomRepository personRoomRepository) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
        this.personRoomRepository = personRoomRepository;
    }

    @Transactional(readOnly=true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return convertToDTO(task);
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        // Validate creator exists
        Person creator = personRepository.findById(taskDTO.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + taskDTO.getCreatorId()));

        // Validate room exists
        Room room = roomRepository.findById(taskDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + taskDTO.getRoomId()));

        // Validate creator is in the room
        if (personRoomRepository.findByPerson_IdAndRoom_Id(creator.getId(), room.getId()).isEmpty()) {
            throw new ValidationException("Creator must be in the specified room");
        }

        // Get and validate assignees
        Set<Person> assignees = new HashSet<>();
        if (taskDTO.getAssigneeIds() != null) {
            for (Long assigneeId : taskDTO.getAssigneeIds()) {
                Person assignee = personRepository.findById(assigneeId)
                        .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + assigneeId));

                if (personRoomRepository.findByPerson_IdAndRoom_Id(assignee.getId(), room.getId()).isEmpty()) {
                    throw new ValidationException("Assignee must be in the specified room");
                }
                assignees.add(assignee);
            }
        }

        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCreator(creator);
        task.setAssignees(assignees);

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

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

        if (task.getAssignees() != null) {
            dto.setAssigneeIds(task.getAssignees().stream()
                    .map(Person::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Transactional
    public TaskDTO assignTaskToRoom(Long taskId, Long roomId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Get all persons in the room using the join entity
        Set<Person> personsInRoom = personRoomRepository.findByRoom_Id(roomId)
                .stream()
                .map(PersonRoom::getPerson)
                .collect(Collectors.toSet());

        // Assign task to all persons in the room
        task.setAssignees(new HashSet<>(personsInRoom));

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        return task;
    }

    public void deleteTask(Long id) {
        deleteTaskById(id);
    }
}
