package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.time.LocalDateTime;

import static ru.effective_mobile.task_management_system.dto.utils.Utils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = TASK_DTO)
public class TaskDTO {

    @Schema(description = HEADER, example = HEADER)
    private String header;

    @Schema(description = DESCRIPTION, example = DESCRIPTION)
    private String description;

    @Schema(description = STATUS, example = EXAMPLE_STATUS)
    private StatusTask status;

    @Schema(description = PRIORITY, example = EXAMPLE_PRIORITY)
    private PriorityTask priority;

    @Schema(description = AUTHOR, example = EXAMPLE_FULL_NAME)
    private String author;

    @Schema(description = EMAIL, example = EXAMPLE_EMAIL)
    private String authorEmail;

    @Schema(description = PERFORMER, example = EXAMPLE_FULL_NAME)
    private String performer;

    @Schema(description = EMAIL, example = EXAMPLE_EMAIL)
    private String performerEmail;

    @Schema(description = DATE_AND_TIME, example = EXAMPLE_DATE_AND_TIME)
    private LocalDateTime createdAt;
}