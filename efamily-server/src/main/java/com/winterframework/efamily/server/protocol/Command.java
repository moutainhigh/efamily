package com.winterframework.efamily.server.protocol;

public enum Command {
	/**
	 * 100*:设置指令
	 * 200*:业务指令
	 */ 
	/*LOGON(10002,"登录"),
	GPS_SET(10001,"GPS设定"),
	CHAT(20001,"聊天"),
	GPS_ONFF_SET(10601,"GPS开关设置");*/  
	REGISTER(10001,"注册"),
	LOGON(11001,"登录"),
	GPS_SET(11001,"GPS设定"),
	CHAT(12003,"聊天"),
	MESSAGE(10001,"消息"), 
	NOTICE(10005,"推送"), 
	NOTIFICATION(10009,"系统推送"),
	CHAT_MESSAGE(106001,"聊天消息推送"),
	PUBLIC_DATA(110001,"获取公共数据"),
	MODULE_DATA(17015,"模块数据"),
	HEART_BEAT(110002,"心跳"),
	CHECK_STATUS(11004,"验证登录状态"),
	VERIFYCODE(11002,"获取验证码"),
	APP_DEVICE_SOFTWARE_QUERY(15190,"发起设备软件版本查询"),	
	APP_DEVICE_SOFTWARE_UPDATE(15191,"发起设备软件更新"),	
	
	// ////////////Device////////////////
	
	DEVICE_HARDWARE(20000,"设备硬件信息上传"),
	DEVICE_HEARTBEAT(20001,"设备心跳"),
	DEVICE_HELLO_NEW(20009,"设备打招呼(新)"),
	DEVICE_SOFTWARE_QUERY(20100,"设备软件查询"),
	DEVICE_HELLO(20101,"设备打招呼"),
	DEVICE_BIND(20102,"设备绑定确认"),
	DEVICE_BIND_PUSH(20103,"设备绑定推送"),
	DEVICE_POWER_ONFF(20104,"设备开关机"),
	DEVICE_MOBILE_UPLOAD(20108,"设备移动信息上传"),
	DEVICE_MOBILE_QUERY(20109,"设备移动信息查询"),
	DEVICE_SOFRWARE_UPDATE(20121,"设备软件版本更新"),
	DEVICE_POWER_AUTO_SET(20141,"设备自动开关机设置"),
	DEVICE_POWER_AUTO_QUERY(20142,"设备自动开关机查询"),
	DEVICE_TIME_SET(20151,"设备时间设置"),
	DEVICE_TIME_QUERY(20152,"设备时间查询"),
	DEVICE_BRIGHT_MAX_SET(20161,"最大亮度设置"),
	DEVICE_BRIGHT_SET(20162,"亮度设置"),
	DEVICE_BRIGHT_MAX_QUERY(20163,"最大亮度查询"),
	RESOURCE_UPLOAD(20201,"资源上传"),
	RESOURCE_DOWNLOAD(20202,"资源下载"),
	DEVICE_PARAM_SET(20203,"参数设置"),
	DEVICE_PARAM_GET(20204,"参数查询"),
	DEVICE_USER_QUERY(20205,"用户信息查询"),

	DEVICE_BATTERY_GET(20196,"电池数据查询"),
	DEVICE_REMIND_SET(20401,"提醒设置"),
	DEVICE_REMIND_QUERY(20402,"提醒设置查询"),
	DEVICE_REMIND_ADD(20403,"提醒添加"),
	DEVICE_REMIND_UPDATE(20404,"提醒修改"),
	DEVICE_REMIND_DELETE(20405,"提醒删除"),
	DEVICE_STEP_QUERY(20825,"设备计步查询"),
	DEVICE_STEP_UPLOAD(20844,"设备计步上传"),
	DEVICE_STEP_REMOTE(20824,"设备计步远程"),
	DEVICE_BLOOD_UPLOAD(20851,"设备血压上传"),
	DEVICE_BLOOD_QUERY(20851,"设备血压查询"),
	BATTERY_MAX_QUERY(20195,"电池最大电量查询"),
	DEVICE_MONITOR_UPLOAD(28962,"设备监控上传"),
	DEVICE_SITTING_UPLOAD(20845,"设备久坐上传"),
	DEVICE_SITTING_QUERAY(20829,"设备久坐查询"),
	DEVICE_SIGNAL_MAX_QUERY(20185,"设备信号最大值查询"),
	DEVICE_SIGNAL_QUERY(20186,"设备信号数据查询"),
	DEVICE_SIGNAL_UPLOAD(20187,"设备信号数据上传"),
	DEVICE_CONTACTS_SET(20301,"设备通讯录设置"),
	DEVICE_CONTACTS_GET(20321,"设备通讯录查询"),
	DEVICE_CHAT_SET(20501,"设备聊天设置"),
	DEVICE_CHAT_GET(20502,"设备聊天设置查询"),
	DEVICE_CHAT_SEND(20503,"设备聊天发送"),
	DEVICE_CHAT_PUSH(20504,"设备聊天推送"),
	DEVICE_LOCATION_SATELLITE_QUERY(20624,"设备定位星信噪比查询"),
	DEVICE_LOCATION_QUERY(20625,"设备定位查询"),
	DEVICE_LOCATION_EXCEPTION_UPLOAD(20644,"设备定位异常上传"),
	DEVICE_LOCATION_UPLOAD(20645,"设备定位上传"),
	DEVICE_LOCATION_WIFI_UPLOAD(20646,"设备定位wifi上传"),
	DEVICE_LOCATION_WIFI_QUERY(20647,"设备定位wifi查询"),
	DEVICE_HEART_QUERY(20725,"设备心率查询"),

	DEVICE_HEART_UPLOAD(20744,"设备心率上传"),
	DEVICE_LUNAR_INIT(28971,"设备农历初始化"),
	DEVICE_LUNAR_SET(28972,"设备农历设置"),
	DEVICE_LUNAR_PUSH(28973,"设备农历推送"),
	DEVICE_WEATHER_INIT(28981,"设备天气初始化"),
	DEVICE_WEATHER_SET(28982,"设备天气设置"),
	DEVICE_WEATHER_PUSH(28983,"设备天气推送"),
	DEVICE_MONITOR(28961,"设备监听"),
	DEVICE_SLEEP_QUERY(20925,"设备睡眠查询"),
	DEVICE_SLEEP_UPLOAD(20926,"设备睡眠上传"),
	
	SMS_DEVICE_CONNECT(29101,"设备短信连网"),
	SMS_DEVICE_ONFF(29103,"设备短信开关机"),
	SMS_DEVICE_ONFF_QUERY(29104,"设备短信开关机查询"),
	SMS_DEVICE_LOCATION_ONFF(29131,"设备短信定位开关"),
	SMS_DEVICE_LOCATION_ONFF_QUERY(29132,"设备短信定位开关查询"),
	SMS_DEVICE_LOCATION_QUERY(29133,"设备短信定位数据集查询"),
	REMIND(105001,"提醒");

	private int _code;
	private String _value;
	Command(int code,String value){
		this._code=code;
		this._value=value;
	}
	public int getCode(){
		return _code;
	}
	public String getValue(){
		return _value;
	}
}
