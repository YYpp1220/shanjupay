package com.djh.shanjupay.limit.client.internal.redis;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流 令牌桶算法实现类
 * <p>说明:</p>
 * <li>令牌桶。当你需要尽可能的压榨程序的性能（此时桶的最大容量必然会大于等于程序的最大并发能力），并且所处的场景流量进入波动不是很大（不至于一瞬间取完令牌，压垮后端系统）</li>
 * <li>令牌桶模式的核心是固定“进口”速率。先拿到令牌，再处理请求，拿不到令牌就被「流量干预」。因此，当大量的流量进入时，
 * 只要令牌的生成速度大于等于请求被处理的速度，那么此刻的程序处理能力就是极限。也来分解一下它的实现过程。
 * 控制令牌生成的速率，并放入桶中。这个其实就是单独一个线程在不断的生成令牌。
 * 控制桶中待领取的令牌水位不超过最大水位。这个和「漏桶」一样，就是一个全局计数器，进行加加减减。
 * 你可能也会想到，这样一来令牌桶的容量大小理论上就是程序需要支撑的最大并发数。的确如此，假设同一时刻进入的流量将令牌取完，但是程序来不及处理，将会导致事故发生。</li>
 * @author MyMrDiao
 * @date 2022/03/20
 */
@Slf4j
public class TokenBucketRateLimiter extends AbstractRateLimiter {
    @Override
    protected boolean acquire(ImmutableList<String> keys, int limitCount, int limitPeriod, TimeUnit timeUnit) {
        Long count = redisTemplate.execute(redisLuaScript, keys, limitCount, limitPeriod);
        log.info("[令牌桶限流交易请求],key:{},返回:{},{}秒内,限制次数:{}", keys, count, limitPeriod, limitCount);
        return count != null && count.intValue() == 1;
    }

    @Override
    protected String buildLuaScript() {
        return loadLuaScript("classpath:redis/script/TokenBucket.lua");
    }
}
