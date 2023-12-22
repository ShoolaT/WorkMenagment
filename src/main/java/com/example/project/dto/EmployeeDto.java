package com.example.project.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Email;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private String password;
    private Set<String> authorities;
}

