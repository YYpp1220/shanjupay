package com.djh.shanjupay.limit.client.handler;

import com.djh.shanjupay.limit.client.config.LimitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 限流切面Advisor
 *
 * @author MyMrDiao
 * @date 2022/04/10
 */
@Slf4j
public class LimitPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Autowired
    private LimitConfig limitConfig;

    /**
     * 获取切面表达式
     *
     * @return {@link Pointcut}
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public Pointcut getPointcut() {
        AspectJExpressionPointcut adapterPointcut = new AspectJExpressionPointcut();
        // 从配置文件中获取pointCut表达式
        adapterPointcut.setExpression(limitConfig.getExpression());
        return adapterPointcut;
    }
}
