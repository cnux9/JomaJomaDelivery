package com.example.jomajomadelivery.common.aop.account;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class CurrentUserIdAspect {

    @Around("execution(* com.example.jomajomadelivery..*(.., @CurrentUserId (*), ..))") // AOP 작동 지점 설정
    public Object injectUserId(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 현재 실행 중인 메서드의 파라미터 정보를 가져옴
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters(); // 메서드의 파라미터 배열

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(CurrentUserId.class)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    try {
                        args[i] = Long.parseLong(authentication.getPrincipal().toString());
                    } catch (NumberFormatException ex) {
                        throw new IllegalStateException("Principal cannot be converted to Long.", ex);
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
