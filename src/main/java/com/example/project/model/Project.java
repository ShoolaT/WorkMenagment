package com.example.project.model;
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
    private String projectName;
    @ManyToOne
    @JoinColumn(name = "company_customer") // Assuming "company_id" is the foreign key in the Project table
    private Company customerCompany;
    @ManyToOne
    @JoinColumn(name = "company_executor") // Assuming "company_id" is the foreign key in the Project table
    private Company executorCompany;

    private LocalDate startDate;
    private LocalDate endDate;
    private int priority;

    @ManyToOne
    @JoinColumn(name = "projectLeader")
    private Employee projectLeader;

//    @ManyToMany
//    @JoinTable(
//            name = "project_employee",
//            joinColumns = @JoinColumn(name = "project_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
//    private List<Employee> employees;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;


}

