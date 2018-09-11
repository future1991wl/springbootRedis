package com.zw.zwpp.vo.reqvo;

/**
 * 用户登陆入参 
 * @author lailai
 *
 */
public class UserReqVo {
	private String name;
	private String password;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "UserReqVo [name=" + name + ", password=" + password + ", code=" + code + "]";
	}
	
}
