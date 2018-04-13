package com.winterframework.efamily.enums;

public enum AppType {
	SOLGO(1), 
	NOVA(2),
	KANDDOO(3),
	INSTITUTION(4),
	HUASHOU(5),
	ZHIHUIJIANKANG(6),
	QIZHI(7),
	BEIDOU(8),
	ANLIXIN(9);
	private int _value;
	AppType(int value){
		this._value=value;
	}
	public int getValue(){
		return _value;
	}
}
