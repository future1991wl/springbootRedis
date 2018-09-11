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
import com.zw.zwpp.utils.RedisUtil;
import com.zw.zwpp.utils.SmsSendUtil;
import com.zw.zwpp.vo.reqvo.SmsReqVo;


@RestController
public class SmsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SmsSendUtil smsSendUtil;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserService userService;
	@RequestMapping("/sms")
	public BaseResponse smsSendMessage(HttpServletRequest request, SmsReqVo req) {
		BaseResponse baseResponse = new BaseResponse();
		String contentType = req.getContentType();
		String phone = req.getPhone();
		//注册已存在就不发短信了
		User user = userService.findByName(phone);
		if("1".equals(contentType)) {
			if(user != null) {
				baseResponse.setMsg("该用户名已经存在！");
				baseResponse.setCode(500);
				return baseResponse;
			}
		}else if("2".equals(contentType)){
			//改密判断是否已存在用户
			if(user == null) {
				baseResponse.setMsg("该用户名不存在！");
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
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			baseResponse.setCode(500);
			baseResponse.setMsg("sig加密异常");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			baseResponse.setCode(500);
			baseResponse.setMsg("sig加密异常");
		}
		return baseResponse;
	}
	@RequestMapping("haha")
	public String haha(Object json) {
		logger.info("haha入参：{}",json);
		return json.toString();
		
	}
}
