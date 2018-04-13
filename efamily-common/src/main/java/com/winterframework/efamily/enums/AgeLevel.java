package com.winterframework.efamily.enums;

/**
 * 年龄层级
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月22日
 */
public enum AgeLevel {
	FS(1,50,60),
	SS(2,60,70),
	SE(3,70,80),
	EN(4,80,90),
	NH(5,90,100);
	
	private int _value;
	private int _low;
	private int _high;
	AgeLevel(int value,int low,int high){
		this._value=value;
		this._low=low;
		this._high=high;
	}
	public int getValue(){
		return _value;
	}
	public int getLow(){
		return _low;
	}
	public int getHigh(){
		return _high;
	}
}
