package com.winterframework.efamily.dto;

import java.util.List;

public class QuitFamilyResponse { 


	  private Long familyId ;

	  private List<UnbindDeviceInfo> unbindDevicedInfo ;
	  
	  
	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public List<UnbindDeviceInfo> getUnbindDevicedInfo() {
		return unbindDevicedInfo;
	}

	public void setUnbindDevicedInfo(List<UnbindDeviceInfo> unbindDevicedInfo) {
		this.unbindDevicedInfo = unbindDevicedInfo;
	}
	
}