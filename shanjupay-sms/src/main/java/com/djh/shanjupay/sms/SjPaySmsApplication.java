package com.djh.shanjupay.sms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * sj支付短信应用程序
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@SpringBootApplication
@ImportAutoConfiguration(value = {})
public class SjPaySmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjPaySmsApplication.class, args);
    }

}