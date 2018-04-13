package com.winterframework.efamily.service;

import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationSemi;

public interface IEfLocationSemiService  extends IBaseService<EfLocationSemi>{
	List<EfLocationSemi> getListByTimeSpan(Long userId,Long deviceId,Date timeFrom,Date timeTo,Integer type) throws BizException;
}
