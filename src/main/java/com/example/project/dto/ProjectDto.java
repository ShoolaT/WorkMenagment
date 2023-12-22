package com.example.project.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProjectDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private CompanyDto customerCompanyId;
    @NotBlank
    private CompanyDto executorCompanyId;
    @NotBlank
    private EmployeeDto projectLeader;
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String startDate;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String endDate;
    @NotBlank
    @Size(min = 1, max = 10)
    private int priority;
}

