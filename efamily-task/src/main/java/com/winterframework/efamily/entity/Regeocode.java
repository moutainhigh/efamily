package com.winterframework.efamily.entity;

public class Regeocode {

	//"country":"中国","province":"北京市","city":[],"citycode":"010","district":"海淀区","adcode":"110108",
	//"township":"燕园街道","towncode":"110108015000",
	//"neighborhood":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},
	//"building":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},
	//"streetNumber":{"street":"颐和园路","number":"5号","location":"116.310518,39.9918931","direction":"东","distance":"44.4465"},
	//"businessAreas":[{"location":"116.29522008325625,39.99426090286774","name":"颐和园","id":"110108"}
	private String formatted_address;
	private AddressComponentGaoDe addressComponent;
/*	private String pois;
	private String roads;
	private String roadinters;
	private String aois;*/
	
	
	public String getFormatted_address() {
		return formatted_address;
	}
	public String getFormattedAddress() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public AddressComponentGaoDe getAddressComponent() {
		return addressComponent;
	}
	public void setAddressComponent(AddressComponentGaoDe addressComponent) {
		this.addressComponent = addressComponent;
	}
	@Override
	public String toString() {
		return "Regeocode [formatted_address=" + formatted_address
				+ ", addressComponent=" + addressComponent + "]";
	}
	
	
}
