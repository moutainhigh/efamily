package com.winterframework.efamily.base.model;

public class Status {
	private int code;
	private String[] params;
	
	public Status(){
		
	}
	public Status(int code){
		this.code=code;
	}
	public Status(int code,String[] params){
		this.code=code;
		this.params=params;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}
	
	
}
