package com.winterframework.efamily.entity;


public class OrgUnbindUserBaseInfo {

	private Long orgUserId;//机构用户ID
	private String orgUserName;//机构会员名称
	private String idNumber;//身份证编号
	private String  phoneNumber;//设备手机号码
	public Long getOrgUserId() {
		return orgUserId;
	}
	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
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
	@Override
	public String toString() {
		return "OrgUnbindUserBaseInfo [orgUserId=" + orgUserId
				+ ", orgUserName=" + orgUserName + ", idNumber=" + idNumber
				+ ", phoneNumber=" + phoneNumber + "]";
	}
}
