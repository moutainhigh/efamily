package com.winterframework.efamily.enums;

public enum DeviceType {
	WATCH(1), 
	OLDPHONE(2),
	RING(3);
	
	private int _value;
	DeviceType(int value){
		this._value=value;
	}
	public int getValue(){
		return _value;
	}
}
