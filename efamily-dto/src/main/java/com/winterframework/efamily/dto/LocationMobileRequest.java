package com.winterframework.efamily.dto;

import java.util.List;

import com.winterframework.efamily.dto.device.DeviceMobileRequest;


public class LocationMobileRequest{
	private List<LocationWifiRequest> wifiList;
	private List<DeviceMobileRequest> mobileList;
	private List<LocationRequest> gpsList;
	public List<LocationWifiRequest> getWifiList() {
		return wifiList;
	}
	public void setWifiList(List<LocationWifiRequest> wifiList) {
		this.wifiList = wifiList;
	}
	public List<DeviceMobileRequest> getMobileList() {
		return mobileList;
	}
	public void setMobileList(List<DeviceMobileRequest> mobileList) {
		this.mobileList = mobileList;
	}
	public List<LocationRequest> getGpsList() {
		return gpsList;
	}
	public void setGpsList(List<LocationRequest> gpsList) {
		this.gpsList = gpsList;
	}
	
	
	
	
	
	
}
