package com.example.project.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Email;


@Data
@Builder
public class EmployeeDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String middleName;
    @Email
    private String email;
    private Long companyId;
}

