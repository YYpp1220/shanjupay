/*
package com.djh.shanjupay.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * swagger配置类
 *
 * @author MrMyHui
 * @date 2021/04/16
 *//*

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket () {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 要扫描的API（controller）的基础包
                .apis(RequestHandlerSelectors.basePackage("com.djh.shanjupay.*.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo() {
        Contact contact = new Contact("开发者", "", "");
        return new ApiInfoBuilder()
                .title("闪聚支付-商户应用API文档")
                .description("")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
*/
