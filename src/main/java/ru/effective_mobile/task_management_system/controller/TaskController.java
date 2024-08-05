package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;
import ru.effective_mobile.task_management_system.service.TaskService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание новой задачи")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody CreateTaskDTO dto) {
        taskService.addTask(dto);
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(summary = "Получение задачи по ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Получение всех задач")
    @GetMapping("/get/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam Integer offset,
                                                     @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasks(offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по заголовку задачи")
    @GetMapping("/get/all/header/{header}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByHeader(@PathVariable String header,
                                                             @RequestParam Integer offset,
                                                             @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByHeader(header, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по статусу задачи")
    @GetMapping("/get/all/status/{status}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByStatus(@PathVariable StatusTask status,
                                                             @RequestParam Integer offset,
                                                             @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByStatus(status, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по приоритету задачи")
    @GetMapping("/get/all/priority/{priority}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPriority(@PathVariable PriorityTask priority,
                                                               @RequestParam Integer offset,
                                                               @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPriority(priority, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по ID автора")
    @GetMapping("/get/all/author/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorId(@PathVariable Long id,
                                                               @RequestParam Integer offset,
                                                               @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorId(id, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по ID исполнителя")
    @GetMapping("/get/all/performer/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerId(@PathVariable Long id,
                                                                  @RequestParam Integer offset,
                                                                  @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerId(id, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по Email автора")
    @GetMapping("/get/all/author/email/{email}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorEmail(@PathVariable String email,
                                                                  @RequestParam Integer offset,
                                                                  @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorEmail(email, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по Email исполнителя")
    @GetMapping("/get/all/performer/email/{email}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerEmail(@PathVariable String email,
                                                                     @RequestParam Integer offset,
                                                                     @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerEmail(email, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по имени автора")
    @GetMapping("/get/all/author/fullName")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorFullName(@RequestParam(required = false) String firstName,
                                                                     @RequestParam(required = false) String lastName,
                                                                     @RequestParam Integer offset,
                                                                     @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorFullName(firstName, lastName, offset - 1, limit));
    }

    @Operation(summary = "Получение всех задач по имени исполнителя")
    @GetMapping("/get/all/performer/fullName")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerFullName(@RequestParam(required = false) String firstName,
                                                                        @RequestParam(required = false) String lastName,
                                                                        @RequestParam Integer offset,
                                                                        @RequestParam Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerFullName(firstName, lastName, offset - 1, limit));
    }

    @Operation(summary = "Изменение заголовка задачи по ID задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}/header")
    public ResponseEntity<?> editHeaderTask(@PathVariable Long id,
                                            @RequestBody UpdateHeaderTaskDTO dto) {

        taskService.editHeaderTask(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение описания задачи по ID задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}/description")
    public ResponseEntity<?> editDescriptionTask(@PathVariable Long id,
                                                 @RequestBody UpdateDescriptionTaskDTO dto) {

        taskService.editDescriptionTask(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение исполнителя задачи по ID задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}/performer")
    public ResponseEntity<?> editPerformerTask(@PathVariable Long id,
                                               @RequestBody UpdatePerformerTaskDTO dto) {

        taskService.editPerformerTask(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение статуса задачи по ID задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}/status/{status}")
    public ResponseEntity<?> editStatusTask(@PathVariable Long id,
                                            @PathVariable StatusTask status) {

        taskService.editStatusTask(id, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение приоритета задачи по ID задачи")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{id}/priority/{priority}")
    public ResponseEntity<?> editPriorityTask(@PathVariable Long id,
                                              @PathVariable PriorityTask priority) {

        taskService.editPriorityTask(id, priority);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление задачи по ID")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}