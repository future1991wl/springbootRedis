package com.zw.zwpp.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zw.zwpp.base.BaseResponse;

//@ControllerAdvice
public class MyExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler
	@ResponseBody
	public BaseResponse defaultErrorHandler(HttpServletRequest req, Exception e) {
		BaseResponse resp = new BaseResponse();
		if(e instanceof CommonRuntiontimeException) {
			CommonRuntiontimeException commonExce =	(CommonRuntiontimeException)e;
			resp.setCode(500);
			resp.setMsg(commonExce.getMessage());
			logger.info("通用运行时异常：{}",commonExce.getMessage());
		}
		return resp;
		
	}
}
