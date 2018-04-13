package com.winterframework.efamily.dto.manage;

import java.util.List;

public class UserMonitorStruc {
	private int totalCount;	//总用户数
	private int totalAppCount;	//APP总用户数
	private int totalWatchCount;	//WATCH总用户数
//	private int totalOnlineCount;	//总在线用户数
//	private int totalOnlineAppCount;	//APP总在线用户数
//	private int totalOnlineWatchCount;	//WATCH总在线用户数
	List<UserMonitorStrucDetail> userMonitorStrucDetail;
	
	List<UserStruc> userStrucList;	//用户登录数据
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalAppCount() {
		return totalAppCount;
	}
	public void setTotalAppCount(int totalAppCount) {
		this.totalAppCount = totalAppCount;
	}
	public int getTotalWatchCount() {
		return totalWatchCount;
	}
	public void setTotalWatchCount(int totalWatchCount) {
		this.totalWatchCount = totalWatchCount;
	}
//	public int getTotalOnlineCount() {
//		return totalOnlineCount;
//	}
//	public void setTotalOnlineCount(int totalOnlineCount) {
//		this.totalOnlineCount = totalOnlineCount;
//	}
//	public int getTotalOnlineAppCount() {
//		return totalOnlineAppCount;
//	}
//	public void setTotalOnlineAppCount(int totalOnlineAppCount) {
//		this.totalOnlineAppCount = totalOnlineAppCount;
//	}
//	public int getTotalOnlineWatchCount() {
//		return totalOnlineWatchCount;
//	}
//	public void setTotalOnlineWatchCount(int totalOnlineWatchCount) {
//		this.totalOnlineWatchCount = totalOnlineWatchCount;
//	}
	public List<UserStruc> getUserStrucList() {
		return userStrucList;
	}
	public void setUserStrucList(List<UserStruc> userStrucList) {
		this.userStrucList = userStrucList;
	}
	public List<UserMonitorStrucDetail> getUserMonitorStrucDetail() {
		return userMonitorStrucDetail;
	}
	public void setUserMonitorStrucDetail(List<UserMonitorStrucDetail> userMonitorStrucDetail) {
		this.userMonitorStrucDetail = userMonitorStrucDetail;
	}
}