package ru.effective_mobile.task_management_system.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с комментарием")
public class CommentDTO {

    @Schema(description = "Имя автора комментария", example = "Иван Иванов")
    private String author;

    @Schema(description = "Текст комментария", example = "Текст комментария")
    private String text;

    @Schema(description = "Дата и время создания комментария", example = "2024-08-05T22:19:33")
    private LocalDateTime createdAt;
}