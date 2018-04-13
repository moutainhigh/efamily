package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EfUserWeather;
import com.winterframework.efamily.entity.EfWeather;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.EjlUser;

public interface IWeatherService{

	public void sendWeather(List<EfUserWeather> userList,EfWeather efWeather) throws Exception;
	
	public void getWeather(String cityName) throws Exception;
	
	public void userWeatherCityHander(EjlLocation ejlLocation); 
	/**
	 * 用户城市变更
	 * @throws BizException
	 */
	void cityChange() throws BizException; 
	public void saveUserWeather(String cityName,Long userId) throws BizException; 
	
	/**
	 * 处理单个用户城市变化
	 * @param ctx
	 * @param user
	 * @throws BizException
	 */
	void handleOneUserCityChange(Context ctx, EjlUser user) throws BizException;
	
	
	public void sendUserCityWeather(Context ctx) throws BizException;
}
