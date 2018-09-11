package com.zw.zwpp.vo.reqvo;

/**
 * 用户登陆入参
 * 
 * @author lailai
 *
 */
public class SmsReqVo {
	private String phone;
	private String contentType;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "SmsReqVo [phone=" + phone + ", contentType=" + contentType + "]";
	}

	

}
