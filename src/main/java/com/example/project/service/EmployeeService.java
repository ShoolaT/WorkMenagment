package com.example.project.service;

import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    EmployeeRepository repository;
    public Optional<Employee> getEmployeeById(Integer id){
        var employee = repository.findById(id);
        return employee;
    }
}
