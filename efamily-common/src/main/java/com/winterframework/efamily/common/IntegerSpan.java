package com.winterframework.efamily.common;

public class IntegerSpan extends Span {
	public IntegerSpan(String spanStr){
		super(spanStr);
	}
	public Integer getDown() {
		String o=(String)super.getDown();
		if(null!=o){
			return new Integer(o);
		}
		return null;
	}
	public Integer getUp() {
		String o=(String)super.getUp();
		if(null!=o){
			return new Integer(o);
		}
		return null;
	}
	
	
}
