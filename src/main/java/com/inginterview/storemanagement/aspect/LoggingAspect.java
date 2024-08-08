package com.inginterview.storemanagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final static String ALL_SERVICES = "execution(* com.inginterview.storemanagement.service..*(..))";
    private final static String ALL_CONTROLLERS = "execution(* com.inginterview.storemanagement.controller..*(..))";

    private long timestamp;

    @Before(value = ALL_SERVICES + " || " + ALL_CONTROLLERS)
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        log.info("Started executing:  {}", joinPoint.getSignature().getDeclaringTypeName());
        timestamp = System.currentTimeMillis();
    }

    @AfterReturning(pointcut = ALL_SERVICES + " || " + ALL_CONTROLLERS, returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        log.info("Finished executing: {} in: {}ms", joinPoint.getSignature().getDeclaringTypeName(), System.currentTimeMillis() - timestamp);
    }

    @AfterThrowing(pointcut = ALL_SERVICES + " || " + ALL_CONTROLLERS, throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred: {}: {}", joinPoint.getSignature().getDeclaringTypeName(), exception.getMessage());
    }
}
