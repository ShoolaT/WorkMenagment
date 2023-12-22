package com.example.project.controller;

import com.example.project.dto.CompanyDto;
import com.example.project.dto.EmployeeDto;
import com.example.project.dto.ProjectCreateDto;
import com.example.project.dto.ProjectDto;
import com.example.project.service.CompanyService;
import com.example.project.service.EmployeeService;
import com.example.project.service.ProjectService;
import com.example.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final TaskService taskService;

    @GetMapping("/create")
    public String createShowProject(Model model) {
        List<CompanyDto> companies = companyService.getAllCompanies();
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("companies", companies);
        model.addAttribute("employees", employees);
        return "projects/createProject";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute ProjectCreateDto projectDto) {
        projectService.saveProject(projectDto);
        return "redirect:/projects/all";
    }

   @GetMapping("/all")
    public String getAllProjects(Model model,
                               @RequestParam(name = "sort", defaultValue = "id") String sortCriteria) {
        var tasks = projectService.getProjects(0, 9, sortCriteria);
        model.addAttribute("tasks", tasks);
        return "projects/allProjects";
    }

    @GetMapping("/{id}")
    public String getProjectById(@PathVariable Long id, Model model) {
        ProjectDto project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("employees",employeeService.getEmployeesByProjectId(project.getId()));
        model.addAttribute("tasks", taskService.getTasksByProjectId(id));
        return "projects/getProject";
    }

    @PostMapping("/{id}/edit")
    public String updateProject(@PathVariable Long id, @ModelAttribute ProjectCreateDto projectDto) {
        projectDto.setId(id);
        projectService.updateProject(projectDto);
        return "redirect:/projects/"+ projectDto.getId();
    }
    @GetMapping("/{id}/edit")
    public String updateShowProject(@PathVariable Long id, Model model){
        ProjectDto projectDto = projectService.getProjectById(id);
        if(projectDto != null){
            model.addAttribute("project", projectDto);
            model.addAttribute("companies", companyService.getAllCompanies());
            model.addAttribute("employees", employeeService.getAllEmployees());
        } else {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return "projects/updateProject";
    }
    @GetMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects/all";
    }
    @GetMapping("/{projectId}/addEmployee")
    public String addEmployeeShow(@PathVariable Long projectId, Model model){
        model.addAttribute("projectEmployeesIds", employeeService.getEmployeesByProjectId(projectId).stream().map(EmployeeDto::getId).collect(Collectors.toList()));

        model.addAttribute("employees", employeeService.getAllEmployees());
        return "projects/addEmployeeToProject";
    }
    @PostMapping("/{projectId}/addEmployee/{employeeId}")
    public String addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.addEmployeeToProject(projectId, employeeId);
        return "redirect:/projects/" + projectId;
    }
    @GetMapping("/{projectId}/removeEmployee/{employeeId}")
    public String removeEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.removeEmployeeFromProject(projectId, employeeId);
        return "redirect:/projects/" + projectId;
    }
}
