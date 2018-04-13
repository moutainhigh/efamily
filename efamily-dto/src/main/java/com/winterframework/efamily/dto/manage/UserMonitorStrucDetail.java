package com.winterframework.efamily.dto.manage;

public class UserMonitorStrucDetail {
    private String ip;
    private String time;
	private int totalOnlineCount;	//总在线用户数
	private int totalOnlineAppCount;	//APP总在线用户数
	private int totalOnlineWatchCount;	//WATCH总在线用户数
	

	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getTotalOnlineCount() {
		return totalOnlineCount;
	}
	public void setTotalOnlineCount(int totalOnlineCount) {
		this.totalOnlineCount = totalOnlineCount;
	}
	public int getTotalOnlineAppCount() {
		return totalOnlineAppCount;
	}
	public void setTotalOnlineAppCount(int totalOnlineAppCount) {
		this.totalOnlineAppCount = totalOnlineAppCount;
	}
	public int getTotalOnlineWatchCount() {
		return totalOnlineWatchCount;
	}
	public void setTotalOnlineWatchCount(int totalOnlineWatchCount) {
		this.totalOnlineWatchCount = totalOnlineWatchCount;
	}
	
	
}
