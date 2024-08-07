package ru.effective_mobile.task_management_system.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;
import ru.effective_mobile.task_management_system.exception.ForbiddenException;
import ru.effective_mobile.task_management_system.exception.task.TaskNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserFullNameNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserIdNotFoundException;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.HIGH;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.MEDIUM;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IN_PROGRESS;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IS_PENDING;
import static ru.effective_mobile.task_management_system.utils.Constants.*;
import static ru.effective_mobile.task_management_system.utils.Naming.*;
import static ru.effective_mobile.task_management_system.utils.UtilsService.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(userRepository, taskRepository);
    }

    @Test
    @Order(100)
    @DisplayName(ADD_TASK + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void addTaskSuccessful() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        assertTrue(taskService.addTask(createTaskDTO));

        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(1))
                .findAll();
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(200)
    @DisplayName(GET_TASK_BY_ID + " - " + SUCCESSFUL)
    void getTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        var expected = taskDTO;
        var actual = taskService.getTaskById(anyLong());

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(201)
    @DisplayName(GET_TASK_BY_ID + " - " + EXCEPTION_ID)
    void getTaskByIdException() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.getTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(202)
    @DisplayName(GET_ALL_TASKS + " - " + SUCCESSFUL)
    void getAllTasksSuccessful() {
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        var expected = listTaskDTO;
        var actual = taskService.getAllTasks(1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findAll(any(Pageable.class));
    }

    @Test
    @Order(203)
    @DisplayName(GET_ALL_TASKS_BY_HEADER + " - " + SUCCESSFUL)
    void getAllTasksByHeaderSuccessful() {
        when(taskRepository.findAllByHeaderContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByHeader(HEADER, 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findAllByHeaderContainingIgnoreCase(anyString(), any(Pageable.class));

    }

    @Test
    @Order(204)
    @DisplayName(GET_ALL_TASKS_BY_STATUS + " - " + SUCCESSFUL)
    void getAllTasksByStatusSuccessful() {
        when(taskRepository.findAllByStatus(any(StatusTask.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByStatus(IS_PENDING, 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findAllByStatus(any(StatusTask.class), any(Pageable.class));
    }

    @Test
    @Order(205)
    @DisplayName(GET_ALL_TASKS_BY_PRIORITY + " - " + SUCCESSFUL)
    void getAllTasksByPrioritySuccessful() {
        when(taskRepository.findAllByPriority(any(PriorityTask.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByPriority(MEDIUM, 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findAllByPriority(any(PriorityTask.class), any(Pageable.class));
    }

    @Test
    @Order(206)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + SUCCESSFUL)
    void getAllTasksByAuthorIdSuccessful() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByAuthorId(anyLong(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(207)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_ID + " - " + EXCEPTION_ID)
    void getAllTasksByAuthorIdException() {
        assertThrows(UserIdNotFoundException.class, () ->
                taskService.getAllTasksByAuthorId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(208)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + SUCCESSFUL)
    void getAllTasksByPerformerIdSuccessful() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(performer));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByPerformerId(anyLong(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(209)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_ID + " - " + EXCEPTION_ID)
    void getAllTasksByPerformerIdException() {
        assertThrows(UserIdNotFoundException.class, () ->
                taskService.getAllTasksByPerformerId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(210)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_EMAIL + " - " + SUCCESSFUL)
    void getAllTasksByAuthorEmailSuccessful() {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByAuthorEmail(anyString(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(1))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(211)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_EMAIL + " - " + EXCEPTION_EMAIL)
    void getAllTasksByAuthorEmailException() {
        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.getAllTasksByAuthorEmail(anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(212)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_EMAIL + " - " + SUCCESSFUL)
    void getAllTasksByPerformerEmailSuccessful() {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByPerformerEmail(anyString(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(1))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(213)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_EMAIL + " - " + EXCEPTION_EMAIL)
    void getAllTasksByPerformerEmailException() {
        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.getAllTasksByPerformerEmail(anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(214)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_FULL_NAME + " - " + SUCCESSFUL)
    void getAllTasksByAuthorFullNameSuccessful() {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthor(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByAuthorFullName(anyString(), anyString(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(1))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(215)
    @DisplayName(GET_ALL_TASKS_BY_AUTHOR_FULL_NAME + " - " + EXCEPTION_NAME)
    void getAllTasksByAuthorFullNameException() {
        assertThrows(UserFullNameNotFoundException.class, () ->
                taskService.getAllTasksByAuthorFullName(anyString(), anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(216)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_FULL_NAME + " - " + SUCCESSFUL)
    void getAllTasksByPerformerFullNameSuccessful() {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(author));
        when(taskRepository.findAllByPerformer(any(User.class), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByPerformerFullName(anyString(), anyString(), 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(1))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(217)
    @DisplayName(GET_ALL_TASKS_BY_PERFORMER_FULL_NAME + " - " + EXCEPTION_NAME)
    void getAllTasksByPerformerFullNameException() {
        assertThrows(UserFullNameNotFoundException.class, () ->
                taskService.getAllTasksByPerformerFullName(anyString(), anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(300)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editHeaderTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editHeaderTaskById(anyLong(), updateHeaderTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(301)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editHeaderTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editHeaderTaskById(anyLong(), updateHeaderTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(302)
    @DisplayName(EDIT_HEADER_TASK_BY_ID + " - " + EXCEPTION_ID)
    void editHeaderTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.editHeaderTaskById(anyLong(), updateHeaderTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(400)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editDescriptionTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editDescriptionTaskById(anyLong(), updateDescriptionTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(401)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editDescriptionTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editDescriptionTaskById(anyLong(), updateDescriptionTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(402)
    @DisplayName(EDIT_DESCRIPTION_TASK_BY_ID + " - " + EXCEPTION_ID)
    void editDescriptionTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.editDescriptionTaskById(anyLong(), updateDescriptionTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(700)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPerformerTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        assertTrue(taskService.editPerformerTaskById(anyLong(), updatePerformerTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(701)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editPerformerTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editPerformerTaskById(anyLong(), updatePerformerTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(702)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + EXCEPTION_TASK_ID)
    void editPerformerTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.editPerformerTaskById(anyLong(), updatePerformerTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(703)
    @DisplayName(EDIT_PERFORMER_TASK_BY_ID + " - " + EXCEPTION_AUTHOR_ID)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPerformerTaskByIdExceptionUser() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.editPerformerTaskById(anyLong(), updatePerformerTaskDTO));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(600)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editPriorityTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editPriorityTaskById(anyLong(), HIGH));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(601)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editPriorityTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editPriorityTaskById(anyLong(), HIGH));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(602)
    @DisplayName(EDIT_PRIORITY_TASK_BY_ID + " - " + EXCEPTION_ID)
    void editPriorityTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.editPriorityTaskById(anyLong(), HIGH));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(500)
    @DisplayName(EDIT_STATUS_TASK_BY_ID_AUTHOR + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void editStatusTaskByIdSuccessfulAuthor() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editStatusTaskById(anyLong(), IN_PROGRESS));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(501)
    @DisplayName(EDIT_STATUS_TASK_BY_ID_PERFORMER + " - " + SUCCESSFUL)
    @WithMockUser(username = "performer@gmail.com", authorities = "USER")
    void editStatusTaskByIdSuccessfulPerformer() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editStatusTaskById(anyLong(), IN_PROGRESS));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(502)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void editStatusTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editStatusTaskById(anyLong(), IN_PROGRESS));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(503)
    @DisplayName(EDIT_STATUS_TASK_BY_ID + " - " + EXCEPTION_ID)
    void editStatusTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.editStatusTaskById(anyLong(), IN_PROGRESS));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(800)
    @DisplayName(DELETE_TASK_BY_ID + " - " + SUCCESSFUL)
    @WithMockUser(username = EMAIL_AUTHOR, authorities = "USER")
    void deleteTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.deleteTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .delete(any(Task.class));
    }

    @Test
    @Order(801)
    @DisplayName(DELETE_TASK_BY_ID + " - " + EXCEPTION_FORBIDDEN)
    @WithMockUser(username = EMAIL_USER, authorities = "USER")
    void deleteTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.deleteTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .delete(any(Task.class));
    }

    @Test
    @Order(802)
    @DisplayName(DELETE_TASK_BY_ID + " - " + EXCEPTION_ID)
    void deleteTaskByIdExceptionTask() {
        assertThrows(TaskNotFoundException.class, () ->
                taskService.deleteTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .delete(any(Task.class));
    }
}