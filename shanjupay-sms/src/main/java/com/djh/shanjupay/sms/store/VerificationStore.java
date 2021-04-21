package com.djh.shanjupay.sms.store;

/**
 * 验证信息存储 kv
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public interface VerificationStore{


    /**
     * 集
     *
     * @param key    关键
     * @param value  价值
     * @param expire 到期
     */
    void set(String key, String value, Integer expire);


    /**
     * 得到
     *
     * @param key 关键
     * @return {@link String}
     */
    String get(String key);


}
