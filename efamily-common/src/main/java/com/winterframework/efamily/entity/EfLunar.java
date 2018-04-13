 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;



import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;
import com.winterframework.orm.dal.ibatis3.BaseEntity;



/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfLunar extends FmlBaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "EfLunar";
	public static final String ALIAS_SOLAR_DATE = "日期";
	public static final String ALIAS_LUNAR_DATE = "农历";
	public static final String ALIAS_WEEK = "星期";
	public static final String ALIAS_TERM = "节气";
	public static final String ALIAS_ZODIAC = "生肖";
	public static final String ALIAS_GANZHI = "干支";
	public static final String ALIAS_SUIT = "宜";
	public static final String ALIAS_AVOID = "忌";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_SOLAR_DATE = DATE_TIME_FORMAT;
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Date solarDate;
	private String lunarDate;
	private Integer week;
	private String term;
	private String zodiac;
	private String ganzhi;
	private String suit;
	private String avoid;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfLunar(){
	}

	public EfLunar(
		Long id
	){
		this.id = id;
	}

	public String getSolarDateString() {
		return date2String(getSolarDate(), FORMAT_SOLAR_DATE);
	}
	public void setSolarDateString(String value) {
		setSolarDate(string2Date(value, FORMAT_SOLAR_DATE,Date.class));
	}
	
	public void setSolarDate(Date value) {
		this.solarDate = value;
	}
	
	public Date getSolarDate() {
		return this.solarDate;
	}
	public void setLunarDate(String value) {
		this.lunarDate = value;
	}
	
	public String getLunarDate() {
		return this.lunarDate;
	}
	public void setWeek(Integer value) {
		this.week = value;
	}
	
	public Integer getWeek() {
		return this.week;
	}
	public void setTerm(String value) {
		this.term = value;
	}
	
	public String getTerm() {
		return this.term;
	}
	public void setZodiac(String value) {
		this.zodiac = value;
	}
	
	public String getZodiac() {
		return this.zodiac;
	}
	public void setGanzhi(String value) {
		this.ganzhi = value;
	}
	
	public String getGanzhi() {
		return this.ganzhi;
	}
	public void setSuit(String value) {
		this.suit = value;
	}
	
	public String getSuit() {
		return this.suit;
	}
	public void setAvoid(String value) {
		this.avoid = value;
	}
	
	public String getAvoid() {
		return this.avoid;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCreatorId(Long value) {
		this.creatorId = value;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	public String getCreateTimeString() {
		return date2String(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("SolarDate",getSolarDate())		
		.append("LunarDate",getLunarDate())		
		.append("Week",getWeek())		
		.append("Term",getTerm())		
		.append("Zodiac",getZodiac())		
		.append("Ganzhi",getGanzhi())		
		.append("Suit",getSuit())		
		.append("Avoid",getAvoid())		
		.append("Remark",getRemark())		
		.append("CreatorId",getCreatorId())		
		.append("CreateTime",getCreateTime())		
		.append("UpdatorId",getUpdatorId())		
		.append("UpdateTime",getUpdateTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getSolarDate())
		.append(getLunarDate())
		.append(getWeek())
		.append(getTerm())
		.append(getZodiac())
		.append(getGanzhi())
		.append(getSuit())
		.append(getAvoid())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfLunar == false) return false;
		if(this == obj) return true;
		EfLunar other = (EfLunar)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getSolarDate(),other.getSolarDate())

		.append(getLunarDate(),other.getLunarDate())

		.append(getWeek(),other.getWeek())

		.append(getTerm(),other.getTerm())

		.append(getZodiac(),other.getZodiac())

		.append(getGanzhi(),other.getGanzhi())

		.append(getSuit(),other.getSuit())

		.append(getAvoid(),other.getAvoid())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

