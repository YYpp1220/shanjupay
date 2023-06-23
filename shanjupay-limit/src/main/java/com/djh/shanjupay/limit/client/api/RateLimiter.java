package com.djh.shanjupay.limit.client.api;

import com.djh.common.xkernel.spi.Spi;
import com.djh.shanjupay.limit.client.config.LimitConfig;
import com.google.common.collect.ImmutableList;

import java.util.concurrent.TimeUnit;

/**
 * 速度限制器
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Spi(LimitConfig.DEFAULT_LIMIT_IMPL)
public interface RateLimiter {
    /**
     * 默认的时间单位（秒）
     */
    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 默认限流时间范围
     */
    int DEFAULT_PERIOD = 1;

    /**
     * 默认的限流数量
     */
    int DEFAULT_LIMIT_COUNT = 10;

    /**
     * 尝试获取
     *
     * @param keys  key
     * @param count 限流数量
     * @return boolean
     */
    boolean tryAcquire(ImmutableList<String> keys, int count);

    /**
     * 尝试获取
     *
     * @param keys   key
     * @param count  限流数量
     * @param period 限流时间范围
     * @return boolean
     */
    boolean tryAcquire(ImmutableList<String> keys, int count, int period);

    /**
     * 尝试获取
     *
     * @param keys     key
     * @param count    限流数量
     * @param period   限流时间范围
     * @param timeUnit 时间单位
     * @return boolean
     */
    boolean tryAcquire(ImmutableList<String> keys, int count, int period, TimeUnit timeUnit);
}
