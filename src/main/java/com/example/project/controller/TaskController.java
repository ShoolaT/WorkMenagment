package com.example.project.controller;

import com.example.project.dto.TaskDto;
import com.example.project.model.Task;
import com.example.project.service.TaskService;
import com.example.project.telegram_bot.service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TelegramBot telegramBot;

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
        Task createdTask=taskService.convertToEntity(taskDto);
        String message = EmojiParser.parseToUnicode(":bell::bell::bell::bell::bell:\n New task created:\n" + createdTask.toString());
        telegramBot.sendCreateTaskNotification(message);

        return taskService.saveTask(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskDto.setId(id);

        Task updatedTask=taskService.convertToEntity(taskDto);
        String message = EmojiParser.parseToUnicode(":bell::bell::bell::bell::bell:\nTask updated:\n" + updatedTask.toString());
        telegramBot.sendCreateTaskNotification(message);

        return taskService.saveTask(taskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

