package com.winterframework.efamily.base.utils;

import java.nio.charset.Charset;

public class CharsetFactory {

	public static Charset getCharset(int charset){
		for(CharsetEnum charsetEnum:CharsetEnum.values()){
			if(charset==charsetEnum.getCode()){
				return Charset.forName(charsetEnum.getValue());
			}
		}
		return Charset.forName(CharsetEnum.UTF8.getValue());
	}
	
	public enum CharsetEnum{
		UTF8(0,"UTF-8"),
		GBK(1,"GBK"),
		GB2312(2,"GB2312"),
		ISO8859(3,"ISO8859-1"); 
		private int _code;
		private String _value;
		CharsetEnum(int code,String value){
			this._code=code;
			this._value=value;
		}
		public int getCode(){
			return _code;
		}
		public String getValue(){
			return _value;
		}
	}

}
