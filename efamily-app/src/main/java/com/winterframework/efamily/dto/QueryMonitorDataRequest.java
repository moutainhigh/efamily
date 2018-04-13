package com.winterframework.efamily.dto;

import java.util.Date;

/** 
* @ClassName: QueryMonitorDataRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:13:24 
*  
*/
public class QueryMonitorDataRequest {
	private Integer dateType;
	private Long startDateTime;
	private Long endDateTime;
	private Integer monitorDataType;
	private Long userId;
	private Date startQueryTime;
	private Date endQueryTime;
	private Long deviceId;
	private Integer currentPage;
	private Integer perPageSize;
	
	private String sortColumns;

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
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

	public Integer getMonitorDataType() {
		return monitorDataType;
	}

	public void setMonitorDataType(Integer monitorDataType) {
		this.monitorDataType = monitorDataType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartQueryTime() {
		return startQueryTime;
	}

	public void setStartQueryTime(Date startQueryTime) {
		this.startQueryTime = startQueryTime;
	}

	public Date getEndQueryTime() {
		return endQueryTime;
	}

	public void setEndQueryTime(Date endQueryTime) {
		this.endQueryTime = endQueryTime;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

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
	
}
