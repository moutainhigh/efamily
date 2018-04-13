package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IAppDeviceHealthHeartService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("appDeviceHealthHeartServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class AppDeviceHealthHeartServiceImpl extends EjlComHealthHeartRateServiceImpl implements IAppDeviceHealthHeartService{

	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	@Resource(name = "ejlHealthHeartRateDaoImpl")
	private IEjlHealthHeartRateDao ejlHealthHeartRateDao;
	
	@Override
	protected IEjlHealthHeartRateDao getEntityDao() {
		return ejlHealthHeartRateDao;
	}
	
	@Override
	public void start(Context ctx, Long userId, Long deviceId) throws BizException {
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(device==null){
			throw new BizException(StatusBizCode.APP_DEVICE_NOT_EXISTS.getValue());
		}
		if(!device.getUserId().equals(userId)){
			throw new BizException(StatusBizCode.APP_DEVICE_UNBOUND.getValue());
		}
		if(device.getSoftwareStatus()!=null && device.getSoftwareStatus().equals(EjlDevice.SoftwareStatus.DOING)){
			throw new BizException(StatusBizCode.APP_DEVICE_SOFTWARE_DOING.getValue());
		}
		final int operMaxMin=5;
		if(device.getOperStatus()!=null && device.getOperStatus().intValue()!=EjlDevice.OperStatus.INIT.getValue()
				 && device.getOperStatus().intValue()!=EjlDevice.OperStatus.FINISH.getValue()
				 && device.getOperTime()!=null && DateUtils.calcMinutesBetween(device.getOperTime(),DateUtils.currentDate())<=operMaxMin){
			throw new BizException(StatusBizCode.APP_DEVICE_BUSING.getValue());
		}
		device.setOperType(EjlDevice.OperType.HEART.getValue());
		device.setOperStatus(EjlDevice.OperStatus.REQ.getValue());
		device.setOperUserId(ctx.getUserId());
		device.setOperTime(DateUtils.currentDate());
		ejlComDeviceService.save(ctx, device);
			
		try{
			notificationUtil.notification(userId, deviceId, 20826,NotyMessage.Type.OPER, null, true);
		}catch(Exception e){
			log.error("device heart start push failed.userId="+userId+" deviceId="+deviceId,e);
			throw new BizException(StatusBizCode.APP_DEVICE_OPER_FAILED.getValue());
		}
	}
	@Override
	public void stop(Context ctx, Long userId, Long deviceId)
			throws BizException {
		EjlDevice device=ejlComDeviceService.get(deviceId);
		if(device==null){
			throw new BizException(StatusBizCode.APP_DEVICE_NOT_EXISTS.getValue());
		}
		if(!device.getUserId().equals(userId)){
			throw new BizException(StatusBizCode.APP_DEVICE_UNBOUND.getValue());
		}
		if(device.getSoftwareStatus()!=null && device.getSoftwareStatus().equals(EjlDevice.SoftwareStatus.DOING)){
			throw new BizException(StatusBizCode.APP_DEVICE_SOFTWARE_DOING.getValue());
		}
		//本人操作的心率才结束
		if(device.getOperType()!=null && device.getOperType().intValue()==EjlDevice.OperType.HEART.getValue()
				&& device.getOperUserId()!=null && device.getOperUserId().longValue()==ctx.getUserId()){
			device.setOperType(EjlDevice.OperType.HEART.getValue());
			device.setOperStatus(EjlDevice.OperStatus.FINISH.getValue());
			ejlComDeviceService.save(ctx, device);
		}
			
		try{
			notificationUtil.notification(userId, deviceId, 20836,NotyMessage.Type.OPER, null, true);
		}catch(Exception e){
			log.error("device heart start push failed.userId="+userId+" deviceId="+deviceId,e);
			throw new BizException(StatusBizCode.APP_DEVICE_OPER_FAILED.getValue());
		}
	}
	@Override
	public EjlHealthHeartRate queryLatest(Context ctx, Long userId,
			Long deviceId) throws BizException {
		try{
			return getEntityDao().getLastUserHeartRate(userId, deviceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
}
