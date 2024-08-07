package ru.effective_mobile.task_management_system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.effective_mobile.task_management_system.dto.security.JwtDTO;
import ru.effective_mobile.task_management_system.exception.EmailAlreadyUseException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.model.UserDetailsImpl;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.effective_mobile.task_management_system.utils.Constants.TOKEN;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsService.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName(REGISTER_SUCCESSFUL)
    void testRegisterSuccessful() {
        when(jwtService.generateToken(any(UserDetailsImpl.class)))
                .thenReturn(TOKEN);

        var expected = new JwtDTO(TOKEN);
        var actual = authService.register(registerDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(passwordEncoder, times(1))
                .encode(anyString());
        verify(userRepository, times(1))
                .save(any(User.class));
        verify(jwtService, timeout(1))
                .generateToken(any(UserDetailsImpl.class));
    }

    @Test
    @DisplayName(REGISTER_EXCEPTION)
    void testRegisterException() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        assertThrows(EmailAlreadyUseException.class, () ->
                authService.register(registerDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(passwordEncoder, times(0))
                .encode(anyString());
        verify(userRepository, times(0))
                .save(any(User.class));
    }

    @Test
    @DisplayName(LOGIN_SUCCESSFUL)
    void testLoginSuccessful() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));
        when(jwtService.generateToken(any(UserDetailsImpl.class)))
                .thenReturn(TOKEN);

        var expected = new JwtDTO(TOKEN);
        var actual = authService.login(loginDTO);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(jwtService, timeout(1))
                .generateToken(any(UserDetailsImpl.class));
    }

    @Test
    @DisplayName(LOGIN_EXCEPTION)
    void testLoginException() {
        assertThrows(UserEmailNotFoundException.class, () ->
                authService.login(loginDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
    }
}