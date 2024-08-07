package ru.effective_mobile.task_management_system.utils;

import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;
import ru.effective_mobile.task_management_system.dto.tasks.TaskDTO;
import ru.effective_mobile.task_management_system.model.Comment;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;

import static java.time.LocalDateTime.MIN;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.MEDIUM;
import static ru.effective_mobile.task_management_system.enums.Role.USER;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IS_PENDING;

public class UtilsMapper {

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
            .header("Заголовок задачи")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author(author)
            .performer(performer)
            .createdAt(MIN)
            .build();

    public static Comment comment = Comment.builder()
            .id(1L)
            .text("Текст комментария")
            .author(performer)
            .task(task)
            .createdAt(MIN)
            .build();

    public static TaskDTO taskDTO = TaskDTO.builder()
            .header("Заголовок задачи")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author("Иван Иванов")
            .authorEmail("author@gmail.com")
            .performer("Петр Петров")
            .performerEmail("performer@gmail.com")
            .createdAt(MIN)
            .build();

    public static CommentDTO commentDTO = CommentDTO.builder()
            .author("Петр Петров")
            .text("Текст комментария")
            .createdAt(MIN)
            .build();

    public static CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
            .header("Заголовок задачи")
            .description("Описание задачи")
            .status(IS_PENDING)
            .priority(MEDIUM)
            .build();

    public static CreateOrUpdateCommentDTO createOrUpdateCommentDTO = CreateOrUpdateCommentDTO.builder()
            .text("Текст комментария")
            .build();
}