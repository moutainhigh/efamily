package com.winterframework.efamily.entity;

public class OrgUserFamilyDiseaseBaseInfo {

	private Long diseaseId;
	private Long orgId;
	private Long employeeId;
	private Integer status;
	private String relation;
	private String disease;
	
	public Long getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(Long diseaseId) {
		this.diseaseId = diseaseId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	@Override
	public String toString() {
		return "OrgUserFamilyDiseaseBaseInfo [diseaseId=" + diseaseId
				+ ", orgId=" + orgId + ", employeeId=" + employeeId
				+ ", status=" + status + ", relation=" + relation
				+ ", disease=" + disease + "]";
	}

	
}
