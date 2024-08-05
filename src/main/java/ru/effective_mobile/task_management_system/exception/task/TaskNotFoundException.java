package ru.effective_mobile.task_management_system.exception.task;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Задача с данным ID не найдена!");
    }
}