package com.djh.shanjupay;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.djh.shanjupay.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 闪聚支付启动类
 *
 * @author MrMyHui
 * @date 2021/04/02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.djh.shanjupay.merchant.mapper")
@ImportAutoConfiguration(value = {LogAspect.class})
public class SjPayMerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(SjPayMerchantApplication.class, args);
    }

    /*** 分页插件，自动识别数据库类型 */
    @Bean
    public PaginationInterceptor paginationInterceptor () {
        return new PaginationInterceptor();
    }

    /*** 启用性能分析插件 */
    @Bean
    public PerformanceInterceptor performanceInterceptor () {
        return new PerformanceInterceptor();
    }
}
