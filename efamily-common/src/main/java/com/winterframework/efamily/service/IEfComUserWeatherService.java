package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfUserWeather;

public interface IEfComUserWeatherService extends IBaseService<EfUserWeather> {
	public List<String> getUnSendCityList() throws Exception;
}
