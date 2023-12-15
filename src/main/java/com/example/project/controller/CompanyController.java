package com.example.project.controller;

import com.example.project.dto.CompanyDto;
import com.example.project.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable Long id) {
        var company = companyService.getCompanyById(id).get();
        return companyService.convertToDto(company);
    }

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        return companyService.saveCompany(companyDto);
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        companyDto.setId(id);
        return companyService.saveCompany(companyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
