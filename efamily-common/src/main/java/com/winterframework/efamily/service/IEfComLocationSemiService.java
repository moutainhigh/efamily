package com.winterframework.efamily.service;


import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLocationSemi;


public interface IEfComLocationSemiService extends IBaseService<EfLocationSemi> {
	/**
	 * 获取前一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getPrevious(EfLocationSemi locationSemi) throws BizException;
	/**
	 *  获取后一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getNext(EfLocationSemi locationSemi) throws BizException;
	/**
	 * 获取前一坐标点(根据previousId)
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getPreviousById(EfLocationSemi locationSemi) throws BizException;
	/**
	 *  获取后一坐标点(根据nextId)
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getNextById(EfLocationSemi locationSemi) throws BizException;

	public List<EfLocationSemi> getDeviceLocationSemiByStatus(Integer count,Long deviceId,int status) throws Exception;

	public EfLocationSemi getLastLocation(Long userId, Long deviceId) throws BizException;

	public EfLocationSemi getNewestLocationForQueryNotice(Long deviceId, Long deviceUserId, Date date) throws Exception;
	
	public Integer updateStatus(Long id,Integer status) throws Exception;
}
