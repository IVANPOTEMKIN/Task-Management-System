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

    @Schema(description = "Заголовок задачи")
    private String header;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи")
    private StatusTask status;

    @Schema(description = "Приоритет задачи")
    private PriorityTask priority;

    @Schema(description = "Имя автора задачи")
    private String author;

    @Schema(description = "Email автора задачи")
    private String authorEmail;

    @Schema(description = "Имя исполнителя задачи")
    private String performer;

    @Schema(description = "Email исполнителя задачи")
    private String performerEmail;

    @Schema(description = "Дата и время создания задачи")
    private LocalDateTime createdAt;
}