package com.example.project.dto;

import com.example.project.enums.TaskStatus;
import lombok.*;

@Data
@Builder
public class TaskDto {
    private Long id;
    private String name;
    private EmployeeDto authorId;//должен быть я
    private EmployeeDto executorId;
    private TaskStatus status;
    private String comment;
    private int priority;
    private ProjectDto projectId;
}
