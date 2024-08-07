package ru.effective_mobile.task_management_system.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
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
    @DisplayName(value = "Добавление нового комментария - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void addCommentSuccessful() {
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
    @DisplayName(value = "Добавление нового комментария - ошибка (Указан несуществующий email автора)")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void addCommentExceptionAuthor() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

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
    @DisplayName(value = "Добавление нового комментария - ошибка (Указан несуществующий ID задачи)")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void addCommentExceptionTask() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

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
    @DisplayName(value = "Получение комментария по ID - успешно")
    void getCommentByIdSuccessful() {
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
    @DisplayName(value = "Получение комментария по ID - ошибка (Указан несуществующий ID)")
    void getCommentByIdException() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () ->
                commentService.getCommentById(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(202)
    @DisplayName(value = "Получение всех комментариев по ID задачи - успешно")
    void getAllCommentsByTaskIdSuccessful() {
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
    @DisplayName(value = "Получение всех комментариев по ID задачи - ошибка (Указан несуществующий ID задачи)")
    void getAllCommentsByTaskIdException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                commentService.getAllCommentsByTaskId(anyLong(), 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTask(any(Task.class), any(Pageable.class));
    }

    @Test
    @Order(204)
    @DisplayName(value = "Получение всех комментариев по ID автора - успешно")
    void getAllCommentsByAuthorIdSuccessful() {
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
    @DisplayName(value = "Получение всех комментариев по ID автора - ошибка (Указан несуществующий ID автора)")
    void getAllCommentsByAuthorIdException() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () ->
                commentService.getAllCommentsByAuthorId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(206)
    @DisplayName(value = "Получение всех комментариев по ID задачи и ID автора - успешно")
    void getAllCommentsByTaskIdAuthorIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));
        when(commentRepository.findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class)))
                .thenReturn(List.of(comment));

        var expected = listCommentDto;
        var actual = commentService.getAllCommentsByTaskIdAuthorId(1L, 1L, 1, 1);

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
    @DisplayName(value = "Получение всех комментариев по ID задачи и ID автора - ошибка (Указан несуществующий ID задачи)")
    void getAllCommentsByTaskIdAuthorIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                commentService.getAllCommentsByTaskIdAuthorId(1L, 1L, 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class));
    }

    @Test
    @Order(208)
    @DisplayName(value = "Получение всех комментариев по ID задачи и ID автора - ошибка (Указан несуществующий ID автора)")
    void getAllCommentsByTaskIdAuthorIdExceptionAuthor() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () ->
                commentService.getAllCommentsByTaskIdAuthorId(1L, 1L, 1, 1));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .findAllByTaskAndAuthor(any(Task.class), any(User.class), any(Pageable.class));
    }

    @Test
    @Order(300)
    @DisplayName(value = "Изменение текста комментария по ID - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void editTextCommentSuccessful() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertTrue(commentService.editTextComment(anyLong(),
                new CreateOrUpdateCommentDTO("Новый текст комментария")));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(1))
                .save(any(Comment.class));
    }

    @Test
    @Order(301)
    @DisplayName(value = "Изменение текста комментария по ID - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
    void editTextCommentForbiddenException() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        assertThrows(ForbiddenException.class, () ->
                commentService.editTextComment(anyLong(), new CreateOrUpdateCommentDTO("Новый текст комментария")));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(302)
    @DisplayName(value = "Изменение текста комментария по ID - ошибка (Указан несуществующий ID)")
    void editTextCommentExceptionComment() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () ->
                commentService.editTextComment(anyLong(), new CreateOrUpdateCommentDTO("Новый текст комментария")));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .save(any(Comment.class));
    }

    @Test
    @Order(400)
    @DisplayName(value = "Удаление комментария по ID - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void deleteCommentSuccessful() {
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
    @DisplayName(value = "Удаление комментария по ID - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
    void deleteCommentForbiddenException() {
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
    @DisplayName(value = "Удаление комментария по ID - ошибка (Указан несуществующий ID)")
    void deleteCommentExceptionComment() {
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () ->
                commentService.deleteComment(anyLong()));

        verify(commentRepository, times(1))
                .findById(anyLong());
        verify(commentRepository, times(0))
                .delete(any(Comment.class));
    }
}