package com.djh.shanjupay.limit.starter;

import com.djh.common.xkernel.spi.ExtensionLoader;
import com.djh.shanjupay.limit.client.api.RateLimiter;
import com.djh.shanjupay.limit.client.config.LimitConfig;
import com.djh.shanjupay.limit.client.handler.AnnotationLimitHandler;
import com.djh.shanjupay.limit.client.handler.ConfigLimitHandler;
import com.djh.shanjupay.limit.client.handler.LimitPointcutAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * 自动配置类
 *
 * @author MyMrDiao
 * @date 2022/04/04
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LimitConfig.class)
@ConditionalOnClass(RateLimiter.class)
@ConditionalOnProperty(prefix = LimitConfig.PREFIX, value = LimitConfig.ENABLED, matchIfMissing = true)
public class LimitAutoConfiguration {
    @Autowired
    private LimitConfig limitConfig;

    /**
     * 限流实现类
     */
    @Value("${limit-rule.limitImpl}")
    private String limitImpl;

    @Bean
    @ConditionalOnMissingBean(RateLimiter.class)
    public RateLimiter createRateLimiter() {
        log.info("初始化分布式限流对象,实现类名称:{}",limitConfig.getImpl());
        if (StringUtils.isEmpty(limitImpl)) {
            RateLimiterHolder.rateLimiter = ExtensionLoader.getExtensionLoader(RateLimiter.class).getExtension(limitConfig.getImpl());
        } else {
            RateLimiterHolder.rateLimiter = ExtensionLoader.getExtensionLoader(RateLimiter.class).getExtension(limitImpl);
        }
        log.info("初始化分布式限流对象成功,实现类:{}", RateLimiterHolder.rateLimiter);
        return RateLimiterHolder.rateLimiter;
    }

    @Bean
    @ConditionalOnProperty(prefix = LimitConfig.PREFIX, value = LimitConfig.EXP)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LimitPointcutAdvisor adapterServiceAdvisor() {
        LimitPointcutAdvisor advisor = new LimitPointcutAdvisor();
        advisor.setAdviceBeanName("limitPointcutAdvisor");
        advisor.setAdvice(createConfigLimitHandler());
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    @Bean
    public ConfigLimitHandler createConfigLimitHandler() {
        return new ConfigLimitHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LimitPointcutAdvisor.class)
    public AnnotationLimitHandler createAnnotationLimitHandler() {
        return new AnnotationLimitHandler();
    }

}
