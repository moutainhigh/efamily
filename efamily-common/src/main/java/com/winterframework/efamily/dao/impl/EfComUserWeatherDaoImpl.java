package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComUserWeatherDao;
import com.winterframework.efamily.entity.EfUserWeather;

@Repository("efComUserWeatherDaoImpl")
public class EfComUserWeatherDaoImpl <E extends EfUserWeather> extends BaseDaoImpl<EfUserWeather> implements IEfComUserWeatherDao{

	@Override
	public List<String> getUnSendCityList() throws Exception {
		return this.sqlSessionTemplate.selectList("getUnSendCityList");
	}
}
