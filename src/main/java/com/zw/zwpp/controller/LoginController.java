package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.utils.DesUtil;
import com.zw.zwpp.utils.JwtUtil;
import com.zw.zwpp.utils.RedisUtil;
import com.zw.zwpp.utils.TokenUtil;
import com.zw.zwpp.vo.reqvo.UserReqVo;


@Controller
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private TokenUtil tokenUtil;
	@ResponseBody
	@PostMapping("/login")
	public BaseResponse login(UserReqVo reqVo) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse resp = new BaseResponse();
		String name = reqVo.getName();
		String reqPassword = reqVo.getPassword();
		if(StringUtils.isBlank(name) || StringUtils.isBlank(reqPassword)) {
			resp.setCode(500);
			resp.setMsg("用户名或密码为空！");
			return resp;
		}
		String encoderByMd5 = DesUtil.EncoderByMd5(reqPassword);
		User  u = userService.findByName(name);
		if(u != null) {
			String password = u.getPassword();
			if(encoderByMd5.equals(password)) {
				String token = jwtUtil.getToken(name);
				JSONObject json = new JSONObject();
				json.put("name", name);
				json.put("partId", u.getId());
				redisUtil.set("token:"+token, json.toJSONString(), 1800);
				String parseToken = jwtUtil.parseToken(token, String.class);
				System.out.println(token + parseToken);
				resp.setData(token);
				return resp;
			}else {
				resp.setCode(500);
				resp.setMsg("用户密码有误！");
			}
			
		}else {
			resp.setCode(500);
			resp.setMsg("用户名不存在！");
		}
		return resp;
	}
	@ResponseBody
	@PostMapping("/signIn")
	public BaseResponse signIn(UserReqVo reqVo) {
		BaseResponse resp = new BaseResponse();
		User u = new User();
		String password = "";
		try {
			password = DesUtil.EncoderByMd5(reqVo.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("密码加密异常：{} ",e.getMessage());
			resp.setCode(500);
			resp.setMsg("密码加密异常");
			return resp;
		}
		u.setName(reqVo.getName());
		u.setPassword(password);
		User  oldUser = userService.findByName(reqVo.getName());
		if(oldUser != null) {
			resp.setMsg("已存在该用户名！");
			resp.setCode(500);
			return resp;
		}
		User insertUser = userService.insert(u);
		if(insertUser != null) {
			return resp;
		}else {
			resp.setMsg("注册客户失败");
			resp.setCode(500);
			return resp;
		}
	}
	@PostMapping("/test")
	public BaseResponse test(@RequestParam String token) {
		BaseResponse resp = new BaseResponse(); 
		long userId = tokenUtil.checkToken(token);
		return resp;
		
	}
}
