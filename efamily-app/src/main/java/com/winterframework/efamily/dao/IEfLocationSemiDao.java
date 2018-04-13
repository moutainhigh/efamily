package com.winterframework.efamily.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.entity.EfLocationSemi;

public interface IEfLocationSemiDao extends IEfComLocationSemiDao{
	List<EfLocationSemi> getListByTimeSpan(Long userId,Long deviceId,Date timeFrom,Date timeTo,Integer type) throws Exception;
}
