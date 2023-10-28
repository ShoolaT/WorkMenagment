package com.example.project.dto;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private Long customerCompanyId;
    private Long executorCompanyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int priority;
}

