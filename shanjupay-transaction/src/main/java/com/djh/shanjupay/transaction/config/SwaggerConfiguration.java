package com.djh.shanjupay.transaction.config;

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

/**
 * 昂首阔步的配置
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
public class SwaggerConfiguration {

	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo())
				.select()
				// 要扫描的API(Controller)基础包
				.apis(RequestHandlerSelectors.basePackage("com.djh.shanjupay.transaction.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * 构建api的信息
	 *
	 * @return springfox.documentation.service.ApiInfo
	 * @Title: 构建API基本信息
	 * @methodName: buildApiInfo
	 */
	private ApiInfo buildApiInfo() {
		Contact contact = new Contact("开发者","","");
		return new ApiInfoBuilder()
				.title("聚宇交易服务API文档")
				.description("包含商户交易相关api")
				.contact(contact)
				.version("1.0.0").build();
	}

}
