package com.winterframework.efamily.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static String getNumberFromString(String resourceStr){
		if(resourceStr==null || resourceStr.trim().equals("")){
			return "";
		}
	    StringBuffer buf = new StringBuffer();
		Pattern p = Pattern.compile("[0-9\\.]+");
	    Matcher m = p.matcher(resourceStr);
	    while(m.find()){
	       buf.append(m.group());
	    }
	    return buf.toString();
    }
	
	public static String getPhone(String resourceStr){
	   String phone = getNumberFromString(resourceStr);
	   //*** 截取后 11 位 为电话号码
	   if(phone.length()>11){
		   phone = phone.substring(phone.length()-11);
	   }
	   return phone;
	}
	
	public static String emptyIf(String value, String defaultValue) {
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value;
	}
	
	public static String LongToStr(Long longData,String emptyDefault){
		if(longData == null){
			return "";
		}else{
			return String.valueOf(longData);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		System.out.println(getPhone(("++231801234+5678")));
	}

}
