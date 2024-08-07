package ru.effective_mobile.task_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.effective_mobile.task_management_system.config.SecurityConfig;
import ru.effective_mobile.task_management_system.repository.CommentRepository;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.AuthServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.CommentServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.JwtServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.TaskServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsController.*;

@WebMvcTest
@Import(SecurityConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private AuthenticationProvider authenticationProvider;
    @SpyBean
    private JwtServiceImpl jwtService;
    @SpyBean
    private AuthServiceImpl authService;
    @SpyBean
    private TaskServiceImpl taskService;
    @SpyBean
    private CommentServiceImpl commentService;
    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    @DisplayName(REGISTER + " - " + STATUS_201)
    void test_register_Status201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(registerDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName(REGISTER + " - " + STATUS_400)
    void test_register_Status400_EmailAlreadyUseException() throws Exception {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(registerDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName(REGISTER + " - " + STATUS_400)
    void test_register_Status400_RequestBodyValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName(LOGIN + " - " + STATUS_200)
    void test_login_Status200() throws Exception {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @DisplayName(LOGIN + " - " + STATUS_400)
    void test_login_Status400_RequestBodyValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    @DisplayName(LOGIN + " - " + STATUS_404)
    void test_login_Status404_UserEmailNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}