package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.effective_mobile.task_management_system.controller.utils.Utils.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Создание новой задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_201,
                            description = DESCRIPTION_CODE_201,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody CreateTaskDTO dto) {
        taskService.addTask(dto);
        return ResponseEntity.status(CREATED).build();
    }

    @Operation(
            summary = "Получение задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/{ID}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable(name = "ID") Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(
            summary = "Получение всех задач",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                     @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasks(offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по заголовку задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/header")
    public ResponseEntity<List<TaskDTO>> getAllTasksByHeader(@RequestParam(name = "Заголовок") String header,
                                                             @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                             @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByHeader(header, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по статусу задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/status")
    public ResponseEntity<List<TaskDTO>> getAllTasksByStatus(@RequestParam(name = "Статус") StatusTask status,
                                                             @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                             @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByStatus(status, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по приоритету задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/priority")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPriority(@RequestParam(name = "Приоритет") PriorityTask priority,
                                                               @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                               @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPriority(priority, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по ID автора",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/author/{ID}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorId(@PathVariable(name = "ID") Long id,
                                                               @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                               @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorId(id, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по ID исполнителя",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/performer/{ID}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerId(@PathVariable(name = "ID") Long id,
                                                                  @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                                  @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerId(id, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по email автора",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/author/email")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorEmail(@RequestParam(name = "Email") String email,
                                                                  @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                                  @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorEmail(email, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по email исполнителя",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/performer/email")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerEmail(@RequestParam(name = "Email") String email,
                                                                     @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                                     @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerEmail(email, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по имени автора",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/author/fullName")
    public ResponseEntity<List<TaskDTO>> getAllTasksByAuthorFullName(@RequestParam(name = "Имя", required = false) String firstName,
                                                                     @RequestParam(name = "Фамилия", required = false) String lastName,
                                                                     @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                                     @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByAuthorFullName(firstName, lastName, offset, limit));
    }

    @Operation(
            summary = "Получение всех задач по имени исполнителя",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/performer/fullName")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPerformerFullName(@RequestParam(name = "Имя", required = false) String firstName,
                                                                        @RequestParam(name = "Фамилия", required = false) String lastName,
                                                                        @RequestParam(name = "Кол-во страниц", defaultValue = "1") Integer offset,
                                                                        @RequestParam(name = "Кол-во задач", defaultValue = "20") Integer limit) {

        return ResponseEntity.ok(taskService.getAllTasksByPerformerFullName(firstName, lastName, offset, limit));
    }

    @Operation(
            summary = "Изменение заголовка задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{ID}/header")
    public ResponseEntity<?> editHeaderTaskById(@PathVariable(name = "ID") Long id,
                                                @RequestBody UpdateHeaderTaskDTO dto) {

        taskService.editHeaderTaskById(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Изменение описания задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{ID}/description")
    public ResponseEntity<?> editDescriptionTaskById(@PathVariable(name = "ID") Long id,
                                                     @RequestBody UpdateDescriptionTaskDTO dto) {

        taskService.editDescriptionTaskById(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Изменение исполнителя задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{ID}/performer")
    public ResponseEntity<?> editPerformerTaskById(@PathVariable(name = "ID") Long id,
                                                   @RequestBody UpdatePerformerTaskDTO dto) {

        taskService.editPerformerTaskById(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Изменение статуса задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{ID}/status")
    public ResponseEntity<?> editStatusTaskById(@PathVariable(name = "ID") Long id,
                                                @RequestParam(name = "Статус") StatusTask status) {

        taskService.editStatusTaskById(id, status);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Изменение приоритета задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/edit/{ID}/priority")
    public ResponseEntity<?> editPriorityTaskById(@PathVariable(name = "ID") Long id,
                                                  @RequestParam(name = "Приоритет") PriorityTask priority) {

        taskService.editPriorityTaskById(id, priority);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удаление задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = DESCRIPTION_CODE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = DESCRIPTION_CODE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_401,
                            description = DESCRIPTION_CODE_401,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_403,
                            description = DESCRIPTION_CODE_403,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = DESCRIPTION_CODE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = DESCRIPTION_CODE_500,
                            content = @Content()
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteTaskById(@PathVariable(name = "ID") Long id) {

        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}