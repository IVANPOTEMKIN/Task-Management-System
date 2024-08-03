package ru.effective_mobile.task_management_system.exception;

public class EmailAlreadyUse extends RuntimeException {

    public EmailAlreadyUse() {
        super("Данный email уже используется!");
    }
}