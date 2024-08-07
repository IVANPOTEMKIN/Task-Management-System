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
import ru.effective_mobile.task_management_system.dto.tasks.UpdateDescriptionTaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.UpdateHeaderTaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.UpdatePerformerTaskDTO;
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
    @DisplayName(value = "Добавление новой задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Получение задачи по ID - успешно")
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
    @DisplayName(value = "Получение задачи по ID - ошибка (Указан несуществующий ID)")
    void getTaskByIdException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.getTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(202)
    @DisplayName(value = "Получение всех задач - успешно")
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
    @DisplayName(value = "Получение всех задач по заголовку - успешно")
    void getAllTasksByHeaderSuccessful() {
        when(taskRepository.findAllByHeaderContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(List.of(task));

        var expected = listTaskDTO;
        var actual = taskService.getAllTasksByHeader("Заголовок", 1, 1);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(taskRepository, times(1))
                .findAllByHeaderContainingIgnoreCase(anyString(), any(Pageable.class));

    }

    @Test
    @Order(204)
    @DisplayName(value = "Получение всех задач по статусу - успешно")
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
    @DisplayName(value = "Получение всех задач по приоритету - успешно")
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
    @DisplayName(value = "Получение всех задач по ID автора - успешно")
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
    @DisplayName(value = "Получение всех задач по ID автора - ошибка (Указан несуществующий ID)")
    void getAllTasksByAuthorIdException() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () ->
                taskService.getAllTasksByAuthorId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(208)
    @DisplayName(value = "Получение всех задач по ID исполнителя - успешно")
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
    @DisplayName(value = "Получение всех задач по ID исполнителя - ошибка (Указан несуществующий ID)")
    void getAllTasksByPerformerIdException() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () ->
                taskService.getAllTasksByPerformerId(anyLong(), 1, 1));

        verify(userRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(210)
    @DisplayName(value = "Получение всех задач по email автора - успешно")
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
    @DisplayName(value = "Получение всех задач по email автора - ошибка (Указан несуществующий email)")
    void getAllTasksByAuthorEmailException() {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.getAllTasksByAuthorEmail(anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(212)
    @DisplayName(value = "Получение всех задач по email исполнителя - успешно")
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
    @DisplayName(value = "Получение всех задач по email исполнителя - ошибка (Указан несуществующий email)")
    void getAllTasksByPerformerEmailException() {
        when(userRepository.findByEmailContainingIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.getAllTasksByPerformerEmail(anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByEmailContainingIgnoreCase(anyString());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(214)
    @DisplayName(value = "Получение всех задач по имени автора - успешно")
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
    @DisplayName(value = "Получение всех задач по имени автора - ошибка (Указано несуществующее имя)")
    void getAllTasksByAuthorFullNameException() {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserFullNameNotFoundException.class, () ->
                taskService.getAllTasksByAuthorFullName(anyString(), anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(0))
                .findAllByAuthor(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(216)
    @DisplayName(value = "Получение всех задач по имени исполнителя - успешно")
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
    @DisplayName(value = "Получение всех задач по имени исполнителя - ошибка (Указано несуществующее имя)")
    void getAllTasksByPerformerFullNameException() {
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserFullNameNotFoundException.class, () ->
                taskService.getAllTasksByPerformerFullName(anyString(), anyString(), 1, 1));

        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(anyString(), anyString());
        verify(taskRepository, times(0))
                .findAllByPerformer(any(User.class), any(Pageable.class));
    }

    @Test
    @Order(300)
    @DisplayName(value = "Изменение заголовка по ID задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void editHeaderTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editHeaderTaskById(anyLong(), new UpdateHeaderTaskDTO("Новый заголовок")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(301)
    @DisplayName(value = "Изменение заголовка по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
    void editHeaderTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editHeaderTaskById(anyLong(), new UpdateHeaderTaskDTO("Новый заголовок")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(302)
    @DisplayName(value = "Изменение заголовка по ID задачи - ошибка (Указан несуществующий ID)")
    void editHeaderTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.editHeaderTaskById(anyLong(), new UpdateHeaderTaskDTO("Новый заголовок")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(400)
    @DisplayName(value = "Изменение описания по ID задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void editDescriptionTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertTrue(taskService.editDescriptionTaskById(anyLong(), new UpdateDescriptionTaskDTO("Новое описание задачи")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(401)
    @DisplayName(value = "Изменение описания по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
    void editDescriptionTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editDescriptionTaskById(anyLong(), new UpdateDescriptionTaskDTO("Новое описание задачи")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(402)
    @DisplayName(value = "Изменение описания по ID задачи - ошибка (Указан несуществующий ID)")
    void editDescriptionTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.editDescriptionTaskById(anyLong(), new UpdateDescriptionTaskDTO("Новое описание задачи")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(700)
    @DisplayName(value = "Изменение исполнителя по ID задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void editPerformerTaskByIdSuccessful() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(author));

        assertTrue(taskService.editPerformerTaskById(anyLong(), new UpdatePerformerTaskDTO("author@gmail.com")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    @Order(701)
    @DisplayName(value = "Изменение исполнителя по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
    void editPerformerTaskByIdForbiddenException() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));

        assertThrows(ForbiddenException.class, () ->
                taskService.editPerformerTaskById(anyLong(), new UpdatePerformerTaskDTO("author@gmail.com")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(702)
    @DisplayName(value = "Изменение исполнителя по ID задачи - ошибка (Указан несуществующий ID задачи)")
    void editPerformerTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.editPerformerTaskById(anyLong(), new UpdatePerformerTaskDTO("author@gmail.com")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(0))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(703)
    @DisplayName(value = "Изменение исполнителя по ID задачи - ошибка (Указан несуществующий ID исполнителя)")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
    void editPerformerTaskByIdExceptionUser() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.of(task));
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserEmailNotFoundException.class, () ->
                taskService.editPerformerTaskById(anyLong(), new UpdatePerformerTaskDTO("author@gmail.com")));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(userRepository, times(1))
                .findByEmail(anyString());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(600)
    @DisplayName(value = "Изменение приоритета по ID задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Изменение приоритета по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Изменение приоритета по ID задачи - ошибка (Указан несуществующий ID)")
    void editPriorityTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.editPriorityTaskById(anyLong(), HIGH));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(500)
    @DisplayName(value = "Изменение статуса по ID задачи автором - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Изменение статуса по ID задачи исполнителем - успешно")
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
    @DisplayName(value = "Изменение статуса по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Изменение статуса по ID задачи - ошибка (Указан несуществующий ID)")
    void editStatusTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.editStatusTaskById(anyLong(), IN_PROGRESS));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .save(any(Task.class));
    }

    @Test
    @Order(800)
    @DisplayName(value = "Удаление задачи по ID задачи - успешно")
    @WithMockUser(username = "author@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Удаление задачи по ID задачи - ошибка (Ошибка доступа)")
    @WithMockUser(username = "user@gmail.com", authorities = "USER")
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
    @DisplayName(value = "Удаление задачи по ID задачи - ошибка (Указан несуществующий ID)")
    void deleteTaskByIdExceptionTask() {
        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.deleteTaskById(anyLong()));

        verify(taskRepository, times(1))
                .findById(anyLong());
        verify(taskRepository, times(0))
                .delete(any(Task.class));
    }
}