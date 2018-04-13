package com.winterframework.efamily.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;

@Service("ejlComUserDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserDeviceServiceImpl extends BaseServiceImpl<IEjlComUserDeviceDao,EjlUserDevice> implements IEjlComUserDeviceService {

	@Resource(name="ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;

	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserServiceImpl;
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserServiceImpl;
	
	@Override
	protected IEjlComUserDeviceDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComUserDeviceDaoImpl;
	}
	
	@Override
	public EjlUserDevice getValidByUserId(Long userId) throws BizException {
		try{
			return ejlComUserDeviceDaoImpl.getOnlyDeviceByUser(userId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 
	}
	public void bindDeviceForConfirm(Context ctx,Long userId,Long deviceId) throws BizException{
		EjlUser user = ejlComUserServiceImpl.get(userId);
		
		//*** 判断是否没有APP用户  ***
		EjlUser userCheck = new EjlUser();
		userCheck.setType(EfamilyConstant.USER_TYPE_APP);
		userCheck.setFamilyId(user.getFamilyId());
		List<EjlUser> userList = ejlComUserServiceImpl.selectListObjByAttribute(ctx, userCheck);
		if(userList==null || userList.size()==0){
			log.info("设备确认绑定时，家庭中没有APP用户，则绑定失败。userId = "+userId+"; deviceId = "+deviceId+" ; ");
			ejlComDeviceServiceImpl.bindDeviceFailForConfirm(ctx, userId, deviceId);
			throw new BizException(StatusBizCode.DEVICE_CONFIRM_FAIL.getValue());
		}
		
	    //device  user_id  family_id	
        EjlDevice device = ejlComDeviceServiceImpl.get(deviceId);
        device.setFamilyId(user.getFamilyId());
        device.setUserId(userId);
        device.setBindOnOffTime(new Date());
        device.setBindFinishTime(DateUtils.currentDate());
        ejlComDeviceServiceImpl.save(ctx,device);
        
	    //user type
        user.setPhone(StringUtils.isBlank(device.getPhoneNumber())?"":device.getPhoneNumber());
		user.setType(EfamilyConstant.USER_TYPE_WATCH);
		ejlComUserServiceImpl.save(ctx,user);
		log.info("ejl_phone_number_to_ejl_user_phone:"+deviceId+" ; phonenumber :"+device.getPhoneNumber()+" ; user_phone_number : "+user.getPhone()+" ; ");

        
	    //user_device status  
        EjlUserDevice userDevice =  new  EjlUserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceId(deviceId);
        userDevice = ejlComUserDeviceDaoImpl.selectOneObjByAttribute(userDevice);
        userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
        save(ctx,userDevice);
        
	    //user_family
        EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(user.getFamilyId());
		ejlFamilyUser = ejlComFamilyUserServiceImpl.selectOneObjByAttribute(ctx,ejlFamilyUser);
		ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_WATCH);
		ejlFamilyUser.setManageType(EfamilyConstant.MANAGE_TYPE_AGREE);
		ejlComFamilyUserServiceImpl.save(ctx,ejlFamilyUser);
		
		//添加推送用户设置  待完成
		
 	}
	
	public void unbindDeviceForConfirm(Context ctx,Long userId,Long deviceId) throws BizException{
		//user type
	   	EjlUser user = ejlComUserServiceImpl.get(userId);
		user.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
		user.setPhone("");
		ejlComUserServiceImpl.save(ctx,user);
	   	
	    //device  user_id  family_id	
        EjlDevice device = ejlComDeviceServiceImpl.get(deviceId);
        device.setFamilyId(0L);
        device.setUserId(0L);
        device.setPhoneNumber("");
        ejlComDeviceServiceImpl.save(ctx,device);
        
	    //user_device status  
        EjlUserDevice userDevice =  new  EjlUserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceId(deviceId);
        userDevice = ejlComUserDeviceDaoImpl.selectOneObjByAttribute(userDevice);
        userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_STOP);
        save(ctx,userDevice);
        
	    //user_family
        EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(user.getFamilyId());
		ejlFamilyUser = ejlComFamilyUserServiceImpl.selectOneObjByAttribute(ctx,ejlFamilyUser);
		ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
		ejlComFamilyUserServiceImpl.save(ctx,ejlFamilyUser);
			
	}
	
	
}
