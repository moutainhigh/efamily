package com.winterframework.efamily.dto;
public class WatchDeviceManageResponse { 

	private String watchId;
	private String parameterIndex;
	private String parameterContext;
	private String userId;
	private String familyId;
	private String usingDeviceUserId;
	
	public String getUsingDeviceUserId() {
		return usingDeviceUserId;
	}
	public void setUsingDeviceUserId(String usingDeviceUserId) {
		this.usingDeviceUserId = usingDeviceUserId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	
	
}