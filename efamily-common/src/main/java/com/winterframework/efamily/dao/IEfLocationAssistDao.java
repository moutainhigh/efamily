package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfLocationAssist;

public interface IEfLocationAssistDao extends IBaseDao<EfLocationAssist> {
	/**
	 * 根据坐标ID获取
	 * @param locationId
	 * @return
	 * @throws Exception
	 */
	EfLocationAssist getByLocationId(Long locationId) throws Exception;
}
