package ru.effective_mobile.task_management_system.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @Schema(name = "Автор комментария")
    private String author;

    @Schema(name = "Текст комментария")
    private String text;

    @Schema(name = "Дата и время создания комментария")
    private LocalDateTime createdAt;
}