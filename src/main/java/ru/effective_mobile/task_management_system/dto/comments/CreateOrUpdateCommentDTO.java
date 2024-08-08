package ru.effective_mobile.task_management_system.dto.comments;

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
@Schema(description = CREATE_OR_UPDATE_COMMENT_DTO)
public class CreateOrUpdateCommentDTO {

    @Schema(description = TEXT, example = TEXT)
    @Size(min = 10, max = 100,
            message = TEXT_MESSAGE)
    @NotBlank(message = TEXT_MESSAGE_NOT_BLANK)
    private String text;
}