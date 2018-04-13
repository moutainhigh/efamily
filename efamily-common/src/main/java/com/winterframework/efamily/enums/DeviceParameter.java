package com.winterframework.efamily.enums;

public enum DeviceParameter {
	/* 设置与查询对应 */
	CONNECT_BEAT(400001,"connect_beat"),	//连网心跳频率
	CONNECT_TIMEOUT(400002,"connect_timeout"),	//连网超时
	CONNECT_RETRY(400003,"net_retry"),	//连网重试次数
	NET_RETRY(400004,"net_retry"),	//网络重启次数
	//BRIGHT_MAX(400005,"bright_max"),	//最大亮度
	BRIGHT(400006,"bright"),	//亮度
	//SOUND_MAX(400007,"sound_max"),	//最大声音
	SOUND(400008,"sound"),	//声音
	SIGNAL_GATHER_FREQ(400009,"signal_gather_freq"),	//信号采集频率
	SIGNAL_UPLOAD_FREQ(400010,"signal_upload_freq"),	//信号上传频率
	SIGNAL(400011,"signal_gather_freq"),	//信号
	BATTERY(400012,"battery"),	//电量采集频率
	//BATTERY_GATHER_FREQ(400012,"battery_gather_freq"),	//电量采集频率
	BATTERY_UPLOAD_FREQ(400013,"battery_upload_freq"),	//电量上传频率
	LOCATION_ONFF(400014,"location_onff"),	//定位开关
	LOCATION_GATHER_FREQ(400015,"location_gather_freq"),	//定位采集频率
	LOCATION_UPLOAD_FREQ(400016,"location_upload_freq"),	//定位上传频率
	HEALTH_HEART_ONFF(400017,"health_heart_onff"),	//心率开关
	HEALTH_HEART_GATHER_FREQ(400018,"health_heart_gather_freq"),	//心率采集频率
	HEALTH_HEART_UPLOAD_FREQ(400019,"health_heart_upload_freq"),	//心率上传频率
	HEALTH_WALK_ONFF(400020,"health_walk_onff"),	//计步开关
	HEALTH_WALK_UPLOAD_FREQ(400021,"health_walk_upload_freq"),	//计步上传频率 
	CHAT_SETTING(400022,"chat_setting"),	//聊天设置
	CONTACTS(400023,"contacts"),	//通讯录
	SOFTWARE_VERSION(400024,"software_version"),	//软件版本
	POWER_AUTO(400025,"power_auto"),	//自动开关机
	SITTING_ONFF(400026,"sitting_onff"),	//久坐开关
	SITTING_TIME(400027,"sitting_time"),	//久坐时间
	SLEEP_ONFF(400028,"sleep_onff"),	//睡眠监测开关
	SLEEP_SPAN(400029,"sleep_span"),	//睡眠监测时间区间
	LOCATION_GATHER_FREQ_RUN(400030,"location_gather_freq_run"),	//定位采集频率(跑步)
	LOCATION_GATHER_FREQ_SWIM(400031,"location_gather_freq_swim"),	//定位采集频率(游泳)
	LOCATION_GATHER_FREQ_BIKE(400032,"location_gather_freq_bike"),	//定位采集频率(骑行)
	TEST(99999999,"test");	//test
	
	private int _code;
	private String _value;
	DeviceParameter(int code,String value){
		this._code=code;
		this._value=value;
	}
	public int getCode(){
		return _code;
	}
	public String getValue(){
		return _value;
	}
	public static DeviceParameter get(int code){
		for(DeviceParameter param:DeviceParameter.values()){
			if(code==param.getCode()){
				return param;
			}
		}
		return null;
	}
}
