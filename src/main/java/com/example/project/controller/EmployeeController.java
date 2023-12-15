package com.example.project.controller;

import com.example.project.dto.CompanyDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.service.CompanyService;
import com.example.project.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    @GetMapping("/create")
    public String createShowEmployee(Model model) {
        List<CompanyDto> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "employees/createEmployee";
    }
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
        return "redirect:/employees/all";
    }

    @GetMapping("/all")
    public String getAllEmployees(Model model) {
        var employees = employeeService.getEmployees(0,9,"id");
        model.addAttribute("employees", employees);
        model.addAttribute("companies", companyService.getAllCompanies());
        return "employees/allEmployees";
    }
    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        CompanyDto companyDto = companyService.getCompany(employee.getCompanyId());
        model.addAttribute("employee", employee);
        model.addAttribute("company",companyDto);
        return "employees/getEmployee";
    }
    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeDto employeeDto) {
        employeeDto.setId(id);
        employeeService.updateEmployee(employeeDto);
        return "redirect:/employees/"+employeeDto.getId();
    }
    @GetMapping("/{id}/edit")
    public String updateShowEmployee(@PathVariable Long id, Model model) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        CompanyDto companyDto = companyService.getCompany(employeeDto.getCompanyId());
        if (employeeDto != null) {
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("companies",companyService.getAllCompanies());
        } else {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return "employees/updateEmployee";
    }

    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/all";
    }

}
