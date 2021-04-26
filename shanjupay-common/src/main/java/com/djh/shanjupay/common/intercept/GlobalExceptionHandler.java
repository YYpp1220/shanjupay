package com.djh.shanjupay.common.intercept;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 全局异常处理程序
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public RestResponse<Nullable> exceptionGet(HttpServletRequest req, HttpServletResponse response , Exception e) {
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			if (CommonErrorCode.CUSTOM.equals(be.getErrorCode())){
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getMessage());
			} else {
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
			}

		} else if (e instanceof NoHandlerFoundException){
			return new RestResponse<Nullable>(404, "找不到资源");
		} else if (e instanceof HttpRequestMethodNotSupportedException){
			return new RestResponse<Nullable>(405, "method 方法不支持");
		} else if (e instanceof HttpMediaTypeNotSupportedException){
			return new RestResponse<Nullable>(415, "不支持媒体类型");
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
			BindingResult result = validException.getBindingResult();
			List<String> fieldErrorList = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
			log.error("【参数校验异常】{}", validException);
			return new RestResponse<Nullable>(425, fieldErrorList.get(0));
		}

		log.error("【系统异常】{}", e);
		return  new RestResponse<Nullable>(CommonErrorCode.UNKNOWN.getCode(), CommonErrorCode.UNKNOWN.getDesc());
	}

}
