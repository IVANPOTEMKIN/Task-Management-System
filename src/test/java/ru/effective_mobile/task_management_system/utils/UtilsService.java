package ru.effective_mobile.task_management_system.utils;

import ru.effective_mobile.task_management_system.dto.security.LoginDTO;
import ru.effective_mobile.task_management_system.dto.security.RegisterDTO;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.TaskDTO;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;

import java.util.List;

import static java.time.LocalDateTime.MIN;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.MEDIUM;
import static ru.effective_mobile.task_management_system.enums.Role.USER;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IS_PENDING;

public class UtilsService {

    public static User author = User.builder()
            .id(1L)
            .firstName("Иван")
            .lastName("Иванов")
            .email("author@gmail.com")
            .password("QwErTy.1234")
            .role(USER)
            .build();

    public static User performer = User.builder()
            .id(2L)
            .firstName("Петр")
            .lastName("Петров")
            .email("performer@gmail.com")
            .password("QwErTy.1234")
            .role(USER)
            .build();

    public static Task task = Task.builder()
            .id(1L)
            .header("Заголовок")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author(author)
            .performer(performer)
            .createdAt(MIN)
            .build();

    public static TaskDTO taskDTO = TaskDTO.builder()
            .header("Заголовок")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author("Иван Иванов")
            .authorEmail("author@gmail.com")
            .performer("Петр Петров")
            .performerEmail("performer@gmail.com")
            .createdAt(MIN)
            .build();

    public static CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
            .header("Заголовок")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .build();

    public static RegisterDTO registerDTO = RegisterDTO.builder()
            .firstName("Иван")
            .lastName("Иванов")
            .email("user@gmail.com")
            .password("QwErTy.1234")
            .build();

    public static LoginDTO loginDTO = LoginDTO.builder()
            .email("user@gmail.com")
            .password("QwErTy.1234")
            .build();

    public static List<TaskDTO> listTaskDTO = List.of(taskDTO);
}