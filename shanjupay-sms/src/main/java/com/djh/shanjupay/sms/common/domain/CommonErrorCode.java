package com.djh.shanjupay.sms.common.domain;


/**
 * 常见的错误代码
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public enum CommonErrorCode implements ErrorCode {

	/**
	 * 成功
	 */
	SUCCESS(0, "成功"),

	/**
	 * 自定义
	 */
	CUSTOM(999998, "自定义异常"),

	/**
	 * 未知的
	 */
	UNKNOWN(999999, "未知错误");


	private int code;
	private String desc;

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	CommonErrorCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static CommonErrorCode setErrorCode(int code) {
		for (CommonErrorCode errorCode : CommonErrorCode.values()) {
			if (errorCode.getCode() == code) {
				return errorCode;
			}
		}
		return null;
	}
}
