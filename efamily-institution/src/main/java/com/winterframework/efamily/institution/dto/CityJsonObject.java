package com.winterframework.efamily.institution.dto;

import java.util.List;

import com.winterframework.efamily.entity.Location;

public class CityJsonObject {

	private Location location;
	private String formatted_address;
	private String business;
	private AddressComponent addressComponent;
	private List poiRegions;
	private String sematic_description;
	private int cityCode;
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}
	public List getPoiRegions() {
		return poiRegions;
	}
	public void setPoiRegions(List poiRegions) {
		this.poiRegions = poiRegions;
	}
	public String getSematic_description() {
		return sematic_description;
	}
	public void setSematic_description(String sematic_description) {
		this.sematic_description = sematic_description;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
}
