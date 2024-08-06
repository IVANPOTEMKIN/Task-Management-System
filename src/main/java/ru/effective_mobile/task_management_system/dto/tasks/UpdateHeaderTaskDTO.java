package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.effective_mobile.task_management_system.dto.utils.Utils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = UPDATE_HEADER_TASK_DTO)
public class UpdateHeaderTaskDTO {

    @Schema(description = HEADER, example = HEADER)
    @Size(min = 10, max = 30,
            message = HEADER_MESSAGE)
    @NotBlank(message = HEADER_MESSAGE_NOT_BLANK)
    private String header;
}