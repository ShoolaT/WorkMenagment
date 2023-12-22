package com.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectCreateDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private Long customerCompanyId;
    @NotBlank
    private Long executorCompanyId;
    @NotBlank
    private Long projectLeader;
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

