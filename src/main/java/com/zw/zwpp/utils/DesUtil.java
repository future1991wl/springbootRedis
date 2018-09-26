package com.zw.zwpp.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;


public class DesUtil {
	private static byte[] iv = {1,2,3,4,5,6,7,8};
	private final static String key = "ZBG10223";
	/**
	 * md5加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(str.getBytes("utf-8"));
		Encoder encoder = Base64.getEncoder();
		String encodeToString = encoder.encodeToString(digest);
		return encodeToString;
	}
	/**
	 * 对字符串进行加密
	 * @param jsonStr
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encryptDES(String jsonStr,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		IvParameterSpec spec = new IvParameterSpec(iv);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
		byte[] encryptedData = cipher.doFinal(jsonStr.getBytes());
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(encryptedData);
	}
	/**
	 * 字符串解密
	 * @param decryptStr
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptDES(String decryptStr, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(decryptStr);
		IvParameterSpec spec = new IvParameterSpec(iv);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
		byte[] decryptDate = cipher.doFinal(decode);
		return new String(decryptDate);
	}
	/**
	 * 参数加密
	 * @param data
	 * @return
	 */
	public static String DateEncrytionNo(String data) {
		String newData = "";
		try {
			String encryptDES = encryptDES(data, key);
			newData = encryptDES.toString().replace("+", "-").replace("\\", "_").replace(" ", "*");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return newData;
	}
	/**
	 * 参数解密
	 * @param data
	 * @return
	 */
	public static String DataDecryptionNo(String data) {
		String newData = "";
		String strBack = data.toString().replace("-", "+").replace("_", "\\").replace("*", " ");
		try {
			newData = decryptDES(strBack, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return newData;
	}
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		json.put("name", "15311147438");
		json.put("time", "1536823651715");
		json.put("money", "15.5");
		String jsonStr = JSONObject.toJSONString(json);
		String dateEncrytionNo = DateEncrytionNo(jsonStr);
		String dataDecryptionNo = DataDecryptionNo("ay9FDGh6h6ZFzW8wJWYhHSHkBFzprxqvIm3o0JtZyQKANDKFm8a8w ka nMT4RXsSMRFkW hv/8sJg34d7uL/g==");
		System.out.println("加密"+dateEncrytionNo+"解密"+dataDecryptionNo);
	}
}
