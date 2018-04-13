package com.winterframework.efamily.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.modules.web.util.JsonMapper;

public class JsonUtils {
	protected static  JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	/**
	 * json数据结构转换对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr, clazz);
	}
	/**json数据结构转换List<MyBean>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJson2List(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr,
				JsonMapper.nonDefaultMapper().createCollectionType(List.class, clazz));
	}
	
	/**
	 * 对象转换json数据结构
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return jmapper.toJson(obj);
	}
	
	public static void main(String[] args){
		/*String s="[{\"level\":\"3\",\"time\":\"1428551739622\"},{\"level\":\"4\",\"time\":\"1428551739623\"}]";
		List<DeviceSignalRecordRequest> ss=fromJson2List(s, DeviceSignalRecordRequest.class);
		
		String sss=toJson(new DeviceSignalRecordRequest());
		System.out.println(ss);*/
		
		List<String> a=new ArrayList<String>();
		List<String> b=new ArrayList<String>();
		List<List<String>> cc =new ArrayList<List<String>>();
		a.add("11:00");
		a.add("12:00");
		b.add("13:00");
		b.add("14:00");
		cc.add(a);
		cc.add(b);
		System.out.println(JsonUtils.toJson(a));
		List<String>  k=JsonUtils.fromJson2List(JsonUtils.toJson(a),String.class);
		for(String s:k){
			System.out.println(s);
		}
	}
	
}
