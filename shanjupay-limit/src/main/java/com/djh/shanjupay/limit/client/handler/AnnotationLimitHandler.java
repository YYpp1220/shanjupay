package com.djh.shanjupay.limit.client.handler;

import com.djh.shanjupay.limit.client.annotation.Limit;
import com.djh.shanjupay.limit.client.config.LimitRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流注解处理器
 * <li>基于aspectj</li>
 *
 * @author MyMrDiao
 * @date 2022/03/25
 */
@Slf4j
@Aspect
@Component
public class AnnotationLimitHandler extends AbstractLimitHandler<Object> {
    @Around("@annotation(limit)")
    public Object around(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取拦截的方法
        Method method = signature.getMethod();
        // 获取拦截的类名
        String className = signature.getDeclaringType().getName();
        // 获取拦截的方法名
        String methodName = method.getName();
        String defaultKey = StringUtils.join(className, "_", methodName);
        log.info("[AnnotationLimitHandler限流交易请求],尝试获取方法:{},执行权限", defaultKey);
        // 组装限流规则
        LimitRule limitRule = LimitRule.builder()
                .defaultKey(defaultKey)
                .count(limit.count())
                .key(limit.key())
                .limitType(limit.limitType())
                .period(limit.period())
                .prefix(limit.prefix())
                .fallbackStrategy(limit.fallbackStrategy())
                .fallbackImpl(limit.fallbackImpl())
                .build();
        return handle(limitRule, () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("[AnnotationLimitHandler限流交易请求],方法执行异常",throwable);
            }
            return null;
        });
    }
}
