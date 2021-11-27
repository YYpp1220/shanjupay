package com.djh.shanjupay.common.interfaces.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis缓存
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public class RedisCache implements Cache {

	private StringRedisTemplate redisTemplate;

	public RedisCache(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Set<String> getKeys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public Set<String> getKeys() {
		return getKeys("*");
	}

	@Override
	public Boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public Long delMulti(String... delKeys) {
		return redisTemplate.opsForValue().getOperations().delete(Arrays.asList(delKeys));
	}

	@Override
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, String value, Integer expire) {
		redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
	}

	@Override
	public void mset(Map<String, String> map) {
		redisTemplate.opsForValue().multiSet(map);
	}

	@Override
	public Boolean mIsSet(Map<String, String> map) {
		return redisTemplate.opsForValue().multiSetIfAbsent(map);
	}

	@Override
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public List<String> mget(String... keys) {
		return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
	}

	@Override
	public Long ttl(String key) {
		return redisTemplate.opsForValue().getOperations().getExpire(key);
	}

	@Override
	public void expire(String key, int expire) {
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	@Override
	public void append(String key, String value) {
		redisTemplate.opsForValue().append(key, value);
	}

	@Override
	public String getSet(String key, String newValue) {
		return redisTemplate.opsForValue().getAndSet(key, newValue);
	}

	@Override
	public boolean setNx(String key, String value) {
		//noinspection ConstantConditions
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	@Override
	public Long incrBy(String key, Long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	@Override
	public Long decr(String key) {
		return redisTemplate.opsForValue().decrement(key);
	}

	@Override
	public Object eval(String script, List<String> keys, List<String> args) {
		RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
		return redisTemplate.execute(redisScript, keys, args);
	}
}
