package ru.effective_mobile.task_management_system.dto.comments;

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
@Schema(description = "Запрос на создание/изменение текста комментария")
public class CreateOrUpdateCommentDTO {

    @Schema(description = "Текст комментария", example = "Текст комментария")
    @Size(min = 10, max = 100,
            message = "Текст комментария должен быть от 10 до 100 символов")
    @NotBlank(message = "Текст комментария не может быть пустым")
    private String text;
}