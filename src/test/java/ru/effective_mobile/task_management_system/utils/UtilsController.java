package ru.effective_mobile.task_management_system.utils;

import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.dto.security.LoginDTO;
import ru.effective_mobile.task_management_system.dto.security.RegisterDTO;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.model.Comment;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;

import static java.time.LocalDateTime.MIN;
import static ru.effective_mobile.task_management_system.enums.PriorityTask.MEDIUM;
import static ru.effective_mobile.task_management_system.enums.Role.USER;
import static ru.effective_mobile.task_management_system.enums.StatusTask.IS_PENDING;
import static ru.effective_mobile.task_management_system.utils.Constants.*;

public class UtilsController {

    public static RegisterDTO registerDTO = RegisterDTO.builder()
            .firstName(FIRST_NAME_AUTHOR)
            .lastName(LAST_NAME_AUTHOR)
            .email(EMAIL_AUTHOR)
            .password(PASSWORD)
            .build();

    public static LoginDTO loginDTO =
            new LoginDTO(EMAIL_AUTHOR, PASSWORD);

    public static User author = User.builder()
            .id(1L)
            .firstName(FIRST_NAME_AUTHOR)
            .lastName(LAST_NAME_AUTHOR)
            .email(EMAIL_AUTHOR)
            .password(PASSWORD)
            .role(USER)
            .build();

    public static User performer = User.builder()
            .id(2L)
            .firstName(FIRST_NAME_PERFORMER)
            .lastName(LAST_NAME_PERFORMER)
            .email(EMAIL_PERFORMER)
            .password(PASSWORD)
            .role(USER)
            .build();

    public static Task task = Task.builder()
            .id(1L)
            .header(HEADER)
            .description(DESCRIPTION)
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author(author)
            .performer(performer)
            .createdAt(MIN)
            .build();

    public static TaskDTO taskDTO = TaskDTO.builder()
            .header(HEADER)
            .description(DESCRIPTION)
            .status(IS_PENDING)
            .priority(MEDIUM)
            .author(FULL_NAME_AUTHOR)
            .authorEmail(EMAIL_AUTHOR)
            .performer(FULL_NAME_PERFORMER)
            .performerEmail(EMAIL_PERFORMER)
            .createdAt(MIN)
            .build();

    public static CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
            .header(HEADER)
            .description(DESCRIPTION)
            .status(IS_PENDING)
            .priority(MEDIUM)
            .build();

    public static UpdateHeaderTaskDTO updateHeaderTaskDTO =
            new UpdateHeaderTaskDTO(NEW_HEADER);

    public static UpdateDescriptionTaskDTO updateDescriptionTaskDTO =
            new UpdateDescriptionTaskDTO(NEW_DESCRIPTION);

    public static UpdatePerformerTaskDTO updatePerformerTaskDTO =
            new UpdatePerformerTaskDTO(EMAIL_AUTHOR);

    public static CreateOrUpdateCommentDTO createOrUpdateCommentDTO =
            new CreateOrUpdateCommentDTO(TEXT);

    public static Comment comment = Comment.builder()
            .id(1L)
            .text(TEXT)
            .author(author)
            .task(task)
            .createdAt(MIN)
            .build();

    public static CommentDTO commentDTO = CommentDTO.builder()
            .text(TEXT)
            .author(FULL_NAME_AUTHOR)
            .createdAt(MIN)
            .build();
}