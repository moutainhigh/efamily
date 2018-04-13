package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEJLComUserHealythConfigDao;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlHealthSitting;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.UserHealtyConfig;
import com.winterframework.efamily.service.IDeviceSittingService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("deviceSittingServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceSittingServiceImpl extends EjlComHealthSittingServiceImpl implements IDeviceSittingService {
	private static final Logger log = LoggerFactory.getLogger(DeviceSittingServiceImpl.class);
//	@Resource(name = "deviceSittingDaoImpl")
//	private IDeviceSittingDao deviceSittingDaoImpl;
	
	@Resource(name = "eJLComUserHealythConfigDaoImpl")
	private IEJLComUserHealythConfigDao eJLComUserHealythConfigDaoImpl;
	
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl;
	
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;

	@Override
	public StepResponse save(Context ctx,Long userId, Long deviceId, List<EjlHealthSitting> recordList) throws BizException {
		/**
		 * 1.insert设备久坐数据
		 */
		
		try {
			if(recordList!=null){
				save(ctx,recordList);
			}
		} catch (Exception e) {
			log.error("", e);
			throw new BizException(StatusCode.SAVE_FAILED.getValue());
		}

		StepResponse bizRes = new StepResponse();
		return bizRes;
	}

	@Override
	public void saveSitSwitch(Context ctx, Long userId, Long deviceId,
			Integer sitSwitch) throws BizException {
		UserHealtyConfig userHealtyConfige=eJLComUserHealythConfigDaoImpl.getByDeviceIdAndUser(userId, deviceId);
		if(userHealtyConfige == null){
			userHealtyConfige = new UserHealtyConfig();
			userHealtyConfige.setUserId(userId);
			userHealtyConfige.setDeviceId(deviceId);
			userHealtyConfige.setSittingSwitch(sitSwitch);
			userHealtyConfige.setHeartSwitch(YesNo.NO.getValue());
			userHealtyConfige.setStepSwitch(YesNo.NO.getValue());
			userHealtyConfige.setClimbSwitch(YesNo.NO.getValue());
			userHealtyConfige.setSleepSwitch(YesNo.NO.getValue());
			eJLComUserHealythConfigDaoImpl.insert(userHealtyConfige);
		}else{
			userHealtyConfige.setSittingSwitch(sitSwitch);
			eJLComUserHealythConfigDaoImpl.update(userHealtyConfige);
		}
		Map<String,String> data = new HashMap<String,String>();
		data.put("userId",  userId+"");
		data.put("deviceId",  deviceId+"");
		data.put("sitSwitch", sitSwitch.intValue()+"");
		data.put("time", DateUtils.convertDate2Long(DateUtils.currentDate())+"");
		EjlUser userWatch =  ejlComUserDaoImpl.getById(userId);
		EjlUser user = new EjlUser();
		user.setFamilyId(userWatch.getFamilyId());
		user.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> userList = ejlComUserDaoImpl.selectListObjByAttribute(user);
		notificationUtil.notifyUser(data,userList,userId,NoticeType.SWITCH_ONOFF,null);
	}
}