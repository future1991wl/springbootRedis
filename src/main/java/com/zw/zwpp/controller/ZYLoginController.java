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
public class ZYLoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisUtil redisUtil;
	@ResponseBody
	@RequestMapping("/login")
	public BaseResponse login(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String s = request.getParameter("s");
		logger.info("登陆入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析登陆入参：{}",reqStr);
		JSONObject parseObject = JSONObject.parseObject(reqStr);
		UserReqVo reqVo = JSONObject.toJavaObject(parseObject, UserReqVo.class);
		BaseResponse resp = new BaseResponse();
		String name = reqVo.getName();
		String reqPassword = reqVo.getPassword();
		if(StringUtils.isBlank(name) || StringUtils.isBlank(reqPassword)) {
			resp.setCode(500);
			resp.setMsg("用户名或密码为空！");
			logger.info("用户名或密码为空！");
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
				logger.info("登陆成功！：{}",resp);
				return resp;
			}else {
				resp.setCode(500);
				resp.setMsg("用户密码有误！");
				logger.info("用户密码有误！");
			}
			
		}else {
			resp.setCode(500);
			resp.setMsg("用户名不存在！");
			logger.info("用户名不存在！");
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping("/signIn")
	public BaseResponse signIn(HttpServletRequest request) {
		String s = request.getParameter("s");
		logger.info("注册入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析注册入参：{}",reqStr);
		JSONObject parseObject = JSONObject.parseObject(reqStr);
		UserReqVo reqVo = JSONObject.toJavaObject(parseObject, UserReqVo.class);
		BaseResponse resp = new BaseResponse();
		String vCode = (String)redisUtil.get(reqVo.getName());
		if(vCode == null) {
			resp.setCode(500);
			resp.setMsg("验证超时！");
			logger.info("验证超时！");
			return resp;
		}
		if(!vCode.equals(reqVo.getCode())) {
			resp.setCode(500);
			resp.setMsg("验证码输入有误！");
			logger.info("验证码输入有误！");
			return resp;
		}
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
			logger.info("已存在该用户名！");
			resp.setCode(500);
			return resp;
		}
		User insertUser = userService.insert(u);
		if(insertUser != null) {
			return resp;
		}else {
			resp.setMsg("注册客户失败");
			logger.info("插入数据失败，注册客户失败");
			resp.setCode(500);
			return resp;
		}
	}
	@ResponseBody
	@RequestMapping("/updatePassword")
	public BaseResponse updatePassword(HttpServletRequest request) {
		String s = request.getParameter("s");
		logger.info("改密入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析改密入参：{}",reqStr);
		JSONObject parseObject = JSONObject.parseObject(reqStr);
		UserReqVo reqVo = JSONObject.toJavaObject(parseObject, UserReqVo.class);
		BaseResponse resp = new BaseResponse();
		String vCode = (String)redisUtil.get(reqVo.getName());
		if(vCode == null) {
			resp.setCode(500);
			resp.setMsg("验证超时！");
			logger.info("验证超时！");
			return resp;
		}
		if(!vCode.equals(reqVo.getCode())) {
			resp.setCode(500);
			resp.setMsg("验证码输入有误！");
			logger.info("验证码输入有误！");
			return resp;
		}
		User user = userService.findByName(reqVo.getName());
		if(user == null) {
			resp.setMsg("该用户名不存在！");
			logger.info("该用户名不存在！");
			resp.setCode(500);
			return resp;
		}
		//加密新密码
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
		user.setPassword(password);
		Integer id = userService.updateUser(user);
		if(id==null) {
			resp.setCode(500);
			resp.setMsg("密码修改失败");
			logger.info("密码修改失败");
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/ifSignIn")
	public BaseResponse ifSignIn(HttpServletRequest request) {
		String s = request.getParameter("s");
		logger.info("判断是否已注册入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析判断是否已注册入参：{}",reqStr);
		JSONObject parseObject = JSONObject.parseObject(reqStr);
		UserReqVo reqVo = JSONObject.toJavaObject(parseObject, UserReqVo.class);
		BaseResponse resp = new BaseResponse();
		String name = reqVo.getName();
		User  u = userService.findByName(name);
		if(u == null) {
			resp.setCode(0);
			resp.setMsg("用户未注册,请注册！");
			logger.info("用户未注册,请注册！");
		}else {
			resp.setCode(1);
			resp.setMsg("用户已注册,请登录！");
			logger.info("用户已注册,请登录！");
		}
		return resp;
	}
}
