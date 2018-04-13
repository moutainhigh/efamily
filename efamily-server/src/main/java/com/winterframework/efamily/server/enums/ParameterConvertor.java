package com.winterframework.efamily.server.enums;

import java.util.HashMap;
import java.util.Map;
public class ParameterConvertor {
	private static Map<Integer,Integer> convertorMap=new HashMap<Integer, Integer>();
	static{
		//convertorMap.put(100030, "address_list");           //---- （通讯录）：获取通讯录。 ----
		convertorMap.put(100041, 400008);                 //---- （音量）：获取音量值。----
		convertorMap.put(100051, 400006);             //---- （亮度）：获取亮度值。----
		convertorMap.put(100061, 400022);                  //---- （震动模式）：获取震动模式的值。----
		convertorMap.put(100071, 400017);             //---- （心率）：获取心率开关值。---- 
		convertorMap.put(100081, 400020);           //---- （记步）：获取记步的开关值。----
		//convertorMap.put(100101, "on_off");                 //---- （关／关机）：关机。----
		convertorMap.put(100121, 400023);             //---- （静音）：获取静音模式的值。----
		convertorMap.put(400025, 400027);
		convertorMap.put(400024, 400026);
		convertorMap.put(400028, 400028);
		convertorMap.put(400029, 400029);
		convertorMap.put(400030, 400030);
		convertorMap.put(400031, 400031);
		convertorMap.put(400032, 400032);
		
		//-----------------------  后台服务设置 手表相关参数  ---------------
		convertorMap.put(100131,400001);    //connect_beat	连网心跳频率	   
		convertorMap.put(100141,400002);    //connect_timeout	连网超时	   
		convertorMap.put(100151,400003);    //connect_retry	连网重试次数	   
		convertorMap.put(100161,400004);    //net_restart	网络重启次数	     	   
		convertorMap.put(100171,400009);    //signal_max	最大信号值	   
		convertorMap.put(100181,400010);    //signal_upload_freq	信号上传频率	   
		convertorMap.put(100191,400011);    //signal	信号	   
		convertorMap.put(100201,400012);    //battery_max	最大电池值	   
		convertorMap.put(100211,400013);    //battery_upload_freq	电池上传频率	   
		convertorMap.put(100221,400014);    //location_onff	定位开关	   
		convertorMap.put(100231,400015);    //location_gather_freq	定位采集频率	   
		convertorMap.put(100241,400016);    //location_upload_freq	定位上传频率	   
		convertorMap.put(100251,400018);    //health_heart_gather_freq	心率采集频率	   
		convertorMap.put(100261,400019);    //health_heart_upload_freq	心率上传频率	   	   
		convertorMap.put(100271,400021);    //health_walk_upload_freq	计步上传频率
		
	}
	public static Integer get(Integer appCode){
		return convertorMap.get(appCode);
	}
}
