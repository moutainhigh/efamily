package com.winterframework.efamily.core.base;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

@SuppressWarnings("serial")
public class FmlBaseEntity extends BaseEntity {
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATE_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	protected String remark;
	protected Long creatorId;
	protected Date createTime;
	protected Long updatorId;
	protected Date updateTime;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getUpdatorId() {
		return updatorId;
	}
	public void setUpdatorId(Long updatorId) {
		this.updatorId = updatorId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	} 
	
	
}
