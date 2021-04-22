package com.djh.shanjupay;


import com.djh.shanjupay.common.config.LogAspect;
import com.djh.shanjupay.common.config.RedisConfig;
import com.djh.shanjupay.common.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * sj支付短信应用程序
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportAutoConfiguration(value = {LogAspect.class, RedisConfig.class, WebMvcConfig.class})
public class SjPaySmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjPaySmsApplication.class, args);
    }

}