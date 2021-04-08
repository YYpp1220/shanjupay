package com.djh.shanjupay.common.interfaces.error;

/**
 * 错误代码
 *
 * @author MrMyHui
 * @date 2021/03/26
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
