package ru.effective_mobile.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Schema(name = "Email")
    private String email;

    @Schema(name = "Пароль")
    @Size(min = 8,
            message = "Пароль должен быть не менее 8 символов")
    private String password;
}