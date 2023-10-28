package com.example.project.controller;

import com.example.project.dto.TaskDto;
import com.example.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/sortByPriority")
    public List<TaskDto> sortByPriority() {
        return taskService.sortByPriority();
    }

    @GetMapping("/sortByName")
    public List<TaskDto> sortByName() {
        return taskService.sortByName();
    }

    @GetMapping("/sortByAuthor")
    public List<TaskDto> sortByAuthor() {
        return taskService.sortByAuthor();
    }

    @GetMapping("/sortByExecutor")
    public List<TaskDto> sortByExecutor() {
        return taskService.sortByExecutor();
    }
    @GetMapping("/sortByStatus")
    public List<TaskDto> sortByStatus() {
        return taskService.sortByStatus();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }



    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.saveTask(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskDto.setId(id);
        return taskService.saveTask(taskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

