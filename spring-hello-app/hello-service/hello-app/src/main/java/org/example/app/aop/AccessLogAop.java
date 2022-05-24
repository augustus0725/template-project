package org.example.app.aop;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@NoArgsConstructor
@Order(1)
@Slf4j
public class AccessLogAop {
    @Value("${settings.long-query-log:false}")
    private boolean longQueryLog;

    @Value("${settings.long-query-limit-ms:1000}")
    private int longQueryLimit;

    @Pointcut("@annotation(org.example.commons.annotation.Loggable)")
    public void accessLogWithAnnotation() {
    }

    @Around(value = "accessLogWithAnnotation()")
    @SuppressWarnings("all")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder w = new StringBuilder();
        long startTime = System.currentTimeMillis();
        long duration = 0;

        w.append("url: ").append(request.getRequestURL()).append('\t');
        w.append("method: ").append(request.getMethod()).append('\t');
        w.append("ip: ").append(request.getRemoteAddr()).append('\t');
        w.append("func:  ").append(pjp.getSignature().getDeclaringTypeName()).append('.').append(pjp.getSignature().getName()).append('\t');
        w.append("params: ").append(Arrays.toString(pjp.getArgs())).append('\t');
        Object ret = pjp.proceed();
        duration = System.currentTimeMillis() - startTime;
        w.append("cost： ").append(duration).append("ms").append('\t');
        w.append("return： ").append(ret);

        if (longQueryLog && duration > longQueryLimit
                || !longQueryLog) {
            log.info(w.toString());
        }

        return ret;
    }
}
