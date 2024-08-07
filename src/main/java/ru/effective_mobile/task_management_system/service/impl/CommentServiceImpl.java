package ru.effective_mobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;
import ru.effective_mobile.task_management_system.exception.CommentNotFoundException;
import ru.effective_mobile.task_management_system.exception.ForbiddenException;
import ru.effective_mobile.task_management_system.exception.task.TaskNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserIdNotFoundException;
import ru.effective_mobile.task_management_system.repository.CommentRepository;
import ru.effective_mobile.task_management_system.repository.TaskRepository;
import ru.effective_mobile.task_management_system.repository.UserRepository;
import ru.effective_mobile.task_management_system.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;
import static ru.effective_mobile.task_management_system.mapper.CommentMapper.INSTANCE;

@Service
@Validated
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Override
    public boolean addComment(Long id, CreateOrUpdateCommentDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserEmailNotFoundException::new);
        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        var comment = INSTANCE.createOrUpdateCommentDTOToComment(dto);

        comment.setAuthor(author);
        comment.setTask(task);
        comment.setCreatedAt(LocalDateTime.now().truncatedTo(SECONDS));

        commentRepository.save(comment);
        return true;
    }

    @Override
    public CommentDTO getCommentById(Long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return INSTANCE.commentToCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentsByTaskId(Long id, Integer offset, Integer limit) {
        var task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        return commentRepository.findAllByTask(task, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::commentToCommentDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> getAllCommentsByAuthorId(Long id, Integer offset, Integer limit) {
        var author = userRepository.findById(id)
                .orElseThrow(UserIdNotFoundException::new);

        return commentRepository.findAllByAuthor(author, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::commentToCommentDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> getAllCommentsByTaskIdAndAuthorId(Long taskId, Long authorId,
                                                              Integer offset, Integer limit) {

        var task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        var author = userRepository.findById(authorId)
                .orElseThrow(UserIdNotFoundException::new);

        return commentRepository.findAllByTaskAndAuthor(task, author, PageRequest.of(offset - 1, limit))
                .stream()
                .map(INSTANCE::commentToCommentDTO)
                .toList();
    }

    @Override
    public boolean editTextComment(Long id, CreateOrUpdateCommentDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (!authentication.getName().equals(comment.getAuthor().getEmail())) {
            throw new ForbiddenException();
        }

        comment.setText(dto.getText());
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean deleteComment(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (!authentication.getName().equals(comment.getAuthor().getEmail())
                && !authentication.getName().equals(comment.getTask().getAuthor().getEmail())) {

            throw new ForbiddenException();
        }

        commentRepository.delete(comment);
        return true;
    }
}