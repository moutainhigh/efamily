/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.device.DevicePowerAutoRequest;

/**
 * 单位转换工具类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年8月23日
 */
public class UnitUtil {
	private static final int SIGNAL_MAX_LEVEL=5;	//后续改为数据库设置
	/**
	 * 信号格数
	 * @param value
	 * @param maxValue
	 * @param maxLevel
	 * @return
	 */
	public static int getSignalLevel(int value,int maxValue,int maxLevel) {
		return (int)Math.ceil(value*SIGNAL_MAX_LEVEL*1.0/maxValue);
	}
	
	public static void main(String[] args){
		  
    	DevicePowerAutoRequest bizReq=new DevicePowerAutoRequest();
		bizReq.setOns(JsonUtils.fromJson2List("", String.class));
		bizReq.setOffs(JsonUtils.fromJson2List("[\"8:00\",\"14:00\"]", String.class)); 
		System.out.println(JsonUtils.toJson(bizReq));
		
		Map<String,String> mapTemp = new HashMap<String,String>();
		mapTemp.put("ons", "[\"8:00\",\"14:00\"]");//*** 自动开机时间   ["8:00","14:00"]
		mapTemp.put("offs", "[\"8:00\",\"14:00\"]");
		System.out.println(JsonUtils.toJson(mapTemp));

		
		
	}
}
