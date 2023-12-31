package com.example.project.controller;

import com.example.project.dto.EmployeeDto;
import com.example.project.dto.ProjectDto;
import com.example.project.dto.TaskCreateDto;
import com.example.project.dto.TaskDto;
import com.example.project.enums.TaskStatus;
import com.example.project.service.EmployeeService;
import com.example.project.service.ProjectService;
import com.example.project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @GetMapping("/create")
    public String createShowTask(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        List<ProjectDto> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("statusList",TaskStatus.values());
        return "tasks/createTask";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskCreateDto taskDto) {
        taskService.saveTask(taskDto);
        return "redirect:/tasks/all";
    }

    @GetMapping("/all")
    public String getAllTasks(Model model,
                              @RequestParam(name = "sort", defaultValue = "id") String sortCriteria) {
        var tasks = taskService.getTasks(0, 9, sortCriteria);
        model.addAttribute("tasks", tasks);
        return "tasks/allTasks";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        TaskDto taskDto = taskService.getTaskById(id);
        model.addAttribute("task", taskDto);
        return "tasks/getTask";
    }
    @PostMapping("/{id}/edit")
    public String updateTask(@PathVariable Long id, @ModelAttribute TaskCreateDto taskDto) {
        taskDto.setId(id);
        taskService.updateTask(taskDto);
        return "redirect:/tasks/"+ taskDto.getId();
    }
    @GetMapping("/{id}/edit")
    public String updateShowTask(@PathVariable Long id, Model model) {
       TaskDto taskDto = taskService.getTaskById(id);
        if (taskDto != null) {
            model.addAttribute("task", taskDto);
            model.addAttribute("employees", employeeService.getAllEmployees());
            model.addAttribute("projects", projectService.getAllProjects());
            model.addAttribute("statusList",TaskStatus.values());
        } else {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return "tasks/updateTask";
    }
    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/all";
    }
}

