package kr.co.picTO.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class AopConfiguration {

    @Pointcut("execution(* kr.co.picTO.controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("AOP BEFORE Method Name : " + method.getName());

        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            log.info("AOP BEFORE Type : " + obj.getClass().getSimpleName());
            log.info("AOP BEFORE Value : " + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterRun(JoinPoint joinPoint, Object returnObj) {
        log.info("Return Obj returnObj : " + returnObj);
    }
}
