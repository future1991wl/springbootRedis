package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.utils.DesUtil;
import com.zw.zwpp.utils.RedisUtil;
import com.zw.zwpp.utils.SmsSendUtil;
import com.zw.zwpp.vo.reqvo.SmsReqVo;
import com.zw.zwpp.vo.reqvo.UserReqVo;


@RestController
public class ZYSmsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SmsSendUtil smsSendUtil;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserService userService;
	@RequestMapping("/sms")
	public BaseResponse smsSendMessage(HttpServletRequest request) {
		String s = request.getParameter("s");
		logger.info("发送短信入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析发送短信入参：{}",reqStr);
		JSONObject parseObject = JSONObject.parseObject(reqStr);
		SmsReqVo req = JSONObject.toJavaObject(parseObject, SmsReqVo.class);
		BaseResponse baseResponse = new BaseResponse();
		String contentType = req.getContentType();
		String phone = req.getPhone();
		//注册已存在就不发短信了
		User user = userService.findByName(phone);
		if("1".equals(contentType)) {
			if(user != null) {
				baseResponse.setMsg("注册该用户名已经存在！");
				baseResponse.setCode(500);
				logger.info("注册该用户名已经存在！");
				return baseResponse;
			}
		}else if("2".equals(contentType)){
			//改密判断是否已存在用户
			if(user == null) {
				baseResponse.setMsg("改密该用户名不存在！");
				logger.info("改密该用户名不存在！");
				baseResponse.setCode(500);
				return baseResponse;
			}
		}
		String code = smsSendUtil.getRandomCode();
		
		redisUtil.set(phone, code, 120);
		try {
			String resp = smsSendUtil.sendRandomCode(phone, code, contentType);
			if(!StringUtils.isEmpty(resp)) {
				JSONObject resJson = JSONObject.parseObject(resp);
				String respCode = resJson.getString("respCode");
				logger.info("respCode:{}",respCode);
				if(!"00000".equals(respCode)) {
					baseResponse.setCode(500);
					baseResponse.setMsg(resJson.getString("respDesc"));
				}
			}else {
				baseResponse.setCode(500);
				baseResponse.setMsg("连接短信平台异常");
				logger.info("连接短信平台异常");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			baseResponse.setCode(500);
			baseResponse.setMsg("sig加密异常");
			logger.info("sig加密异常");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			baseResponse.setCode(500);
			baseResponse.setMsg("sig加密异常");
			logger.info("sig加密异常");
		}
		return baseResponse;
	}
}
