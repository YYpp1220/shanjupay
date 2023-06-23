package com.djh.shanjupay.limit.client.internal.redis;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流 固定窗口算法实现类
 * <p>说明:</p>
 * <li>固定窗口，一般来说，如非时间紧迫，不建议选择这个方案，太过生硬。但是，为了能快速止损眼前的问题可以作为临时应急的方案</li>
 * <li>固定窗口有一点需要注意的是，假如请求的进入非常集中，那么所设定的「限流阈值」等同于你需要承受的最大并发数。
 * 所以，如果需要顾忌到并发问题，那么这里的「固定周期」设定的要尽可能的短。因为，这样的话「限流阈值」的数值就可以相应的减小。
 * 甚至，限流阈值就可以直接用并发数来指定。比如，假设固定周期是 3 秒，那么这里的阈值就可以设定为「平均并发数 *3」。
 * 不过不管怎么设定，固定窗口永远存在的缺点是：由于流量的进入往往都不是一个恒定的值，所以一旦流量进入速度有所波动，
 * 要么计数器会被提前计满，导致这个周期内剩下时间段的请求被“限制”。要么就是计数器计不满，也就是「限流阈值」设定的过大，导致资源无法充分利用</li>
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Slf4j
public class FixedWindowRateLimiter extends AbstractRateLimiter {
    @Override
    protected boolean acquire(ImmutableList<String> keys, int limitCount, int limitPeriod, TimeUnit timeUnit) {
        Long count = redisTemplate.execute(redisLuaScript, keys, limitCount, limitPeriod);
        log.info("[固定窗口限流交易请求],key:{},返回:{},{}秒内,限制次数:{}", keys, count, limitPeriod, limitCount);
        return count != null && count.intValue() == 1;
    }

    @Override
    protected String buildLuaScript() {
        return loadLuaScript("classpath:redis/script/FixedWindow.lua");
    }
}
