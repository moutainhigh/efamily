package com.winterframework.efamily.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.dto.device.DeviceHelloNewRequest;
import com.winterframework.efamily.dto.device.DeviceHelloNewResponse;
import com.winterframework.efamily.dto.device.DeviceHelloRequest;
import com.winterframework.efamily.dto.device.DeviceHelloResponse;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserLoginRecord;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceHelloService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComFamilyService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserLoginRecordService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IQrcodeService;
 

@Service("deviceHelloServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceHelloServiceImpl extends HelloServiceImpl implements IDeviceHelloService{
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService; 
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService; 
	@Resource(name="ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceService;
	@Resource(name="ejlComFamilyServiceImpl")
	private IEjlComFamilyService ejlComFamilyService; 
	@Resource(name = "ejlComUserLoginRecordServiceImpl")
	private IEjlComUserLoginRecordService ejlComUserLoginRecordService;
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeService;
	@Override
	public DeviceHelloResponse hello(Context ctx,DeviceHelloRequest bizReq,String token) throws BizException {
		DeviceHelloResponse bizRes=new DeviceHelloResponse();
		String imei=bizReq.getImei();
		Qrcode qrcode= qrcodeService.getByImei(imei);
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue(),new String[]{imei});
		}
		if(null==redisClient.get(imei)){
			throw new BizException(StatusBizCode.DEVICE_NO_BIND_REQ.getValue(),new String[]{imei});	//不存在绑定请求	
		}else{ 
			EjlDevice device = checkDevice(imei); 
			Long fromId=Long.valueOf(redisClient.get(imei));
			EjlUser fromUser = checkUser(fromId);
			EjlFamily family = checkFamily(fromUser.getFamilyId());
			EjlUser deviceUser=ejlComUserService.get(device.getUserId());
			bizRes.setUserId(device.getUserId());
			bizRes.setDeviceId(device.getId());
			bizRes.setFromId(fromId);
			bizRes.setNickName(fromUser.getNickName());
			bizRes.setPhoneNumber(fromUser.getPhone());
			bizRes.setFamilyName(family.getName());
			bizRes.setChatRoomId(family.getId());
			bizRes.setDeviceNickName(deviceUser.getNickName());
			bizRes.setDevicePhoneNumber(deviceUser.getPhone());
			//删除缓存
			redisClient.del(bizReq.getImei()); 
		}
		return bizRes;
	}
	@Override
	public DeviceHelloNewResponse helloNew(Context ctx,DeviceHelloNewRequest bizReq) throws BizException {
		DeviceHelloNewResponse bizRes=new DeviceHelloNewResponse();
		String imei=bizReq.getImei();
		Qrcode qrcode= qrcodeService.getByImei(imei);
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue(),new String[]{imei});
		}
		EjlDevice device=ejlComDeviceService.getByImei(imei);
		if(null==device){
			throw new BizException(StatusBizCode.DEVICE_NO_BIND_REQ.getValue(),new String[]{imei});	//不存在绑定请求	
		}
		if(device.getStatus().longValue()==EjlDevice.STATUS.BIND.getValue()){
			throw new BizException(StatusBizCode.DEVICE_HAS_BIND.getValue());
		}
		if(device.getStatus().longValue()==EjlDevice.STATUS.UNBIND.getValue() 
				&& null!=device.getBindUserId()){
			Long userId=device.getUserId();
			Long familyId=device.getFamilyId();
			EjlUser user=ejlComUserService.get(userId);
			if(null==user){
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_EXISTS.getValue()); 
			}
			EjlFamily family=ejlComFamilyService.get(familyId);
			if(null==family){
				throw new BizException(StatusBizCode.DEVICE_FAMILY_INVALID.getValue()); 
			}
			bizRes.setUserId(device.getUserId());
			bizRes.setDeviceId(device.getId());
			bizRes.setNickname(user.getNickName());
			bizRes.setFamilyName(family.getName());
			bizRes.setHasBindReq(true);
		}else{
			bizRes.setHasBindReq(false);
		}
		return bizRes;
	}
	@Override
	public void loginRecord(Context ctx, EjlUserLoginRecord loginRecord)
			throws BizException {
		loginRecord.setLoginTime(new Date());
		ejlComUserLoginRecordService.save(ctx, loginRecord);
	}
	
	private EjlUser checkUser(Long fromId) throws BizException {
		EjlUser fromUser=ejlComUserService.get(fromId);
		if(null==fromUser){
			throw new BizException(StatusBizCode.USER_UN_VALID.getValue(),new String[]{String.valueOf(fromId)});
		}
		return fromUser;
	}

	private EjlDevice checkDevice(String imei)
			throws BizException {
		EjlDevice device=ejlComDeviceService.getByImei(imei); 
		if(null==device){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue(),new String[]{imei});
		}
		return device;
	}
	private EjlFamily checkFamily(Long id)
			throws BizException {
		EjlFamily family=ejlComFamilyService.get(id); 
		if(null==family){
			throw new BizException(StatusBizCode.DEVICE_FAMILY_INVALID.getValue(),new String[]{id+""});
		}
		return family;
	}


}
