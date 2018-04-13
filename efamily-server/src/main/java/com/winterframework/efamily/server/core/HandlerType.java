package com.winterframework.efamily.server.core;

public enum HandlerType {
	/**
	 * 1:APP
	 * 2:WATCH
	 */ 
	APP(1,"APP"),
	WATCH(2,"WATCH"); 
	
	private int _code;
	private String _value;
	HandlerType(int code,String value){
		this._code=code;
		this._value=value;
	}
	public int getCode(){
		return _code;
	}
	public String getValue(){
		return _value;
	}
}
