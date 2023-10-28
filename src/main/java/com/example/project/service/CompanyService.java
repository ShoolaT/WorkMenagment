package com.example.project.service;

import com.example.project.dto.CompanyDto;
import com.example.project.model.Company;
import com.example.project.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Company> getCompanyById(Long id) {
       return companyRepository.findById(id);
    }

    public CompanyDto saveCompany(CompanyDto companyDto) {
        Company company = convertToEntity(companyDto);
        company = companyRepository.save(company);
        return convertToDto(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // Additional methods, if needed

    private CompanyDto convertToDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .build();
    }

    private Company convertToEntity(CompanyDto companyDto) {
        return Company.builder()
                .id(companyDto.getId())
                .name(companyDto.getName())
                .build();
    }
}

