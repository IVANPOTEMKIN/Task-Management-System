package ru.effective_mobile.task_management_system.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective_mobile.task_management_system.exception.CommentNotFoundException;
import ru.effective_mobile.task_management_system.exception.EmailAlreadyUseException;
import ru.effective_mobile.task_management_system.exception.ForbiddenException;
import ru.effective_mobile.task_management_system.exception.task.TaskAlreadyAddedException;
import ru.effective_mobile.task_management_system.exception.task.TaskNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserEmailNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserFullNameNotFoundException;
import ru.effective_mobile.task_management_system.exception.user.UserIdNotFoundException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            EmailAlreadyUseException.class,
            TaskAlreadyAddedException.class,
            ConstraintViolationException.class})
    public ResponseEntity<String> badRequest(Throwable e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbidden(Throwable e) {
        return ResponseEntity.status(FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            TaskNotFoundException.class,
            UserEmailNotFoundException.class,
            UserFullNameNotFoundException.class,
            UserIdNotFoundException.class,
            CommentNotFoundException.class})
    public ResponseEntity<String> handle_NotFound_Exception(Throwable e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
}