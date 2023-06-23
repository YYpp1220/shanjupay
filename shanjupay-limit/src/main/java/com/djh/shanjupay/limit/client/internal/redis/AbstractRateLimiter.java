package com.djh.shanjupay.limit.client.internal.redis;

import com.djh.shanjupay.limit.client.api.RateLimiter;
import com.djh.shanjupay.limit.client.exception.LimitException;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 抽象限流接口实现
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Slf4j
public abstract class AbstractRateLimiter implements RateLimiter {
    protected RedisScript<Long> redisLuaScript;

    @Autowired
    protected RedisTemplate<String, Serializable> redisTemplate;

    /**
     * init lua
     */
    @PostConstruct
    public void initLUA() {
        redisLuaScript = new DefaultRedisScript<>(buildLuaScript(), Long.class);
    }

    /**
     * 尝试获取
     *
     * @param keys  key
     * @param count 限流数量
     * @return boolean
     */
    @Override
    public boolean tryAcquire(ImmutableList<String> keys, int count) {
        return tryAcquire(keys, count, RateLimiter.DEFAULT_PERIOD);
    }

    /**
     * 尝试获取
     *
     * @param keys   key
     * @param count  限流数量
     * @param period 限流时间范围
     * @return boolean
     */
    @Override
    public boolean tryAcquire(ImmutableList<String> keys, int count, int period) {
        return tryAcquire(keys, count, period, RateLimiter.DEFAULT_TIME_UNIT);
    }

    /**
     * 尝试获取
     *
     * @param keys     key
     * @param count    限流数量
     * @param period   限流时间范围
     * @param timeUnit 时间单位
     * @return boolean
     */
    @Override
    public boolean tryAcquire(ImmutableList<String> keys, int count, int period, TimeUnit timeUnit) {
        return acquire(keys, count, period, timeUnit);
    }

    /**
     * 收购
     *
     * @param keys     key
     * @param count    限流数量
     * @param period   限流时间范围
     * @param timeUnit 时间单位
     * @return boolean
     */
    protected abstract boolean acquire(ImmutableList<String> keys, int count, int period, TimeUnit timeUnit);

    /**
     * 构建lua脚本
     *
     * @return {@link String}
     */
    protected abstract String buildLuaScript();

    /**
     * 负载lua脚本
     *
     * @param path 路径
     * @return {@link String}
     */
    protected String loadLuaScript(String path) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
            Resource[] resources = resolver.getResources(path);
            String luaScriptContent = StreamUtils.copyToString(resources[0].getInputStream(), StandardCharsets.UTF_8);
            log.info("完成加载lua脚本：{}", luaScriptContent);
            return luaScriptContent;
        } catch (IOException e) {
            e.printStackTrace();
            throw new LimitException("加载lua脚本异常", e);
        }
    }
}
