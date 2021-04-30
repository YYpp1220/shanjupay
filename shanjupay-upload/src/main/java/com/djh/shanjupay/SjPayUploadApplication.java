package com.djh.shanjupay;

import com.djh.shanjupay.common.config.LogAspect;
import com.djh.shanjupay.common.config.RedisConfig;
import com.djh.shanjupay.common.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * sj支付上传应用程序
 *
 * @author MrMyHui
 * @date 2021/04/30
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.djh.shanjupay.upload.mapper")
@ImportAutoConfiguration(value = {LogAspect.class, RedisConfig.class, WebMvcConfig.class})
public class SjPayUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(SjPayUploadApplication.class, args);
    }
}
