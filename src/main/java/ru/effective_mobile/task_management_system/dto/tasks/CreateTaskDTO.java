package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание задачи")
public class CreateTaskDTO {

    @Schema(description = "Заголовок задачи")
    @Size(min = 10, max = 30,
            message = "Заголовок задачи должен быть от 10 до 30 символов")
    @NotBlank(message = "Заголовок задачи не может быть пустым")
    private String header;

    @Schema(description = "Описание задачи")
    @Size(min = 10, max = 100,
            message = "Описание задачи должно быть от 10 до 100 символов")
    @NotBlank(message = "Описание задачи не может быть пустым")
    private String description;

    @Schema(description = "Статус задачи")
    private StatusTask status;

    @Schema(description = "Приоритет задачи")
    private PriorityTask priority;
}