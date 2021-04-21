package com.djh.shanjupay.sms.generator;

/**
 * 验证key生成器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public interface VerificationKeyGenerator {
    /**
     * 生成
     *
     * @param prefix 前缀
     * @return {@link String}
     */
    String generate(String prefix);
}
