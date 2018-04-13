package com.winterframework.efamily.entity;

public class BusinessAreas {

/*    "businessAreas": [
		                {
		                    "location": "116.29522008325625,39.99426090286774",
		                    "name": "颐和园",
		                    "id": "110108"
		                }
		            ]*/
		  
	private String location;
	private String name;
	private String id;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "BusinessAreas [location=" + location + ", name=" + name
				+ ", id=" + id + "]";
	}
	
}
