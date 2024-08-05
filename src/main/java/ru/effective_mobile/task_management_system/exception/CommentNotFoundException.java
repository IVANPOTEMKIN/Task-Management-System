package ru.effective_mobile.task_management_system.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super("Комментарий с данным ID не найден!");
    }
}