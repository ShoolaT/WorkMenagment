package com.example.project.service;

import com.example.project.dto.TaskDto;
import com.example.project.model.Task;
import com.example.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
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

    // Additional methods, if needed

    private TaskDto convertToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .authorId(task.getAuthor().getId())
                .executorId(task.getExecutor().getId())
                .status(task.getStatus())
                .comment(task.getComment())
                .priority(task.getPriority())
                .projectId(task.getProject().getId())
                .build();
    }

    private Task convertToEntity(TaskDto taskDto) {
        Task task = Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .status(taskDto.getStatus())
                .comment(taskDto.getComment())
                .priority(taskDto.getPriority())
                .build();

        // Set other fields (author, executor, project) as needed

        return task;
    }
}

