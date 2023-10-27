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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "company_executor", cascade = CascadeType.ALL)
    private List<Project> executor_projects;

    @OneToMany(mappedBy = "company_customer", cascade = CascadeType.ALL)
    private List<Project> customer_projects;
}
