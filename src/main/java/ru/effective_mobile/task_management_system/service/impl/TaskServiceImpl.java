package ru.effective_mobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.task_management_system.dto.tasks.CreateTaskDTO;
import ru.effective_mobile.task_management_system.exception.UserNotFoundException;
import ru.effective_mobile.task_management_system.mapper.TaskMapper;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.TaskService;

import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void add(CreateTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = TaskMapper.INSTANCE.createTaskDTOToTask(dto);

        User author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        task.setAuthor(author);
        task.setCreatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }
}