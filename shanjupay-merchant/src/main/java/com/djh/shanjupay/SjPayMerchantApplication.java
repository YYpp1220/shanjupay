package com.djh.shanjupay;

import org.springframework.boot.SpringApplication;
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
public class SjPayMerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(SjPayMerchantApplication.class, args);
    }
}
