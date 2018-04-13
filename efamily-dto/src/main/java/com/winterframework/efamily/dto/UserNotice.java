package com.winterframework.efamily.dto;

import java.util.Map;

public class UserNotice {
	private Long userId;		//用户ID
	private Long deviceId;		//设备ID
	private String userName;	//用户名称
	private Long noticeId;		//通知ID
	private NoticeType noticeType;	//通知类型
	private Map<String, String> paramMap;
	
	public enum NoticeType{
		FRIEND(1001),	//好友邀请
		TEST(201),	
		
		//-------------------  APP和SERVICE端的推送   COMMON   ------------------------------------
		
		SCAN_WATCH(101001),	//绑定手表
		UPDATE_WATCH_OWNER(101002),	//更新手表拥有者
		MANAGE_FAMILY_USER(102001),	//管理家庭用户
		MANAGE_FAMILY_USER_AGREE(102002),	//允许家庭用户加入
		FAMILY_INFO_UPDATE(102003),	//家庭信息修改
		ATTENTION_APPLY_CANCEL(102004),	//申请 取消 关注推送
		ATTENTION_AGREE(102005),	//关注 同意 推送
		TRANSFER_FAMILY_HOST(102006),	//转让家主推送 
		MANAGE_FAMILY_USER_DELETE_QUIT(102007),	//转让家主推送
		UPDATE_USER_INFO(103001),	//更新用户信息
		MANAGE_FRIEND_USER(104001), //管理好友
		MANAGE_FRIEND_USER_AGREE(104002),	//允许加入好友 
		MANAGE_FRIEND_ADDRESSlIST(104003),	//根据通讯录加为好友
		BIND_REFUSED(101003),	//绑定拒绝
		BIND_ACCEPT(101004),	//绑定接受
		BATTERY_WARNING(101005), //低电告警
		ELECTRONIC_WARNING(101006), //围栏告警
		DEVICE_ONOFF(101007),//设备开关机
		RATE_WARNING(101008),
		BLOOOD_PRESSURE(101009),
		//106001 已经被使用：发送聊天信息使用
		MANAGE_GROUP_SETTING(107001),//修改群组信息 
		FORBIT_SPEAK(107002),	//禁言推送
		LOGIN_REPEAT_REMOVE_BEFORE_USER(108001), //重复登录剔除前面登录的用户，并推送告诉前面的用户 
		UPDATE_HEALTHY(109001),//更新健康数据
		REMIND_SEND(109002),//提醒已发送
		SLEEP_START(109004),//睡眠开始通知 
		SWITCH_ONOFF(109005),//久坐提醒
		LOCATION_NEWEST_QUERY(109006),//定位最新查询
		APP_DEVICE_SOFTWARE_UPDATE_PUSH(115101),//APP发起的设备软件更新结果
		APP_DEVICE_HEALTH_HEART_START(115102),	//APP发起的设备心率测量开始
		APP_DEVICE_HEALTH_HEART_FINISH(115103),	//APP发起的设备心率测量结束
		//-------------------  手表APP交互   COMMON   ------------------------------------
		
		APP_DEVICE_SPAN_WATCH(20103), //扫描手表(绑定手表)。
		APP_DEVICE_ON_OFF_WATCH(20104), //设备开关机。
		
		APP_DEVICE_UNBIND_WATCH(20106), //解绑手表
		APP_DEVICE_RESTORE_SETTING(20107), //设备恢复出厂设置
		APP_DEVICE_AUTO_ON_OFF_WATCH(20141), //自动开关机设置。
		APP_DEVICE_ADDRESSLIST_WATCH(20301), //设置手表通讯录。
		//APP_DEVICE_UPDATE_WATCH_OWNER(),	//更换手表使用者(解绑旧表(人)换新表(人)，互换表。)。
		APP_DEVICE_PARAMETER_MANAGE(20203),	//设置和读取手表参数(解绑手表，通讯录设置和获取，设置和获取电量，亮度等参数)。
		APP_DEVICE_PARAMETER_MANAGE_NEW(20208),	//(新)设置和读取手表参数(解绑手表，通讯录设置和获取，设置和获取电量，亮度等参数)。
		APP_DEVICE_SEND_MESSAGE(20504), //给手表发送消息的接口(手表聊天室聊天时收发消息)。
		APP_DEVICE_MONITOR(28961),	//打开监听接口
		ALARM_SET(20499),	//闹铃告警
		APP_DEVICE_TRIGER_LOCATION(20626);
		
		//------------------------------------------------------------------------------
		private int _value=1;
		NoticeType(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	public UserNotice(){
		
	}

	public UserNotice(Long userId, String userName, Long noticeId, Map<String, String> paramMap) {
		this.userId = userId;
		this.userName = userName;
		this.noticeId = noticeId;
		this.paramMap = paramMap;
	}


	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public static void main(String[] args) {
		System.out.println(NoticeType.APP_DEVICE_ADDRESSLIST_WATCH);
	}
}