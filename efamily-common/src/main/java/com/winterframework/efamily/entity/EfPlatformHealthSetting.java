package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfPlatformHealthSetting extends FmlBaseEntity {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = -7021617253380871986L;
	private Integer ageLevel;	//年龄层级
	private String bloodHighSpan;	//收缩压范围
	private String bloodLowSpan;	//舒张压范围
	private String rateSpan; //心率范围
	
	
	public Integer getAgeLevel() {
		return ageLevel;
	}
	public void setAgeLevel(Integer ageLevel) {
		this.ageLevel = ageLevel;
	}
	public String getBloodHighSpan() {
		return bloodHighSpan;
	}
	public void setBloodHighSpan(String bloodHighSpan) {
		this.bloodHighSpan = bloodHighSpan;
	}
	public String getBloodLowSpan() {
		return bloodLowSpan;
	}
	public void setBloodLowSpan(String bloodLowSpan) {
		this.bloodLowSpan = bloodLowSpan;
	}
	public String getRateSpan() {
		return rateSpan;
	}
	public void setRateSpan(String rateSpan) {
		this.rateSpan = rateSpan;
	}

	
}
	

