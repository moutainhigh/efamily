package com.winterframework.efamily.common;

import com.winterframework.efamily.base.enums.Separator;

public class Span {
	private String span;
	
	public Span(String spanStr){
		this.span=spanStr;
	}
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}
	
	private String[] getValues(){
		if(null!=span){			
			return span.split(Separator.wave);
		}
		return null;
	}
	protected Object getDown(){
		String[] v=getValues();
		if(null!=v && v.length>0){
			return v[0];
		}	
		return null;
	}
	protected Object getUp(){
		String[] v=getValues();
		if(null!=v && v.length>1){
			return v[1];
		}	
		return null;
	}
}
