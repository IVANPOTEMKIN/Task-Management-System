package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;
import ru.effective_mobile.task_management_system.service.TaskService;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание новой задачи")
    @PostMapping("/add")
    public void add(@RequestBody CreateTaskDTO dto) {
        taskService.add(dto);
    }
}