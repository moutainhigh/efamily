package com.winterframework.efamily.server.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.server.exception.ServerException;

public class MapToJavaBeanUtils {
	private static final Logger log = LoggerFactory.getLogger(MapToJavaBeanUtils.class); 

	 public static <T> T transMap2Bean(Map<String, String> map, Class<T> type) throws ServerException {  
	        if (map == null ) {  
	            return null;  
	        }  
	        T obj = null ;
	        try {  
	        	obj = type.newInstance();
	            BeanUtils.populate(obj, map);  
	        } catch (Exception e) {  
	            log.error("MAP 转换为 JAVABEAN时出现异常: map = "+map+" ; type = "+type.getClass(), e); 
	            throw new ServerException("MAP 转换为 JAVABEAN时出现异常: map = "+map+" ; type = "+type.getClass());
	        }  
	        return obj;
	 }  
	 public static Map<String, String> transBean2Map(Object obj) {  
	        if(obj == null)  
	            return null;   
	   
	        return new org.apache.commons.beanutils.BeanMap(obj);  
	}    
	public static void main(String[] args) throws ServerException, IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("verifyCode", "0000");
		map.put("phoneNumber", "13416898831");
		map.put("type", "1");
		map.put("isReLogin", "0");
		map.put("inviteNumber;", "2");
		/*LoginRequest loginRequest = MapToJavaBeanUtils.transMap2Bean(map,LoginRequest.class) ;
		
		System.out.println("loginRequest : "+loginRequest.toString());
		
		Map<String,String> ttt = transBean2Map(loginRequest);
		System.out.println("ttt : "+ttt.get("phoneNumber"));
		Long.valueOf("0");*/
	}
	
	
}
