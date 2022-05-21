package com.test.crudsample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

@Component
@Aspect
public class customerAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postAction() {
    }

    @Before("postAction()")
    public void logAction(JoinPoint joinPoint) {
        Class clazz = joinPoint.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(clazz);

        String url = getRequestUrl(joinPoint, clazz);
        String payload = getPayload(joinPoint);
        logger.info("POST " + url + " Payload " + payload);
    }

    private String getRequestUrl(JoinPoint joinPoint, Class clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        return getPostUrl(requestMapping, methodPostMapping);
    }

    private String getPayload(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String parameterName = signature.getParameterNames()[i];
            builder.append(parameterName);
            builder.append(": ");
            builder.append(joinPoint.getArgs()[i].toString());
            builder.append(", ");
        }
        return builder.toString();
    }

    private String getPostUrl(RequestMapping requestMapping, PostMapping postMapping) {
        String baseUrl = getUrl(requestMapping.value());
        String endpoint = getUrl(postMapping.value());

        return baseUrl + endpoint;
    }

    private String getUrl(String[] urls) {
        if (urls.length == 0) return "";
        else return urls[0];
    }
}