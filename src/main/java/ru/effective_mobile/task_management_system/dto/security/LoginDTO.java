package ru.effective_mobile.task_management_system.dto.security;

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
@Schema(description = LOGIN_DTO)
public class LoginDTO {

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

    @Schema(description = PASSWORD, example = EXAMPLE_PASSWORD)
    @Pattern.List({
            @Pattern(regexp = PATTERN_PASSWORD,
                    message = PASSWORD_MESSAGE_1),
            @Pattern(regexp = PATTERN_PASSWORD,
                    message = PASSWORD_MESSAGE_2),
            @Pattern(regexp = PATTERN_PASSWORD,
                    message = PASSWORD_MESSAGE_3),
            @Pattern(regexp = PATTERN_PASSWORD,
                    message = PASSWORD_MESSAGE_4)})
    @NotBlank(message = PASSWORD_MESSAGE_NOT_BLANK)
    private String password;
}