package com.djh.shanjupay.sms.generator;

/**
 * 认证码生成器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public interface VerificationCodeGenerator {

    /**
     * 认证码生成
     *
     * @return {@link String}
     */
    String generate();

}
