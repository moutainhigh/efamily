package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.entity.EfLocationSemi;


public interface IEfLocationSemiService extends IEfComLocationSemiService {
	public void initLocationSemi() throws Exception;
	
	public void juheEjlLocation(List<EfLocationSemi> ejlLocationList,boolean isInit) throws Exception;

}
