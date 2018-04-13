package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dao.IDeviceStepDao;
import com.winterframework.efamily.dto.StepResponse;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.service.IDeviceStepService;

@Service("deviceStepServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceStepServiceImpl extends EjlComHealthStepCountServiceImpl implements IDeviceStepService {
	private static final Logger log = LoggerFactory.getLogger(DeviceHeartServiceImpl.class);
	@Resource(name = "deviceStepDaoImpl")
	private IDeviceStepDao deviceStepDaoImpl;

	@Override
	public StepResponse save(Context ctx,Long userId, Long deviceId, List<EjlHealthStepCount> recordList) throws BizException {
		/**
		 * 1.insert设备计步表
		 */
		
		try {
			if(recordList!=null){
				save(ctx,recordList);
			}
		} catch (Exception e) {
			log.error("", e);
			throw new BizException(StatusCode.SAVE_FAILED.getValue());
		}

		recordList.get(0);
		///????entity=getByDeviceKey(deviceId,code);
		//entity.setParamValue(value);
		//deviceParamConfigService.save(entity);
		StepResponse bizRes = new StepResponse();
		return bizRes;
	}

}