package com.example.project.controller;

import com.example.project.dto.CompanyDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.dto.ProjectDto;
import com.example.project.service.CompanyService;
import com.example.project.service.EmployeeService;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping("/create")
    public String createShowProject(Model model) {
        List<CompanyDto> companies = companyService.getAllCompanies();
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("companies", companies);
        model.addAttribute("employees", employees);
        return "projects/createProject";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute ProjectDto projectDto) {
        projectService.saveProject(projectDto);
        return "redirect:/projects/all";
    }

    @PostMapping("/{projectId}/addEmployee/{employeeId}")
    public String addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
//        projectService.addEmployeeToProject(projectId, employeeId);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{projectId}/removeEmployee/{employeeId}")
    public String removeEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
//        projectService.removeEmployeeFromProject(projectId, employeeId);
        return "redirect:/projects/" + projectId;
    }



    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/sortByPriority")
    public List<ProjectDto> sortByPriority() {
        return projectService.sortByPriority();
    }

    @GetMapping("/sortByName")
    public List<ProjectDto> sortByName() {
        return projectService.sortByName();
    }

    @GetMapping("/sortByStartDate")
    public List<ProjectDto> sortByStartDate() {
        return projectService.sortByStartDate();
    }

    @GetMapping("/sortByEndDate")
    public List<ProjectDto> sortByEndDate() {
        return projectService.sortByEndDate();
    }

    @GetMapping("/sortByProjectLeader")
    public List<ProjectDto> sortByProjectLeader() {
        return projectService.sortByProjectLeader();
    }



    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id) {
//        var project = projectService.getProjectById(id).get();
//        return projectService.convertToDto(project);
        return null;
    }



    @PostMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        projectDto.setId(id);
        return projectService.saveProject(projectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

}
