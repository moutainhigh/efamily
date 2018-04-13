package com.winterframework.efamily.service;


import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.ScanWatchResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;

public interface IEjlUserDeviceService extends IBaseService<EjlUserDevice>{
	
	public Long saveUserDevice(Context ctx,Long userId,Long deviceId,Long status)throws BizException;
	
	public Long saveEjlDevice(Context ctx,Long userId,Long familyId,String deviceCode,String phoneNumber) throws BizException;
	
	public int updateUnbindDeviceByAttribute(Context ctx,Long userId,Long deviceId,Long familyId,Long status)throws BizException;
	
	public EjlUserDevice getByAttribute(Long userId,Long deviceId,Long status) throws BizException;
	
	public List<UnbindDeviceInfo> unbindAllDeviceInFamily(Context ctx,Long familyId,Long userId) throws BizException;
	
	public Long getUserUseingDeviceId(Long userId)  throws BizException;
	
	public List<EjlUserDevice> getAllByEntity(EjlUserDevice ejlUserDevice) throws Exception;
	
	public boolean checkScanWatch(Context ctx,Long userId,String watchCode,String nickName,String phoneNumber,String zombieUserId) throws BizException ;
	
	public ScanWatchResponse manageScanWatch(Context ctx,Long userId,String watchCode,String nickName,String phoneNumber,String zombieUserId) throws BizException;
	
	public EjlUserDevice getEjlUserDeviceByDeviceIdAndStatus(Long deviceId,Long status) throws BizException;
	
	public List<EjlUserDevice> getEjlUserDeviceBy(Long userId,Long deviceId,Long status) throws BizException;
	
	public Long getDeviceUseingUserId(Long deviceId)  throws BizException;
	public Long getDeviceUseingUserIdByCode(String deviceCode)  throws BizException;
	
	public boolean checkBindDeviceBy(Long optBindUserId) throws BizException;
	
	public void unBindDevice(Context ctx,Long deviceId) throws BizException;
	
	public void unBindDevice(Context ctx,String imei,String key) throws BizException;
	
	//机构绑定设备
	public ScanWatchResponse bindOrgDevice(Context ctx,Long userId,String watchCode,String phoneNumber,String nickName)throws BizException;
	
	public EjlUser getDeviceUser(Context ctx, String imei) throws BizException;
	
}
