package com.example.project.model;
import jakarta.persistence.*;
import lombok.*;
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
    private String customerCompany;
    private String executorCompany;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}

