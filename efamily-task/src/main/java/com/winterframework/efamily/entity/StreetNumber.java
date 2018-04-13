package com.winterframework.efamily.entity;

public class StreetNumber {
/**
 		            "streetNumber": {
		                "street": "颐和园路",
		                "number": "5号",
		                "location": "116.310518,39.9918931",
		                "direction": "东",
		                "distance": "44.4465"
		            }
 */
	
	private String street;
	private String number;
	private String location;
	private String direction;
	private String distance;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "StreetNumber [street=" + street + ", number=" + number
				+ ", location=" + location + ", direction=" + direction
				+ ", distance=" + distance + "]";
	}
}
