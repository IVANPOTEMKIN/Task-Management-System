package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;

public interface TaskService {

    void add(@Valid CreateTaskDTO dto);
}