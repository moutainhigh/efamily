package com.winterframework.efamily.dto;

import java.util.List;

public class ManageJoinFamilyResponse { 

	private Long familyId ;

	private List<UnbindDeviceInfo> unbindDeviceInfo;
	
	public List<UnbindDeviceInfo> getUnbindDeviceInfo() {
		return unbindDeviceInfo;
	}

	public void setUnbindDeviceInfo(List<UnbindDeviceInfo> unbindDeviceInfo) {
		this.unbindDeviceInfo = unbindDeviceInfo;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	@Override
	public String toString() {
		return "ManageJoinFamilyResponse [familyId=" + familyId
				+ ", unbindDeviceInfo=" + unbindDeviceInfo + "]";
	}

}