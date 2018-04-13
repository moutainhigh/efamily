package com.winterframework.efamily.dto;

public class SetUserBarrierRequest {

	    private Long userId;
	    private String x;
	    private String y;
	    private String radius;
	    private Integer status;//0 关 1 开
	    private Integer type;//1 百度  2高德
	    private String remark;
	    private Long fenceId;
	    private Integer orgTag;
	    
		public Long getFenceId() {
			return fenceId;
		}
		public void setFenceId(Long fenceId) {
			this.fenceId = fenceId;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
		}
		public String getY() {
			return y;
		}
		public void setY(String y) {
			this.y = y;
		}
		public String getRadius() {
			return radius;
		}
		public void setRadius(String radius) {
			this.radius = radius;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getOrgTag() {
			return orgTag;
		}
		public void setOrgTag(Integer orgTag) {
			this.orgTag = orgTag;
		}
		
		
		
}
