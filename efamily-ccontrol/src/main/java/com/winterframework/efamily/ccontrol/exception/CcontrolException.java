package com.winterframework.efamily.ccontrol.exception;

import com.winterframework.efamily.ccontrol.model.ResultCode;

@SuppressWarnings("serial")
public class CcontrolException extends Exception {
	private int code;
	
	public CcontrolException() {
		super();
	}

	public CcontrolException(String message) {
		super(message);
	}

	public CcontrolException(Throwable cause) {
		super(cause);
	}
	public CcontrolException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	public CcontrolException(ResultCode result) {
		super(result.getMsg());
		this.code = result.getCode();
	}
	public CcontrolException(int code, String msg, Throwable exception) {
		super(msg, exception);
		this.code = code;
	}
	public CcontrolException(int code) {
		super();
		this.code = code;
	} 
	public CcontrolException(int code,Throwable exception) {
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
