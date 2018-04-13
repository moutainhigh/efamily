package com.winterframework.efamily.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EfLocationSemi;

public interface ITskLocationSemiDao extends IEfComLocationSemiDao{
	/**获取未处理设备ID列表
	 * @param flag	处理标识
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getDeviceIdListByFlag(int flag,Date timeFrom,Date timeTo) throws Exception;
	/**
	 * 获取同一时间和类型的点集合
	 * @param userId
	 * @param deviceId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<EfLocationSemi> getSameListById(Long userId,Long deviceId,Long id) throws Exception;
	
	/**
	 * 获取未处理数据最大最小时间
	 * @param deviceId
	 * @param flag	处理标识
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getMaxMinTimeByDeviceIdAndFlag(Long userId,Long deviceId,int flag,Date timeFrom,Date timeTo) throws Exception;
	/**
	 * 获取未处理数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @param flag	处理标识
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListByDeviceIdAndTimeFromToAndFlag(Long userId,Long deviceId,Date timeFrom,Date timeTo,int flag) throws Exception;
	/**
	 * 获取P点方圆X经纬度差的点列表
	 * @param deviceId
	 * @param longitude
	 * @param latitude
	 * @param flag	处理标识
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndFlag(Long userId,Long deviceId,double longitudeFrom,double longitudeTo,double latitudeFrom,double latitudeTo,int flag) throws BizException;	
	/**
	 * 获取时间范围内的点列表
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListNearByDeviceIdAndTime(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	/**
	 * 获取P点方圆X经纬度差 某些状态的点列表
	 * @param deviceId
	 * @param longitudeFrom
	 * @param longitudeTo
	 * @param latitudeFrom
	 * @param latitudeTo
	 * @param flag
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndFlags(Long userId,Long deviceId,double longitudeFrom,double longitudeTo,double latitudeFrom,double latitudeTo, List<Integer> flags) throws BizException;
	/**
	 * 获取前后count个点
	 * @param id
	 * @param count
	 * @param flags
	 * @return
	 * @throws Exception
	 */
	List<EfLocationSemi> getPreNextListByIdAndCountAndFlags(Long userId,Long deviceId,Long id,int count,List<Integer> flags) throws Exception;
			
	/**
	 * 获取P点方圆X经纬度差 排除某些状态的点列表
	 * @param deviceId
	 * @param longitudeFrom
	 * @param longitudeTo
	 * @param latitudeFrom
	 * @param latitudeTo
	 * @param flags
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListNearByDeviceIdAndLatLngAndExcludeFlags(Long userId,Long deviceId,double longitudeFrom,double longitudeTo,double latitudeFrom,double latitudeTo, List<Integer> flags) 
			throws BizException;
	/**获取保留点列表
	 * @param timeFrom
	 * @param timeTo
	 * @param flag	处理标识
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getListByTimeFromToAndFlag(Date timeFrom,Date timeTo,int flag) throws BizException;
	
	
	/**获取设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @param flags	处理标识
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getDeviceIdListByFlags(Date timeFrom,Date timeTo,List<Integer> flags) throws Exception;
	/**
	 * 获取数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @param flags	处理标识
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getMaxMinTimeByDeviceIdAndFlags(Long userId,Long deviceId,Date timeFrom,Date timeTo,List<Integer> flags) throws Exception;
	
	EfLocationSemi getFirstByDeviceIdAndFlags(Long userId,Long deviceId,Date timeFrom,Date timeTo,List<Integer> flags) throws Exception;
	
	List<EfLocationSemi> getListByDeviceIdAndTimeFromToAndFlags(Long userId,Long deviceId,Date timeFrom,Date timeTo,List<Integer> flags) throws BizException;
	
	public List<EfLocationSemi> getSameList(Long userId,Long deviceId,Integer source,Date time,String remark) throws Exception;
}
