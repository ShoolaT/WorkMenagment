package com.example.project.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private Long customerCompanyId;
    private Long executorCompanyId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private int priority;
}

