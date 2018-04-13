 
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.core.base.FmlBaseEntity;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamConnect;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
 
public class EfDeviceSetting extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4029673103511049441L;
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private String connect;	//连网设置	 jsonofDeviceParamConnect
	private String common;	//普通设置	jsonofDeviceParamCommon
	private String frequency; //频率设置 jsonofDeviceParamFrequency
	private String health;	//健康设置	jsonofDeviceParamHealth
	
	public static void coverNewValue(EfDeviceSetting oldObj,EfDeviceSetting newObj){
		if(null!=newObj.getCommon()){
			DeviceParamCommon param=JsonUtils.fromJson(oldObj.getCommon(),DeviceParamCommon.class);
			DeviceParamCommon paramNew=JsonUtils.fromJson(newObj.getCommon(),DeviceParamCommon.class);
			
			DeviceParamCommon.coverNewValue(param, paramNew);
			oldObj.setCommon(JsonUtils.toJson(param));
		}
		if(null!=newObj.getConnect()){
			DeviceParamConnect param=JsonUtils.fromJson(oldObj.getConnect(),DeviceParamConnect.class);
			DeviceParamConnect paramNew=JsonUtils.fromJson(newObj.getConnect(),DeviceParamConnect.class);
			
			DeviceParamConnect.coverNewValue(param, paramNew);
			oldObj.setConnect(JsonUtils.toJson(param));
		}
		if(null!=newObj.getFrequency()){
			DeviceParamFrequency param=JsonUtils.fromJson(oldObj.getFrequency(),DeviceParamFrequency.class);
			DeviceParamFrequency paramNew=JsonUtils.fromJson(newObj.getFrequency(),DeviceParamFrequency.class);
			
			DeviceParamFrequency.coverNewValue(param, paramNew);
			oldObj.setFrequency(JsonUtils.toJson(param));
		}
		if(null!=newObj.getHealth()){
			DeviceParamHealth param=JsonUtils.fromJson(oldObj.getHealth(),DeviceParamHealth.class);
			DeviceParamHealth paramNew=JsonUtils.fromJson(newObj.getHealth(),DeviceParamHealth.class);
			
			DeviceParamHealth.coverNewValue(param, paramNew);
			oldObj.setHealth(JsonUtils.toJson(param));
		}
	}
	
	public enum ModuleCode{ 
		CONNECT(410001),COMMON(410002),HEALTH(410003),FREQUENCY(410004);
		private int _value;
		ModuleCode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}		
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
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	
	
}
	

