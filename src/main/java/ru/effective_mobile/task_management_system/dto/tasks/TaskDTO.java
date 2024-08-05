package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с задачей")
public class TaskDTO {

    @Schema(description = "Заголовок задачи", example = "Заголовок задачи")
    private String header;

    @Schema(description = "Описание задачи", example = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи", example = "MEDIUM")
    private StatusTask status;

    @Schema(description = "Приоритет задачи", example = "IN_PROGRESS")
    private PriorityTask priority;

    @Schema(description = "Имя автора задачи", example = "Иван Иванов")
    private String author;

    @Schema(description = "Email автора задачи", example = "user@gmail.com")
    private String authorEmail;

    @Schema(description = "Имя исполнителя задачи", example = "Иван Иванов")
    private String performer;

    @Schema(description = "Email исполнителя задачи", example = "user@gmail.com")
    private String performerEmail;

    @Schema(description = "Дата и время создания задачи", example = "2024-08-05T22:19:33")
    private LocalDateTime createdAt;
}