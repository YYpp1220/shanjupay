package com.djh.shanjupay.limit.client.internal.fallback;

import com.djh.shanjupay.limit.client.api.Fallback;

/**
 * 服务降级回退处理接口默认实现
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
public class DefaultFallback implements Fallback<Object> {
    /**
     * 服务降级处理
     *
     * @return {@link Object}
     */
    @Override
    public Object getFallback() {
        return null;
    }
}
