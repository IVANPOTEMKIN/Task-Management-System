package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.StatusTask;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskByPerformerDTO {

    @Schema(name = "Статус задачи")
    private StatusTask status;
}