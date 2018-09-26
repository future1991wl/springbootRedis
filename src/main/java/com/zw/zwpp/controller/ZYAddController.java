package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.utils.DesUtil;
import com.zw.zwpp.utils.JwtUtil;
import com.zw.zwpp.utils.RedisUtil;
import com.zw.zwpp.vo.reqvo.UserReqVo;


@Controller
public class ZYAddController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@ResponseBody
	@RequestMapping("/addM")
	public BaseResponse login(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String s = request.getParameter("s");
		logger.info("充值入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析充值入参：{}",reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		String name = reqJson.getString("name");
		double money = reqJson.getDouble("money");
		long time = reqJson.getLong("time");
		long nowTime = System.currentTimeMillis();
		BaseResponse resp = new BaseResponse();
		if((nowTime-time)>3000) {
			resp.setCode(500);
			resp.setMsg("调用充值接口超时，安全设置3秒内");
			logger.info("调用充值接口超时，安全设置3秒内");
			return resp;
		}
		User  u = userService.findByName(name);
		if(u==null) {
			resp.setCode(500);
			resp.setMsg("充值失败,不存在此用户!");
			logger.info("充值失败,不存在此用户!");
			return resp;
		}
		u.setMoney(u.getMoney()+money);
		Integer i = userService.updateMoneyByUser(u);
		if(i==null) {
			resp.setCode(500);
			resp.setMsg("充值失败");
			logger.info("充值失败");
			return resp;
		}
		resp.setCode(200);
		resp.setMsg("充值成功"+i+"个用户");
		logger.info("充值成功");
		return resp;
	}
}
