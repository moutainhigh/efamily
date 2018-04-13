package com.winterframework.efamily.dto.device;

public class ResourceUploadRequest{
	private String resourceId;	//资源ID
	private String type;	//类型
	private String extType;	//扩展类型
	private String filePath;	//资源路径
	private Integer isMulti;	//是否多清度
	private String remark;	//内容
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtType() {
		return extType;
	}
	public void setExtType(String extType) {
		this.extType = extType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Integer getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(Integer isMulti) {
		this.isMulti = isMulti;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
	
	
}
