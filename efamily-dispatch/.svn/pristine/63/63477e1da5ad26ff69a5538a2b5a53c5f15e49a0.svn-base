package com.winterframework.efamily.dispatch.exception;

import com.winterframework.efamily.dispatch.model.ResultCode;
 
@SuppressWarnings("serial")
public class DispatchException extends Exception {
	private int code;
	
	public DispatchException() {
		super();
	}

	public DispatchException(String message) {
		super(message);
	}

	public DispatchException(Throwable cause) {
		super(cause);
	}
	public DispatchException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	public DispatchException(ResultCode result) {
		super(result.getMsg());
		this.code = result.getCode();
	}
	public DispatchException(int code, String msg, Throwable exception) {
		super(msg, exception);
		this.code = code;
	}
	public DispatchException(int code) {
		super();
		this.code = code;
	} 
	public DispatchException(int code,Throwable exception) {
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
