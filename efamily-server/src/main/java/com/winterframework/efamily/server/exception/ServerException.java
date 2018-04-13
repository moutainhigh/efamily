package com.winterframework.efamily.server.exception;

@SuppressWarnings("serial")
public class ServerException extends Exception {
	private int code=9;
	
	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}
	public ServerException(int code, String msg, Throwable exception) {
		super(msg, exception);
		this.code = code;
	}
	public ServerException(int code) {
		super();
		this.code = code;
	} 
	public ServerException(int code,Throwable exception) {
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
