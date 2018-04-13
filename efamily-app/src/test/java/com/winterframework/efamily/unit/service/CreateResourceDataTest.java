package com.winterframework.efamily.unit.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.service.IEfComResourceAssistService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IResourceService;
import com.winterframework.efamily.thirdparty.sms.IBaseSmsService;
import com.winterframework.modules.test.BaseTestCase;

public class CreateResourceDataTest extends BaseTestCase {
	Logger log = LoggerFactory.getLogger(CreateChatRoomGroupTest.class);

	@Resource(name = "kangdooSmsServiceImpl")
	private IBaseSmsService kangdooSmsServiceImpl;

	
	
	@Test
	@Rollback(false)
	public void test() throws Exception {
		
		kangdooSmsServiceImpl.send("15013569367", "test");
        
		
	}

}