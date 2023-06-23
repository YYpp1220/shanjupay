package com.djh.shanjupay.limit.client.config;

import com.djh.shanjupay.limit.client.enums.FallbackStrategy;
import com.djh.shanjupay.limit.client.enums.LimitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * 限流规则配置
 * 单个接口方法限流规则配置
 *
 * @author MyMrDiao
 * @date 2022/03/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitRule {
    /**
     * 给定的时间范围，单位秒，默认值1
     */
    public static final int DEFAULT_PERIOD = 1;

    /**
     * 一定时间内最大访问次数，默认值50
     */
    public static final int DEFAULT_COUNT = 50;

    /**
     * 默认的key
     */
    private String defaultKey = "";

    /**
     * key
     */
    private String key;

    /**
     * key的前缀
     */
    private String prefix;

    /**
     * 限流给定的时间范围，默认单位（秒）
     */
    private int period;

    /**
     * 一定时间内接口可访问次数，也就是限流的次数
     */
    private int count;

    /**
     * 限流的类型，默认是自定义
     */
    private LimitType limitType;

    /**
     * 降级策略，默认快速失败，可自定义降级策略
     */
    private FallbackStrategy fallbackStrategy;

    /**
     * 如果当前降级策略为回退的话，那么需要自己实现，这就是回退的接口实现的名称
     */
    private String fallbackImpl;

    /**
     * 初始化限流规则配置
     *
     * @return {@link LimitRule}
     */
    public LimitRule initialize() {
        this.setPeriod(DEFAULT_PERIOD);
        this.setCount(DEFAULT_COUNT);
        this.setLimitType(LimitType.CUSTOMER);
        this.setFallbackStrategy(FallbackStrategy.FAIL_FAST);
        this.setFallbackImpl(LimitConfig.DEFAULT_IMPL);
        return this;
    }
}
