
	 /**
	 * Copyright (c) 2005-2012 winterframework.com
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 */
	 package com.winterframework.efamily.entity;


	import com.winterframework.efamily.core.base.FmlBaseEntity;

	/**
	 * @author cms group
	 * @version 1.0
	 * @since 1.0
	 */


	public class OrgFence extends FmlBaseEntity {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1;
		//alias
		public static final String TABLE_ALIAS = "OrgFence";
		public static final String ALIAS_STATUS = "状态 0：关闭  1：打开 ";
		public static final String ALIAS_LOCATION = "坐标 x,y";
		public static final String ALIAS_RADIUS = "围栏半径";
		public static final String ALIAS_REMARK = "备注";
		public static final String ALIAS_CREATOR_ID = "创建人";
		public static final String ALIAS_CREATE_TIME = "创建时间";
		public static final String ALIAS_UPDATOR_ID = "更新人";
		public static final String ALIAS_UPDATE_TIME = "更新时间";
		
		//date formats
				public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
				public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
		
		//columns START
		private Long orgId;
		private Integer status;
		private String location;
		private Long radius;
		private Integer type;
		private String name;
		private String address;
		
		//columns END

		
		public OrgFence(){
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public OrgFence(
			Long id
		){
			this.id = id;
		}

		public void setStatus(Integer value) {
			this.status = value;
		}
		
		public Integer getStatus() {
			return this.status;
		}
		public void setLocation(String value) {
			this.location = value;
		}
		
		public String getLocation() {
			return this.location;
		}
		public void setRadius(Long value) {
			this.radius = value;
		}
		
		public Long getRadius() {
			return this.radius;
		}

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Override
		public String toString() {
			return "OrgFence [orgId=" + orgId + ", status=" + status
					+ ", location=" + location + ", radius=" + radius
					+ ", type=" + type + ", name=" + name + ", address="
					+ address + "]";
		}

		
	}


