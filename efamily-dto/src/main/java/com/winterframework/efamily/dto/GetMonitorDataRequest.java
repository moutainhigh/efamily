package com.winterframework.efamily.dto;
public class GetMonitorDataRequest { 

	private Integer currentPage ;
	private Integer perPageSize ;
	private Integer dateType ;
	private Integer monitorDataType ;
	private Long startDateTime ;
	private Long endDateTime ;
	private Long userId ;
	private Long deviceId ;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPerPageSize() {
		return perPageSize;
	}
	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}
	public Integer getDateType() {
		return dateType;
	}
	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	public Integer getMonitorDataType() {
		return monitorDataType;
	}
	public void setMonitorDataType(Integer monitorDataType) {
		this.monitorDataType = monitorDataType;
	}
	public Long getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Long startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Long getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Long endDateTime) {
		this.endDateTime = endDateTime;
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

}