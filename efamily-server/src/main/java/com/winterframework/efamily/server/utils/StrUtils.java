package com.winterframework.efamily.server.utils;

import org.apache.commons.lang3.StringUtils;

public class StrUtils {

	public static Integer stringToInteger(String str,Integer defaultValue){
		Integer result = defaultValue ;
		if(StringUtils.isNotBlank(str)){
		    result = Integer.valueOf(str);	
		}
		return result;
	}
	
	public static Integer stringToInteger(String str){
		Integer result = null ;
		if(StringUtils.isNotBlank(str)){
		    result = Integer.valueOf(str);	
		}
		return result;
	}
	
	public static Long stringToLong(String str,Long defaultValue){
		Long result = defaultValue ;
		if(StringUtils.isNotBlank(str)){
		    result = Long.valueOf(str);	
		}
		return result;
	}
	
	public static Long stringToLong(String str){
		Long result = null ;
		if(StringUtils.isNotBlank(str)){
		    result = Long.valueOf(str);	
		}
		return result;
	}
	
	public static Double stringToDouble(String str,Double defaultValue){
		Double result = defaultValue ;
		if(StringUtils.isNotBlank(str)){
		    result = Double.valueOf(str);	
		}
		return result;
	}
	
	public static Double stringToDouble(String str){
		Double result = null ;
		if(StringUtils.isNotBlank(str)){
		    result = Double.valueOf(str);	
		}
		return result;
	}
	
}