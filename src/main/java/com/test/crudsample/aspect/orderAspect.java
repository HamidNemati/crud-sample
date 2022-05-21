package com.test.crudsample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class orderAspect {
    Logger logger = LoggerFactory.getLogger(orderAspect.class);


    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void logRequest() {

    }

    @Before("logRequest()")
    public void logAspect(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        String shortString = joinPoint.toShortString();
        String kind = joinPoint.getKind();


//        logger.info(joinPoint.toLongString());
    }
}
