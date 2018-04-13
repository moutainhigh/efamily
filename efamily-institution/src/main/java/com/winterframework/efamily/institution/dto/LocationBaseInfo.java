package com.winterframework.efamily.institution.dto;

public class LocationBaseInfo {

	private String location;
    private Integer type;
    private Long time;    
    private String addressDesc;
    private Long duration;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getAddressDesc() {
		return addressDesc;
	}
	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "LocationBaseInfo [location=" + location + ", type=" + type
				+ ", time=" + time + ", addressDesc=" + addressDesc
				+ ", duration=" + duration + "]";
	}
    
}
