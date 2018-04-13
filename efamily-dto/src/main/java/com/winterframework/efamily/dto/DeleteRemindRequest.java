package com.winterframework.efamily.dto;

import java.util.List;

public class DeleteRemindRequest { 

	private Long remindId ;
	private Long type ;
	private List<Long> remindIds;
	public Long getRemindId() {
		return remindId;
	}
	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public List<Long> getRemindIds() {
		return remindIds;
	}
	public void setRemindIds(List<Long> remindIds) {
		this.remindIds = remindIds;
	}
	
}