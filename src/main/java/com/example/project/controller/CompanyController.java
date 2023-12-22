package com.example.project.controller;

import com.example.project.dto.CompanyDto;
import com.example.project.service.CompanyService;
import com.example.project.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    @GetMapping("/create")
    public String createShowCompany(){
        return "companies/createCompany";
    }

    @PostMapping("/create")
    public String createCompany(@ModelAttribute CompanyDto companyDto) {
        companyService.saveCompany(companyDto);
        return "redirect:/companies/all";
    }

    @GetMapping("/all")
    public String getAllCompanies(Model model,
                                  @RequestParam(name = "sort", defaultValue = "id") String sortCriteria) {
        var companies = companyService.getCompanies(0,9,sortCriteria);
        model.addAttribute("companies",companies);
        return "companies/allCompanies";
    }

    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Long id, Model model) {
        CompanyDto company = companyService.getCompanyById(id);
        model.addAttribute("employees", employeeService.getEmployeesByCompanyId(company.getId()));
        model.addAttribute("company", company);
        return "companies/getCompany";
    }


    @PostMapping("/{id}/edit")
    public String updateCompany(@PathVariable Long id, @ModelAttribute CompanyDto companyDto) {
        companyDto.setId(id);
        companyService.saveCompany(companyDto);
        return "redirect:/companies/" + companyDto.getId();
    }
    @GetMapping("/{id}/edit")
    public String updateShowCompany(@PathVariable Long id, Model model) {
        CompanyDto companyDto = companyService.getCompanyById(id);
        if (companyDto != null) {
            model.addAttribute("company", companyDto);
        } else {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return "companies/updateCompany";
    }
    @GetMapping("/{id}/delete")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies/all";
    }
}
