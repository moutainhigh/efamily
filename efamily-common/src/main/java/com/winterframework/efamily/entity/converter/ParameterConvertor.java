package com.winterframework.efamily.entity.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.entity.EfDeviceSetting;
public class ParameterConvertor {
	private static Map<Integer,Integer> convertorMap=new HashMap<Integer, Integer>();
	private static Map<Integer,List<Integer>> moduleMap=new HashMap<Integer, List<Integer>>();
	
	private static List<Integer> connectList=new ArrayList<Integer>();
	private static List<Integer> commonList=new ArrayList<Integer>();
	private static List<Integer> healthList=new ArrayList<Integer>();
	private static List<Integer> freqList=new ArrayList<Integer>();
	static{
		connectList.add(400001);
		connectList.add(400002);
		connectList.add(400003);
		connectList.add(400004);
	}
	static{
		commonList.add(400006);
		commonList.add(400008);
		commonList.add(400022);
		commonList.add(400023);
		commonList.add(400024);
		commonList.add(400025);
		commonList.add(400014);
		commonList.add(400017);
		commonList.add(400020);
		commonList.add(400026);
		commonList.add(400028);
		
	}
	static{
		healthList.add(400027);
		healthList.add(400029);
		healthList.add(400033);
		healthList.add(400034);
		healthList.add(400036);
		healthList.add(400037);
	}
	static{
		freqList.add(400009);
		freqList.add(400010);
		freqList.add(400012);
		freqList.add(400013);
		freqList.add(400015);
		freqList.add(400016);
		freqList.add(400018);
		freqList.add(400019);
		freqList.add(400021);
	}
	
	static{
		moduleMap.put(EfDeviceSetting.ModuleCode.CONNECT.getValue(), connectList);
		moduleMap.put(EfDeviceSetting.ModuleCode.COMMON.getValue(), commonList);
		moduleMap.put(EfDeviceSetting.ModuleCode.HEALTH.getValue(), healthList);
		moduleMap.put(EfDeviceSetting.ModuleCode.FREQUENCY.getValue(), freqList);
	}
	public enum ParamIndex{ 
		SOUND(100004),BRIGHT(100005),SHAKE(100006),QUIET(100017),LOCATION_ONFF(400014),
		HEART_ONFF(100007),WALK_ONFF(100008),SITTING_ONFF(400024),SLEEP_ONFF(400028),
		SITTING_TIME(400025),SITTING(400036),SLEEP_SPAN(400029),SLEEP(400037),SIGNAL_GFREQ(100171),SIGNAL_UFREQ(100181),
		BATTERY_GFREQ(100001),BATTERY_UFREQ(100211),HEART_GFREQ(100251),HEART_UFREQ(100261),
		WALK_UFREQ(100271),LOCATION_GFREQ(100231),LOCATION_UFREQ(100241);
		private int _value;
		ParamIndex(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}		
	} 
	static{
		//convertorMap.put(100030, "address_list");           //---- （通讯录）：获取通讯录。 ----
		convertorMap.put(100004, 400008);                 //---- （音量）：获取音量值。----
		convertorMap.put(100005, 400006);             //---- （亮度）：获取亮度值。----
		convertorMap.put(100006, 400022);                  //---- （震动模式）：获取震动模式的值。----
		convertorMap.put(100007, 400017);             //---- （心率）：获取心率开关值。---- 
		convertorMap.put(100008, 400020);           //---- （记步）：获取记步的开关值。----
		//convertorMap.put(100101, "on_off");                 //---- （关／关机）：关机。----
		convertorMap.put(100017, 400023);             //---- （静音）：获取静音模式的值。----
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
		convertorMap.put(100001,400012);    //battery_max	最大电池值	   
		convertorMap.put(100211,400013);    //battery_upload_freq	电池上传频率	   
		convertorMap.put(400014,400014);    //location_onff	定位开关	   
		convertorMap.put(100231,400015);    //location_gather_freq	定位采集频率	   
		convertorMap.put(100241,400016);    //location_upload_freq	定位上传频率	   
		convertorMap.put(100251,400018);    //health_heart_gather_freq	心率采集频率	   
		convertorMap.put(100261,400019);    //health_heart_upload_freq	心率上传频率	   	   
		convertorMap.put(100271,400021);    //health_walk_upload_freq	计步上传频率
		
	}
	public static Integer get(Integer appCode){
		Integer code=convertorMap.get(appCode);
		if(null==code){
			code=appCode;
		}
		return code;
	}
	
	public static Integer getModuleCode(Integer paramCode){
		for(Map.Entry<Integer,List<Integer>>  entry:moduleMap.entrySet()){
			List<Integer> l=entry.getValue();
			if(l.contains(paramCode)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
