package com.winterframework.efamily.enums;


public enum DeviceOperation {
	UPDATE(100,"update"),           //软件更新
	ONFF(101,"onff"),           //***开关机
	SOS(102,"sos"),             //***紧急呼叫
	CHARGE(103,"charge"),       //***充电
	LOCATION(104,"location"),      //***定位开关
	RESET(108,"reset"),	//恢复出厂设置
	SOFTWARE(109,"software");	//软件更新
	
	private int _code;
	private String _value;
	DeviceOperation(int code,String value){
		this._code=code;
		this._value=value;
	}
	public int getCode(){
		return _code;
	}
	public String getValue(){
		return _value;
	}
	public static DeviceOperation getOperation(int code){
		for(DeviceOperation o:DeviceOperation.values()){
			if(code==o.getCode()){
				return o;
			}
		}
		return null;
	}
}
