package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfUserHealthSetting extends FmlBaseEntity {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = -4672438880490532633L;
	private Long userId;	//用户ID
	private Integer age;	//年龄
	private Integer height;	//身高
	private Double weight;	//体重
	private Integer arm;	//臂长
	private String bloodHighSpan;	//收缩压范围
	private String bloodLowSpan;	//舒张压范围
	private String rateSpan; //心率范围
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Integer getArm() {
		return arm;
	}
	public void setArm(Integer arm) {
		this.arm = arm;
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
	

