package com.winterframework.efamily.dto;
public class WatchDeviceManageRequest { 

	private String watchId;
	private String parameterIndex;
	private String parameterContext;
	private String familyId;
	private Long oprType ;//操作类型： 0获取 ；1设置
	
	public Long getOprType() {
		return oprType;
	}
	public void setOprType(Long oprType) {
		this.oprType = oprType;
	}
	public String getWatchId() {
		return watchId;
	}
	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}
	public String getParameterIndex() {
		return parameterIndex;
	}
	public void setParameterIndex(String parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public String getParameterContext() {
		return parameterContext;
	}
	public void setParameterContext(String parameterContext) {
		this.parameterContext = parameterContext;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	
}