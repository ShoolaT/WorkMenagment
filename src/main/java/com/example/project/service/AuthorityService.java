package com.example.project.service;

import com.example.project.dto.CompanyDto;
import com.example.project.model.Authority;
import com.example.project.model.Company;
import com.example.project.repository.AuthorityRepository;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public List<Authority> getAllAuthorities() {
        List<Authority> authorities = authorityRepository.findAll();
        return authorities;
    }
    public List<Authority> getAuthoritiesByNameList(List<String> names) {
        return authorityRepository.findByNameIn(names);
    }
}
