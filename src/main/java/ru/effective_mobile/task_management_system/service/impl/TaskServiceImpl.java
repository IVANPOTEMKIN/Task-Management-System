package ru.effective_mobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
import ru.effective_mobile.task_management_system.model.User;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.effective_mobile.task_management_system.mapper.TaskMapper.INSTANCE;

@Service
@Validated
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void addTask(CreateTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = INSTANCE.createTaskDTOToTask(dto);

        User author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserEmailNotFoundException::new);

        for (Task element : taskRepository.findAll()) {
            if (element.getHeader().equalsIgnoreCase(task.getHeader())) {
                throw new TaskAlreadyAddedException();
            }
        }

        task.setAuthor(author);
        task.setCreatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return INSTANCE.taskToTaskDTO(task);
    }

    @Override
    public List<TaskDTO> getAllTasks(Integer offset, Integer limit) {
        return taskRepository
                .findAll(PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByHeader(String header, Integer offset, Integer limit) {
        return taskRepository
                .findAllByHeaderContainingIgnoreCase(header, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByStatus(StatusTask status, Integer offset, Integer limit) {
        return taskRepository
                .findAllByStatus(status, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPriority(PriorityTask priority, Integer offset, Integer limit) {
        return taskRepository
                .findAllByPriority(priority, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorId(Long id, Integer offset, Integer limit) {
        User author = userRepository.findById(id).orElseThrow(UserIdNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerId(Long id, Integer offset, Integer limit) {
        User performer = userRepository.findById(id).orElseThrow(UserIdNotFoundException::new);

        return taskRepository
                .findAllByPerformer(performer, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorEmail(String email, Integer offset, Integer limit) {
        User author = userRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(UserEmailNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerEmail(String email, Integer offset, Integer limit) {
        User performer = userRepository.findByEmailContainingIgnoreCase(email)
                .orElseThrow(UserEmailNotFoundException::new);

        return taskRepository
                .findAllByAuthor(performer, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByAuthorFullName(String firstName, String lastName,
                                                     Integer offset, Integer limit) {

        User author = userRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName)
                .orElseThrow(UserFullNameNotFoundException::new);

        return taskRepository
                .findAllByAuthor(author, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getAllTasksByPerformerFullName(String firstName, String lastName,
                                                        Integer offset, Integer limit) {

        User performer = userRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName)
                .orElseThrow(UserFullNameNotFoundException::new);

        return taskRepository
                .findAllByAuthor(performer, PageRequest.of(offset, limit))
                .stream()
                .map(INSTANCE::taskToTaskDTO)
                .toList();
    }

    @Override
    public void editHeaderTask(Long id, UpdateHeaderTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setHeader(dto.getHeader());
        taskRepository.save(task);
    }

    @Override
    public void editDescriptionTask(Long id, UpdateDescriptionTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setDescription(dto.getDescription());
        taskRepository.save(task);
    }

    @Override
    public void editPerformerTask(Long id, UpdatePerformerTaskDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        User performer = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(UserEmailNotFoundException::new);

        task.setPerformer(performer);
        taskRepository.save(task);
    }

    @Override
    public void editStatusTask(Long id, StatusTask status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())
                && (task.getPerformer() != null && !authentication.getName().equals(task.getPerformer().getEmail()))) {

            throw new ForbiddenException();
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public void editPriorityTask(Long id, PriorityTask priority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        task.setPriority(priority);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        if (!authentication.getName().equals(task.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        taskRepository.delete(task);
    }
}