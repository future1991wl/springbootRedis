package com.zw.zwpp.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtUtil {
	private String secret = "2312h12j1h2guih1iuh1ig3uf12hu";
	private long expire = 1800000L;//过期时间毫秒
	
	/**
	 * jwt 生成token
	 * @param object
	 * @param json
	 * @return
	 */
	public <T> String getToken(T object) {
		JWTSigner signer = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String writeValueAsString = mapper.writeValueAsString(object);
			claims.put("exp", System.currentTimeMillis()+expire);
			//claims.put("iat", System.currentTimeMillis());
			claims.put("payload", writeValueAsString);
			return signer.sign(claims);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * jwt 解析token
	 * @param token
	 * @param classT
	 * @return
	 */
	public <T> T parseToken(String token, Class<T> classT) {
		JWTVerifier verifier = new JWTVerifier(secret);
		try {
			Map<String, Object> claims = verifier.verify(token);
			if(claims.containsKey("exp") && claims.containsKey("payload")) {
				long exp = (Long)claims.get("exp");
				long currentTimeMillis = System.currentTimeMillis();
				if(exp > currentTimeMillis) {
					String payload = (String)claims.get("payload");
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(payload, classT);
				}
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JWTVerifyException e) {
			e.printStackTrace();
		}
		return null;
	}
}
