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
@Schema(description = "Запрос на создание/изменение данных задачи автором")
public class CreateOrUpdateTaskByAuthorDTO {

    @Schema(description = "Заголовок задачи")
    @Size(min = 4, max = 20,
            message = "Заголовок задачи должен быть от 4 до 20 символов")
    @NotBlank(message = "Заголовок задачи не может быть пустым")
    private String header;

    @Schema(description = "Описание задачи")
    @Size(min = 10, max = 100,
            message = "Описание задачи должно быть от 10 до 100 символов")
    @NotBlank(message = "Описание задачи не может быть пустым")
    private String description;

    @Schema(description = "Статус задачи")
    @NotBlank(message = "Статус задачи не может быть пустым")
    private StatusTask status;

    @Schema(description = "Приоритет задачи")
    @NotBlank(message = "Приоритет задачи не может быть пустым")
    private PriorityTask priority;

    @Schema(description = "Имя исполнителя задачи")
    @Size(min = 2, max = 16,
            message = "Имя исполнителя задачи должно быть от 2 до 16 символов")
    @NotBlank(message = "Имя исполнителя задачи не может быть пустым")
    private String performer;
}