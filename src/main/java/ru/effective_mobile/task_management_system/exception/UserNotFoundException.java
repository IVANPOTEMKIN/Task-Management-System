package ru.effective_mobile.task_management_system.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Пользователь с данным email не найден!");
    }
}