package ru.effective_mobile.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class RegisterDTO {

    @Schema(description = "Имя пользователя", example = "Иван")
    @Pattern(regexp = "^[а-яёА-ЯЁa-zA-Z]{2,16}$",
            message = """
                    Имя пользователя должно быть от 2 до 16 символов
                    Имя пользователя должно содержать только буквы""")
    @NotBlank(message = "Имя не может быть пустым")
    private String username;

    @Schema(description = "Email", example = "ivan@gmail.com")
    @Pattern(regexp = "^([a-zA-Z0-9]+)([a-zA-Z0-9.]*)@([a-zA-Z]+)\\.([a-zA-Z]{2,6})$")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Schema(description = "Пароль", example = "QwErTy.1234")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&=+.])(?=\\S+$).{8,}$",
            message = """
                    Пароль должен быть не менее 8 символов
                    Пароль должен содержать минимум одну прописную букву
                    Пароль должен содержать минимум одну заглавную букву
                    Пароль должен содержать минимум один специальный знак - @#$%^&=+.""")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}