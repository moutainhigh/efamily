package com.winterframework.efamily.dto;
public class GetVersionInfoResponse { 

	private String  versionNumber;
	private String  versionDescribe;
	private String  updateFlag;
	private String  downloadUrl;
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getVersionDescribe() {
		return versionDescribe;
	}
	public void setVersionDescribe(String versionDescribe) {
		this.versionDescribe = versionDescribe;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	
}