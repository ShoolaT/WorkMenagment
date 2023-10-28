package com.example.project.service;

import com.example.project.dto.EmployeeDto;
import com.example.project.model.Company;
import com.example.project.model.Employee;
import com.example.project.repository.CompanyRepository;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyService companyService;


    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto) // Convert entity to DTO
                .collect(Collectors.toList());
//        return employeeRepository.findAll();
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return convertToDto(employee);
//        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
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
