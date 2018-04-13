package com.winterframework.efamily.entity;

public class EmployeeCanOptOrg {

	private Long orgId;//机构ID
	private String name;//机构名称
	private String province;//机构省份
	private String city;//机构城市
	private String county;//机构区县
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	@Override
	public String toString() {
		return "EmployeeCanOptOrg [orgId=" + orgId + ", name=" + name
				+ ", province=" + province + ", city=" + city + ", county="
				+ county + "]";
	}
	
}
