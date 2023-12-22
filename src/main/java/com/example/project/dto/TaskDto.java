package com.example.project.dto;

import com.example.project.enums.TaskStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String name;
    private Long authorId;
    private Long executorId;
    private TaskStatus status;
    private String comment;
    private int priority;
    private Long projectId;
}
