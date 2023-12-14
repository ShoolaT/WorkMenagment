package com.example.project.service;

import com.example.project.dto.EmployeeDto;
import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto) // Convert entity to DTO
                .collect(Collectors.toList());
//        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void saveEmployee(EmployeeDto employeeDto) {
        var company = companyService.getCompanyById(employeeDto.getCompanyId());
        if(company.isEmpty()){
            throw new NoSuchElementException("Company not found id: "+employeeDto.getCompanyId());
        }
        var employee = Employee.builder()
                .email(employeeDto.getEmail())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .password(encoder.encode(employeeDto.getPassword()))
                .enabled(Boolean.TRUE)
                .company(company.get())
                .roles(new HashSet<>())
                .build();
        employee.addRole(roleRepository.findByRole("ROLE_USER"));
//        Employee employee = convertToEntity(employeeDto);
//        employee = employeeRepository.save(employee);
        employeeRepository.saveAndFlush(employee);
//        return convertToDto(employee);
//        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    private EmployeeDto convertToDto(Employee employee) {
        var company = companyService.getCompanyById(employee.getCompany().getId());
        if(company.isEmpty()){
            throw new NoSuchElementException("Company not found with id: "+employee.getCompany());
        }
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .email(employee.getEmail())
                .companyId(company.get().getId())
                .build();
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        var company = companyService.getCompanyById(employeeDto.getCompanyId());
        if(company.isEmpty()){
            throw new NoSuchElementException("Company not found id: "+employeeDto.getCompanyId());
        }
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .email(employeeDto.getEmail())
                .company(company.get())
                .build();
    }
}
