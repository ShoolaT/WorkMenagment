package com.example.project.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_company_id", referencedColumnName = "id")
    private Company customerCompany;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_company_id", referencedColumnName = "id")
    private Company executorCompany;

    private LocalDate startDate;
    private LocalDate endDate;
    private int priority;

    @ManyToOne
    private Employee projectLeader;
    @ManyToMany
    @JoinTable(
            name = "employees_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}

