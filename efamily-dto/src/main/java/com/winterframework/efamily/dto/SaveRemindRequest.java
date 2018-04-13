package com.winterframework.efamily.dto;


public class SaveRemindRequest { 
	private Long remindId ;
	private String remindText ;
	private String content ;
    private String remindName ;
    private Long remindType ;
    private Long remindRepeatType ;
    private Long deliverTime ;
    private Long deliverDeadTime ;
    private Long deliverUserId ;
    private Long durationTime ;
	public Long getRemindId() {
		return remindId;
	}
	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}
	public String getRemindText() {
		return remindText;
	}
	public void setRemindText(String remindText) {
		this.remindText = remindText;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemindName() {
		return remindName;
	}
	public void setRemindName(String remindName) {
		this.remindName = remindName;
	}
	public Long getRemindType() {
		return remindType;
	}
	public void setRemindType(Long remindType) {
		this.remindType = remindType;
	}
	public Long getRemindRepeatType() {
		return remindRepeatType;
	}
	public void setRemindRepeatType(Long remindRepeatType) {
		this.remindRepeatType = remindRepeatType;
	}
	public Long getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Long deliverTime) {
		this.deliverTime = deliverTime;
	}
	public Long getDeliverDeadTime() {
		return deliverDeadTime;
	}
	public void setDeliverDeadTime(Long deliverDeadTime) {
		this.deliverDeadTime = deliverDeadTime;
	}
	public Long getDeliverUserId() {
		return deliverUserId;
	}
	public void setDeliverUserId(Long deliverUserId) {
		this.deliverUserId = deliverUserId;
	}
	public Long getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
	}
    
    
}