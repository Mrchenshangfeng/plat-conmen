package com.hywisdom.platform.common.web.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

import com.hywisdom.platform.common.utils.RequestUtils;

/**
 * 〈功能简述〉<br>
 * 〈记录请求日志〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controller() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void mappingAnnotations() {
    }

    @Order(1)
    @Around("(controller() || restController()) && mappingAnnotations()")
    public Object logHttpInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 接收到请求，记录请求内容
        log.info("==================== WebInfoAspect Start ====================");
        log.info("---- {} : {}", request.getMethod(), request.getRequestURL().toString());
        log.info("---- Http Headers : {}", RequestUtils.getHeaderMap(request));
        log.info("---- Http Parameters : {}", RequestUtils.getParameterMap(request));
        log.info("---- Execute Method : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("---- Execute Arguments : {}", RequestUtils.formatArgs(joinPoint.getArgs()));

        // 执行方法
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object resultObj = joinPoint.proceed();
        stopWatch.stop();

        double elapsedTime = stopWatch.getTime() / 1000;
        Signature signature = joinPoint.getSignature();
        String infoString = "[" + signature.toShortString() + "][Elapsed time: " + elapsedTime + " s]";
        if (elapsedTime > 1) {
            log.error(infoString + "[Note that it's time consuming!]");
        } else {
            log.info(infoString);
        }
        log.info("---- Execute Result : [{}]", String.valueOf(resultObj));
        log.info("==================== WebInfoAspect End ====================");
        return resultObj;
    }
}
