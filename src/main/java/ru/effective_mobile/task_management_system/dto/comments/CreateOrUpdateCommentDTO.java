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

    @Schema(description = "Текст комментария")
    @Size(min = 10, max = 100,
            message = "Описание задачи должно быть от 10 до 100 символов")
    @NotBlank(message = "Комментария не может быть пустым")
    private String text;
}