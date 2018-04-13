package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComDeviceHealthyDataPushDao;
import com.winterframework.efamily.entity.EfDeviceHealthyDataPush;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfComDeviceHealthyDataPushService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IQrcodeService;


@Service("efComDeviceHealthyDataPushServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComDeviceHealthyDataPushServiceImpl extends BaseServiceImpl<IEfComDeviceHealthyDataPushDao,EfDeviceHealthyDataPush> implements IEfComDeviceHealthyDataPushService {
 
	@Resource(name="efComDeviceHealthyDataPushDaoImpl")
	private IEfComDeviceHealthyDataPushDao efComDeviceHealthyDataPushDaoImpl;
	
	@Resource(name="qrcodeServiceImpl")
	protected IQrcodeService qrcodeServiceImpl;	
	
	@Resource(name="ejlComDeviceServiceImpl")
	protected IEjlComDeviceService ejlComDeviceServiceImpl;	
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;
	
	@Override
	protected IEfComDeviceHealthyDataPushDao getEntityDao() { 
		return efComDeviceHealthyDataPushDaoImpl;
	}
	
	public List<EfDeviceHealthyDataPush> getDeviceHealthyDataPushList(Date fromTime, Date toTime, int status) throws Exception{
		return efComDeviceHealthyDataPushDaoImpl.getDeviceHealthyDataPushList(fromTime, toTime, status);
	}
	
	
	public void saveBloodPressureToDataPush(Long userId,Long deviceId,String imei,List<EjlHealthBloodPressure> bloodList) throws BizException{
		if(bloodList==null||bloodList.size()==0){
			return;
		}
		for(EjlHealthBloodPressure ejlHealthBloodPressure: bloodList){
			saveDeviceHealthyDataForPush(userId, deviceId, ejlHealthBloodPressure.getId(), getEjlHealthBloodPressureMessage(imei, ejlHealthBloodPressure),EfDeviceHealthyDataPush.Type.BLOOD.getValue());
		}
	}
	
	public void saveHeartToDataPush(Long userId,Long deviceId,String imei,List<EjlHealthHeartRate> heartList) throws BizException{
		if(heartList==null||heartList.size()==0){
			return;
		}
		for(EjlHealthHeartRate ejlHealthHeartRate: heartList){
			saveDeviceHealthyDataForPush(userId, deviceId, ejlHealthHeartRate.getId(), getEjlHealthHeartMessage(imei, ejlHealthHeartRate),EfDeviceHealthyDataPush.Type.HEART.getValue());
		}
	}
	
	public String getEjlHealthBloodPressureMessage(String imei,EjlHealthBloodPressure ejlHealthBloodPressure){
		StringBuffer buf = new StringBuffer();
		buf.append("imei=").append(imei)
		   .append("&systolicPressure=").append(ejlHealthBloodPressure.getHigh())
		   .append("&diastolicPressure=").append(ejlHealthBloodPressure.getLow())
		   .append("&fromTime=").append(ejlHealthBloodPressure.getFromTime())
		   .append("&toTime=").append(ejlHealthBloodPressure.getToTime());
		
		
/*		imei:设备号码
		systolicPressure ：收缩压
		diastolicPressure：舒张压
		time:时间
		sysType:收缩压类型（1低 2高）
		diaType:舒张压类型（1低 2高）*/
		
		return buf.toString();
	}
	
	public String getEjlHealthHeartMessage(String imei,EjlHealthHeartRate ejlHealthHeartRate){
		StringBuffer buf = new StringBuffer();
		buf.append("imei=").append(imei)
		   .append("&heartRate=").append(ejlHealthHeartRate.getRate())
		   .append("&fromTime=").append(ejlHealthHeartRate.getFromTime())
		   .append("&toTime=").append(ejlHealthHeartRate.getToTime());
		
		return buf.toString();
	}
	
	public void saveDeviceHealthyDataForPush(Long userId,Long deviceId,Long heahthyId,String healthyData,int type) throws BizException{
		EfDeviceHealthyDataPush efDeviceHealthyDataPush = new EfDeviceHealthyDataPush();
		
		Context ctx = new Context();
		ctx.set("userId", -1L);
		
		EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(deviceId);
		Qrcode qrcode = qrcodeServiceImpl.getByImei(ejlDevice.getCode());
		
		int number = qrcode.getCustomerNumber();
		Long customerId = null;
		try{
		 customerId = efComCustomerService.getEfCustomerBy(number).getId(); 
		}catch(Exception e){
			throw new BizException(StatusBizCode.DEVICE_CUSTOMER_NO_EXIST.getValue());
		}
		
		efDeviceHealthyDataPush.setCustomerId(customerId);
		efDeviceHealthyDataPush.setSendNumber(0);
		efDeviceHealthyDataPush.setDeviceId(deviceId);
		efDeviceHealthyDataPush.setUserId(userId);
		efDeviceHealthyDataPush.setType(type);
		efDeviceHealthyDataPush.setImei(qrcode.getImei());
		efDeviceHealthyDataPush.setStatus(0);
		
		efDeviceHealthyDataPush.setHealthyId(heahthyId);
		efDeviceHealthyDataPush.setHealthyData(healthyData);
		
		save(ctx, efDeviceHealthyDataPush);
	}
	
	
}