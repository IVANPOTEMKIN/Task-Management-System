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

import static ru.effective_mobile.task_management_system.dto.utils.Utils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = CREATE_TASK_DTO)
public class CreateTaskDTO {

    @Schema(description = HEADER, example = HEADER)
    @Size(min = 10, max = 30,
            message = HEADER_MESSAGE)
    @NotBlank(message = HEADER_MESSAGE_NOT_BLANK)
    private String header;

    @Schema(description = DESCRIPTION, example = DESCRIPTION)
    @Size(min = 10, max = 100,
            message = DESCRIPTION_MESSAGE_NOT_BLANK)
    @NotBlank(message = DESCRIPTION_MESSAGE_NOT_BLANK)
    private String description;

    @Schema(description = STATUS, example = EXAMPLE_STATUS)
    private StatusTask status;

    @Schema(description = PRIORITY, example = EXAMPLE_PRIORITY)
    private PriorityTask priority;
}