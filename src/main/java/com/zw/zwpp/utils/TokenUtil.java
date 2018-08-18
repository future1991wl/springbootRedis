package com.zw.zwpp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.zw.zwpp.common.CommonRuntiontimeException;

@Component
public class TokenUtil {
	@Autowired
	private RedisUtil redisUtil;
	public long checkToken(String token) {
		String userStr = (String)redisUtil.get("token:" + token);
		if(StringUtils.isBlank(userStr)) {
			throw new CommonRuntiontimeException("token 已过期");
		}
		JSONObject userJson = JSONObject.parseObject(userStr);
		String partId = userJson.getString("partId");
		return Long.parseLong(partId);
		
	}
}
