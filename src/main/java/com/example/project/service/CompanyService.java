package com.example.project.service;

import com.example.project.dto.CompanyDto;
import com.example.project.model.Company;
import com.example.project.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    public Page<CompanyDto> getCompanies(int page, int size, String sort) {
        var list = companyRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return toPage(list.getContent(), PageRequest.of(list.getNumber(), list.getSize(), list.getSort()));
    }

    private Page<CompanyDto> toPage(List<Company> companies, Pageable pageable) {
        var list = companies.stream()
                .map(this::convertToDto)
                .toList();
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<CompanyDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CompanyDto getCompanyById(Long id) {
       var company = companyRepository.findById(id).get();
        return convertToDto(company);
    }
    public Optional<Company> getCompany(Long id) {
        return Optional.of(companyRepository.findById(id).get());
    }

    public CompanyDto saveCompany(CompanyDto companyDto) {
        Company company = convertToEntity(companyDto);
        company = companyRepository.save(company);
        return convertToDto(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }



    public CompanyDto convertToDto(Company company) {
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

