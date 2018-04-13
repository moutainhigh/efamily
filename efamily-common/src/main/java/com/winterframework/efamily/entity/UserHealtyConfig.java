package com.winterframework.efamily.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class UserHealtyConfig  extends FmlBaseEntity{
	private static final long serialVersionUID = 1603613724933974141L;
	
	//columns START
	private Long userId;
	private Integer sittingSwitch;
	private Integer stepSwitch;
	private Integer heartSwitch;
	private Long deviceId;
	private Integer climbSwitch;
	private Integer sleepSwitch;
	
	//columns END		
	
	public UserHealtyConfig(){
		
	}
		
	public UserHealtyConfig(Long id){
		this.id=id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSittingSwitch() {
		return sittingSwitch;
	}

	public void setSittingSwitch(Integer sittingSwitch) {
		this.sittingSwitch = sittingSwitch;
	}

	public Integer getStepSwitch() {
		return stepSwitch;
	}

	public void setStepSwitch(Integer stepSwitch) {
		this.stepSwitch = stepSwitch;
	}

	public Integer getHeartSwitch() {
		return heartSwitch;
	}

	public void setHeartSwitch(Integer heartSwitch) {
		this.heartSwitch = heartSwitch;
	}
	
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())
		.append("deviceId",getDeviceId())
		.append("UserId",getUserId())		
		.append("sittingSwitch",getSittingSwitch())		
		.append("stepSwitch",getStepSwitch())
		.append("heartSwitch",getHeartSwitch())
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId()).append(getDeviceId())
		.append(getUserId())
		.append(getSittingSwitch())
		.append(getStepSwitch())
		.append(getHeartSwitch())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof UserHealtyConfig == false) return false;
		if(this == obj) return true;
		UserHealtyConfig other = (UserHealtyConfig)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())
		
		.append(getDeviceId(), other.getDeviceId())

		.append(getUserId(),other.getUserId())

		.append(getSittingSwitch(),other.getSittingSwitch())

		.append(getStepSwitch(),other.getStepSwitch())
		.append(getHeartSwitch(),other.getHeartSwitch())

			.isEquals();
	}

}
