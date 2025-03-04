package com.example.aopdemo.aspect;

import com.example.aopdemo.exception.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Aspect for preventing unauthorized access to the secured controller endpoints.
 */
@Aspect
@Component
@Order(2)
public class SecuredAspect {
    private final Logger logger = LoggerFactory.getLogger(SecuredAspect.class);

    @Pointcut("execution(public * com.example.aopdemo.controller.WeatherController.*(..)) && @annotation(annotation)")
    private void weatherPointcut(SecuredEndpoint annotation) {}

    @Around(value = "weatherPointcut(annotation)", argNames = "joinPoint,annotation")
    public Object beforeWeatherEndpoint(ProceedingJoinPoint joinPoint, SecuredEndpoint annotation) throws Throwable {
        if (!isAuthorized(joinPoint, annotation)) {
            throw new UnauthorizedException("Unauthorized: invalid secret key.");
        }
        try {
            return joinPoint.proceed();
        }
        catch (Throwable ex) {
            logger.error("Error during execution of {}: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex;
        }
    }

    private boolean isAuthorized(ProceedingJoinPoint joinPoint, SecuredEndpoint annotation) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof String secretKey)) {
            logger.warn("Missing or invalid secret key in method: {}",
                    joinPoint.getSignature());
            return false;
        }

        boolean authorized = secretKey.equals(annotation.value());
        if (!authorized) {
            logger.warn("Unauthorized access attempt to {}: invalid secret key.", joinPoint.getSignature());
        }

        return authorized;
    }
}