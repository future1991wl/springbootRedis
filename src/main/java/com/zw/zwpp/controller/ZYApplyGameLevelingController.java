package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.ApplyInfo;
import com.zw.zwpp.service.ZYApplyService;
import com.zw.zwpp.utils.DesUtil;

@RestController
public class ZYApplyGameLevelingController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZYApplyService applyService;

	@RequestMapping("/apply")
	public BaseResponse login(HttpServletRequest request)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String s = request.getParameter("s");
		logger.info("申请代练入参：{}", s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析申请代练入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		ApplyInfo reqApply = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
		// ApplyInfo reqApply = new ApplyInfo();
		// reqApply.setGameVersion(version);
		// reqApply.setName(name);
		BaseResponse serviceRes = applyService.applyGameLeveling(reqApply);

		return serviceRes;
	}

	@RequestMapping("/getGameLevelingInfo")
	public BaseResponse getGameLevelingInfo(HttpServletRequest request)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse res = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("通过游戏账号和游戏版本获取账号代练信息：{}", s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析通过游戏账号和游戏版本获取账号代练信息入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		String gameID = reqJson.getString("gameID");
		String gameVersion = reqJson.getString("gameVersion");
		ApplyInfo applyInfo = applyService.getGameLevelingInfo(gameID, gameVersion);
		// 不返回密码
		applyInfo.setGamePwd("");
		res.setData(applyInfo);
		logger.info("解析通过游戏账号和游戏版本获取账号代练信息出参：{}", res);
		return res;
	}

	@RequestMapping("/updateGameLevelingInfo")
	public BaseResponse updateGameLevelingInfo(HttpServletRequest request)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BaseResponse res = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("通过游戏账号和游戏版本修改账号代练信息：{}", s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析通过游戏账号和游戏账号修改该代练信息入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		String gameID = reqJson.getString("gameID");
		String gameVersion = reqJson.getString("gameVersion");
		ApplyInfo rr = applyService.getGameLevelingInfo(gameID, gameVersion);
		ApplyInfo reqApplyInfo = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
		//复制对象null字段不复制
		BeanUtils.copyProperties(reqApplyInfo, rr, getNullPropertyNames(reqApplyInfo));
		applyService.save(rr);
		logger.info("通过账号和版本修改代练信息成功:{}", res);
		// 不返回密码
		return res;
	}
	//获取此对象字段为空的字段数组
	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
