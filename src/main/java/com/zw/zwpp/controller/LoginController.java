package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.utils.DesUtil;
import com.zw.zwpp.utils.JwtUtil;
import com.zw.zwpp.vo.reqvo.UserReqVo;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	@ResponseBody
	@PostMapping("/login")
	public BaseResponse login(UserReqVo reqVo) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse resp = new BaseResponse();
		String name = reqVo.getName();
		String reqPassword = reqVo.getPassword();
		String encoderByMd5 = DesUtil.EncoderByMd5(reqPassword);
		User  u = userService.findByName(name);
		if(u != null) {
			String password = u.getPassword();
			if(encoderByMd5.equals(password)) {
				String token = jwtUtil.getToken(name);
				String parseToken = jwtUtil.parseToken(token, String.class);
				System.out.println(token + parseToken);
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
	public BaseResponse signIn(UserReqVo reqVo) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse resp = new BaseResponse();
		User u = new User();
		String password = DesUtil.EncoderByMd5(reqVo.getPassword());
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
}
