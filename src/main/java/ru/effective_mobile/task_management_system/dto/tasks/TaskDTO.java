package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    @Schema(name = "Заголовок задачи")
    private String header;

    @Schema(name = "Описание задачи")
    private String description;

    @Schema(name = "Статус задачи")
    private StatusTask status;

    @Schema(name = "Приоритет задачи")
    private PriorityTask priority;

    @Schema(name = "Автор задачи")
    private String author;

    @Schema(name = "Email автора задачи")
    private String authorEmail;

    @Schema(name = "Исполнитель задачи")
    private String performer;

    @Schema(name = "Email исполнителя задачи")
    private String performerEmail;

    @Schema(name = "Дата и время создания задачи")
    private LocalDateTime createdAt;
}