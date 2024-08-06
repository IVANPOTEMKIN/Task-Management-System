package ru.effective_mobile.task_management_system.logging;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* ru.effective_mobile.task_management_system.service.AuthService.*(..))")
    public void allMethodsAuthService() {
    }

    @Pointcut("execution(* ru.effective_mobile.task_management_system.service.JwtService.*(..))")
    public void allMethodsJwtService() {
    }

    @Pointcut("execution(* ru.effective_mobile.task_management_system.service.TaskService.*(..))")
    public void allMethodsTaskService() {
    }

    @Pointcut("execution(* ru.effective_mobile.task_management_system.service.CommentService.*(..))")
    public void allMethodsCommentService() {
    }
}