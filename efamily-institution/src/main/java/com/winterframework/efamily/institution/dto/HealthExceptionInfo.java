package com.winterframework.efamily.institution.dto;

public class HealthExceptionInfo {

	public Long alarmType;
	public String value;
	public Integer flag;//0正常  1低  2高
	public Long getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Long alarmType) {
		this.alarmType = alarmType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "HealthExceptionInfo [alarmType=" + alarmType + ", value="
				+ value + ", flag=" + flag + "]";
	}
	
}
