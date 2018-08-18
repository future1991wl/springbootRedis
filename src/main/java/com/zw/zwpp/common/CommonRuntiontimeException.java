package com.zw.zwpp.common;

public class CommonRuntiontimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public CommonRuntiontimeException(String message) {
		super(message);
	}
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
