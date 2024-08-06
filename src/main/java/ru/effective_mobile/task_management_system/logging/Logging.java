package ru.effective_mobile.task_management_system.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Logging {

    @Around("ru.effective_mobile.task_management_system.logging.PointCuts.allMethodsAuthService()"
            + "|| ru.effective_mobile.task_management_system.logging.PointCuts.allMethodsJwtService()"
            + "|| ru.effective_mobile.task_management_system.logging.PointCuts.allMethodsCommentService()"
            + "|| ru.effective_mobile.task_management_system.logging.PointCuts.allMethodsTaskService()"
    )
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String methodClass = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object result;

        try {
            result = joinPoint.proceed();

        } catch (Exception e) {
            log.error("Ошибка в работе метода {} сервиса {}\n{}\n", methodName, methodClass, e.getMessage());
            throw e;
        }

        log.info("Успешное завершение работы метода {} сервиса {}", methodName, methodClass);
        return result;
    }
}