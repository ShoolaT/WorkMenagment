package com.example.project.dto;

import com.example.project.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private Long authorId;
    @NotBlank
    private Long executorId;
    @NotBlank
    private TaskStatus status;
    @NotBlank
    private String comment;
    @NotBlank
    @Size(min = 1, max = 10)
    private int priority;
    @NotBlank
    private Long projectId;
}
