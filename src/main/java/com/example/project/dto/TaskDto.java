package com.example.project.dto;

import com.example.project.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
public class TaskDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private EmployeeDto authorId;
    @NotBlank
    private EmployeeDto executorId;
    @NotBlank
    private TaskStatus status;
    @NotBlank
    private String comment;
    @NotBlank
    @Size(min = 1, max = 10)
    private int priority;
    @NotBlank
    private ProjectDto projectId;
}
