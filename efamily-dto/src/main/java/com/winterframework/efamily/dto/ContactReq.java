package com.winterframework.efamily.dto;

import java.util.List;

public class ContactReq {
	private String imei;
	private List<Contact> contacts;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	} 
}
