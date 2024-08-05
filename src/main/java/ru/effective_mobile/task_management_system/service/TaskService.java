package ru.effective_mobile.task_management_system.service;

import jakarta.validation.Valid;
import ru.effective_mobile.task_management_system.dto.tasks.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.util.List;

public interface TaskService {

    /**
     * Добавление задачи
     *
     * @param dto данные задачи
     */
    void addTask(@Valid CreateTaskDTO dto);

    /**
     * Получение задачи по ID
     *
     * @param id ID задачи
     * @return задача
     */
    TaskDTO getTaskById(Long id);

    /**
     * Получение списка всех задач
     *
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasks(Integer offset, Integer limit);

    /**
     * Получение списка всех задач по заголовку задачи
     *
     * @param header заголовок задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByHeader(String header, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по статусу задачи
     *
     * @param status статус задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByStatus(StatusTask status, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по приоритету задачи
     *
     * @param priority приоритет задачи
     * @param offset   кол-во страниц
     * @param limit    кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPriority(PriorityTask priority, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по ID автора задачи
     *
     * @param id     ID автора задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorId(Long id, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по ID исполнителя задачи
     *
     * @param id     ID исполнителя задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerId(Long id, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по email автора задачи
     *
     * @param email  email автора задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorEmail(String email, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по email исполнителя задачи
     *
     * @param email  email исполнителя задачи
     * @param offset кол-во страниц
     * @param limit  кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerEmail(String email, Integer offset, Integer limit);

    /**
     * Получение списка всех задач по имени автора задачи
     *
     * @param firstName имя автора задачи
     * @param lastName  фамилия автора задачи
     * @param offset    кол-во страниц
     * @param limit     кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByAuthorFullName(String firstName, String lastName,
                                              Integer offset, Integer limit);

    /**
     * Получение списка всех задач по имени исполнителя задачи
     *
     * @param firstName имя исполнителя задачи
     * @param lastName  фамилия исполнителя задачи
     * @param offset    кол-во страниц
     * @param limit     кол-во задач на странице
     * @return список задач
     */
    List<TaskDTO> getAllTasksByPerformerFullName(String firstName, String lastName,
                                                 Integer offset, Integer limit);

    /**
     * Изменение заголовка задачи по ID
     *
     * @param id  ID задачи
     * @param dto заголовок задачи
     */
    void editHeaderTask(Long id, @Valid UpdateHeaderTaskDTO dto);

    /**
     * Изменение описания задачи по ID
     *
     * @param id  ID задачи
     * @param dto описание задачи
     */
    void editDescriptionTask(Long id, @Valid UpdateDescriptionTaskDTO dto);

    /**
     * Изменение исполнителя задачи по ID
     *
     * @param id  ID задачи
     * @param dto email исполнителя задачи
     */
    void editPerformerTask(Long id, @Valid UpdatePerformerTaskDTO dto);

    /**
     * Изменение приоритета задачи по ID
     *
     * @param id       ID задачи
     * @param priority приоритет задачи
     */
    void editPriorityTask(Long id, PriorityTask priority);

    /**
     * Изменение статуса задачи по ID
     *
     * @param id     ID задачи
     * @param status статус задачи
     */
    void editStatusTask(Long id, StatusTask status);

    /**
     * Удаление задачи по ID
     *
     * @param id ID задачи
     */
    void deleteTask(Long id);
}