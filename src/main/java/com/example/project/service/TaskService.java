package com.example.project.service;

import com.example.project.dto.TaskCreateDto;
import com.example.project.dto.TaskDto;
import com.example.project.model.Task;
import com.example.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public Page<TaskDto> getTasks(int page, int size, String sort) {
        var list = taskRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return toPage(list.getContent(), PageRequest.of(list.getNumber(), list.getSize(), list.getSort()));
    }

    private Page<TaskDto> toPage(List<Task> tasks, Pageable pageable) {
        var list = tasks.stream()
                .map(this::convertToDto)
                .toList();
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<TaskDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

//    public List<TaskDto> getAllTasks() {
//        List<Task> tasks = taskRepository.findAll();
//        return tasks.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return convertToDto(task);
    }

    public TaskDto saveTask(TaskCreateDto taskDto) {
        TaskDto taskDtoForCreate = TaskDto.builder()
                .name(taskDto.getName())
                .authorId(employeeService.getEmployeeById(taskDto.getAuthorId()))
                .executorId(employeeService.getEmployeeById(taskDto.getExecutorId()))
                .status(taskDto.getStatus())
                .comment(taskDto.getComment())
                .priority(taskDto.getPriority())
                .projectId(projectService.getProjectById(taskDto.getProjectId()))
                .build();
        Task task = convertToEntity(taskDtoForCreate);
        task = taskRepository.save(task);
        return convertToDto(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDto updateTask(TaskCreateDto taskDto) {
        TaskDto taskDtoForUpdate = TaskDto.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .authorId(employeeService.getEmployeeById(taskDto.getAuthorId()))
                .executorId(employeeService.getEmployeeById(taskDto.getExecutorId()))
                .status(taskDto.getStatus())
                .comment(taskDto.getComment())
                .priority(taskDto.getPriority())
                .projectId(projectService.getProjectById(taskDto.getProjectId()))
                .build();

        boolean existingTask = taskRepository.existsById(taskDtoForUpdate.getId());
        if (!existingTask) {
            throw new NoSuchElementException("Task with name: " + taskDto.getName() + " not found.");
        }
        Task task = convertToEntity(taskDtoForUpdate);
        task = taskRepository.save(task);
        return convertToDto(task);
    }

    private TaskDto convertToDto(Task task) {
        var executor = employeeService.getEmployeeById(task.getExecutor().getId());

        var author = employeeService.getEmployeeById(task.getAuthor().getId());
        var project = projectService.getProjectById(task.getProject().getId());
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .authorId(author)
                .executorId(executor)
                .status(task.getStatus())
                .comment(task.getComment())
                .priority(task.getPriority())
                .projectId(project)
                .build();
    }

    private Task convertToEntity(TaskDto taskDto) {
        var executor = employeeService.getEmployee(taskDto.getExecutorId().getId());
        if (executor.isEmpty()) {
            throw new NoSuchElementException("Executor not found with id: " + taskDto.getExecutorId());
        }
        var author = employeeService.getEmployee(taskDto.getAuthorId().getId());
        if (author.isEmpty()) {
            throw new NoSuchElementException("Author not found with id: " + taskDto.getAuthorId());
        }
        var project = projectService.getProjectById(taskDto.getProjectId().getId());
        Task task = Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .author(author.get())
                .executor(executor.get())
                .status(taskDto.getStatus())
                .comment(taskDto.getComment())
                .priority(taskDto.getPriority())
                .project(projectService.convertToEntity(project))
                .build();
        return task;
    }
    public List<TaskDto> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}