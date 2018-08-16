package com.zw.zwpp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;


public class DesUtil {
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(str.getBytes("utf-8"));
		Encoder encoder = Base64.getEncoder();
		String encodeToString = encoder.encodeToString(digest);
		return encodeToString;
		
		
	}
}
