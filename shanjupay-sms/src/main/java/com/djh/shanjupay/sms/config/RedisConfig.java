package com.djh.shanjupay.sms.config;


import com.djh.shanjupay.sms.common.cache.Cache;
import com.djh.shanjupay.sms.common.cache.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * redis配置
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Configuration
public class RedisConfig {
	
	@Bean
	public Cache cache(StringRedisTemplate redisTemplate){
		return new RedisCache(redisTemplate);
	}
	

}
