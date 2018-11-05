package com.zw.zwpp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.EquipmentInfo;
import com.zw.zwpp.service.ZYEquipmentService;
import com.zw.zwpp.utils.DesUtil;


@RestController
@RequestMapping("/equipment")
public class ZYEquipmentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZYEquipmentService zyEquipmentService;
	@ResponseBody
	@RequestMapping("/saveAndUpdate")
	public BaseResponse saveAndUpdateEquipment(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String s = request.getParameter("s");
		logger.info("添加模拟器入参：{}",s);
		String reqStr = DesUtil.DataDecryptionNo(s);
		logger.info("解析添加模拟器入参：{}",reqStr);
		JSONObject reqJson = JSONObject.parseObject(reqStr);
		EquipmentInfo equipmentInfo = JSONObject.toJavaObject(reqJson, EquipmentInfo.class);
		String address = equipmentInfo.getAddress();
		BaseResponse resp = new BaseResponse();
		if(StringUtils.isNotBlank(address)) {
			EquipmentInfo resEntity = zyEquipmentService.saveAndUpdateEquipment(equipmentInfo);
			if(resEntity != null) {
				resp.setData(resEntity);
			}else {
				resp.setCode(500);
				resp.setMsg("新增或修改失败！");
			}
		}else {
			resp.setCode(500);
			resp.setMsg("模拟器地址为空！");
		}
		logger.info("添加或修改模拟器出参：{}",JSONObject.toJSONString(resp));
		return resp;
	}
}
