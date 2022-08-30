package kr.co.picTO.common.aop;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@NoArgsConstructor
public class LoggerAspect {

    @Around(value = "execution(public * kr.co.picTO.*.controller.*.*(..))")
    public Object logControllers(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">> Controller Request : {}.{}({})", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info(">> Controller Result : {}", result);
        return result;
    }

    @Around(value = "execution(public * kr.co.picTO.*.application.*Service.*(..))")
    public Object logServices(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">> Service Request : {}.{}({})", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info(">> Service Result : {}", result);
        return result;
    }

    @Around(value = "execution(public * kr.co.picTO.*.domain.*Repo.*(..))")
    public Object logRepositories(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">> Repository Request : {}.{}({})", pjp.getTarget().getClass().getInterfaces()[0].getName(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info(">> Repository Result : {}", result);
        return result;
    }

    @Around(value = "execution(public * kr.co.picTO.admin.presentation.*.*Controller.*(..))")
    public Object logAdminControllers(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">> Admin Controller Request : {}.{}({})", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info(">> Admin Controller Result : {}", result);
        return result;
    }

    @Around(value = "execution(public * kr.co.picTO.admin.application.*.*Service.*(..))")
    public Object logAdminServices(ProceedingJoinPoint pjp) throws Throwable {
        log.info(">> Admin Service Request : {}.{}({})", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = pjp.proceed();
        log.info(">> Admin Service Result : {}", result);
        return result;
    }
}
