package com.example.project.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
    @JsonBackReference
    @JoinColumn(name = "customer_company_id", referencedColumnName = "id")
    private Company customerCompany;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "executor_company_id", referencedColumnName = "id")
    private Company executorCompany;

    private String startDate;
    private String endDate;
    private int priority;

    @ManyToOne(cascade = CascadeType.MERGE)
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

