package ru.effective_mobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;
import ru.effective_mobile.task_management_system.exception.ForbiddenException;
import ru.effective_mobile.task_management_system.exception.task.TaskAlreadyAddedException;
import ru.effective_mobile.task_management_system.exception.task.TaskNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserFullNameNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserIdNotFoundException;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;
import static ru.effective_mobile.task_management_system.mapper.TaskMapper.INSTANCE;

@Service
@Validated
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public boolean addTask(CreateTaskDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = INSTANCE.createTaskDTOToTask(dto);

        var author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserEmailNotFoundException::new);

        for (Task element : taskRepository.findAll()) {
            if (element.getHeader().equalsIgnoreCase(task.getHeader())) {
                throw new TaskAlreadyAddedException();
            }
        }

        task.setAuthor(author);
        task.setCreatedAt(LocalDateTime.now().truncatedTo(SECONDS));

        taskRepository.save(task);
        return true;
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        return INSTANCE.taskToTaskDTO(task);
    }

    @Override
    public List<TaskDTO> getAllTasks(Integer offset, Integer limit) {
        return taskRepository
                .findAll(PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByHeader(String header, Integer offset, Integer limit) {
        return taskRepository
                .findAllByHeaderContainingIgnoreCase(header, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByStatus(StatusTask status, Integer offset, Integer limit) {
        return taskRepository
                .findAllByStatus(status, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPriority(PriorityTask priority, Integer offset, Integer limit) {
        return taskRepository
                .findAllByPriority(priority, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorId(Long id, Integer offset, Integer limit) {
        var author = userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerId(Long id, Integer offset, Integer limit) {
        var performer = userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);

        return taskRepository
                .findAllByPerformer(performer, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorEmail(String email, Integer offset, Integer limit) {
        var author = userRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(UserEmailNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerEmail(String email, Integer offset, Integer limit) {
        var performer = userRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(UserEmailNotFoundException::new);

        return taskRepository
                .findAllByPerformer(performer, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorFullName(String firstName, String lastName,
                                                     Integer offset, Integer limit) {

        var author = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName)
                .orElseThrow(UserFullNameNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerFullName(String firstName, String lastName,
                                                        Integer offset, Integer limit) {

        var performer = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName)
                .orElseThrow(UserFullNameNotFoundException::new);

        return taskRepository
                .findAllByPerformer(performer, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Transactional
    @Override
    public boolean editHeaderTaskById(Long id, UpdateHeaderTaskDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setHeader(dto.getHeader());
        taskRepository.save(task);
        return true;
    }

    @Transactional
    @Override
    public boolean editDescriptionTaskById(Long id, UpdateDescriptionTaskDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setDescription(dto.getDescription());
        taskRepository.save(task);
        return true;
    }

    @Transactional
    @Override
    public boolean editPerformerTaskById(Long id, UpdatePerformerTaskDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        var performer = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(UserEmailNotFoundException::new);

        task.setPerformer(performer);
        taskRepository.save(task);
        return true;
    }

    @Transactional
    @Override
    public boolean editStatusTaskById(Long id, StatusTask status) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())
                && (task.getPerformer() != null && !authentication.getName().equals(task.getPerformer().getEmail()))) {

            throw new ForbiddenException();
        }

        task.setStatus(status);
        taskRepository.save(task);
        return true;
    }

    @Transactional
    @Override
    public boolean editPriorityTaskById(Long id, PriorityTask priority) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setPriority(priority);
        taskRepository.save(task);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteTaskById(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        taskRepository.delete(task);
        return true;
    }
}