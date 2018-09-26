package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.ApplyInfo;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.service.ZYApplyService;
import com.zw.zwpp.utils.DesUtil;


@RestController
public class ZYApplyController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZYApplyService applyService;
	@RequestMapping("/apply")
	public BaseResponse login(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse resp = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("申请代练入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析申请代练入参：{}",reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		ApplyInfo reqApply = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
//		ApplyInfo reqApply = new ApplyInfo();
//		reqApply.setGameVersion(version);
//		reqApply.setName(name);
		BaseResponse serviceRes = applyService.applyGameLeveling(reqApply);
		
		return resp;
	}
}
