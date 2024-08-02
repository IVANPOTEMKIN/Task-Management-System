package ru.effective_mobile.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @Schema(name = "Имя пользователя")
    @Pattern(regexp = "^[а-яёА-ЯЁa-zA-Z]{2,16}$",
            message = """
                    Имя пользователя должно быть от 2 до 16 символов
                    Имя пользователя должно содержать только буквы""")
    private String username;

    @Schema(name = "Email")
    @Pattern(regexp = "^([a-zA-Z0-9]+)([.]*)@([a-zA-Z]+)\\.([a-zA-Z]{2,6})$")
    private String email;

    @Schema(name = "Пароль")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = """
                    Пароль должен быть не менее 8 символов
                    Пароль должен содержать минимум одну прописную букву
                    Пароль должен содержать минимум одну заглавную букву
                    Пароль должен содержать минимум один специальный знак - @#$%^&=+.""")
    private String password;
}