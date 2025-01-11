package com.example.jomajomadelivery.orders.aspect;

import com.example.jomajomadelivery.orders.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.create(..)) ||" +
            "execution(* com.example.jomajomadelivery.orders.service.OrdersService.update(..))")
    public void ordersServicePointcut() {}

    @AfterReturning(pointcut = "ordersServicePointcut()",returning = "result")
    public void logOrderActivity(Object result) {
        if (result instanceof Order order) {
            log.info("Order Log - 상태: {}, 요청 시각: {}, 가게 ID: {}, 주문 ID: {}",
                    order.getStatus(),
                    LocalTime.now(),
                    order.getStore().getStoreId(),
                    order.getOrderId());
        }
    }

//    -- 혹시몰라 남겨놓는 이전코드 --
//    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.create(*))")
//    public void orderCreate() {}
//
//    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.update(*))")
//    public void orderStatusUpdate() {}
//
//
//    @After("orderCreate()")
//    public void logAfterCreate() {
//        log.info("주문 생성됨");
//    }
//
//    @After("orderStatusUpdate()")
//    public void logAfterUpdate() {
//        log.info("주문 상태 변경됨");
//    }
}
