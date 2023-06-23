package com.djh.shanjupay.limit.client.internal.redis;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * RedisTemplate配置类
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Slf4j
@Configuration
public class LimitRedisConfig {
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer<Serializable> fastJsonRedisSerializer = new FastJsonRedisSerializer(Serializable.class);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat(DATA_FORMAT);
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置key的序列化方式为StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
