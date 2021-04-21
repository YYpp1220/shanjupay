package com.djh.shanjupay.sms.generator;

import java.util.UUID;

/**
 * uuidverification键生成器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public class UUIDVerificationKeyGenerator implements VerificationKeyGenerator {
    @Override
    public String generate(String prefix) {
        String uuid = UUID.randomUUID().toString();
        return prefix + uuid.replaceAll("-", "");
    }
}
