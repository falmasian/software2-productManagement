package com.productManagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // فقط سرویس‌های داخل پکیج مورد نظر
    @Pointcut("execution(* com.productManagement.service..*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("➡️ Calling {}.{} with arguments: {}", className, methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("✅ {}.{} returned: {}", className, methodName, result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("❌ Exception in {}.{}: {}", className, methodName, exception.getMessage(), exception);
    }
}
