package ru.effective_mobile.task_management_system.dto.utils;

public class Utils {

    /* SECURITY */

    public static final String REGISTER_DTO = "Запрос на регистрацию";
    public static final String LOGIN_DTO = "Запрос на аутентификацию";
    public static final String JWT_DTO = "Ответ c токеном доступа";

    public static final String FIRST_NAME = "Имя пользователя";
    public static final String LAST_NAME = "Фамилия пользователя";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Пароль";
    public static final String TOKEN = "Токен доступа";

    public static final String EXAMPLE_FIRST_NAME = "Иван";
    public static final String EXAMPLE_LAST_NAME = "Иванов";
    public static final String EXAMPLE_EMAIL = "user@gmail.com";
    public static final String EXAMPLE_PASSWORD = "QwErTy.1234";
    public static final String EXAMPLE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpdmFuQGdtYWlsLmNvbSIsImlhdCI6MTcyMjg4ODQ1MiwiZXhwIjoxNzIzMDMyNDUyfQ.r2ldS_Ba59gJE50Cbq9Vi25LT3mGLRV_UrGkx92pVKg";

    public static final String PATTERN_EMAIL = "^([a-zA-Z0-9]+)([a-zA-Z0-9.]*)@([a-zA-Z]+)\\.([a-zA-Z]{2,6})$";
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&=+.])(?=\\S+$).{8,}$";
    public static final String PATTERN_NAME = "^[а-яёА-ЯЁa-zA-Z]{2,15}$";

    public static final String EMAIL_MESSAGE_1 = "Email должен содержать только латинские буквы";
    public static final String EMAIL_MESSAGE_2 = "Email должен содержать знак";
    public static final String EMAIL_MESSAGE_3 = "Email должен содержать домен";
    public static final String EMAIL_MESSAGE_NOT_BLANK = "Email не может быть пустым";

    public static final String FIRST_NAME_MESSAGE_1 = "Имя пользователя должно быть от 2 до 15 символов";
    public static final String FIRST_NAME_MESSAGE_2 = "Имя пользователя должно содержать только буквы";
    public static final String FIRST_NAME_MESSAGE_NOT_BLANK = "Имя пользователя не может быть пустым";

    public static final String LAST_NAME_MESSAGE_1 = "Фамилия пользователя должна быть от 2 до 15 символов";
    public static final String LAST_NAME_MESSAGE_2 = "Фамилия пользователя должна содержать только буквы";
    public static final String LAST_NAME_MESSAGE_NOT_BLANK = "Фамилия пользователя не может быть пустым";

    public static final String PASSWORD_MESSAGE_1 = "Пароль должен быть не менее 8 символов";
    public static final String PASSWORD_MESSAGE_2 = "Пароль должен содержать минимум одну прописную букву";
    public static final String PASSWORD_MESSAGE_3 = "Пароль должен содержать минимум одну заглавную букву";
    public static final String PASSWORD_MESSAGE_4 = "Пароль должен содержать минимум один специальный знак - @#$%^&=+.";
    public static final String PASSWORD_MESSAGE_NOT_BLANK = "Пароль не может быть пустым";

    /* TASK */

    public static final String TASK_DTO = "Ответ с задачей";
    public static final String CREATE_TASK_DTO = "Запрос на создание задачи";
    public static final String UPDATE_HEADER_TASK_DTO = "Запрос на изменение заголовка задачи";
    public static final String UPDATE_DESCRIPTION_TASK_DTO = "Запрос на изменение описания задачи";
    public static final String UPDATE_PERFORMER_TASK_DTO = "Запрос на изменение исполнителя задачи";

    public static final String HEADER = "Заголовок задачи";
    public static final String DESCRIPTION = "Описание задачи";
    public static final String PRIORITY = "Приоритет задачи";
    public static final String STATUS = "Статус задачи";
    public static final String AUTHOR = "Имя и фамилия автора";
    public static final String PERFORMER = "Имя и фамилия исполнителя";
    public static final String DATE_AND_TIME = "Дата и время создания";

    public static final String EXAMPLE_PRIORITY = "LOW/MEDIUM/HIGH";
    public static final String EXAMPLE_STATUS = "IS_PENDING/IN_PROGRESS/COMPLETED";
    public static final String EXAMPLE_FULL_NAME = "Иван Иванов";
    public static final String EXAMPLE_DATE_AND_TIME = "2024-08-05T22:19:33";

    public static final String HEADER_MESSAGE = "Заголовок задачи должен быть от 10 до 30 символов";
    public static final String HEADER_MESSAGE_NOT_BLANK = "Заголовок задачи не может быть пустым";

    public static final String DESCRIPTION_MESSAGE = "Описание задачи должно быть от 10 до 100 символов";
    public static final String DESCRIPTION_MESSAGE_NOT_BLANK = "Описание задачи не может быть пустым";

    /* COMMENT */

    public static final String COMMENT_DTO = "Ответ с комментарием";
    public static final String CREATE_OR_UPDATE_COMMENT_DTO = "Запрос на создание/изменение текста комментария";

    public static final String TEXT = "Текст комментария";

    public static final String TEXT_MESSAGE = "Текст комментария должен быть от 10 до 100 символов";
    public static final String TEXT_MESSAGE_NOT_BLANK = "Текст комментария не может быть пустым";
}