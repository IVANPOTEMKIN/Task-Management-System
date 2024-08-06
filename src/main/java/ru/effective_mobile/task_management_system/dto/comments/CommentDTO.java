package ru.effective_mobile.task_management_system.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.effective_mobile.task_management_system.dto.utils.Utils.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = COMMENT_DTO)
public class CommentDTO {

    @Schema(description = AUTHOR, example = EXAMPLE_FULL_NAME)
    private String author;

    @Schema(description = TEXT, example = TEXT)
    private String text;

    @Schema(description = DATE_AND_TIME, example = EXAMPLE_DATE_AND_TIME)
    private LocalDateTime createdAt;
}