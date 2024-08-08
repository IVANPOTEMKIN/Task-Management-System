package ru.effective_mobile.task_management_system.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.effective_mobile.task_management_system.exception.CommentNotFoundException;
import ru.effective_mobile.task_management_system.exception.ForbiddenException;
import ru.effective_mobile.task_management_system.exception.task.TaskNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserIdNotFoundException;
import ru.effective_mobile.task_management_system.model.Comment;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.repository.CommentRepository;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ru.effective_mobile.task_management_system.utils.Constants.EMAIL_AUTHOR;
import static ru.effective_mobile.task_management_system.utils.Constants.EMAIL_USER;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsService.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(userRepository, taskRepository, commentRepository);
    }

    @Test
    @Order(100)
    @DisplayName(ADD_COMMENT + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_add_Comment_Successful() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(commentService.addComment(anyLong(), createOrUpdateCommentDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .save(any(Comment.class));
    }

    @Test
    @Order(101)
    @DisplayName(ADD_COMMENT + " - " + EXCEPTION_EMAIL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_UserEmailNotFoundException() {
        assertThrows(UserEmailNotFoundException.class, () ->
                commentService.addComment(anyLong(), createOrUpdateCommentDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(102)
    @DisplayName(ADD_COMMENT + " - " + EXCEPTION_ID)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_addComment_TaskNotFoundException() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));
        assertThrows(TaskNotFoundException.class, () ->
                commentService.addComment(anyLong(), createOrUpdateCommentDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(200)
    @DisplayName(GET_COMMENT_BY_ID + " - " + SUCCESSFUL)
    void test_getCommentById_Successful() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        var expected = commentDTO;
        var actual = commentService.getCommentById(anyLong());

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(commentRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(201)
    @DisplayName(GET_COMMENT_BY_ID + " - " + EXCEPTION_ID)
    void test_getCommentById_CommentNotFoundException() {
        assertThrows(CommentNotFoundException.class, () ->
                commentService.getCommentById(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(202)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + SUCCESSFUL)
    void test_getAllCommentsByTaskId_Successful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(commentRepository.findAllByTask(any(Task.class), any(Pageable.class)))
                .thenReturn(List.of(comment));

        var expected = listCommentDto;
        var actual = commentService.getAllCommentsByTaskId(anyLong(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .findAllByTask(any(Task.class), any(Pageable.class));
    }

    @Test
    @Order(203)
    @DisplayName(GET_ALL_COMMENTS_BY_TASK_ID + " - " + EXCEPTION_TASK_ID)
    void test_getAllCommentsByTaskId_TaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () ->
                commentService.getAllCommentsByTaskId(anyLong(), 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTask(any(Task.class), any(Pageable.class));
    }

    @Test
    @Order(204)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + "  - " + SUCCESSFUL)
    void test_getAllCommentsByAuthorId_Successful() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));
        when(commentRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(comment));

        var expected = listCommentDto;
        var actual = commentService.getAllCommentsByAuthorId(anyLong(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(205)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID + "  - " + EXCEPTION_AUTHOR_ID)
    void test_getAllCommentsByAuthorId_UserIdNotFoundException() {
        assertThrows(UserIdNotFoundException.class, () ->
                commentService.getAllCommentsByAuthorId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(206)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + SUCCESSFUL)
    void test_getAllCommentsByTaskIdAndAuthorId_Successful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));
        when(commentRepository.findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class)))
                .thenReturn(List.of(comment));

        var expected = listCommentDto;
        var actual = commentService.getAllCommentsByTaskIdAndAuthorId(1L, 1L, 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class));
    }

    @Test
    @Order(207)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + EXCEPTION_TASK_ID)
    void test_getAllCommentsByTaskIdAuthorId_TaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () ->
                commentService.getAllCommentsByTaskIdAndAuthorId(1L, 1L, 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class));
    }

    @Test
    @Order(208)
    @DisplayName(GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID + " - " + EXCEPTION_AUTHOR_ID)
    void test_getAllCommentsByTaskIdAuthorId_UserIdNotFoundException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        assertThrows(UserIdNotFoundException.class, () ->
                commentService.getAllCommentsByTaskIdAndAuthorId(1L, 1L, 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class));
    }

    @Test
    @Order(300)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_editTextComment_Successful() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertTrue(commentService.editTextComment(anyLong(), createOrUpdateCommentDTO));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .save(any(Comment.class));
    }

    @Test
    @Order(301)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void test_editTextComment_ForbiddenException() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertThrows(ForbiddenException.class, () ->
                commentService.editTextComment(anyLong(), createOrUpdateCommentDTO));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(302)
    @DisplayName(EDIT_TEXT_COMMENT_BY_ID + " - " + EXCEPTION_ID)
    void test_editTextComment_CommentNotFoundException() {
        assertThrows(CommentNotFoundException.class, () ->
                commentService.editTextComment(anyLong(), createOrUpdateCommentDTO));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(400)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void test_deleteComment_Successful() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertTrue(commentService.deleteComment(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .delete(any(Comment.class));
    }

    @Test
    @Order(401)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void test_deleteComment_ForbiddenException() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertThrows(ForbiddenException.class, () ->
                commentService.deleteComment(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .delete(any(Comment.class));
    }

    @Test
    @Order(402)
    @DisplayName(DELETE_COMMENT_BY_ID + " - " + EXCEPTION_ID)
    void test_deleteComment_CommentNotFoundException() {
        assertThrows(CommentNotFoundException.class, () ->
                commentService.deleteComment(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .delete(any(Comment.class));
    }
}