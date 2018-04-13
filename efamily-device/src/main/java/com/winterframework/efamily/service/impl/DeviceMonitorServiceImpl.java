package com.winterframework.efamily.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.entity.EjlDeviceMonitor;
import com.winterframework.efamily.service.IDeviceMonitorService;

@Service("deviceMonitorServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceMonitorServiceImpl extends EjlComDeviceMonitorServiceImpl implements IDeviceMonitorService {
	private static final Logger log = LoggerFactory.getLogger(DeviceMonitorServiceImpl.class);

	@Override
	public StepResponse saveOrUpdate(Context ctx,Long userId, Long deviceId, EjlDeviceMonitor record) throws BizException {
		/**
		 * 1.insert用户设备监控
		 */
		try {
			if(record!=null){
				EjlDeviceMonitor ejlDeviceMonitor = new EjlDeviceMonitor();
				ejlDeviceMonitor.setDeviceId(record.getDeviceId());
				ejlDeviceMonitor.setDeviceUserId(record.getDeviceUserId());
				EjlDeviceMonitor queryDeviceMonitor = selectOneObjByAttribute(ctx, ejlDeviceMonitor);
				//如果数据库中存在设备，更新设备监控数据，不存在就插入设备监控数据
				if(queryDeviceMonitor == null || queryDeviceMonitor.getId() == null) {
					save(ctx,record);
				}else {
					//存在设备，修改设备的最后通话时间
					queryDeviceMonitor.setEndTime(DateUtils.currentDate());
					save(ctx,queryDeviceMonitor);
				}
				
			}
		} catch (Exception e) {
			log.error("", e);
			throw new BizException(StatusCode.SAVE_FAILED.getValue());
		}
		StepResponse bizRes = new StepResponse();
		return bizRes;
	}

}