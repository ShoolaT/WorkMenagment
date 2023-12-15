package com.example.project.service;

import com.example.project.dto.EmployeeDto;
import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyService companyService;

    public Employee findByEmail(String email) {
        return employeeRepository.findOneByEmailIgnoreCase(email).orElse(null);
    }
    public Optional<Employee> findOneByEmail(String email) {
        return employeeRepository.findOneByEmailIgnoreCase(email);
    }


    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);


        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public EmployeeDto convertToDto(Employee employee) {
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
