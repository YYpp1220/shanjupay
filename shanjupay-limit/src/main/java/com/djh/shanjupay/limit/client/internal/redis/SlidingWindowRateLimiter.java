package com.djh.shanjupay.limit.client.internal.redis;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流 滑动窗口算法实现类
 * <p>说明:</p>
 * <li>滑动窗口。这个方案适用于对异常结果「高容忍」的场景，毕竟相比“两窗”少了一个缓冲区。但是，胜在实现简单</li>
 * <li>该算法中会将一个时间窗口划分成一个一个的小区间，在每一个区间内对请求数进行统计，然后进行加和，
 * 这种算法避免了固定窗口计数器带来的双倍突发请求，当新的窗口区间到来时就抛弃最老的一个区间，
 * 如果想要提高精度就需要将单位时间窗口内区间划分的越多。
 * 滑动窗口其实就是对固定窗口做了进一步的细分，将原先的粒度切的更细，比如 1 分钟的固定窗口切分为 60 个 1 秒的滑动窗口。然后统计的时间范围随着时间的推移同步后移。
 * 同时，我们还可以得出一个结论是：如果固定窗口的「固定周期」已经很小了，那么使用滑动窗口的意义也就没有了。举个例子，
 * 现在的固定窗口周期已经是 1 秒了，再切分到毫秒级别能反而得不偿失，会带来巨大的性能和资源损耗。
 * 虽然说滑动窗口可以改善这个问题，但是本质上还是预先划定时间片的方式，属于一种“预测”，意味着几乎肯定无法做到 100% 的物尽其用.</li>
 * @author MyMrDiao
 * @date 2022/03/20
 */
@Slf4j
public class SlidingWindowRateLimiter extends AbstractRateLimiter {
    @Override
    protected boolean acquire(ImmutableList<String> keys, int limitCount, int limitPeriod, TimeUnit timeUnit) {
        Long count = redisTemplate.execute(redisLuaScript, keys, 1, limitCount, limitPeriod, timeUnit);
        log.info("[滑动窗口限流交易请求],key:{},{}秒内,返回数量:{},{}秒内,限制次数:{}", keys, limitPeriod, count, limitPeriod, limitCount);
        return count != null && count.intValue() > 0;
    }

    @Override
    protected String buildLuaScript() {
        return loadLuaScript("classpath:redis/script/SlidingWindow.lua");
    }
}
