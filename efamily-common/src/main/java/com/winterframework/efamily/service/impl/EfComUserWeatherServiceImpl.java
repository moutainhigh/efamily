package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;

import com.winterframework.efamily.dao.IEfComUserWeatherDao;

import com.winterframework.efamily.entity.EfUserWeather;

import com.winterframework.efamily.service.IEfComUserWeatherService;

@Service("efComUserWeatherServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComUserWeatherServiceImpl extends BaseServiceImpl<IEfComUserWeatherDao,EfUserWeather> implements IEfComUserWeatherService {

	@Resource(name="efComUserWeatherDaoImpl")
	private IEfComUserWeatherDao efComUserWeatherDaoImpl;
	@Override
	protected IEfComUserWeatherDao getEntityDao() {
		return efComUserWeatherDaoImpl;
	}
	@Override
	public List<String> getUnSendCityList() throws Exception {
		return efComUserWeatherDaoImpl.getUnSendCityList();
	}
}
