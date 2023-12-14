package com.example.project.controller;

import com.example.project.controller.interf.AuthController;
import com.example.project.dto.EmployeeDto;
import com.example.project.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final EmployeeService employeeService;

    @Override
    public String register(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "/auth/register";
    }

    @Override
    public String register(@Valid EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            employeeService.saveEmployee(employeeDto);
            return "redirect:/";
        }
        model.addAttribute("employeeDto", employeeDto);
        return "/auth/register";
    }

    @Override
    public String login(
            @RequestParam(defaultValue = "false", required = false) Boolean error,
            Model model
    ) {
        if (error.equals(Boolean.TRUE)) {
            model.addAttribute("error", "Invalid Username or Password");
        }
        return "/auth/login";
    }


}
