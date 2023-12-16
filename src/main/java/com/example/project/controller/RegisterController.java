package com.example.project.controller;
import com.example.project.dto.EmployeeDto;
import com.example.project.model.Employee;
import com.example.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class RegisterController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        EmployeeDto employee = new EmployeeDto();
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


//    @PostMapping("/register")
//    public ResponseEntity<String> registerNewUser(@RequestBody EmployeeDto employeeDto) {
//        if (employeeService.findByEmail(employeeDto.getEmail()) != null) {
//            System.out.println("---------------register error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists.");
//        } else {
//            employeeService.saveEmployee(employeeDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
//        }
//    }



}