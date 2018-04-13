package com.winterframework.efamily.unit.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.enums.DeviceOperation;
import com.winterframework.efamily.service.IDeviceOperationService;
import com.winterframework.modules.test.BaseTestCase;



public class DeviceServiceTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(DeviceServiceTest.class);

	@Resource(name="deviceOperationServiceImpl")
	private IDeviceOperationService deviceOperationService;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		DeviceOperationRequest req=new DeviceOperationRequest();
		req.setCode(DeviceOperation.CHARGE.getCode());
		req.setStatus(1);
		req.setTime(DateUtils.getCurTime());
		deviceOperationService.operation(new Context(),req);
	}
	
}