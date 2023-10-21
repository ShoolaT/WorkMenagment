package com.example.project.model;

import com.example.project.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Employee author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Employee executor;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String comment;
    private int priority;

}