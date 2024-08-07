package ru.effective_mobile.task_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.effective_mobile.task_management_system.enums.StatusTask;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.repository.CommentRepository;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.AuthServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.CommentServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.JwtServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.effective_mobile.task_management_system.utils.Constants.*;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsController.*;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskControllerTest {

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
    @SpyBean
    private JwtServiceImpl jwtService;
    @SpyBean
    private AuthServiceImpl authService;
    @SpyBean
    private TaskServiceImpl taskService;
    @SpyBean
    private CommentServiceImpl commentService;
    @InjectMocks
    private TaskController taskController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(100)
    @DisplayName(ADD_TASK + " - " + STATUS_201)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void addTaskStatus201() throws Exception {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/add")
                        .content(objectMapper.writeValueAsString(createTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(101)
    @DisplayName(ADD_TASK + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void addTaskStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/add")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(102)
    @DisplayName(ADD_TASK + " - " + STATUS_401)
    void addTaskStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/add")
                        .content(objectMapper.writeValueAsString(createTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(103)
    @DisplayName(ADD_TASK + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void addTaskStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/add")
                        .content(objectMapper.writeValueAsString(createTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(200)
    @DisplayName(GET_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        var expected = taskDTO;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header").value(expected.getHeader()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().toString()))
                .andExpect(jsonPath("$.priority").value(expected.getPriority().toString()))
                .andExpect(jsonPath("$.author").value(expected.getAuthor()))
                .andExpect(jsonPath("$.authorEmail").value(expected.getAuthorEmail()))
                .andExpect(jsonPath("$.performer").value(expected.getPerformer()))
                .andExpect(jsonPath("$.performerEmail").value(expected.getPerformerEmail()));
    }

    @Test
    @Order(201)
    @DisplayName(GET_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getTaskByIdStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(202)
    @DisplayName(GET_TASK_BY_ID + " - " + STATUS_401)
    void getTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(203)
    @DisplayName(GET_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(204)
    @DisplayName(GET_ALL_TASKS + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksStatus200() throws Exception {
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all?offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(205)
    @DisplayName(GET_ALL_TASKS + " - " + STATUS_401)
    void getAllTasksStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all?offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(206)
    @DisplayName(GET_ALL_TASKS_BY_HEADER + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByHeaderStatus200() throws Exception {
        when(taskRepository.findAllByHeaderContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/header?Заголовок=Заголовок&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(207)
    @DisplayName(GET_ALL_TASKS_BY_HEADER + " - " + STATUS_401)
    void getAllTasksByHeaderStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/header?Заголовок=Заголовок&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(208)
    @DisplayName(GET_ALL_TASKS_BY_STATUS + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByStatusStatus200() throws Exception {
        when(taskRepository.findAllByStatus(any(StatusTask.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/status?Статус=IS_PENDING&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(209)
    @DisplayName(GET_ALL_TASKS_BY_STATUS + " - " + STATUS_401)
    void getAllTasksByStatusStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/status?Статус=IS_PENDING&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(210)
    @DisplayName(GET_ALL_TASKS_BY_PRIORITY + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPriorityStatus200() throws Exception {
        when(taskRepository.findAllByStatus(any(StatusTask.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/priority?Приоритет=MEDIUM&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(211)
    @DisplayName(GET_ALL_TASKS_BY_PRIORITY + " - " + STATUS_401)
    void getAllTasksByPriorityStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/priority?Приоритет=MEDIUM&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(212)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorIDStatus200() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/1?offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(213)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorIDStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/0?offset=1&limit=1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(214)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + STATUS_401)
    void getAllTasksByAuthorIDStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/1?offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(215)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorIDStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/1?offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(216)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerIDStatus200() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(performer));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/1?offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(217)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerIDStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/0?offset=1&limit=1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(218)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + STATUS_401)
    void getAllTasksByPerformerIDStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/1?offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(219)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerIDStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/1?offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(220)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_EMAIL + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorEmailStatus200() throws Exception {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/email?Email=author@gmail.com&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(221)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_EMAIL + " - " + STATUS_401)
    void getAllTasksByAuthorEmailStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/email?Email=author@gmail.com&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(222)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_EMAIL + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorEmailStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/email?Email=author@gmail.com&offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(223)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_EMAIL + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerEmailStatus200() throws Exception {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(performer));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/email?Email=performer@gmail.com&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(224)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_EMAIL + " - " + STATUS_401)
    void getAllTasksByPerformerEmailStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/email?Email=performer@gmail.com&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(225)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_EMAIL + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerEmailStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/email?Email=performer@gmail.com&offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(226)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_FULL_NAME + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorFullNameStatus200() throws Exception {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(227)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_FULL_NAME + " - " + STATUS_401)
    void getAllTasksByAuthorFullNameStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(228)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_FULL_NAME + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByAuthorFullNameStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/author/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(229)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_FULL_NAME + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerFullNameStatus200() throws Exception {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(performer));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(230)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_FULL_NAME + " - " + STATUS_401)
    void getAllTasksByPerformerFullNameStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(231)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_FULL_NAME + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void getAllTasksByPerformerFullNameStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/get/all/performer/fullName?Имя=Иван&Фамилия=Иванов&offset=1&limit=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(300)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editHeaderTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/header")
                        .content(objectMapper.writeValueAsString(updateHeaderTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(301)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editHeaderTaskByIdStatus400() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/header")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(302)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + STATUS_401)
    void editHeaderTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/header")
                        .content(objectMapper.writeValueAsString(updateHeaderTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(303)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editHeaderTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/header")
                        .content(objectMapper.writeValueAsString(updateHeaderTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(304)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editHeaderTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/header")
                        .content(objectMapper.writeValueAsString(updateHeaderTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(400)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editDescriptionTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/description")
                        .content(objectMapper.writeValueAsString(updateDescriptionTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(401)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editDescriptionTaskByIdStatus400() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/description")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(402)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + STATUS_401)
    void editDescriptionTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/description")
                        .content(objectMapper.writeValueAsString(updateDescriptionTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(403)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editDescriptionTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/description")
                        .content(objectMapper.writeValueAsString(updateDescriptionTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(404)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editDescriptionTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/description")
                        .content(objectMapper.writeValueAsString(updateDescriptionTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(500)
    @DisplayName(EDIT_STATUS_TASK_BY_ID_AUTHOR + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editStatusTaskByIdAuthorStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/status?Статус=IN_PROGRESS")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(501)
    @DisplayName(EDIT_STATUS_TASK_BY_ID_PERFORMER + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_PERFORMER, authorities = "USER")
    void editStatusTaskByIdPerformerStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/status?Статус=COMPLETED")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(502)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editStatusTaskByIdStatus400() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/0/status?Статус=IN_PROGRESS")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(503)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + STATUS_401)
    void editStatusTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/status?Статус=IN_PROGRESS")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(504)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editStatusTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/status?Статус=IN_PROGRESS")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(505)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editStatusTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/status?Статус=IN_PROGRESS")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(600)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPriorityTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/priority?Приоритет=HIGH")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(601)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPriorityTaskByIdStatus400() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/0/priority?Приоритет=HIGH")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(602)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + STATUS_401)
    void editPriorityTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/priority?Приоритет=HIGH")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(603)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editPriorityTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/priority?Приоритет=HIGH")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(604)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPriorityTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/priority?Приоритет=HIGH")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(700)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPerformerTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/performer")
                        .content(objectMapper.writeValueAsString(updatePerformerTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(701)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPerformerTaskByIdStatus400() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/performer")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(702)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + STATUS_401)
    void editPerformerTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/performer")
                        .content(objectMapper.writeValueAsString(updatePerformerTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(703)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editPerformerTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/performer")
                        .content(objectMapper.writeValueAsString(updatePerformerTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(704)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPerformerTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/edit/1/performer")
                        .content(objectMapper.writeValueAsString(updatePerformerTaskDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(800)
    @DisplayName(DELETE_TASK_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void deleteTaskByIdStatus200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/delete/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(801)
    @DisplayName(DELETE_TASK_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void deleteTaskByIdStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/delete/0")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(802)
    @DisplayName(DELETE_TASK_BY_ID + " - " + STATUS_401)
    void deleteTaskByIdStatus401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/delete/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(803)
    @DisplayName(DELETE_TASK_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void deleteTaskByIdStatus403() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/delete/1")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(804)
    @DisplayName(DELETE_TASK_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void deleteTaskByIdStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/delete/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}