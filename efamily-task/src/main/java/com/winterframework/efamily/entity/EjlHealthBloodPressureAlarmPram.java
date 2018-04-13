package com.winterframework.efamily.entity;

import java.util.Date;

import com.winterframework.efamily.common.IntegerSpan;

public class EjlHealthBloodPressureAlarmPram {

	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer highMin;	//收缩压最小值
	private Integer highMax;	//收缩压最大值
	private Integer lowMin;	//舒张压最小值
	private Integer lowMax;	//舒张压最大值
	private Date fromTime;	//测试开始时间
	private Date toTime;	//测试结束时间
	
	
	public static EjlHealthBloodPressureAlarmPram getEjlHealthBloodPressureAlarmPram(Long userId,Long deviceId,Date fromTime,Date toTime,EfUserHealthSetting userHealthSett){
		EjlHealthBloodPressureAlarmPram ejlHealthBloodPressureAlarmPram = new EjlHealthBloodPressureAlarmPram();
		ejlHealthBloodPressureAlarmPram.setUserId(userId);
		ejlHealthBloodPressureAlarmPram.setDeviceId(deviceId);
		ejlHealthBloodPressureAlarmPram.setFromTime(fromTime);
		ejlHealthBloodPressureAlarmPram.setToTime(toTime);
		
		
		IntegerSpan spanBloodHigh=new IntegerSpan(userHealthSett.getBloodHighSpan());
		Integer bloodHighMin=spanBloodHigh.getDown();
		Integer bloodHighMax=spanBloodHigh.getUp();
		
		IntegerSpan spanBloodLow=new IntegerSpan(userHealthSett.getBloodLowSpan());
		Integer bloodLowMin=spanBloodLow.getDown();
		Integer bloodLowMax=spanBloodLow.getUp();
		
		
		ejlHealthBloodPressureAlarmPram.setHighMax(bloodHighMax);
		ejlHealthBloodPressureAlarmPram.setHighMin(bloodHighMin);
		ejlHealthBloodPressureAlarmPram.setLowMax(bloodLowMax);
		ejlHealthBloodPressureAlarmPram.setLowMin(bloodLowMin);
		
		
		return ejlHealthBloodPressureAlarmPram;
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
	public Integer getHighMin() {
		return highMin;
	}
	public void setHighMin(Integer highMin) {
		this.highMin = highMin;
	}
	public Integer getHighMax() {
		return highMax;
	}
	public void setHighMax(Integer highMax) {
		this.highMax = highMax;
	}
	public Integer getLowMin() {
		return lowMin;
	}
	public void setLowMin(Integer lowMin) {
		this.lowMin = lowMin;
	}
	public Integer getLowMax() {
		return lowMax;
	}
	public void setLowMax(Integer lowMax) {
		this.lowMax = lowMax;
	}
	
	public Date getFromTime() {
		return fromTime;
	}



	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}



	public Date getToTime() {
		return toTime;
	}



	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}



	@Override
	public String toString() {
		return "EjlHealthBloodPressureAlarmPram [userId=" + userId
				+ ", deviceId=" + deviceId + ", highMin=" + highMin
				+ ", highMax=" + highMax + ", lowMin=" + lowMin + ", lowMax="
				+ lowMax + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ "]";
	}
	
	
}
