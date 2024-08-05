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
@Schema(description = "Запрос на аутентификацию")
public class LoginDTO {

    @Schema(description = "Email", example = "user@gmail.com")
    @Pattern(regexp = PATTERN_EMAIL)
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Schema(description = "Пароль", example = "QwErTy.1234")
    @Pattern(regexp = PATTERN_PASSWORD,
            message = PASSWORD_MESSAGE)
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}