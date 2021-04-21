package com.djh.shanjupay.sms.common.domain;

/**
 * 错误代码
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public interface ErrorCode {

    /**
     * 获取代码
     *
     * @return int
     */
    int getCode();

    /**
     * 得到desc
     *
     * @return {@link String}
     */
    String getDesc();

}
