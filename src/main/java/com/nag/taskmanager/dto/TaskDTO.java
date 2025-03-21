package com.nag.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
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

    private List<Long> assigneeIds; // Optional field

    // Constructors, getters and setters
    public TaskDTO() {

    }

    public TaskDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
