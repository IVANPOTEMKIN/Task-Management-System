package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.effective_mobile.task_management_system.dto.utils.Utils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = UPDATE_PERFORMER_TASK_DTO)
public class UpdatePerformerTaskDTO {

    @Schema(description = EMAIL, example = EXAMPLE_EMAIL)
    @Pattern.List({
            @Pattern(regexp = PATTERN_EMAIL,
                    message = EMAIL_MESSAGE_1),
            @Pattern(regexp = PATTERN_EMAIL,
                    message = EMAIL_MESSAGE_2),
            @Pattern(regexp = PATTERN_EMAIL,
                    message = EMAIL_MESSAGE_3)})
    @NotBlank(message = EMAIL_MESSAGE_NOT_BLANK)
    private String email;
}