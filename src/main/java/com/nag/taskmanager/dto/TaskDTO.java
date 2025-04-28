package com.nag.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskDTO {

    // Getters and setters
    private Long id;

    @NotBlank(message="Task name is required. Enter a task name.")
    private String name;

    // Description is not needed
    private String description;

    @NotNull(message="Creator ID is required. Enter a creator ID.")
    private Long creatorId;

    @NotNull(message="Room ID is required. Enter a room ID.")
    private Long roomId;

    @NotNull(message="Asignee IDs are required. Please assign the task to at least one person.")
    @Size(min=1, message="Assign the task to at least one person.")
    private List<Long> assigneeIds;

    // Constructors, getters and setters
    public TaskDTO() {
    }

    /*
    public TaskDTO(Long id, String name, String description, Long creatorId, Long roomId, List<Long> assigneeIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
        this.roomId = roomId;
        this.assigneeIds = assigneeIds;
    }
    */

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
