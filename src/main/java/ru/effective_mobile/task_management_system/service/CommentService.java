package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;

import java.util.List;

public interface CommentService {

    void addComment(Long id, @Valid CreateOrUpdateCommentDTO dto);

    CommentDTO getCommentById(Long id);

    List<CommentDTO> getAllCommentsByTaskId(Long id, Integer offset, Integer limit);

    List<CommentDTO> getAllCommentsByAuthorId(Long id, Integer offset, Integer limit);

    List<CommentDTO> getAllCommentsByTaskIdAuthorId(Long taskId, Long authorId,
                                                    Integer offset, Integer limit);

    void editTextComment(Long id, @Valid CreateOrUpdateCommentDTO dto);

    void deleteComment(Long id);
}