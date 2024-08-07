package ru.effective_mobile.task_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.effective_mobile.task_management_system.repository.CommentRepository;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.AuthServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.CommentServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.JwtServiceImpl;
import ru.effective_mobile.task_management_system.service.impl.TaskServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.effective_mobile.task_management_system.utils.Constants.EMAIL_AUTHOR;
import static ru.effective_mobile.task_management_system.utils.Constants.EMAIL_USER;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsController.*;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerTest {

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
    private CommentController commentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(100)
    @DisplayName(ADD_COMMENT + " - " + STATUS_201)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_Status201() throws Exception {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(101)
    @DisplayName(ADD_COMMENT + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 0)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(102)
    @DisplayName(ADD_COMMENT + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_Status400_RequestBodyValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 1)
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(103)
    @DisplayName(ADD_COMMENT + " - " + STATUS_401)
    void test_addComment_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(104)
    @DisplayName(ADD_COMMENT + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_Status404_TaskNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(105)
    @DisplayName(ADD_COMMENT + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_Status404_UserEmailNotFoundException() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/task/comment/add/taskId/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(200)
    @DisplayName(GET_COMMENT_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getCommentById_Status200() throws Exception {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        var expected = commentDTO;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(expected.getText()))
                .andExpect(jsonPath("$.author").value(expected.getAuthor()));
    }

    @Test
    @Order(201)
    @DisplayName(GET_COMMENT_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getCommentById_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/{id}", 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(202)
    @DisplayName(GET_COMMENT_BY_ID + " - " + STATUS_401)
    void test_getCommentById_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(203)
    @DisplayName(GET_COMMENT_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getCommentById_Status404_CommentNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(204)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskId_Status200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @Order(205)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskId_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{id}", 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(206)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + STATUS_401)
    void test_getAllCommentsByTaskId_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(207)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskId_Status404_TaskNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(208)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByAuthorId_Status200() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/authorId/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @Order(209)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByAuthorId_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/authorId/{id}", 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(210)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + " - " + STATUS_401)
    void test_getAllCommentsByAuthorId_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/authorId/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(211)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByAuthorId_Status404_UserIdNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/authorId/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(212)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskIdAndAuthorId_Status200() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{taskId}/authorId/{authorId}", 1, 1))
                .andExpect(status().isOk());
    }

    @Test
    @Order(213)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskIdAndAuthorId_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{taskId}/authorId/{authorId}", 0, 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(214)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + STATUS_401)
    void test_getAllCommentsByTaskIdAndAuthorId_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{taskId}/authorId/{authorId}", 1, 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(215)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskIdAndAuthorId_Status404_TaskNotFoundException() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{taskId}/authorId/{authorId}", 1, 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(216)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_getAllCommentsByTaskIdAndAuthorId_Status404_UserIdNotFoundException() throws Exception {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/task/comment/get/all/taskId/{taskId}/authorId/{authorId}", 1, 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(300)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_editTextCommentById_Status200() throws Exception {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(301)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_editTextCommentById_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 0)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(302)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_editTextCommentById_Status400_RequestBodyValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 1)
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(303)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_401)
    void test_editTextCommentById_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(304)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void test_editTextCommentById_Status403_ForbiddenException() throws Exception {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(305)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_editTextCommentById_Status404_CommentNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/task/comment/edit/{id}", 1)
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDTO))
                        .contentType(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(400)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + STATUS_200)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_deleteCommentById_Status200() throws Exception {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/comment/delete/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(401)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + STATUS_400)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_deleteCommentById_Status400_IdValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/comment/delete/{id}", 0)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(402)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + STATUS_401)
    void test_deleteCommentById_Status401_UnauthorizedException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/comment/delete/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(403)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + STATUS_403)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void test_deleteCommentById_Status403_ForbiddenException() throws Exception {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/comment/delete/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(404)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + STATUS_404)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_deleteCommentById_Status404_CommentNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/task/comment/delete/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}