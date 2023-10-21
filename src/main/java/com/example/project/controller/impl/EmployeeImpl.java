package com.example.project.controller.impl;

import com.example.project.controller.EmployeeController;
import com.example.project.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeImpl implements EmployeeController {
    private final EmployeeService employeeService;

    @Override
    public String getEmployeeById(Integer id) {
        employeeService.getEmployeeById(id);
        return null;
    }

}
