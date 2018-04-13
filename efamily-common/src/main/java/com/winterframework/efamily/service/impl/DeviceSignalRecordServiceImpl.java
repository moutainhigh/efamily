package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IDeviceSignalRecordDao;
import com.winterframework.efamily.entity.DeviceSignalRecord;
import com.winterframework.efamily.service.IDeviceSignalRecordService;

@Service("deviceSignalRecordServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceSignalRecordServiceImpl  extends BaseServiceImpl<IDeviceSignalRecordDao,DeviceSignalRecord> implements IDeviceSignalRecordService {
	@Resource(name="deviceSignalRecordDaoImpl")
	private IDeviceSignalRecordDao dao;
	@Override
	protected IDeviceSignalRecordDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public List<DeviceSignalRecord> queryByDeviceId(Long deviceId)
			throws BizException {
		return dao.getByDeviceId(deviceId);
	}
	@Override
	public int removeByDeviceId(Long deviceId) throws BizException {
		return dao.deleteByDeviceId(deviceId);
	}
}
