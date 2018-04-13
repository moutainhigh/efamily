package com.winterframework.efamily.unit.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.institution.dto.UserFenceBaseInfo;
import com.winterframework.efamily.institution.service.IFenceManageService;
import com.winterframework.modules.test.BaseTestCase;

public class ResourceServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(ResourceServiceImplTest.class);

	@Resource(name="fenceManageServiceImpl")
	private IFenceManageService fenceManageServiceImpl;
	

	@Test
	@Rollback(false)
	public void addFence() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		//Long orgId = 100L;
		System.out.println("*************************START******************************");
		String userFenceBaseInfoStr = "{\"orgId\":100,\"name\":\"围栏名称11\",\"location\":\"22.537638,113.943649\",\"radius\":500,\"address\":\"围栏地址11\",\"type\":2}";
		UserFenceBaseInfo userFenceBaseInfo = JsonUtils.fromJson(userFenceBaseInfoStr, UserFenceBaseInfo.class);
		OrgFence orgFence = fenceManageServiceImpl.saveUserFenceBaseInfo(ctx, userFenceBaseInfo);
		System.out.println("fenceId : "+orgFence);
		
		String userIds = "9001,9002,9003,9004";
		
		fenceManageServiceImpl.saveFenceIdAndUserId(ctx, orgFence, userIds);
		
		System.out.println("**************************END******************************");
	}
}
