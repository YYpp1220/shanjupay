package com.djh.shanjupay.limit.client.internal.redis;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 分布式限流 漏桶算法实现类
 * <p>说明:</p>
 * <li>这个方案最适合作为一个通用方案。虽说资源的利用率上不是极致，但是「宽进严出」的思路在保护系统的同时还留有一些余地，使得它的适用场景更广</li>
 * <li>漏桶模式的核心是固定“出口”的速率，不管进来多少量，出去的速率一直是这么多。如果涌入的量多到桶都装不下了，那么就进行「流量干预」
 * 整个实现过程我们来分解一下。
 * 控制流出的速率。这个其实可以使用前面提到的两个“窗口”的思路来实现。如果当前速率小于阈值则直接处理请求，否则不直接处理请求，进入缓冲区，并增加当前水位。
 * 缓冲的实现可以做一个短暂的休眠或者记录到一个容器中再做异步的重试。
 * 最后控制桶中的水位不超过最大水位。这个很简单，就是一个全局计数器，进行加加减减。
 * 这样一来，你会发现本质就是：通过一个缓冲区将不平滑的流量“整形”成平滑的（高于均值的流量暂存下来补足到低于均值的时期），以此最大化计算处理资源的利用率。
 * 更优秀的「漏桶」策略已经可以在流量的总量充足的情况下发挥你所预期的 100% 处理能力，但这还不是极致。</li>
 * @author MyMrDiao
 * @date 2022/03/19
 */
@Slf4j
public class LeakyBucketRateLimiter extends AbstractRateLimiter {
    @Override
    protected boolean acquire(ImmutableList<String> keys, int count, int period, TimeUnit timeUnit) {
        return false;
    }

    @Override
    protected String buildLuaScript() {
        return loadLuaScript("classpath:redis/script/LeakyBucket.lua");
    }
}
