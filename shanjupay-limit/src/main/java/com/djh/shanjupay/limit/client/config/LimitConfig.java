package com.djh.shanjupay.limit.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口限流配置
 *
 * @author MyMrDiao
 * @date 2022/03/17
 */
@SuppressWarnings("ConfigurationProperties")
@ConfigurationProperties(prefix = LimitConfig.PREFIX)
public class LimitConfig {
    /**
     * 前缀
     */
    public static final String PREFIX = "limit";

    /**
     * 前缀
     */
    public static final String EXP = "expression";

    /**
     * limit是否可用，默认值
     */
    public static final String ENABLED = "enabled";

    /**
     * 降级策略实现类，没有就是默认实现
     */
    public static final String DEFAULT_IMPL = "default";

    /**
     * 限流算法实现，没有就是默认实现
     */
    public static final String DEFAULT_LIMIT_IMPL = "default";

    /**
     * PointCut表达式，默认值
     */
    public static final String DEFAULT_EXP = "";

    /**
     * limit是否可用
     */
    private String enabled = ENABLED;

    /**
     * 实现
     */
    private String impl = DEFAULT_IMPL;

    private String limitImpl = DEFAULT_LIMIT_IMPL;

    /**
     * PointCut表达式
     */
    private String expression = DEFAULT_EXP;

    /**
     * 全局限流规则配置
     */
    @NestedConfigurationProperty
    private LimitRule limitRule = new LimitRule().initialize();

    /**
     * 特殊接口限流规则配置Map
     */
    private Map<String, LimitRule> limitMethodMap = new HashMap<>(5);

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public LimitRule getLimitRule() {
        return limitRule;
    }

    public void setLimitRule(LimitRule limitRule) {
        this.limitRule = limitRule;
    }

    public Map<String, LimitRule> getLimitMethodMap() {
        return limitMethodMap;
    }

    public void setLimitMethodMap(Map<String, LimitRule> limitMethodMap) {
        this.limitMethodMap = limitMethodMap;
    }

    public String getLimitImpl() {
        return limitImpl;
    }

    public void setLimitImpl(String limitImpl) {
        this.limitImpl = limitImpl;
    }
}
