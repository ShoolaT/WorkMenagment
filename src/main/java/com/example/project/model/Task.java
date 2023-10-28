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
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Employee author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private Employee executor;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String comment;
    private int priority;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id")
    private Project project;

}