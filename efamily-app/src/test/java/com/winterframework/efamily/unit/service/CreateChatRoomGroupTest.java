package com.winterframework.efamily.unit.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlChartRoom;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.modules.test.BaseTestCase;



public class CreateChatRoomGroupTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(CreateChatRoomGroupTest.class);

	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		String memberIds = "123456";
		Context ctx=new Context();
		EjlChartRoom ejlChartRoom = ejlUserServiceImpl.createChatRoom(ctx,11111L);
        ejlUserServiceImpl.joinUserChatGroupInfo(ctx,memberIds, ejlChartRoom.getId());
		System.out.println("response : "+ejlChartRoom);
	}
	
}