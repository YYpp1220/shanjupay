package com.djh.shanjupay.sms.store;

import com.djh.shanjupay.common.interfaces.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis验证存储
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Component
public class RedisVerificationStore implements VerificationStore {

    @Autowired
    private Cache cache;

    @Override
    public void set(String key, String value, Integer expire) {
        cache.set(key,value,expire);
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }
}
