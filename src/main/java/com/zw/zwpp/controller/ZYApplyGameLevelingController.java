package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
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
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.ApplyInfo;
import com.zw.zwpp.service.ZYApplyService;
import com.zw.zwpp.utils.DesUtil;

@RestController
public class ZYApplyGameLevelingController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZYApplyService applyService;

	/**
	 * 逻辑修改不用这段代码
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		String flag = request.getParameter("flag");
		String reqStr="";
		logger.info("flag:{},电脑端：false,手机端：true", flag);
		if("true".equals(flag)) {
			logger.info("通过游戏账号和游戏版本修改账号代练信息：{}", s);
			reqStr = DesUtil.DataDecryptionNo(s);
		}else if("false".equals(flag)){
			reqStr ="{"+s+"}";
		}
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
	@RequestMapping("/addApplyGameLeveing")
	public BaseResponse addApplyGameLeveing(HttpServletRequest request){
		BaseResponse res = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("用户申请代练：{}", s);
		String flag = request.getParameter("flag");
		String reqStr="";
		logger.info("flag:{},电脑端：false,手机端：true", flag);
		if("true".equals(flag)) {
			logger.info("通过游戏账号和游戏版本修改账号代练信息：{}", s);
			reqStr = DesUtil.DataDecryptionNo(s);
		}else if("false".equals(flag)){
			reqStr ="{"+s+"}";
		}
		logger.info("解析通过游戏账号和游戏账号申请代练信息入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		ApplyInfo applyInfo = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
		if(StringUtils.isBlank(applyInfo.getName()) || StringUtils.isBlank(applyInfo.getGameID()) || StringUtils.isBlank(applyInfo.getGameVersion())) {
			logger.info("用户账号：{},游戏账号：{},游戏版本：{}", applyInfo.getName(), applyInfo.getGameID(), applyInfo.getGameVersion());
			res.setCode(500);
			res.setMsg("此游戏账号:"+applyInfo.getGameID() +",游戏版本:"+applyInfo.getGameVersion() +"已经存在代练新信息，请先取消代练！");
			return res;
		}
		ApplyInfo oldApplyInfo = applyService.getGameLevelingInfo(applyInfo.getGameID(), applyInfo.getGameVersion());
		if(oldApplyInfo != null) {
			res.setCode(500);
			res.setMsg("此游戏账号:"+applyInfo.getGameID() +",游戏版本:"+applyInfo.getGameVersion() +"已经存在代练新信息，请先取消代练！");
			return res;
		}
		ApplyInfo apply = applyService.save(applyInfo);
		res.setData(apply);
		if(apply == null) {
			res.setCode(500);
			res.setMsg("申请代练失败！");
		}
		return res;
	}
	@RequestMapping("/deleteApplyGameLeveing")
	public BaseResponse deleteApplyGameLeveing(HttpServletRequest request){
		BaseResponse res = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("用户删除代练：{}", s);
		String flag = request.getParameter("flag");
		String reqStr="";
		logger.info("flag:{},电脑端：false,手机端：true", flag);
		if("true".equals(flag)) {
			logger.info("通过游戏账号和游戏版本修改账号代练信息：{}", s);
			reqStr = DesUtil.DataDecryptionNo(s);
		}else if("false".equals(flag)){
			reqStr ="{"+s+"}";
		}
		logger.info("解析通过游戏账号和游戏账号删除代练信息入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		ApplyInfo applyInfo = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
		if(StringUtils.isBlank(applyInfo.getName()) || StringUtils.isBlank(applyInfo.getGameID()) || StringUtils.isBlank(applyInfo.getGameVersion())) {
			logger.info("用户账号：{},游戏账号：{},游戏版本：{}", applyInfo.getName(), applyInfo.getGameID(), applyInfo.getGameVersion());
			res.setCode(500);
			res.setMsg("用户账号、游戏账号、游戏版本某一个可能为空！");
			return res;
		}
		ApplyInfo oldApplyInfo = applyService.getGameLevelingInfo(applyInfo.getGameID(), applyInfo.getGameVersion());
		if(oldApplyInfo != null) {
			int num = applyService.deleteById(oldApplyInfo.getId());
		}
		return res;
	}
	@RequestMapping("/queryApplyGameLeveingList")
	public BaseResponse queryApplyGameLeveingList(HttpServletRequest request){
		BaseResponse res = new BaseResponse();
		String s = request.getParameter("s");
		logger.info("获取代练列表：{}", s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析获取代练列表入参：{}", reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		ApplyInfo applyInfo = JSONObject.toJavaObject(reqJson, ApplyInfo.class);
		if(StringUtils.isBlank(applyInfo.getName())) {
			logger.info("用户账号：{}", applyInfo.getName());
			res.setCode(500);
			res.setMsg("用户账号为空！");
			return res;
		}
		List<ApplyInfo> list = applyService.queryApplyGameLeveingList(applyInfo.getName());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setGamePwd("");
		}
		res.setData(list);
		return res;
	}
}
