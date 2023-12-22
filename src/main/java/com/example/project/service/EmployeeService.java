package com.example.project.service;

import com.example.project.dto.CompanyDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.model.Authority;
import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public Employee findByEmail(String email) {
        return employeeRepository.findOneByEmailIgnoreCase(email).orElse(null);
    }
    public Optional<Employee> findOneByEmail(String email) {
        return employeeRepository.findOneByEmailIgnoreCase(email);
    }
    public Page<EmployeeDto> getEmployees(int page, int size, String sort) {
        var list = employeeRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return toPage(list.getContent(), PageRequest.of(list.getNumber(), list.getSize(), list.getSort()));
    }
    private Page<EmployeeDto> toPage(List<Employee> employees, Pageable pageable) {
        var list = employees.stream()
                .map(this::convertToDto)
                .toList();
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<EmployeeDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public EmployeeDto getEmployeeById(Long id) {
        var employee = employeeRepository.findById(id).get();
        return convertToDto(employee);
    }
    public Optional<Employee> getEmployee(Long id){
        return Optional.of(employeeRepository.findById(id).get());
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        boolean existingEmployee = employeeRepository.existsById(employeeDto.getId());
        if(!existingEmployee){
            throw new NoSuchElementException("Employee with name " + employeeDto.getFirstName() + " not found.");
        }
        Employee employee = convertToEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public EmployeeDto convertToDto(Employee employee) {
        var company = companyService.getCompanyById(employee.getCompany().getId());
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .email(employee.getEmail())
                .companyId(company.getId())
                .build();
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        var company = companyService.getCompany(employeeDto.getCompanyId());
        if(company.isEmpty()){
            throw new NoSuchElementException("Company not found id: "+employeeDto.getCompanyId());
        }

        Set<String> authorityNames = employeeDto.getAuthorities();
        List<String> authorityNameList = List.copyOf(authorityNames); // Convert Set to List

        List<Authority> existingAuthorities = authorityService.getAuthoritiesByNameList(authorityNameList);


        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .email(employeeDto.getEmail())
                .password(employeeDto.getPassword())
                .company(company.get())
                .authorities(new HashSet<>(existingAuthorities))
                .build();
    }
    public List<EmployeeDto> getEmployeesByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<EmployeeDto> getEmployeesByProjectId(Long projectId) {
        List<Employee> employees = employeeRepository.findByProjects_Id(projectId);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
