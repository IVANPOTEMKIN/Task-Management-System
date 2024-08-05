package ru.effective_mobile.task_management_system.exception.user;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException() {
        super("Пользователь с данным ID не найден!");
    }
}