package com.djh.shanjupay.common.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.djh.shanjupay.common.util.RandomUuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * 日志方面
 *
 * @author MyMrDiao
 * @date 2020/10/06
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * com.djh.shanjupay.*.controller..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //日直编号
        MDC.put("UUID", RandomUuidUtil.getShortUuid());

        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        //打印业务操作
        String nameCn = "";
        if (name.contains("list") || name.contains("query")) {
            nameCn = "查询";
        } else if (name.contains("save") || name.contains("create")) {
            nameCn = "新增";
        } else if (name.contains("delete")) {
            nameCn = "删除";
        } else {
            nameCn = "操作";
        }

        //使用反射，获取业务名称
        Class clazz = signature.getDeclaringType();
        Field field;
        String businessName = "";
        try {
            field = clazz.getField("SHANJUPAY_NAME");
            if (!StringUtils.isEmpty(field)) {
                businessName = (String) field.get(clazz);
            }
        } catch (NoSuchFieldException e) {
            log.error("未获取到业务名称");
        } catch (SecurityException e) {
            log.error("获取业务名称失败", e);
        }

        //打印请求信息
        log.info("======================【{}】{}开始======================", businessName, nameCn);
        log.info("请求地址：{} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("类名方法：{} {}", signature.getDeclaringTypeName(), name);
        log.info("远程地址：{}", request.getRemoteAddr());

        //打印请求参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        //排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"shard"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        //为空不会打印，但是像图片等长字段也会打印
        if ("新增".equals(nameCn)) {
            log.info("请求参数：{}", JSONObject.toJSONString(arguments[0], excludeFilter));
        } else {
            log.info("请求参数：{}", JSONObject.toJSONString(arguments, excludeFilter));
        }
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "shard"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("返回结果：{}", JSONObject.toJSONString(result, excludeFilter));
        log.info("=============结束 耗时：{} ms=============", System.currentTimeMillis() - startTime);
        return result;
    }
}