package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;

import java.util.List;

import static ru.effective_mobile.task_management_system.service.utils.Utils.*;

public interface CommentService {

    /**
     * Добавление комментария к задаче
     *
     * @param dto текст комментария
     */
    boolean addComment(@Positive(message = ID_MESSAGE) Long id,
                       @Valid CreateOrUpdateCommentDTO dto);

    /**
     * Получение комментария по ID
     *
     * @param id ID комментария
     * @return комментарий
     */
    CommentDTO getCommentById(@Positive(message = ID_MESSAGE) Long id);

    /**
     * Получение списка всех комментариев по ID задачи
     *
     * @param id     ID задачи
     * @param offset кол-во страниц
     * @param limit  кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByTaskId(@Positive(message = ID_MESSAGE) Long id,
                                            @Positive(message = OFFSET_MESSAGE) Integer offset,
                                            @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех комментариев по ID автора
     *
     * @param id     ID автора
     * @param offset кол-во страниц
     * @param limit  кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByAuthorId(@Positive(message = ID_MESSAGE) Long id,
                                              @Positive(message = OFFSET_MESSAGE) Integer offset,
                                              @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех комментариев по ID задачи и ID автора
     *
     * @param taskId   ID задачи
     * @param authorId ID автора
     * @param offset   кол-во страниц
     * @param limit    кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByTaskIdAndAuthorId(@Positive(message = ID_MESSAGE) Long taskId,
                                                       @Positive(message = ID_MESSAGE) Long authorId,
                                                       @Positive(message = OFFSET_MESSAGE) Integer offset,
                                                       @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Изменение текста комментария по ID
     *
     * @param id  ID задачи
     * @param dto текст комментария
     */
    boolean editTextComment(@Positive(message = ID_MESSAGE) Long id,
                            @Valid CreateOrUpdateCommentDTO dto);

    /**
     * Удаление комментария по ID
     *
     * @param id ID комментария
     */
    boolean deleteComment(@Positive(message = ID_MESSAGE) Long id);
}