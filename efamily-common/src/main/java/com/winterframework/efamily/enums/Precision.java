package com.winterframework.efamily.enums;

/**
 * 精度
 * @ClassName
 * @Description
 * @author ibm
 * 2016年6月21日
 */
public enum Precision {
	HEALTH(1), 	//健康 1位小数
	SPORT(1);	//运动
	private int _value;
	Precision(int value){
		this._value=value;
	}
	public int getValue(){
		return _value;
	}
}
