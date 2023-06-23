package com.djh.shanjupay.limit.client.enums;

/**
 * 降级策略
 *
 * @author MyMrDiao
 * @date 2022/03/17
 */
public enum FallbackStrategy {
    /**
     * 快速失败
     */
    FAIL_FAST,
    /**
     * 回退
     */
    FALLBACK;
}
