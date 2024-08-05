package ru.effective_mobile.task_management_system.dto.utils;

public class Utils {

    public static final String PATTERN_EMAIL = "^([a-zA-Z0-9]+)([a-zA-Z0-9.]*)@([a-zA-Z]+)\\.([a-zA-Z]{2,6})$";
    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&=+.])(?=\\S+$).{8,}$";
    public static final String PATTERN_NAME = "^[а-яёА-ЯЁa-zA-Z]{2,15}$";

    public static final String PASSWORD_MESSAGE = """
            Пароль должен быть не менее 8 символов
            Пароль должен содержать минимум одну прописную букву
            Пароль должен содержать минимум одну заглавную букву
            Пароль должен содержать минимум один специальный знак - @#$%^&=+.""";

    public static final String FIRST_NAME_MESSAGE = """
            Имя пользователя должно быть от 2 до 15 символов
            Имя пользователя должно содержать только буквы""";

    public static final String LAST_NAME_MESSAGE = """
            Имя пользователя должно быть от 2 до 15 символов
            Имя пользователя должно содержать только буквы""";
}