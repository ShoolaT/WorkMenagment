package com.example.project.controller;
import com.example.project.dto.CompanyDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.model.Authority;
import com.example.project.model.Company;
import com.example.project.model.Employee;
import com.example.project.service.AuthorityService;
import com.example.project.service.CompanyService;
import com.example.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class RegisterController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CompanyService companyService;


    //@Secured("isAnonymous()")
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        List<Authority> allAuthorities = authorityService.getAllAuthorities();
        List<CompanyDto> allCompanies = companyService.getAllCompanies();
        EmployeeDto employee = new EmployeeDto();
        model.addAttribute("allAuthorities", allAuthorities);
        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("employee", employee);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute EmployeeDto employee) {
        if (employeeService.findByEmail(employee.getEmail()) != null) {
            System.out.println("---------------register error");
            return "errorRegister";
        }
        else {
            employeeService.saveEmployee(employee);
            return "redirect:/";
        }
    }





}