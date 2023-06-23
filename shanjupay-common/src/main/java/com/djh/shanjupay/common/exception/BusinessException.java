package com.djh.shanjupay.common.exception;


import com.djh.shanjupay.common.interfaces.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务异常
 *
 * @author MrMyHui
 * @date 2021/03/26
 */
@Slf4j
public class BusinessException extends RuntimeException {

	
	private static final long serialVersionUID = 5565760508056698922L;
	
	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public BusinessException() {
		super();
	}

	public BusinessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public BusinessException(ErrorCode errorCode, String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.errorCode = errorCode;
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public BusinessException(ErrorCode errorCode, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.errorCode = errorCode;
	}

	public BusinessException(String arg0) {
		super(arg0);
	}
	
	public BusinessException(ErrorCode errorCode, String arg0) {
		super(arg0);
		this.errorCode = errorCode;
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}
	
	public BusinessException(ErrorCode errorCode, Throwable arg0) {
		super(arg0);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public static boolean paramVerificationEx(BindingResult result) {
		if (result.hasErrors()) {
			List<String> errorStrArr = result.getFieldErrors()
					.stream()
					.map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			log.error(errorStrArr.get(0));
			return true;
		}
		return false;
	}
}
