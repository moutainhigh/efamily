package com.winterframework.efamily.institution.dto;

public class EmployeeOffWorkAndLeaveInfo {

	private Long orgEmployeeId;//员工ID
	private Integer optType;//操作类型   1 请假   2 离职
	private String offWorkStartTime;//请假开始时间
	private String offWorkEndTime;//请假结束时间
	private String content;//请假内容
	
	
	
	public Long getOrgEmployeeId() {
		return orgEmployeeId;
	}
	public void setOrgEmployeeId(Long orgEmployeeId) {
		this.orgEmployeeId = orgEmployeeId;
	}
	public Integer getOptType() {
		return optType;
	}
	public void setOptType(Integer optType) {
		this.optType = optType;
	}
	public String getOffWorkStartTime() {
		return offWorkStartTime;
	}
	public void setOffWorkStartTime(String offWorkStartTime) {
		this.offWorkStartTime = offWorkStartTime;
	}
	public String getOffWorkEndTime() {
		return offWorkEndTime;
	}
	public void setOffWorkEndTime(String offWorkEndTime) {
		this.offWorkEndTime = offWorkEndTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "EmployeeOffWorkAndLeaveInfo [orgEmployeeId=" + orgEmployeeId
				+ ", optType=" + optType + ", offWorkStartTime="
				+ offWorkStartTime + ", offWorkEndTime=" + offWorkEndTime
				+ ", content=" + content + "]";
	}
	
	
}
