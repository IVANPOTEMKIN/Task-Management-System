package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateTaskByAuthorDTO {

    @Schema(name = "Заголовок задачи")
    @Size(min = 4, max = 20,
            message = "Заголовок задачи должен быть от 4 до 20 символов")
    private String header;

    @Schema(name = "Описание задачи")
    @Size(min = 10, max = 100,
            message = "Описание задачи должно быть от 10 до 100 символов")
    private String description;

    @Schema(name = "Статус задачи")
    private StatusTask status;

    @Schema(name = "Приоритет задачи")
    private PriorityTask priority;

    @Schema(name = "Исполнитель задачи")
    @Size(min = 2, max = 16,
            message = "Имя пользователя должно быть от 2 до 16 символов")
    private String performer;
}