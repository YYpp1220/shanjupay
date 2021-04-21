package com.djh.shanjupay.sms.config;


import com.djh.shanjupay.sms.handler.AbstractVerificationHandler;
import com.djh.shanjupay.sms.handler.SmsNumberVerificationHandler;
import com.djh.shanjupay.sms.sms.qcloud.TencentCloudSmsServiceImpl;
import com.djh.shanjupay.sms.store.VerificationStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务配置
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Configuration
public class BusinessConfig {

	@Autowired
	private VerificationStore verificationStore;

	@Autowired
	private TencentCloudSmsServiceImpl qCloudSmsService;

	/**
	 * 短信验证处理器数量
	 *
	 * @return {@link SmsNumberVerificationHandler}
	 */
	@Bean
	public SmsNumberVerificationHandler smsNumberVerificationHandler() {
		SmsNumberVerificationHandler smsNumberVerificationHandler = new SmsNumberVerificationHandler("sms", 6);
		smsNumberVerificationHandler.setVerificationStore(verificationStore);
		smsNumberVerificationHandler.setSmsService(qCloudSmsService);
		return smsNumberVerificationHandler;
	}

	/**
	 * 验证处理程序映射
	 *
	 * @return {@link Map<String, AbstractVerificationHandler>}
	 */
	@Bean
	public Map<String, AbstractVerificationHandler> verificationHandlerMap() {
		List<AbstractVerificationHandler> verificationHandlerList = new ArrayList<>();
		verificationHandlerList.add(smsNumberVerificationHandler());

		Map<String, AbstractVerificationHandler> verificationHandlerMap = new HashMap<>();
		for (AbstractVerificationHandler handler : verificationHandlerList) {
			verificationHandlerMap.put(handler.getName(), handler);
		}
		return verificationHandlerMap;
	}

}
