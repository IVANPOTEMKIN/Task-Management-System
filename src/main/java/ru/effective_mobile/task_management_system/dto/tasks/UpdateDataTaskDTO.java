package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "Запрос на изменение данных задачи")
public class UpdateDataTaskDTO {

    @Schema(description = "Заголовок задачи")
    @Size(min = 10, max = 30,
            message = "Заголовок задачи должен быть от 10 до 30 символов")
    private String header;

    @Schema(description = "Описание задачи")
    @Size(min = 10, max = 100,
            message = "Описание задачи должно быть от 10 до 100 символов")
    private String description;

    @Schema(description = "Статус задачи")
    private StatusTask status;

    @Schema(description = "Приоритет задачи")
    private PriorityTask priority;

    @Schema(description = "Email исполнителя задачи")
    @Pattern(regexp = "^([a-zA-Z0-9]+)([.]*)@([a-zA-Z]+)\\.([a-zA-Z]{2,6})$")
    private String performer;
}