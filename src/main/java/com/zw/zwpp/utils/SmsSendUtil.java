package com.zw.zwpp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value = "classpath:application-dev.properties", encoding = "utf-8")
public class SmsSendUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${smsContent}")
	private String smsContent;
	@Value("${smsContent2}")
	private String smsContent2;
	@Value("${accountSid}")
	private String accountSid;
	@Value("${authToken}")
	private String authToken;
	@Value("${url}")
	private String url;

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public String getRandomCode() {
		double random = Math.random() * 1000000;// 0.0~1.0之间小数
		int codeInt = (int) random;
		return String.valueOf(codeInt);
	}

	public String sendRandomCode(String phone, String randomCode, String contentType)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String content = "";
		switch (contentType) {
		case "1":
			content = smsContent;
			break;
		case "2":
			content = smsContent2;
			break;
		default:
			break;
		}
		content = String.format(content, randomCode);
		logger.info("发送模板为："+content);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String newStr = accountSid + authToken + timestamp;
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] results = md.digest(newStr.getBytes());
		String sig = byteArrayToHexString(results);
		String param = "accountSid=" + accountSid + "&smsContent=" + content + "&to=" + phone + "&timestamp="
				+ timestamp + "&respDataType=JSON&sig=" + sig;
		String resp = "";
			logger.info("调用接口参数：{}",param);
			logger.info("调用接口url：{}",url);
			resp = http.sendPost(url, param);
		return resp;
	}

	private String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
