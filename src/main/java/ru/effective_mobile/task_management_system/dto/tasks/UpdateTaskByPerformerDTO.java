package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.StatusTask;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на обновление статуса задачи исполнителем")
public class UpdateTaskByPerformerDTO {

    @Schema(description = "Статус задачи")
    private StatusTask status;
}