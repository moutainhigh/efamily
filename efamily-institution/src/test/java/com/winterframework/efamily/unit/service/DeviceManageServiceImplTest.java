package com.winterframework.efamily.unit.service;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.WatchParamBaseInfo;
import com.winterframework.efamily.institution.service.IDeviceManageService;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.modules.test.BaseTestCase;


public class DeviceManageServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(DeviceManageServiceImplTest.class);

	@Resource(name="deviceManageServiceImpl")
	private IDeviceManageService deviceManageServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		Long orgId = 100L;
		Integer start = 1;
		String queryValue = "";
		String imei = "";
		Long orgUserId = 1L;
		Long orgDeviceId = 3L;
		
		System.out.println("**************************START******************************");

		List<OrgBindDeviceBaseInfo> orgBindDeviceBaseInfoList = deviceManageServiceImpl.getOrgBindDeviceBaseInfo(orgId, start, queryValue);
		System.out.println("orgBindDeviceBaseInfoList"+JsonUtils.toJson(orgBindDeviceBaseInfoList));
		
	  /*List<OrgUnbindDeviceBaseInfo> orgUnbindDeviceBaseInfoList = deviceManageServiceImpl.getOrgUnbindDeviceBaseInfo(orgId, start, imei);
		System.out.println("orgUnbindDeviceBaseInfoList"+JsonUtils.toJson(orgUnbindDeviceBaseInfoList));
		
		List<OrgUnbindUserBaseInfo> orgUnbindUserBaseInfoList = deviceManageServiceImpl.getOrgUnbindUserBaseInfo(orgId, start, queryValue);
		System.out.println("orgUnbindUserBaseInfoList"+JsonUtils.toJson(orgUnbindUserBaseInfoList));*/
		
		//*** 查询手表基本信息  ***
		//WatchParamBaseInfo watchParamBaseInfo = deviceManageServiceImpl.getWatchParamBaseInfo(1L);
		//System.out.println("watchParamBaseInfo"+JsonUtils.toJson(watchParamBaseInfo));
		
		//*** 保存或修改设备基本信息  ***
		//watchParamBaseInfo.setDeviceName("41xiugai");
		//watchParamBaseInfo.setSimPhoneNumber("135999971059");
		//deviceManageServiceImpl.saveWatchParamBaseInfo(watchParamBaseInfo);
		
		//***********************  解绑  或者  绑定 设备   **********************
		//Integer operType = 1;
		//deviceManageServiceImpl.bindOrUnbindDevice(orgId, orgUserId, orgDeviceId, operType);
		
		System.out.println("**************************END******************************");
		
	}
	
	
	
	
	
	
}
