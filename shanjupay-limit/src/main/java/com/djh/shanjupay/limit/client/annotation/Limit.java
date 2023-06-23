package com.djh.shanjupay.limit.client.annotation;

import com.djh.shanjupay.limit.client.enums.FallbackStrategy;
import com.djh.shanjupay.limit.client.enums.LimitType;

import java.lang.annotation.*;

/**
 * 自定义限流注解
 *
 * @author MyMrDiao
 * @date 2022/03/17
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {
    /**
     * 名称
     *
     * @return {@link String}
     */
    String name() default "";

    /**
     * 限流key
     *
     * @return {@link String}
     */
    String key() default "";

    /**
     * key的前缀
     *
     * @return {@link String}
     */
    String prefix() default "";

    /**
     * 限流给定的时间范围，默认单位（秒）
     *
     * @return int
     */
    int period();

    /**
     * 一定时间内接口可访问次数，也就是限流的次数
     *
     * @return int
     */
    int count();

    /**
     * 限流的类型，默认是自定义
     *
     * @return {@link LimitType}
     */
    LimitType limitType() default LimitType.CUSTOMER;

    /**
     * 降级策略，默认快速失败，可自定义降级策略
     *
     * @return {@link FallbackStrategy}
     */
    FallbackStrategy fallbackStrategy() default FallbackStrategy.FAIL_FAST;

    /**
     * 如果当前降级策略为回退的话，那么需要自己实现，这就是回退的接口实现的名称
     *
     * @return {@link String}
     */
    String fallbackImpl() default "";
}
