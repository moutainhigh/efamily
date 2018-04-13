package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfPlatformDeviceVersionService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IQrcodeService;

@Service("ejlComDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComDeviceServiceImpl extends BaseServiceImpl<IEjlComDeviceDao, EjlDevice>
		implements IEjlComDeviceService {

	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao dao;
	
	@Resource(name="ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserServiceImpl;
	
	@Resource(name="ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceServiceImpl;
	
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserServiceImpl;
	@Resource(name="efPlatformDeviceVersionServiceImpl")
	private IEfPlatformDeviceVersionService efPlatformDeviceVersionService;
	@Resource(name="qrcodeServiceImpl")
	private IQrcodeService qrcodeService;
	
	
	@Override
	protected IEjlComDeviceDao getEntityDao() {
		return dao;
	}
	
	@Override
	public EjlDevice getByImei(String imei) throws BizException { 
		try{
			return dao.getByImei(imei);
		}catch(Exception e){
			log.error("getByImei()->",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),new String[]{imei});
		}
	}
	@Override
	public List<EjlDevice> getByType(Integer type) throws BizException {
		try{
			return dao.getByType(type);
		}catch(Exception e){
			log.error("getByType()->type="+type,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),new String[]{type+""});
		}
	}
	
	@Override
	public List<EjlDevice> getDeviceByTypeAndStatus(Integer type, Integer status)
			throws BizException {
		try{
			return dao.getDeviceByTypeAndStatus(type,status);
		}catch(Exception e){
			log.error("getDeviceByTypeAndStatus()->type="+type,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),new String[]{type+""});
		}
	}

	@Override
	public void updateSoftwareVersion(Context ctx,Long userId, Long deviceId, String version)
			throws BizException {
		EjlDevice device= get(deviceId);
		if(null==device){
			throw new BizException(StatusBizCode.APP_DEVICE_NOT_EXISTS.getValue());
		}
		if(!device.getUserId().equals(userId)){
			throw new BizException(StatusBizCode.APP_DEVICE_NOT_EXISTS.getValue());
		}
		Qrcode qrcode=qrcodeService.getByImei(device.getCode());
		if(null==qrcode){
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		qrcode.setSoftwareVersion(version);
		qrcodeService.save(ctx, qrcode);
		
		EfPlatformDeviceVersion efPlatformDeviceVersion =efPlatformDeviceVersionService.getByDeviceTypeAndDeviceModelAndDeviceVersion(qrcode.getType(), qrcode.getModel(), version);
		if(null==efPlatformDeviceVersion){
			throw new BizException(StatusBizCode.APP_DEVICE_PLATFORM_VERSION_MISSING.getValue());
		}
		device.setSoftwareVersion(efPlatformDeviceVersion.getOpenVersion());
		save(ctx, device);
	}
	@Override
	public void bind() throws BizException {
		//待实现
		/**
		 * 1.绑定关系
		 * 2.推送
		 */
	}
	@Override
	public void unbind() throws BizException {
		//待实现
		/**
		 * 1.解绑关系
		 * 2.推送
		 */
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void bindDeviceFailForConfirm(Context ctx,Long userId,Long deviceId)throws BizException{
		        //user type
			   	EjlUser user = ejlComUserServiceImpl.get(userId);
				user.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
				ejlComUserServiceImpl.save(ctx,user);
			    //device  user_id  family_id	
		        EjlDevice device = getEntityDao().getById(deviceId);
		        device.setFamilyId(0L);
		        device.setUserId(0L);
		        save(ctx,device);
		        
			    //user_device status  
		        EjlUserDevice userDevice =  new  EjlUserDevice();
		        userDevice.setUserId(userId);
		        userDevice.setDeviceId(deviceId);
		        userDevice = ejlComUserDeviceDaoImpl.selectOneObjByAttribute(userDevice);
		        userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_STOP);
		        ejlComUserDeviceServiceImpl.save(ctx,userDevice);
		        
			    //user_family
		        EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
				ejlFamilyUser.setUserId(userId);
				ejlFamilyUser.setFamilyId(user.getFamilyId());
				ejlFamilyUser = ejlComFamilyUserServiceImpl.selectOneObjByAttribute(ctx,ejlFamilyUser);
				ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
				ejlFamilyUser.setManageType(EfamilyConstant.MANAGE_TYPE_AGREE);
				ejlComFamilyUserServiceImpl.save(ctx,ejlFamilyUser);
	}
	
}