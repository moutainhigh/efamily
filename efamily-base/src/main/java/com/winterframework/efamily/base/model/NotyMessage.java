package com.winterframework.efamily.base.model;

import java.util.Map;

public class NotyMessage {
	private Type type;	//消息类型
	private Long id;	//ID
	private int command;	//命令码
	private Map<String,String> data;	//数据
	public enum Type{
		MSG(0,"消息"),NOTICE(1,"通知"),REMIND(2,"提醒"),OPER(3,"操作"),SETT(4,"设置"),ALARM(5,"告警");
		private int _code;
		private String _value;
		Type(int code,String value){
			_code=code;
			_value=value;
		}
		public int getCode(){
			return _code;
		}
		public String getValue(){
			return _value;
		}
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
}
