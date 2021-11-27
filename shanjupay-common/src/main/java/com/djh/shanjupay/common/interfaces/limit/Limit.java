package com.djh.shanjupay.common.interfaces.limit;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author Administrator
 * @date 2021/11/23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Limit {
    /**
     * 限流最大请求数
     *
     * @return {@link String}
     */
    String maxRequest() default "10";

    /**
     * 一个时间窗口（毫秒）
     *
     * @return {@link String}
     */
    String timeRequest() default "1000";
}
