package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.security.JwtDTO;
import ru.effective_mobile.task_management_system.dto.security.LoginDTO;
import ru.effective_mobile.task_management_system.dto.security.RegisterDTO;

public interface AuthService {

    /**
     * Регистрация пользователя
     *
     * @param dto данные пользователя
     * @return токен
     */
    JwtDTO register(@Valid RegisterDTO dto);

    /**
     * Аутентификация пользователя
     *
     * @param dto данные пользователя
     * @return токен
     */
    JwtDTO login(@Valid LoginDTO dto);
}