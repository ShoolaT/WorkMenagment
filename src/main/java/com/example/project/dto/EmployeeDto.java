package com.example.project.dto;
import lombok.*;


@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private Long companyId;
}

