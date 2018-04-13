package com.winterframework.efamily.dto;

import java.util.List;

public class SaveFenceReq {

	private String imei;
	private List<BatchSetOrgUserBarrierRequest> fenceInfo;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public List<BatchSetOrgUserBarrierRequest> getFenceInfo() {
		return fenceInfo;
	}
	public void setFenceInfo(List<BatchSetOrgUserBarrierRequest> fenceInfo) {
		this.fenceInfo = fenceInfo;
	}
}
