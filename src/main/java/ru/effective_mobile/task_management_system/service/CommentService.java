package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ru.effective_mobile.task_management_system.dto.comments.CommentDTO;
import ru.effective_mobile.task_management_system.dto.comments.CreateOrUpdateCommentDTO;

import java.util.List;

public interface CommentService {

    /**
     * Добавление комментария к задаче
     *
     * @param dto текст комментария
     */
    void addComment(@Positive Long id,
                    @Valid CreateOrUpdateCommentDTO dto);

    /**
     * Получение комментария по ID
     *
     * @param id ID комментария
     * @return комментарий
     */
    CommentDTO getCommentById(@Positive Long id);

    /**
     * Получение списка всех комментариев по ID задачи
     *
     * @param id     ID задачи
     * @param offset кол-во страниц
     * @param limit  кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByTaskId(@Positive Long id,
                                            @Positive Integer offset,
                                            @Positive Integer limit);

    /**
     * Получение списка всех комментариев по ID автора
     *
     * @param id     ID автора
     * @param offset кол-во страниц
     * @param limit  кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByAuthorId(@Positive Long id,
                                              @Positive Integer offset,
                                              @Positive Integer limit);

    /**
     * Получение списка всех комментариев по ID задачи и ID автора
     *
     * @param taskId   ID задачи
     * @param authorId ID автора
     * @param offset   кол-во страниц
     * @param limit    кол-во комментариев на странице
     * @return список комментариев
     */
    List<CommentDTO> getAllCommentsByTaskIdAuthorId(@Positive Long taskId,
                                                    @Positive Long authorId,
                                                    @Positive Integer offset,
                                                    @Positive Integer limit);

    /**
     * Изменение текста комментария по ID
     *
     * @param id  ID задачи
     * @param dto текст комментария
     */
    void editTextComment(@Positive Long id,
                         @Valid CreateOrUpdateCommentDTO dto);

    /**
     * Удаление комментария по ID
     *
     * @param id ID комментария
     */
    void deleteComment(@Positive Long id);
}