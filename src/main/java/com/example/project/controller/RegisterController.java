package com.example.project.controller;
import com.example.project.dto.EmployeeDto;
import com.example.project.model.Employee;
import com.example.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class RegisterController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        Employee employee = new Employee();
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