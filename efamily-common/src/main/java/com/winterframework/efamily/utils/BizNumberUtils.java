package com.winterframework.efamily.utils;

import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.redis.RedisClient;

/**
 * 业务号码生成工具类 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年7月16日
 */
@Service("bizNumberUtils")
public class BizNumberUtils {
	private final ReentrantLock lock = new ReentrantLock();
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	public enum Type{
		EFAMILY_NUMBER("family_number",6),	//家庭号码 初始位数：6
		CHAT_GROUP_NUMBER("chat_group_number",5);	//聊天群号码 初始位数：5
		private String _value;
		private int _initLength;
		Type(String value,int initLength){
			this._value=value;
			this._initLength=initLength;
		}
		public String getValue(){
			return this._value;
		}
		public int getInitLength(){
			return this._initLength;
		}
	}
	
	public String getNumber(BizNumberUtils.Type type){
		try {
			lock.lock();
			String key=type.getValue();
			Long number=redis.getIncre(key);
			Long numberInit=getNumberInit(type.getInitLength());
			if(number<numberInit){
				redis.set(key, numberInit+"");
				number=redis.getIncre(key);
			}
			if(isGoodNumber(number)){
				number=redis.getIncre(key);
			}
			return String.valueOf(number);
		} finally {
			lock.unlock();
		}
	}
	
	private Long getNumberInit(int len){
		Long k=1L;
		for(int i=1;i<len;i++){
			k=k*10;
		}
		return k;
	}
	private boolean isGoodNumber(Long number){
		return (number+"").contains("888") || (number+"").contains("666") || (number+"").contains("999");
	}
	
}
