package com.example.project.service;

import com.example.project.dto.EmployeeDto;
import com.example.project.model.Authority;
import com.example.project.model.Employee;
import com.example.project.repository.AuthorityRepository;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private CompanyService companyService;

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

//    public List<EmployeeDto> getAllEmployees() {
//        List<Employee> employees = employeeRepository.findAll();
//        return employees.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

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
        boolean existingEmmployee = employeeRepository.existsById(employeeDto.getId());
        if(existingEmmployee == Boolean.FALSE){
            throw new IllegalArgumentException("Employee with name " + employeeDto.getFirstName() + " not found.");
        }
        Employee employee = convertToEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public EmployeeDto convertToDto(Employee employee) {
        var company = companyService.getCompany(employee.getCompany().getId());
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
        var company = companyService.getCompanyById(employeeDto.getCompanyId());
        if(company.isEmpty()){
            throw new NoSuchElementException("Company not found id: "+employeeDto.getCompanyId());
        }

//        Set<Authority> authorities = employeeDto.getAuthorities().stream()
//                .map(authorityName -> {
//                    Authority authority = new Authority();
//                    authority.setName(authorityName);
//                    return authority;
//                })
//                .collect(Collectors.toSet());

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
}
