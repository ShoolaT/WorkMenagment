package com.example.project.dto;

import com.example.project.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateDto {
    private Long id;
    private String name;
    private Long authorId;//должен быть я
    private Long executorId;
    private TaskStatus status;
    private String comment;
    private int priority;
    private Long projectId;
}
