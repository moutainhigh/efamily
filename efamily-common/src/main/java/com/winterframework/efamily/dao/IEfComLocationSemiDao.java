package com.winterframework.efamily.dao;
import java.util.Date;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfLocationSemi;

public interface IEfComLocationSemiDao extends IBaseDao<EfLocationSemi> {
	/**
	 * 获取前一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getPrevious(Long userId,Long deviceId,Long id,List<Integer> flags) throws BizException;
	/**
	 *  获取后一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getNext(Long userId,Long deviceId,Long id,List<Integer> flags) throws BizException;
	public List<EfLocationSemi> getDeviceLocationSemiByStatus(Integer count,Long deviceId,int status) throws Exception;
	public Integer updateStatus(Long id,Integer status) throws Exception;
	public EfLocationSemi getLast(Long deviceId,Date time) throws Exception;
	
	public List<EfLocationSemi> getAllListByNewUnhander(Long deviceId) throws Exception;
	public List<EfLocationSemi> getFirstLocationByNewUnhanderTime(Long deviceId,Date endTime,Integer time) throws Exception;
	public EfLocationSemi getLastLocation(Long userId,Long deviceId) throws Exception;
	/**
	 * 功能：根据设备号和时间，获取最新的定位信息为APP用户查询时进行推送。
	 * @param deviceId
	 * @param deviceUserId 
	 * @param date   当时查询的时间
	 * @return
	 * @throws Exception
	 */
	public EfLocationSemi getNewestLocationForQueryNotice(Long deviceId, Long deviceUserId, Date date) throws Exception;
	
	
	
	/**
	 * 获取前一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getPreviousByTime(Long userId,Long deviceId,Date time,List<Integer> flags) throws BizException;
	/**
	 *  获取后一坐标点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getNextByTime(Long userId,Long deviceId,Date time,List<Integer> flags) throws BizException;
}
