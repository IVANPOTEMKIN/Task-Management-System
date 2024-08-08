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
import ru.effective_mobile.task_management_system.model.Violation;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            EmailAlreadyUseException.class,
            TaskAlreadyAddedException.class})
    public ResponseEntity<Violation> handleBadRequestException(Throwable e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new Violation(e.getMessage()));
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class})
    public ResponseEntity<List<Violation>> handleValidationException(ConstraintViolationException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(e.getConstraintViolations()
                        .stream()
                        .map(violation -> new Violation(violation.getMessage()))
                        .toList());
    }

    @ExceptionHandler(value = {
            ForbiddenException.class})
    public ResponseEntity<Violation> handleForbiddenException(Throwable e) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(new Violation(e.getMessage()));
    }

    @ExceptionHandler(value = {
            UserEmailNotFoundException.class,
            UserIdNotFoundException.class,
            UserFullNameNotFoundException.class,
            TaskNotFoundException.class,
            CommentNotFoundException.class})
    public ResponseEntity<Violation> handleNotFoundException(Throwable e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new Violation(e.getMessage()));
    }
}