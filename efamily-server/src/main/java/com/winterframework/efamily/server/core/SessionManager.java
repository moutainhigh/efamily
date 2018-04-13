package com.winterframework.efamily.server.core;

import java.util.TreeMap;

public class SessionManager {
	/**
	 * key:sessionId
	 * value:user info
	 */
	private static final TreeMap<Integer,Long> sessionMap = new TreeMap<Integer,Long>();
	private static final Long sessionExpire=30*60*1000L;
	public static Long get(Integer key) { 
		return sessionMap.get(key);
	} 
	public static Integer save(Long value){ 
		String time=String.valueOf(System.currentTimeMillis());
		Integer key=Integer.valueOf(time.substring(3));
		sessionMap.put(key, value);
		return key;
	} 
	public static void remove(Integer key){ 
		sessionMap.remove(key);
	}
	
	public synchronized static void monitor() {
		for (Integer sessionId:sessionMap.keySet()) {
			String time=String.valueOf(System.currentTimeMillis());
			Integer curTime=Integer.valueOf(time.substring(3));
			if (curTime-sessionId> sessionExpire){
				remove(sessionId);
			}
		}
	}
	
}
