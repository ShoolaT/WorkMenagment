package com.example.project.controller.interf;

import com.example.project.dto.EmployeeDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/auth")
public interface AuthController {

    @GetMapping("/register")
    String register(Model model);

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    String register(@Valid EmployeeDto employeeDto, BindingResult bindingResult, Model model);

    @GetMapping("/login")
    String login(Boolean error, Model model);
}
