package com.djh.shanjupay.limit.client.handler;

import com.djh.shanjupay.limit.client.config.LimitRule;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * 限流配置处理器
 * <li>处理配置方式限流</li>
 *
 * @author MyMrDiao
 * @date 2022/03/27
 */
@Slf4j
public class ConfigLimitHandler extends AbstractLimitHandler<Object> implements MethodInterceptor {

    @Nullable
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 获取被拦截的类名
        String className = methodInvocation.getClass().getName();

        // 获取被拦截的方法名
        String methodName = methodInvocation.getMethod().getName();
        String defaultKey = StringUtils.join(className, "_", methodName);

        // 获取配置文件中的限流规则
        LimitRule limitRule = limitConfig.getLimitRule();

        // 如果需要做特殊处理
        if (limitConfig.getLimitMethodMap().containsKey(methodName)) {
            limitRule = limitConfig.getLimitMethodMap().get(methodName);
        }
        limitRule.setDefaultKey(defaultKey);
        log.info("[ConfigLimitHandler限流交易请求],尝试获取方法:{},执行权限", defaultKey);
        return handle(limitRule, () -> {
            try {
                return methodInvocation.proceed();
            } catch (Throwable throwable) {
                log.error("[ConfigLimitHandler限流交易请求],方法执行异常",throwable);
            }
            return null;
        });
    }
}
