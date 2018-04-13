package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationAssist;


public interface IEfLocationAssistService extends IBaseService<EfLocationAssist> {
	/**
	 * 根据坐标ID获取
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	EfLocationAssist getByLocationId(Long locationId) throws BizException;
}
