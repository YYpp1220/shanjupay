package com.djh.shanjupay.limit.client.enums;

/**
 * 限流类型
 *
 * @author MyMrDiao
 * @date 2022/03/17
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,

    /**
     * 请求者IP
     */
    IP;
}
