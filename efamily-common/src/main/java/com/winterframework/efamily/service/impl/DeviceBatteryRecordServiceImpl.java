package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IDeviceBatteryRecordDao;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.service.IDeviceBatteryRecordService;

@Service("deviceBatteryRecordServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceBatteryRecordServiceImpl  extends BaseServiceImpl<IDeviceBatteryRecordDao,DeviceBatteryRecord> implements IDeviceBatteryRecordService {
	@Resource(name="deviceBatteryRecordDaoImpl")
	private IDeviceBatteryRecordDao dao;
	@Override
	protected IDeviceBatteryRecordDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public List<DeviceBatteryRecord> queryByDeviceId(Long deviceId)
			throws BizException {
		return dao.getByDeviceId(deviceId);
	}
	@Override
	public int removeByDeviceId(Long deviceId) throws BizException {
		return dao.deleteByDeviceId(deviceId);
	}
	@Override
	public DeviceBatteryRecord getLastDeviceBatteryRecordByDeviceId(
			Long deviceId,Long time) throws BizException {
		try{
			return dao.getLastDeviceBatteryRecordByDeviceId(deviceId,time);
		}catch(Exception e){
			log.error("dao error.deviceId="+deviceId+" time="+time,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
}
