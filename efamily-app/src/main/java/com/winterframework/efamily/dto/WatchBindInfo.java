package com.winterframework.efamily.dto;

public class WatchBindInfo {

	private String familyName="";
	private String familyCode="";
	private long bindWatchType;
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public long getBindWatchType() {
		return bindWatchType;
	}
	public void setBindWatchType(long bindWatchType) {
		this.bindWatchType = bindWatchType;
	}
	@Override
	public String toString() {
		return "WatchBindInfo [familyName=" + familyName + ", familyCode="
				+ familyCode + ", bindWatchType=" + bindWatchType + "]";
	}
	
}
