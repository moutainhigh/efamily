package com.winterframework.efamily.institution.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandlerUtil {

    public static String getStringFrom(Object queryValue) {   
        if(queryValue==null){
        	return null;
        }
        return queryValue.toString();
    }
	
	
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
    
    public static boolean isNumeric(String str){ 
    	   Pattern pattern = Pattern.compile("[0-9]*"); 
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false; 
    	   } 
    	   return true; 
    }
    
    
}
