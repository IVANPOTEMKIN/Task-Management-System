package ru.effective_mobile.task_management_system.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение заголовка задачи")
public class UpdateHeaderTaskDTO {

    @Schema(description = "Заголовок задачи")
    @Size(min = 10, max = 30,
            message = "Заголовок задачи должен быть от 10 до 30 символов")
    @NotBlank(message = "Заголовок задачи не может быть пустым")
    private String header;
}