 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfLocationWifi extends FmlBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -733799521446677805L;
	private Long userId;
	private Long deviceId;	//设备ID
	private String mac1;
	private String mac2;
	private String mac3;
	private String mac4;
	private String mac5;
	private String macName1;
	private String macName2;
	private String macName3;
	private String macName4;
	private String macName5;
	private Integer signal1;
	private Integer signal2;
	private Integer signal3;
	private Integer signal4;
	private Integer signal5;
	private Long time;
	private Integer opTag;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getMac1() {
		return mac1;
	}
	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}
	public String getMac2() {
		return mac2;
	}
	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}
	public String getMac3() {
		return mac3;
	}
	public void setMac3(String mac3) {
		this.mac3 = mac3;
	}
	public String getMac4() {
		return mac4;
	}
	public void setMac4(String mac4) {
		this.mac4 = mac4;
	}
	public String getMac5() {
		return mac5;
	}
	public void setMac5(String mac5) {
		this.mac5 = mac5;
	}
	public String getMacName1() {
		return macName1;
	}
	public void setMacName1(String macName1) {
		this.macName1 = macName1;
	}
	public String getMacName2() {
		return macName2;
	}
	public void setMacName2(String macName2) {
		this.macName2 = macName2;
	}
	public String getMacName3() {
		return macName3;
	}
	public void setMacName3(String macName3) {
		this.macName3 = macName3;
	}
	public String getMacName4() {
		return macName4;
	}
	public void setMacName4(String macName4) {
		this.macName4 = macName4;
	}
	public String getMacName5() {
		return macName5;
	}
	public void setMacName5(String macName5) {
		this.macName5 = macName5;
	}
	public Integer getSignal1() {
		return signal1;
	}
	public void setSignal1(Integer signal1) {
		this.signal1 = signal1;
	}
	public Integer getSignal2() {
		return signal2;
	}
	public void setSignal2(Integer signal2) {
		this.signal2 = signal2;
	}
	public Integer getSignal3() {
		return signal3;
	}
	public void setSignal3(Integer signal3) {
		this.signal3 = signal3;
	}
	public Integer getSignal4() {
		return signal4;
	}
	public void setSignal4(Integer signal4) {
		this.signal4 = signal4;
	}
	public Integer getSignal5() {
		return signal5;
	}
	public void setSignal5(Integer signal5) {
		this.signal5 = signal5;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getOpTag() {
		return opTag;
	}
	public void setOpTag(Integer opTag) {
		this.opTag = opTag;
	}
	
}

