package com.djh.shanjupay.common.config;

import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.interfaces.limit.Limit;
import com.djh.shanjupay.common.util.RedisLimitUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * LimitAspect限流切面
 *
 * @author MrMyHui
 * @date 2021/11/23
 */
@Slf4j
@Order(0)
@Aspect
@Component
public class LimitAspect {
    /**
     * 一个时间窗口时间(毫秒)(限流时间)
     */
    private static final String TIME_REQUEST = "1000";

    @Autowired
    private RedisLimitUtil redisLimit;

    /**
     * 对应注解
     */
    @Pointcut("@annotation(com.djh.shanjupay.common.interfaces.limit.Limit)")
    public void aspect() {};

    /**
     * 切面
     *
     * @param proceedingJoinPoint 进行连接点
     * @param limit               限制
     * @return {@link Object}
     */
    @Around("aspect() && @annotation(limit)")
    public Object interceptor(ProceedingJoinPoint proceedingJoinPoint, Limit limit) {
        Object result = null;
        Long maxRequest = 0L;
        // 一个时间窗口(毫秒)为1000的话默认调用秒级限流判断(每秒限制多少请求)
        if (TIME_REQUEST.equals(limit.timeRequest())) {
            maxRequest = redisLimit.limit(limit.maxRequest());
        } else {
            maxRequest = redisLimit.limit(limit.maxRequest(), limit.timeRequest());
        }
        // 返回请求数量大于0说明不被限流
        if (maxRequest > 0) {
            // 放行，执行后续方法
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throw new BusinessException(throwable.getMessage());
            }
        } else {
            // 直接返回响应结果
            throw new BusinessException("请求拥挤，请稍后重试！");
        }
        return result;
    }

    /**
     * 执行方法前再执行
     *
     * @param limit 限制
     */
    @Before("aspect() && @annotation(limit)")
    public void before(Limit limit) {
        // logger.info("before");
    }

    /**
     * 执行方法后再执行
     *
     * @param limit 限制
     */
    @After("aspect() && @annotation(limit)")
    public void after(Limit limit) {
        // logger.info("after");
    }
}
