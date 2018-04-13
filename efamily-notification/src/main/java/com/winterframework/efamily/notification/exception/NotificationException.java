package com.winterframework.efamily.notification.exception;

import com.winterframework.efamily.notification.model.ResultCode;

 
@SuppressWarnings("serial")
public class NotificationException extends Exception {
	private int code;
	
	public NotificationException() {
		super();
	}

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(Throwable cause) {
		super(cause);
	}
	public NotificationException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	public NotificationException(ResultCode result) {
		super(result.getMsg());
		this.code = result.getCode();
	}
	public NotificationException(int code, String msg, Throwable exception) {
		super(msg, exception);
		this.code = code;
	}
	public NotificationException(int code) {
		super();
		this.code = code;
	} 
	public NotificationException(int code,Throwable exception) {
		super(exception);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
	@Override
	public String toString() {
		String s = getClass().getName();
        return s + ": " + code;
	}

}
