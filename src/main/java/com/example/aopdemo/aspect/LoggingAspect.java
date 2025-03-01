package com.example.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Aspect for logging start, return value, exceptions and execution time of methods.
 */
@Aspect
@Component
@Order(1)
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.example.aopdemo.controller.WeatherController.*(..))")
    public void endpointPointcut() {}

    @Before("endpointPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String args = String.join(", ", Arrays.stream(joinPoint.getArgs()).map(Objects::toString).toList());
        logger.info("Start execution of {} with args {}", joinPoint.getSignature(), args);
    }

    @AfterReturning(value = "endpointPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("{} returns {}", getMethodName(joinPoint), result);
    }

    @AfterThrowing(value = "endpointPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        logger.error("Error during execution of {}: {}", getMethodName(joinPoint), ex.getMessage());
    }

    @Around("endpointPointcut()")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            var result = joinPoint.proceed();
            long execTime = System.currentTimeMillis() - startTime;
            logger.info("{} execution time: {} ms", getMethodName(joinPoint), execTime);
            return result;
        }
        catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Interval server error.");
        }
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}