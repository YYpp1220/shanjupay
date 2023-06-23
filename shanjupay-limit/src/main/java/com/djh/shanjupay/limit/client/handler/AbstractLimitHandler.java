package com.djh.shanjupay.limit.client.handler;

import com.djh.common.xkernel.spi.ExtensionLoader;
import com.djh.shanjupay.limit.client.api.Fallback;
import com.djh.shanjupay.limit.client.api.RateLimiter;
import com.djh.shanjupay.limit.client.config.LimitConfig;
import com.djh.shanjupay.limit.client.config.LimitRule;
import com.djh.shanjupay.limit.client.enums.FallbackStrategy;
import com.djh.shanjupay.limit.client.exception.LimitException;
import com.djh.shanjupay.limit.client.util.IPUtil;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

/**
 * 抽象限流处理器
 *
 * @author MyMrDiao
 * @date 2022/03/20
 */
@Slf4j
public abstract class AbstractLimitHandler<R> {
    @Autowired
    protected LimitConfig limitConfig;

    @Autowired
    private RateLimiter rateLimiter;

    protected R handle(LimitRule limitRule, Supplier<R> function) {
        // 限流规则整理
        handleLimitRule(limitRule);
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitRule.getPrefix(), limitRule.getKey()));
        try {
            if (rateLimiter.tryAcquire(keys,limitRule.getCount(), limitRule.getPeriod())) {
                log.info("[限流交易请求],尝试获取执行权限成功,开始执行目标方法");
                return function.get();
            }else{
                log.info("[限流交易请求],尝试获取执行权限失败,服务降级处理,降级策略:{}",limitRule.getFallbackStrategy());
                if(FallbackStrategy.FAIL_FAST.equals(limitRule.getFallbackStrategy())){
                    throw new LimitException("访问过于频繁,超出访问限制");
                }
                //获取回退处理接口实现:如果为空则使用默认实现
                Fallback<R> fallback = ExtensionLoader.getExtensionLoader(Fallback.class).getExtension(limitRule.getFallbackImpl());
                fallback = fallback != null ? fallback : ExtensionLoader.getExtensionLoader(Fallback.class).getDefaultExtension();
                return fallback.getFallback();
            }
        } catch (Throwable e) {
            log.error("[限流服务执行异常]",e);
            if (e instanceof LimitException) {
                throw e;
            }
            throw new LimitException("限流服务执行异常",e);
        }
    }

    /**
     * 限流规则整理
     *
     * @param limitRule 限制规则
     */
    private void handleLimitRule(LimitRule limitRule) {
        //限流规则整理:如果为空则使用全局配置
        limitRule.setKey(StringUtils.isBlank(limitRule.getKey()) ? limitConfig.getLimitRule().getKey() : limitRule.getKey());
        limitRule.setCount(limitRule.getCount() == 0 ? limitConfig.getLimitRule().getCount() : limitRule.getCount());
        limitRule.setFallbackImpl(StringUtils.isBlank(limitRule.getFallbackImpl()) ? limitConfig.getLimitRule().getFallbackImpl() : limitRule.getFallbackImpl());
        limitRule.setFallbackStrategy(limitRule.getFallbackStrategy() == null ? limitConfig.getLimitRule().getFallbackStrategy() : limitRule.getFallbackStrategy());
        limitRule.setLimitType(limitRule.getLimitType() == null ? limitConfig.getLimitRule().getLimitType() : limitRule.getLimitType());
        limitRule.setPeriod(limitRule.getPeriod() == 0 ? limitConfig.getLimitRule().getPeriod() : limitRule.getPeriod());
        limitRule.setPrefix(StringUtils.isBlank(limitRule.getPrefix()) ? limitConfig.getLimitRule().getPrefix() : limitRule.getPrefix());

        String key = limitRule.getKey();
        //根据限流类型获取不同的key ,如果不传我们会以方法名作为key
        switch (limitRule.getLimitType()) {
            case IP:
                key = IPUtil.getIpAddress();
                break;
            case CUSTOMER:
                key = StringUtils.isBlank(key) ? StringUtils.upperCase(limitRule.getDefaultKey()) : key;
                break;
            default:
                key = StringUtils.upperCase(limitRule.getDefaultKey());
        }
        limitRule.setKey(key);
    }
}
