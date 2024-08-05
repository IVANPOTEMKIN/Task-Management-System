package ru.effective_mobile.task_management_system.exception.user;

public class UserFullNameNotFoundException extends RuntimeException {

    public UserFullNameNotFoundException() {
        super("Пользователь с данным именем не найден!");
    }
}