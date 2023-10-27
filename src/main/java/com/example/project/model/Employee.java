package com.example.project.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects;

    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @OneToMany(mappedBy = "executor")
    private List<Task> executedTasks;

//    @ManyToOne
//    @JoinColumn(name = "company")
//    private Company company;

//    @OneToMany(mappedBy = "employees")
//    private List<Project> projects;
//
////    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
////    List<Task> tasks;
//
//    @OneToMany(mappedBy = "author")
//    private List<Task> authoredTasks;
//
//    @OneToMany(mappedBy = "executor")
//    private List<Task> executedTasks;


}

