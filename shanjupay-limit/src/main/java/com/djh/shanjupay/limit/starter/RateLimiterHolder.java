package com.djh.shanjupay.limit.starter;

import com.djh.shanjupay.limit.client.api.RateLimiter;

import java.util.Optional;

/**
 * 限流接口对象持有者
 *
 * @author MyMrDiao
 * @date 2022/04/04
 */
public class RateLimiterHolder {
    /** 限流对象*/
    static RateLimiter rateLimiter;

    public static Optional<RateLimiter> getRateLimiter() {
        return Optional.ofNullable(rateLimiter);
    }
}
