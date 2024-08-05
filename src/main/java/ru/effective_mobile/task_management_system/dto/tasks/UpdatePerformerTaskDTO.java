package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.effective_mobile.task_management_system.dto.utils.Utils.PATTERN_EMAIL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение исполнителя задачи")
public class UpdatePerformerTaskDTO {

    @Schema(description = "Email исполнителя задачи")
    @Pattern(regexp = PATTERN_EMAIL)
    @NotBlank(message = "Email исполнителя задачи не может быть пустым")
    private String email;
}