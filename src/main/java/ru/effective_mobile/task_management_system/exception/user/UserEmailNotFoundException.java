package ru.effective_mobile.task_management_system.exception.user;

public class UserEmailNotFoundException extends RuntimeException {

    public UserEmailNotFoundException() {
        super("Пользователь с данным email не найден!");
    }
}