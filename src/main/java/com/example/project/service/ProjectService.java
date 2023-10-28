package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;

    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDto) // Convert entity to DTO
                .collect(Collectors.toList());
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
//        return projectRepository.findById(id).orElse(null);
    }

    public ProjectDto saveProject(ProjectDto projectDto) {
//        return projectRepository.save(project);
        Project project = convertToEntity(projectDto); // Convert DTO to entity
        project = projectRepository.save(project);
        return convertToDto(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public ProjectDto convertToDto(Project project) {
        var executor_company = companyService.getCompanyById(project.getExecutorCompany().getId());
        if (executor_company.isEmpty()) {
            throw new NoSuchElementException("Company not found with id: " + project.getExecutorCompany());
        }
        var customer_company = companyService.getCompanyById(project.getCustomerCompany().getId());
        if (customer_company.isEmpty()) {
            throw new NoSuchElementException("Company not found with id: " + project.getCustomerCompany());
        }
        var leaderProject = employeeService.getEmployeeById(project.getProjectLeader().getId());
        if (leaderProject.isEmpty()) {
            throw new NoSuchElementException("Leader not found with id: " + project.getProjectLeader().getId());
        }
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName()) // Assuming project name is stored in projectName field
                .customerCompanyId(customer_company.get().getId())
                .executorCompanyId(executor_company.get().getId())
                .projectLeader(leaderProject.get().getId())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .priority(project.getPriority())
                .build();

    }

    private Project convertToEntity(ProjectDto projectDto) {
        var executor_company = companyService.getCompanyById(projectDto.getExecutorCompanyId());
        if (executor_company.isEmpty()) {
            throw new NoSuchElementException("Company not found with id: " + projectDto.getExecutorCompanyId());
        }
        var customer_company = companyService.getCompanyById(projectDto.getCustomerCompanyId());
        if (customer_company.isEmpty()) {
            throw new NoSuchElementException("Company not found with id: " + projectDto.getCustomerCompanyId());
        }
        var leaderProject = employeeService.getEmployeeById(projectDto.getProjectLeader());
        if (leaderProject.isEmpty()) {
            throw new NoSuchElementException("Leader not found with id: " + projectDto.getProjectLeader());
        }
        Project project = Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName()) // Assuming project name is stored in projectName field
                .executorCompany(executor_company.get())
                .customerCompany(customer_company.get())
                .startDate(projectDto.getStartDate())
                .projectLeader(leaderProject.get())
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();

        // Set other fields (customerCompany, executorCompany) as needed
        return project;

    }

}
