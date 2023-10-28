package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToDto) // Convert entity to DTO
                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        return convertToDto(project);
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
    private ProjectDto convertToDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName()) // Assuming project name is stored in projectName field
                .customerCompanyId(project.getCustomerCompany().getId())
                .executorCompanyId(project.getExecutorCompany().getId())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .priority(project.getPriority())
                .build();
    }

    private Project convertToEntity(ProjectDto projectDto) {
        Project project = Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName()) // Assuming project name is stored in projectName field
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .priority(projectDto.getPriority())
                .build();

        // Set other fields (customerCompany, executorCompany) as needed

        return project;
    }

}
