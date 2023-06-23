package com.djh.shanjupay.limit.client.api;

import com.djh.common.xkernel.spi.Spi;
import com.djh.shanjupay.limit.client.config.LimitConfig;

/**
 * 服务降级回退处理接口
 * SPI扩展接口
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Spi(LimitConfig.DEFAULT_IMPL)
public interface Fallback<R> {
    /**
     * 服务降级处理
     *
     * @return {@link R}
     */
    default R getFallback() {return null;}
}
