package com.winterframework.efamily.thirdparty.sms;

public class SmsResult {
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String toString(){
		return "SmsResult:success="+success;
	}
}
