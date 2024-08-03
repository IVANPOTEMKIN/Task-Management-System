package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.JwtDTO;
import ru.effective_mobile.task_management_system.dto.LoginDTO;
import ru.effective_mobile.task_management_system.dto.RegisterDTO;

public interface AuthService {

    /**
     * Регистрация пользователя
     *
     * @param registerDTO данные пользователя
     * @return токен
     */
    JwtDTO register(@Valid RegisterDTO registerDTO);

    /**
     * Аутентификация пользователя
     *
     * @param loginDTO данные пользователя
     * @return токен
     */
    JwtDTO login(@Valid LoginDTO loginDTO);
}