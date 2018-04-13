package com.winterframework.efamily.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.entity.converter.DTOConvert;
import com.winterframework.efamily.service.IDeviceOperationService;
 

@Service("deviceOperationServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceOperationServiceImpl extends EfDeviceOperationServiceImpl implements IDeviceOperationService{ 
	private static final Logger log = LoggerFactory.getLogger(DeviceOperationServiceImpl.class);
	 
	@Override
	public void operation(Context ctx, DeviceOperationRequest req) throws BizException {
		save(ctx, DTOConvert.toDeviceOperation(ctx.getUserId(),ctx.getDeviceId(), req));
		 
		AbstractDeviceOperationStrategy operationStrategy=DeviceOperationStrategyFactory.getInstance(req.getCode());
		if(null!=operationStrategy){
			operationStrategy.doProcess(ctx, req);
		}
	}
}
