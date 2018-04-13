package com.winterframework.efamily.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EfLocationSemi;


public interface ITskLocationSemiServiceNew extends IEfComLocationSemiService{
	/**
	 * 获取过滤后的前点
	 * @param locationSemi
	 * @return
	 */
	EfLocationSemi getPreviousAfterFiltered(EfLocationSemi locationSemi) throws BizException;
	EfLocationSemi getNextAfterFiltered(EfLocationSemi locationSemi) throws BizException;
	/**获取未处理设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getUnhandledDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取未处理数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getUnhandledMaxMinTimeByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取未处理数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getUnhandledListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**获取已过滤数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getFilteredListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	List<EfLocationSemi> getLinkedListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	/**获取已聚合设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getAggregationDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取已聚合数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getAggregationMaxMinTimeByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取已聚合数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getAggregationListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	/**获取详情已处理设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getDetailedDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	List<Map<String,Long>> getAggAndDetailedDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取待处理设备列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getNeedHandleDeviceList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取同一时间和类型的点
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getSameList(EfLocationSemi locationSemi) throws BizException;
	/**
	 * 获取详情已处理数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getDetailedMaxMinTimeByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取详情已处理数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getDetailedListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**获取保留点设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getRetainedDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取保留点数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getRetainedMaxMinTimeByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取保留点数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getRetainedListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取P点方圆X经纬度差的点列表
	 * @param deviceId
	 * @param longitude
	 * @param latitude
	 * @param offset
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getRetainedListNearByDeviceIdAndLatLngAndOffset(Long userId,Long deviceId,double longitude,double latitude,double offset) throws BizException;
	List<EfLocationSemi> getValidListNearByDeviceIdAndLatLngAndOffset(Long userId,Long deviceId,double longitude,double latitude,double offset) throws BizException;
	/**
	 * 获取前后count个点
	 * @param id
	 * @param count
	 * @param flags
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getValidPreNextListByIdAndCount(EfLocationSemi locationSemi,int count) throws BizException;
	/**
	 * 获取P点时间差内的基站点列表
	 * @param deviceId
	 * @param longitudeFrom
	 * @param longitudeTo
	 * @param latitudeFrom
	 * @param latitudeTo
	 * @param flag
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getBtsListNearByDeviceIdAndTimeAndOffset(Long userId,Long deviceId,Date time,int offset) throws BizException;
	List<EfLocationSemi> getListNearByDeviceIdAndTimeAndOffset(Long userId,Long deviceId,Date time,int offset) throws BizException;
	/**
	 * 处理聚合
	 * @param locationSemiList
	 * @throws BizException
	 */
	void processAggregation(List<EfLocationSemi> locationSemiList,boolean isInitial) throws BizException;
	/**获取保留点列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getRetainedList(Date timeFrom,Date timeTo) throws BizException;
	
	
	/**获取详情已处理和可疑点设备ID列表
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<Map<String,Long>> getDetailedAndSuspectDeviceIdList(Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取详情已处理和可疑点数据最大最小时间
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	Map<Date, Date> getDetailedAndSuspectMaxMinTimeByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	EfLocationSemi getRetainedFirstByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取详情已处理和可疑点数据第一个点
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getDetailedAndSuspectFirstByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	EfLocationSemi getFilterFirstByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	EfLocationSemi getLinkedFirstByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取未处理数据第一个点
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	EfLocationSemi getUnhandledFirstByDeviceId(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 获取详情已处理和可疑点数据列表（按设备ID、时间范围）
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 * @throws BizException
	 */
	List<EfLocationSemi> getDetailedAndSuspectListByDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	List<EfLocationSemi> getValidListAfterAggredByUserIdAndDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	List<EfLocationSemi> getValidListByUserIdAndDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	List<EfLocationSemi> getListByUserIdAndDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	List<EfLocationSemi> getStill2MoveListByUserIdAndDeviceIdAndTimeFromTo(Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	/**
	 * 连带处理Location数据
	 * @param ctx
	 * @param locationSemi
	 * @param isInitial 是否初始聚合
	 * @throws BizException
	 */
	void dispose(Context ctx,EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationSemi locationSemiNext,boolean isInitial) throws BizException;
	/**
	 * 聚合
	 * @param ctx
	 * @param locationSemi
	 * @param isSecd 是否二次
	 * @throws BizException
	 */
	void aggregation(Context ctx,EfLocationSemi locationSemi,boolean isSecd) throws BizException;
	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 处理坐标
	 * @throws BizException
	 */
	void doProcessNew() throws BizException;
	void process(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 处理坐标详情
	 * @throws BizException
	 */
	//void doProcessDetail(Context ctx,Date timeFrom,Date timeTo) throws BizException;
	
	/**
	 * 处理调整（斜率角和速度）
	 * @throws BizException
	 */
	//void doProcessAdjust(Context ctx,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 处理聚合（坐标点合成）
	 * @throws BizException
	 */
	//void doProcessAggregation(Context ctx,Date timeFrom,Date timeTo) throws BizException;
	/**
	 * 处理聚合（经纬度一致）
	 * @throws BizException
	 */
	//void doProcessAggregationLatLon(Context ctx,Date timeFrom,Date timeTo) throws BizException;
	////////////////////////////////////////////////
	/**
	 * 处理Origin
	 * @throws BizException
	 */
	void doProcessOrigin() throws BizException;
	void filterLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void distinctLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void aggregationLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void doProcessDetail(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo)throws BizException;
	
	void processAggregationLatLon(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void processDetail(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void processAdjust(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	void processAggregation(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException;
	
	public void doProcessOrigin(Long userId,Long deviceId) throws BizException;
		
}
