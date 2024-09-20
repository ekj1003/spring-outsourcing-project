package com.sparta.outsourcing_project.config.aop;

import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class InfoAop {

    @Pointcut("@annotation(com.sparta.outsourcing_project.config.anno.InfoAnnotation)")
    private void infoAnnotation() {}


    /**
     * 어드바이스: 어노테이션 범위 기반
     */
    @Around("infoAnnotation()")
    public Object info(ProceedingJoinPoint joinPoint) throws Throwable {
        //측정 시작
        String currentTime = LocalDateTime.now().toString();

        // 메서드 인자 가져오기
        Object[] args = joinPoint.getArgs();
        OrderRequestDto orderRequestDto = (OrderRequestDto)args[1];

        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            // 측정 완료
            log.info("::: API 요청 시각: {}ms", currentTime);
            log.info("::: Menu Id: {}", orderRequestDto.getMenuId());
            log.info("::: Store Id: {}", orderRequestDto.getStoreId());
        }
    }

}
