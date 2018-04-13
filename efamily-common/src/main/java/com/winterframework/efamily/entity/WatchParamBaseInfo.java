package com.winterframework.efamily.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.dto.DeviceAddressListDto;

public class WatchParamBaseInfo {

	private Long orgUserId;//机构用户ID org_user
	private Long userId;//亿家联用户ID
	private String orgUserName;//机构会员名称
	private String idNumber;//身份证编号
	private String  phoneNumber;//绑定者手机号码
	private String guardianName;//监护人
	private String guardianPhoneNumber;//监护人电话
	
	
	private Long deviceId;//机构用户ID org_user
	private Date updateTime;//设备更新时间   ejl_device
	private String imei;//设备编号
	private String deviceName;//设备名称
	private String battery;//设备电量
	private Long status;//设备状态
	private String  simPhoneNumber;//设备手机号码
	
	private Integer  locationFlag;//设备定位是否打开   ejl_device_parm_config.
	
	private String  deviceQrcode;//设备二维码   ef_qrcode.qrcode_resource_id
	
	private  List<DeviceAddressListDto>  deviceAddressBook;//设备SOS通讯录 ejl_device_address_list
	private String bookFlag="0";//0 未修改  1 修改
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getOrgUserName() {
		return orgUserName;
	}
	public void setOrgUserName(String orgUserName) {
		this.orgUserName = orgUserName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getGuardianPhoneNumber() {
		return guardianPhoneNumber;
	}
	public void setGuardianPhoneNumber(String guardianPhoneNumber) {
		this.guardianPhoneNumber = guardianPhoneNumber;
	}
	public String getSimPhoneNumber() {
		return simPhoneNumber;
	}
	public void setSimPhoneNumber(String simPhoneNumber) {
		this.simPhoneNumber = simPhoneNumber;
	}
	public Integer getLocationFlag() {
		return locationFlag;
	}
	public void setLocationFlag(Integer locationFlag) {
		this.locationFlag = locationFlag;
	}
	public String getDeviceQrcode() {
		return deviceQrcode;
	}
	public void setDeviceQrcode(String deviceQrcode) {
		this.deviceQrcode = deviceQrcode;
	}
	public List<DeviceAddressListDto> getDeviceAddressBook() {
		return deviceAddressBook;
	}
	public void setDeviceAddressBook(List<DeviceAddressListDto> deviceAddressBook) {
		this.deviceAddressBook = deviceAddressBook;
	}
	public String getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(String bookFlag) {
		this.bookFlag = bookFlag;
	}
	@Override
	public String toString() {
		return "WatchParamBaseInfo [orgUserId=" + orgUserId + ", userId="
				+ userId + ", orgUserName=" + orgUserName + ", idNumber="
				+ idNumber + ", phoneNumber=" + phoneNumber + ", guardianName="
				+ guardianName + ", guardianPhoneNumber=" + guardianPhoneNumber
				+ ", deviceId=" + deviceId + ", updateTime=" + updateTime
				+ ", imei=" + imei + ", deviceName=" + deviceName
				+ ", battery=" + battery + ", status=" + status
				+ ", simPhoneNumber=" + simPhoneNumber + ", locationFlag="
				+ locationFlag + ", deviceQrcode=" + deviceQrcode
				+ ", deviceAddressBook=" + deviceAddressBook + ", bookFlag="
				+ bookFlag + "]";
	}
	
}