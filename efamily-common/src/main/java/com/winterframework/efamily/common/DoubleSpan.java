package com.winterframework.efamily.common;

public class DoubleSpan extends Span {
	public DoubleSpan(String spanStr){
		super(spanStr);
	}
	public Double getDown() {
		String o=(String)super.getDown();
		if(null!=o){
			return new Double(o);
		}
		return null;
	}
	public Double getUp() {
		String o=(String)super.getUp();
		if(null!=o){
			return new Double(o);
		}
		return null;
	}
	
	
}
