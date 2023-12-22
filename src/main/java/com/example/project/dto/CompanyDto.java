package com.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {
    private Long id;
    @NotBlank
    private String name;
}