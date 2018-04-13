/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;

import java.util.Date;
import java.util.List;

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

public class EfWeather extends FmlBaseEntity {

	// alias
	public static final String TABLE_ALIAS = "EfWeather";
	public static final String ALIAS_CITY_CODE = "城市编码";
	public static final String ALIAS_SOLAR_DATE = "日期";
	public static final String ALIAS_WEEK = "星期";
	public static final String ALIAS_WEATHER = "天气";
	public static final String ALIAS_TEMPERATURE = "温度(区间)";
	public static final String ALIAS_HUMIDITY = "湿度(区间)";
	public static final String ALIAS_WIND = "风力指数";
	public static final String ALIAS_DRESS = "穿衣指数";
	public static final String ALIAS_GANMAO = "感冒指数";
	public static final String ALIAS_POLLUTION = "污染指数";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_RENEW_TAG = "是否更新";

	// date formats
	public static final String FORMAT_SOLAR_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;

	// columns START
	private Integer cityCode;
	private Date solarDate;
	private Integer week;
	private String weather;
	private String temperature;
	private String humidity;
	private String wind;
	private String dress;
	private String ganmao;
	private String pollution;
	private String remark;
	private Date createTime;
	private Date updateTime;
	private Integer renewTag;
	private String cityName;

	private String temperatureCurrent;
	
	List<EfWeather> weatherList;

	// columns END

	public EfWeather() {
	}

	public EfWeather(Long id) {
		this.id = id;
	}

	public void setCityCode(Integer value) {
		this.cityCode = value;
	}

	public Integer getCityCode() {
		return this.cityCode;
	}

	public String getSolarDateString() {
		return date2String(getSolarDate(), FORMAT_SOLAR_DATE);
	}

	public void setSolarDateString(String value) {
		setSolarDate(string2Date(value, FORMAT_SOLAR_DATE, Date.class));
	}

	public void setSolarDate(Date value) {
		this.solarDate = value;
	}

	public Date getSolarDate() {
		return this.solarDate;
	}

	public void setWeek(Integer value) {
		this.week = value;
	}

	public Integer getWeek() {
		return this.week;
	}

	public void setWeather(String value) {
		this.weather = value;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setTemperature(String value) {
		this.temperature = value;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setHumidity(String value) {
		this.humidity = value;
	}

	public String getHumidity() {
		return this.humidity;
	}

	public void setWind(String value) {
		this.wind = value;
	}

	public String getWind() {
		return this.wind;
	}

	public void setDress(String value) {
		this.dress = value;
	}

	public String getDress() {
		return this.dress;
	}

	public void setGanmao(String value) {
		this.ganmao = value;
	}

	public String getGanmao() {
		return this.ganmao;
	}

	public void setPollution(String value) {
		this.pollution = value;
	}

	public String getPollution() {
		return this.pollution;
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
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME, Date.class));
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
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME, Date.class));
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setRenewTag(Integer value) {
		this.renewTag = value;
	}

	public Integer getRenewTag() {
		return this.renewTag;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTemperatureCurrent() {
		return temperatureCurrent;
	}

	public void setTemperatureCurrent(String temperatureCurrent) {
		this.temperatureCurrent = temperatureCurrent;
	}
	
	

	public List<EfWeather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<EfWeather> weatherList) {
		this.weatherList = weatherList;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId())
				.append("CityCode", getCityCode())
				.append("SolarDate", getSolarDate()).append("Week", getWeek())
				.append("Weather", getWeather())
				.append("Temperature", getTemperature())
				.append("Humidity", getHumidity()).append("Wind", getWind())
				.append("Dress", getDress()).append("Ganmao", getGanmao())
				.append("Pollution", getPollution())
				.append("Remark", getRemark())
				.append("CreatorId", getCreatorId())
				.append("CreateTime", getCreateTime())
				.append("UpdatorId", getUpdatorId())
				.append("UpdateTime", getUpdateTime())
				.append("RenewTag", getRenewTag()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getCityCode())
				.append(getSolarDate()).append(getWeek()).append(getWeather())
				.append(getTemperature()).append(getHumidity())
				.append(getWind()).append(getDress()).append(getGanmao())
				.append(getPollution()).append(getRemark())
				.append(getCreatorId()).append(getCreateTime())
				.append(getUpdatorId()).append(getUpdateTime())
				.append(getRenewTag()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EfWeather == false)
			return false;
		if (this == obj)
			return true;
		EfWeather other = (EfWeather) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getCityCode(), other.getCityCode())

		.append(getSolarDate(), other.getSolarDate())

		.append(getWeek(), other.getWeek())

		.append(getWeather(), other.getWeather())

		.append(getTemperature(), other.getTemperature())

		.append(getHumidity(), other.getHumidity())

		.append(getWind(), other.getWind())

		.append(getDress(), other.getDress())

		.append(getGanmao(), other.getGanmao())

		.append(getPollution(), other.getPollution())

		.append(getRemark(), other.getRemark())

		.append(getCreatorId(), other.getCreatorId())

		.append(getCreateTime(), other.getCreateTime())

		.append(getUpdatorId(), other.getUpdatorId())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getRenewTag(), other.getRenewTag())

		.isEquals();
	}
}
