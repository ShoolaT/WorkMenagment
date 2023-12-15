package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> sortByPriority() {
        List<Project> projects = projectRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> sortByStartDate() {
        List<Project> projects = projectRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"));
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> sortByEndDate() {
        List<Project> projects = projectRepository.findAll(Sort.by(Sort.Direction.ASC, "endDate"));
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> sortByProjectLeader() {
        List<Project> projects = projectRepository.findAll(Sort.by(Sort.Direction.ASC, "projectLeader"));
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> sortByName() {
        List<Project> projects = projectRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }





    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public ProjectDto saveProject(ProjectDto projectDto) {
        Project project = convertToEntity(projectDto);
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
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .customerCompanyId(customer_company.get().getId())
                .executorCompanyId(executor_company.get().getId())
                .projectLeader(leaderProject.getId())
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
        var leaderProject = employeeService.getEmployee(projectDto.getProjectLeader());
        Project project = Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .executorCompany(executor_company.get())
                .customerCompany(customer_company.get())
                .startDate(projectDto.getStartDate())
                .projectLeader(leaderProject.get())
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();

        return project;

    }


}
