package com.djh.shanjupay.common.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录用户
 *
 * @author MyMrDiao
 * @date 2021/05/16
 */
@Data
public class LoginUser {
	private String mobile;
	private Map<String, Object> payload = new HashMap<>();
	private String clientId;
	private String username;
}
