package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.dto.TaskDto;
import com.example.project.model.Project;
import com.example.project.model.Task;
import com.example.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProjectService projectService;

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> sortByPriority() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<TaskDto> sortByName() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<TaskDto> sortByAuthor() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "author_id"));
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> sortByExecutor() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "executor_id"));
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> sortByStatus() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "status"));
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return convertToDto(task);
    }

    public TaskDto saveTask(TaskDto taskDto) {
        Task task = convertToEntity(taskDto);
        task = taskRepository.save(task);
        return convertToDto(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    private TaskDto convertToDto(Task task) {
        var executor = employeeService.getEmployeeById(task.getExecutor().getId());
        if (executor.isEmpty()) {
            throw new NoSuchElementException("Executor not found with id: " + task.getExecutor().getId());
        }
        var author = employeeService.getEmployeeById(task.getAuthor().getId());
        if (author.isEmpty()) {
            throw new NoSuchElementException("Author not found with id: " + task.getAuthor().getId());
        }
        var project = projectService.getProjectById(task.getProject().getId());
        if (project.isEmpty()) {
            throw new NoSuchElementException("Project not found with id: " + task.getProject().getId());
        }
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .authorId(author.get().getId())
                .executorId(executor.get().getId())
                .status(task.getStatus())
                .comment(task.getComment())
                .priority(task.getPriority())
                .projectId(project.get().getId())
                .build();
    }

    private Task convertToEntity(TaskDto taskDto) {
        var executor = employeeService.getEmployeeById(taskDto.getExecutorId());
        if (executor.isEmpty()) {
            throw new NoSuchElementException("Executor not found with id: " + taskDto.getExecutorId());
        }
        var author = employeeService.getEmployeeById(taskDto.getAuthorId());
        if (author.isEmpty()) {
            throw new NoSuchElementException("Author not found with id: " + taskDto.getAuthorId());
        }
        var project = projectService.getProjectById(taskDto.getProjectId());
        if (project.isEmpty()) {
            throw new NoSuchElementException("Project not found with id: " + taskDto.getProjectId());
        }
        Task task = Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .author(author.get())
                .executor(executor.get())
                .status(taskDto.getStatus())
                .comment(taskDto.getComment())
                .priority(taskDto.getPriority())
                .project(project.get())
                .build();
        return task;
    }
}