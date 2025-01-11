package com.example.jomajomadelivery.orders.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.create)")
    public void orderStatusCreate() {}

    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.update)")
    public void orderStatusUpdate() {}


    @Before("orderStatusUpdate()")
    public void logBefore() {
        log.info("update");
    }

    @After("orderStatusUpdate()")
    public void logAfterUpdate() {
        log.info("주문 상태 변경됨");
    }
}
