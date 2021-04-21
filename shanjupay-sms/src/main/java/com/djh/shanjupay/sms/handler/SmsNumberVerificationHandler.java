package com.djh.shanjupay.sms.handler;

import com.djh.shanjupay.sms.generator.NumberVerificationCodeGenerator;
import com.djh.shanjupay.sms.generator.UUIDVerificationKeyGenerator;
import com.djh.shanjupay.sms.generator.VerificationCodeGenerator;
import com.djh.shanjupay.sms.generator.VerificationKeyGenerator;
import com.djh.shanjupay.sms.sms.SmsService;
import com.djh.shanjupay.sms.store.VerificationStore;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 短信(数字随机验证码)处理器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Slf4j
public class SmsNumberVerificationHandler extends AbstractVerificationHandler {

	private String name;

	private int len;

	private VerificationStore verificationStore;

	private VerificationKeyGenerator verificationKeyGenerator;

	private VerificationCodeGenerator verificationCodeGenerator;

	private SmsService smsService;

	public SmsNumberVerificationHandler(String name, int len) {
		this.name = name;
		this.len = len;
		verificationKeyGenerator = new UUIDVerificationKeyGenerator();
		verificationCodeGenerator = new NumberVerificationCodeGenerator(len);
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public VerificationStore getVerificationStore() {
		return verificationStore;
	}

	public void setVerificationStore(VerificationStore verificationStore) {
		this.verificationStore = verificationStore;
	}

	@Override
	public VerificationKeyGenerator getVerificationKeyGenerator() {
		return verificationKeyGenerator;
	}

	@Override
	public VerificationCodeGenerator getVerificationCodeGenerator() {
		return verificationCodeGenerator;
	}

	@Override
	public int getEffectiveTime() {
		return 300;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@Override
	String confusion(Map<String, Object> payload, String key, String code) {
		String mobile = String.valueOf(payload.get("mobile"));

		// 使用腾讯云发送短信
//		smsService.send(mobile, code, getEffectiveTime());

		// 测试使用，在控制台输出验证码
		smsService.sendOnConsole(mobile, code, getEffectiveTime());
		return null;
	}


}
