package com.example.project.controller;

import com.example.project.dto.ProjectDto;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

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
        var project = projectService.getProjectById(id).get();
        return projectService.convertToDto(project);
    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.saveProject(projectDto);
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
