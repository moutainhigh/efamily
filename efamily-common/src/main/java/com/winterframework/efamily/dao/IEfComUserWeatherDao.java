package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfUserWeather;

public interface IEfComUserWeatherDao extends IBaseDao<EfUserWeather>{

	public List<String> getUnSendCityList() throws Exception;
}
