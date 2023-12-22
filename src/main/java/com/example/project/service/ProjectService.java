package com.example.project.service;

import com.example.project.dto.ProjectCreateDto;
import com.example.project.dto.ProjectDto;
import com.example.project.model.Employee;
import com.example.project.model.Project;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public Page<ProjectDto> getProjects(int page, int size, String sort) {
        var list = projectRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return toPage(list.getContent(), PageRequest.of(list.getNumber(), list.getSize(), list.getSort()));
    }

    private Page<ProjectDto> toPage(List<Project> projects, Pageable pageable) {
        var list = projects.stream()
                .map(this::convertToDto)
                .toList();
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<ProjectDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        var project = projectRepository.findById(id).get();
        return convertToDto(project);
    }

    public ProjectDto saveProject(ProjectCreateDto projectDto) {
        ProjectDto projectDtoForCreate = ProjectDto.builder()
                .name(projectDto.getName())
                .customerCompanyId(companyService.getCompanyById(projectDto.getCustomerCompanyId()))
                .executorCompanyId(companyService.getCompanyById(projectDto.getExecutorCompanyId()))
                .projectLeader(employeeService.getEmployeeById(projectDto.getProjectLeader()))
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();
        Project project = convertToEntity(projectDtoForCreate);
        project = projectRepository.save(project);
        return convertToDto(project);
    }
    public ProjectDto updateProject(ProjectCreateDto projectDto) {
        ProjectDto projectDtoForUpdate = ProjectDto.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .customerCompanyId(companyService.getCompanyById(projectDto.getCustomerCompanyId()))
                .executorCompanyId(companyService.getCompanyById(projectDto.getExecutorCompanyId()))
                .projectLeader(employeeService.getEmployeeById(projectDto.getProjectLeader()))
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();
        Project project = convertToEntity(projectDtoForUpdate);
        project = projectRepository.save(project);
        return convertToDto(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public ProjectDto convertToDto(Project project) {
        var executor_company = companyService.getCompanyById(project.getExecutorCompany().getId());
        var customer_company = companyService.getCompanyById(project.getCustomerCompany().getId());
        var leaderProject = employeeService.getEmployeeById(project.getProjectLeader().getId());
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .customerCompanyId(customer_company)
                .executorCompanyId(executor_company)
                .projectLeader(leaderProject)
                .startDate(String.valueOf(project.getStartDate()))
                .endDate(String.valueOf(project.getEndDate()))
                .priority(project.getPriority())
                .build();

    }



    public Project convertToEntity(ProjectDto projectDto) {
        var executor_company = companyService.getCompany(projectDto.getExecutorCompanyId().getId()).get();
        var customer_company = companyService.getCompany(projectDto.getCustomerCompanyId().getId()).get();
        var leaderProject = employeeService.getEmployee(projectDto.getProjectLeader().getId()).get();
        Project project = Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .executorCompany(executor_company)
                .customerCompany(customer_company)
                .startDate(projectDto.getStartDate())
                .projectLeader(leaderProject)
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();

        return project;

    }

    public void addEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));

        project.getEmployees().add(employee);
        projectRepository.save(project);
    }

    public void removeEmployeeFromProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));

        project.getEmployees().remove(employee);
        projectRepository.save(project);
    }

}
