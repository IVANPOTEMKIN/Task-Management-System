package ru.effective_mobile.task_management_system.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Ошибка доступа!");
    }
}