package com.winterframework.efamily.entity;

import com.winterframework.efamily.base.enums.Separator;

public class LatLng {

	public Double latitude;
	public Double longitude;
	
	public LatLng(){
		
	}
	/**
	 * @param 纬度,经度
	 */
	public LatLng(String location){
		if(null!=location && location.contains(Separator.comma)){
			String[] arr=location.split(Separator.comma);
			latitude=Double.valueOf(arr[0]);
			longitude=Double.valueOf(arr[1]);
		}
	}
	public LatLng(Double latitude,Double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String toString(){
		return longitude+","+latitude;
	}
	@Override
	public boolean equals(Object obj) {
		LatLng ll=(LatLng)obj;
		return latitude.equals(ll.getLatitude()) && longitude.equals(ll.getLongitude());
	}
}
