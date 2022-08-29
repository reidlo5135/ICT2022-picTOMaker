package kr.co.picTO.common.configuration;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@NoArgsConstructor
public class AopConfiguration {

//    @Pointcut("execution(* kr.co.picTO.controller..*.*(..)), execution(* kr.co.picTO.service..*.*(..))")
//    private void cut() {}
//
//    @Before("cut()")
//    public void before(JoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        log.info("AOP BEFORE Method Name : " + method.getName());
//
//        Object[] args = joinPoint.getArgs();
//
//        for (Object obj : args) {
//            log.info("AOP BEFORE Type : " + obj.getClass().getSimpleName());
//            log.info("AOP BEFORE Value : " + obj);
//        }
//    }
//
//    @AfterReturning(value = "cut()", returning = "returnObj")
//    public void afterRun(JoinPoint joinPoint, Object returnObj) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        log.info("Return Obj returnObj : " + method.getName() + ", " + returnObj);
//    }

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
}
