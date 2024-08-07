package ru.effective_mobile.task_management_system.utils;

public class Naming {

    /* STATUS */

    public static final String STATUS_200 = "статус 200";
    public static final String STATUS_201 = "статус 201";
    public static final String STATUS_400 = "статус 400";
    public static final String STATUS_401 = "статус 401";
    public static final String STATUS_403 = "статус 403";
    public static final String STATUS_404 = "статус 404";

    /* RESULT */

    public static final String SUCCESSFUL = "успешно";
    public static final String EXCEPTION_ID = "ошибка (Указан несуществующий ID)";
    public static final String EXCEPTION_TASK_ID = "ошибка (Указан несуществующий ID задачи)";
    public static final String EXCEPTION_AUTHOR_ID = "ошибка (Указан несуществующий ID автора)";
    public static final String EXCEPTION_UNIQUE = "ошибка (Указан неуникальный email)";
    public static final String EXCEPTION_EMAIL = "ошибка (Указан несуществующий email)";
    public static final String EXCEPTION_NAME = "ошибка (Указано несуществующее имя)";
    public static final String EXCEPTION_FORBIDDEN = "ошибка (Ошибка доступа)";

    /* MAPPER */

    public static final String MAPPING = "Маппинг - успешно";

    /* AUTH */

    public static final String REGISTER = "Регистрация";
    public static final String LOGIN = "Авторизация";

    /* TASK */

    public static final String ADD_TASK = "Добавление новой задачи";
    public static final String GET_TASK_BY_ID = "Получение задачи по ID";
    public static final String GET_ALL_TASKS = "Получение всех задач";
    public static final String GET_ALL_TASKS_BY_HEADER = "Получение всех задач по заголовку задачи";
    public static final String GET_ALL_TASKS_BY_STATUS = "Получение всех задач по статусу задачи";
    public static final String GET_ALL_TASKS_BY_PRIORITY = "Получение всех задач по приоритету задачи";
    public static final String GET_ALL_TASKS_BY_AUTHOR_ID = "Получение всех задач по ID автора задачи";
    public static final String GET_ALL_TASKS_BY_PERFORMER_ID = "Получение всех задач по ID исполнителя задачи";
    public static final String GET_ALL_TASKS_BY_AUTHOR_EMAIL = "Получение всех задач по email автора задачи";
    public static final String GET_ALL_TASKS_BY_PERFORMER_EMAIL = "Получение всех задач по email исполнителя задачи";
    public static final String GET_ALL_TASKS_BY_AUTHOR_FULL_NAME = "Получение всех задач по имени автора задачи";
    public static final String GET_ALL_TASKS_BY_PERFORMER_FULL_NAME = "Получение всех задач по имени исполнителя задачи";
    public static final String EDIT_HEADER_TASK_BY_ID = "Изменение заголовка задачи по ID";
    public static final String EDIT_DESCRIPTION_TASK_BY_ID = "Изменение описания задачи по ID";
    public static final String EDIT_STATUS_TASK_BY_ID = "Изменение статуса задачи по ID";
    public static final String EDIT_STATUS_TASK_BY_ID_AUTHOR = "Изменение статуса задачи по ID автором задачи";
    public static final String EDIT_STATUS_TASK_BY_ID_PERFORMER = "Изменение статуса задачи по ID исполнителем задачи";
    public static final String EDIT_PRIORITY_TASK_BY_ID = "Изменение приоритета задачи по ID";
    public static final String EDIT_PERFORMER_TASK_BY_ID = "Изменение исполнителя задачи по ID";
    public static final String DELETE_TASK_BY_ID = "Удаление задачи по ID";

    /* COMMENT */

    public static final String ADD_COMMENT = "Добавление нового комментария";
    public static final String GET_COMMENT_BY_ID = "Получение комментария по ID";
    public static final String GET_ALL_COMMENTS_BY_TASK_ID = "Получение всех комментариев по ID задачи";
    public static final String GET_ALL_COMMENTS_BY_AUTHOR_ID = "Получение всех комментариев по ID автора комментария";
    public static final String GET_ALL_COMMENTS_BY_AUTHOR_ID_AND_TASK_ID = "Получение всех комментариев по ID задачи и ID автора комментария";
    public static final String EDIT_TEXT_COMMENT_BY_ID = "Изменение текста комментария по ID";
    public static final String DELETE_COMMENT_BY_ID = "Удаление комментария по ID";
}