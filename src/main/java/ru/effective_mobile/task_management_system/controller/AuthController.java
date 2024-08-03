package ru.effective_mobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.task_management_system.dto.JwtDTO;
import ru.effective_mobile.task_management_system.dto.LoginDTO;
import ru.effective_mobile.task_management_system.dto.RegisterDTO;
import ru.effective_mobile.task_management_system.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public JwtDTO register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public JwtDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}