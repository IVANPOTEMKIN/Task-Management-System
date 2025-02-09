package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.util.List;

import static ru.effective_mobile.task_management_system.service.utils.Utils.*;

public interface TaskService {

    /**
     * Добавление задачи
     *
     * @param dto данные задачи
     */
    boolean addTask(@Valid CreateTaskDTO dto);

    /**
     * Получение задачи по ID
     *
     * @param id ID задачи
     * @return задача
     */
    TaskDTO getTaskById(@Positive(message = ID_MESSAGE) Long id);

    /**
     * Получение списка всех задач
     *
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasks(@Positive(message = OFFSET_MESSAGE) Integer offset,
                              @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по заголовку задачи
     *
     * @param header заголовок задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByHeader(String header,
                                      @Positive(message = OFFSET_MESSAGE) Integer offset,
                                      @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по статусу задачи
     *
     * @param status статус задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByStatus(StatusTask status,
                                      @Positive(message = OFFSET_MESSAGE) Integer offset,
                                      @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по приоритету задачи
     *
     * @param priority приоритет задачи
     * @param offset   кол-во страниц
     * @param limit    кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPriority(PriorityTask priority,
                                        @Positive(message = OFFSET_MESSAGE) Integer offset,
                                        @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по ID автора задачи
     *
     * @param id     ID автора задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorId(@Positive(message = ID_MESSAGE) Long id,
                                        @Positive(message = OFFSET_MESSAGE) Integer offset,
                                        @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по ID исполнителя задачи
     *
     * @param id     ID исполнителя задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerId(@Positive(message = ID_MESSAGE) Long id,
                                           @Positive(message = OFFSET_MESSAGE) Integer offset,
                                           @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по email автора задачи
     *
     * @param email  email автора задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorEmail(String email,
                                           @Positive(message = OFFSET_MESSAGE) Integer offset,
                                           @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по email исполнителя задачи
     *
     * @param email  email исполнителя задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerEmail(String email,
                                              @Positive(message = OFFSET_MESSAGE) Integer offset,
                                              @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по имени автора задачи
     *
     * @param firstName имя автора задачи
     * @param lastName  фамилия автора задачи
     * @param offset    кол-во страниц
     * @param limit     кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorFullName(String firstName,
                                              String lastName,
                                              @Positive(message = OFFSET_MESSAGE) Integer offset,
                                              @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Получение списка всех задач по имени исполнителя задачи
     *
     * @param firstName имя исполнителя задачи
     * @param lastName  фамилия исполнителя задачи
     * @param offset    кол-во страниц
     * @param limit     кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerFullName(String firstName,
                                                 String lastName,
                                                 @Positive(message = OFFSET_MESSAGE) Integer offset,
                                                 @Positive(message = LIMIT_MESSAGE) Integer limit);

    /**
     * Изменение заголовка задачи по ID
     *
     * @param id  ID задачи
     * @param dto заголовок задачи
     */
    boolean editHeaderTaskById(@Positive(message = ID_MESSAGE) Long id,
                               @Valid UpdateHeaderTaskDTO dto);

    /**
     * Изменение описания задачи по ID
     *
     * @param id  ID задачи
     * @param dto описание задачи
     */
    boolean editDescriptionTaskById(@Positive(message = ID_MESSAGE) Long id,
                                    @Valid UpdateDescriptionTaskDTO dto);

    /**
     * Изменение исполнителя задачи по ID
     *
     * @param id  ID задачи
     * @param dto email исполнителя задачи
     */
    boolean editPerformerTaskById(@Positive(message = ID_MESSAGE) Long id,
                                  @Valid UpdatePerformerTaskDTO dto);

    /**
     * Изменение приоритета задачи по ID
     *
     * @param id       ID задачи
     * @param priority приоритет задачи
     */
    boolean editPriorityTaskById(@Positive(message = ID_MESSAGE) Long id,
                                 PriorityTask priority);

    /**
     * Изменение статуса задачи по ID
     *
     * @param id     ID задачи
     * @param status статус задачи
     */
    boolean editStatusTaskById(@Positive(message = ID_MESSAGE) Long id,
                               StatusTask status);

    /**
     * Удаление задачи по ID
     *
     * @param id ID задачи
     */
    boolean deleteTaskById(@Positive(message = ID_MESSAGE) Long id);
}