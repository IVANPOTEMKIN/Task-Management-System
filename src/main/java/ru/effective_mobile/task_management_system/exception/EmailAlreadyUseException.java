package ru.effective_mobile.task_management_system.exception;

public class EmailAlreadyUseException extends RuntimeException {

    public EmailAlreadyUseException() {
        super("Данный email уже используется!");
    }
}