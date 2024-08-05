package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.util.List;

public interface TaskService {

    void addTask(@Valid CreateTaskDTO dto);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getAllTasks(Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByHeader(String header, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByStatus(StatusTask status, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByPriority(PriorityTask priority, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByAuthorId(Long id, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByPerformerId(Long id, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByAuthorEmail(String email, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByPerformerEmail(String email, Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByAuthorFullName(String firstName, String lastName,
                                              Integer offset, Integer limit);

    List<TaskDTO> getAllTasksByPerformerFullName(String firstName, String lastName,
                                                 Integer offset, Integer limit);

    void editHeaderTask(Long id, @Valid UpdateHeaderTaskDTO dto);

    void editDescriptionTask(Long id, @Valid UpdateDescriptionTaskDTO dto);

    void editPerformerTask(Long id, @Valid UpdatePerformerTaskDTO dto);

    void editPriorityTask(Long id, PriorityTask priority);

    void editStatusTask(Long id, StatusTask status);

    void deleteTask(Long id);
}