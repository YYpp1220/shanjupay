package com.djh.shanjupay;

import com.djh.shanjupay.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 闪聚支付启动类
 *
 * @author MrMyHui
 * @date 2021/04/02
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.djh.shanjupay.merchant.mapper")
@ImportAutoConfiguration(LogAspect.class)
public class SjPayMerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(SjPayMerchantApplication.class, args);
    }
}
