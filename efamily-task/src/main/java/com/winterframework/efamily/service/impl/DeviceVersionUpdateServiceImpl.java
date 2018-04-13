package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.service.IDeviceVersionUpdateService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComRemindService;
import com.winterframework.efamily.service.IQrcodeService;
@Service("deviceVersionUpdateServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceVersionUpdateServiceImpl implements IDeviceVersionUpdateService{
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService;
	
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	
	@Resource(name="ejlComRemindServiceImpl")
	private IEjlComRemindService ejlComRemindServiceImpl;
	
	protected Logger log = LoggerFactory.getLogger(FrequencySetServiceImpl.class); 
	
	
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(6);

	@Override
	public void tskDeviceVersionUpdate() throws Exception {
		List<EjlDevice> deviceList=ejlComDeviceService.getByType(EjlDevice.Type.WATCH.getValue());
		if(null!=deviceList){
			for(EjlDevice device:deviceList){
				final Long userId=device.getUserId();
				final Long deviceId=device.getId();
				final String imei = device.getCode();
				if(userId == null || userId==0){
					continue;
				}
				new BizMultiThread(threadPool) {
					protected void doBiz() throws BizException {
						log.debug("deviceUpdate notice process userId"+userId+" deviceId="+deviceId);
						try{
						deviceVersionNotice(deviceId,userId,imei);
						}catch(Exception e){
							throw new BizException(e);
						}
					};
				}.start();
			}
		}
		
	}
	
	private boolean deviceVersionNotice(Long deviceId,Long userId,String imei) throws Exception{
		
		Qrcode qr = qrcodeServiceImpl.getByImei(imei);
		List<String> oldVersion = new ArrayList<String>();
		oldVersion.add("W311SG_V03_01_161230");
		oldVersion.add("W311ZD_V03_01_161230");
		//老版本的手表需要升级
		if(qr.getSoftwareVersion() != null && oldVersion.contains(qr.getSoftwareVersion())){
			this.insertDeviceNotice(deviceId, userId);
			return true;
		}
		return false;
	}
	
	private void insertDeviceNotice(Long deviceId,Long userId) throws Exception{
		EjlRemind entity = new EjlRemind();
		entity.setContent("0af15abbe3a84b45974696f4c29fe679");
		entity.setDeleteStatus(0l);
		entity.setGmtCreated(new Date());
		entity.setName("软件升级提醒");
		entity.setSendMethod(1l);
		entity.setSendPeriod(1l);
		entity.setSendTime(DateUtils.addMinutes(DateUtils.currentDate(), 10));
		entity.setSendTimeEnd(DateUtils.addDays(DateUtils.currentDate(), 50));
		entity.setSendType(2l);
		entity.setSendUserId(userId);
		entity.setSentStatus(0l);
		entity.setUserId(-1l);
		entity.setRemindState(0l);
		entity.setDurationTime(10l);
		entity.setDeviceCommond(1l);
		entity.setCreatorId(-1l);
		Context ctx = new Context();
		ctx.set("userId", -1);
		ejlComRemindServiceImpl.save(ctx, entity);
	}
}
