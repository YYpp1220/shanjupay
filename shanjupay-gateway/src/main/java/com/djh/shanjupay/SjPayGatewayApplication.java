package com.djh.shanjupay;

import com.djh.shanjupay.common.config.LogAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * sj网关应用程序
 *
 * @author MyMrDiao
 * @date 2021/06/13
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ImportAutoConfiguration(value = {LogAspect.class})
public class SjPayGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SjPayGatewayApplication.class, args);
    }
}
