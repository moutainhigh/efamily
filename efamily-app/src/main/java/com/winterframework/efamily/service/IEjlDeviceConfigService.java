package com.winterframework.efamily.service;

import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.DeviceBatteryInfo;
import com.winterframework.efamily.dto.WatchBindInfo;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlDeviceConfigService extends IBaseService<EjlDeviceParmConfig>{

	
	/**
	 * 功能：打开监听设备
	 * @param userId
	 * @param deviceId
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	public boolean openMonitorNotifyDevice(long userId,long deviceId,String phoneNumber,Context ctx) throws Exception;
	/**
	 * 功能：修改设备参数
	 * @param watchId
	 * @param parameterIndex
	 * @param parameterContext
	 * @return
	 */
	public boolean updateWatchDeviceParaBy(Context ctx,long watchId,String parameterIndex,String parameterContext,long userId) throws Exception;
	
	/**
	 * 功能：修改设备参数时，请求参数的检查
	 * @param ctx
	 * @param request
	 * @return
	 */
	public boolean checkParam(WatchDeviceManageRequest request,Long userId);
	
	/**
	 * 功能：获取对应的设备参数
	 * @param watchId
	 * @param parameterIndex
	 * @return
	 */
	public EjlDeviceParmConfig getWatchDeviceParaBy(long watchId, String parameterIndex,String parameterValue)throws NumberFormatException, Exception;
	/**
	 * 功能：获取用户设备
	 * @param deviceId
	 * @return
	 */
	public EjlUserDevice getEjlUserDevice(Long deviceId);
	/**
	 * 功能：修改手表拥有者
	 * @param userId
	 * @param deviceId
	 */
	public Map<String, String> updateWatchOwner(Context ctx,Long userId,Long deviceId,Long userIdWatch,Long userIdExchange,Long oprType,String newUserName,String phoneNumber)  throws BizException;
	/**
	 * 功能：根据设备编码获取设备信息
	 * @param code
	 * @return
	 */
	public EjlDevice getEjlDeviceByCode(String code);
	/**
	 * 
	* @Title: switchOtherWatch 
	* @Description: TODO(将用户USERBB的设备DEVICEBB，给用户USERAA使用) 
	* @param userAA
	* @param userBB
	* @param deviceBB
	* @throws BizException
	 */
	public void switchOtherWatch(Context ctx,Long userAA,Long userBB,Long  deviceBB) throws BizException;
	
	/**
	 * 功能：交换设备
	 * @param userAA
	 * @param deviceAA
	 * @param userBB
	 * @param deviceBB
	 * @throws BizException
	 */
	public void switchDevice(Context ctx,Long userAA,Long deviceAA,Long userBB,Long deviceBB,Long familyId) throws BizException;
	/**
	 * 
	* @Title: getWatchDeviceParaAll 
	* @Description: TODO(获取设备的所有参数) 
	* @param watchId
	* @param userId
	* @return
	* @throws BizException
	 */
	public Map<String,String> getWatchDeviceParaAll(long watchId, long userId) throws BizException ;
	/**
	 * 
	* @Title: queryWatchBindInfo 
	* @Description: TODO(查看设备的绑定情况) 
	* @param watchId
	* @return
	* @throws BizException
	 */
	public WatchBindInfo queryWatchBindInfo(String deviceCode) throws BizException;

	/** 
	* @Title: initDeviceConfig 
	* @Description: 手表入库时初始化手表部分参数
	* @param deviceId
	 * @throws BizException 
	*/
	public void initDeviceConfig(Context ctx,Long deviceId) throws BizException;
	
	/**
	 * 
	 * @param ctx
	 * @param watchId
	 * @param paramKey
	 * @param parameterContext
	 * @return
	 * @throws BizException
	 */
	public int saveOrUpdateDeviceParm(Context ctx,Long watchId,String paramKey,String parameterContext) throws BizException;
	
	/**
	 * 
	 * @param deviceCode
	 * @return
	 * @throws Exception
	 */
	public String getDevicePhoneBy(String deviceCode) throws Exception;
	
	public DeviceBatteryInfo getDeviceBatteryInfo(Long deviceId,String paramKey) throws Exception;
	
	public void notifyAppForUnbindDevice(Long optUserId,Long deviceUserId,Long watchId,List<EjlUser> userList) throws BizException;
	
	public void setWatchAdderssBookList(Context ctx,List<WatchDeviceManageRequest> list) throws BizException;
}
