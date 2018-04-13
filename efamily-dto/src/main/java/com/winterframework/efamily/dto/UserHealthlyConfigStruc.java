package com.winterframework.efamily.dto;

import java.util.List;

public class UserHealthlyConfigStruc {

	private Long deviceId;
	private Long userId;
	private Long id;
	private Long sex=0l;
	private Long age=0l;
	private Long height=0l;
	private Double weight=0d; 
	private Integer arm;
	private Long stepCount=0l;
	private Float sitTime=0f;
	
	private Integer remindSwitch=1;//0开 1关
	private Integer stepCountSwitch=0;//0开 1关
	private Integer heartSwitch=0;//0开 1关
	private Integer climbSwitch=1;//0开 1关
	private Integer sleepSwitch=0;
	
	private List sittingSpan;
	private List sleepSpan;
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSex() {
		return sex;
	}
	public void setSex(Long sex) {
		this.sex = sex;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
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
	public Long getStepCount() {
		return stepCount;
	}
	public void setStepCount(Long stepCount) {
		this.stepCount = stepCount;
	}
	public Float getSitTime() {
		return sitTime;
	}
	public void setSitTime(Float sitTime) {
		this.sitTime = sitTime;
	}
	public Integer getRemindSwitch() {
		return remindSwitch;
	}
	public void setRemindSwitch(Integer remindSwitch) {
		this.remindSwitch = remindSwitch;
	}
	public Integer getStepCountSwitch() {
		return stepCountSwitch;
	}
	public void setStepCountSwitch(Integer stepCountSwitch) {
		this.stepCountSwitch = stepCountSwitch;
	}
	public Integer getHeartSwitch() {
		return heartSwitch;
	}
	public void setHeartSwitch(Integer heartSwitch) {
		this.heartSwitch = heartSwitch;
	}
	public Integer getClimbSwitch() {
		return climbSwitch;
	}
	public void setClimbSwitch(Integer climbSwitch) {
		this.climbSwitch = climbSwitch;
	}
	public Integer getSleepSwitch() {
		return sleepSwitch;
	}
	public void setSleepSwitch(Integer sleepSwitch) {
		this.sleepSwitch = sleepSwitch;
	}
	public List getSittingSpan() {
		return sittingSpan;
	}
	public void setSittingSpan(List sittingSpan) {
		this.sittingSpan = sittingSpan;
	}
	public List getSleepSpan() {
		return sleepSpan;
	}
	public void setSleepSpan(List sleepSpan) {
		this.sleepSpan = sleepSpan;
	}
}
