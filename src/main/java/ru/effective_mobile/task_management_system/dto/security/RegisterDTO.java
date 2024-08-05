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
@Schema(description = "Запрос на регистрацию")
public class RegisterDTO {

    @Schema(description = "Имя пользователя")
    @Pattern(regexp = PATTERN_NAME,
            message = FIRST_NAME_MESSAGE)
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String firstName;

    @Schema(description = "Фамилия пользователя")
    @Pattern(regexp = PATTERN_NAME,
            message = LAST_NAME_MESSAGE)
    @NotBlank(message = "Фамилия пользователя не может быть пустым")
    private String lastName;

    @Schema(description = "Email")
    @Pattern(regexp = PATTERN_EMAIL)
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Schema(description = "Пароль")
    @Pattern(regexp = PATTERN_PASSWORD,
            message = PASSWORD_MESSAGE)
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}