package ru.effective_mobile.task_management_system.exception.task;

public class TaskAlreadyAddedException extends RuntimeException {

    public TaskAlreadyAddedException() {
        super("Задача с данным заголовком уже была добавлена!");
    }
}