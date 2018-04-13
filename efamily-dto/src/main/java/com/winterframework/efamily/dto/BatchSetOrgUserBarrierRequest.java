package com.winterframework.efamily.dto;

public class BatchSetOrgUserBarrierRequest {

	    private String location;
	    private Long radius;
	    private Integer type;
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public Long getRadius() {
			return radius;
		}
		public void setRadius(Long radius) {
			this.radius = radius;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
}
