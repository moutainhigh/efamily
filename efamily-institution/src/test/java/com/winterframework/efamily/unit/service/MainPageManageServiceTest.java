package com.winterframework.efamily.unit.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.institution.dto.DeviceAlarmLastInfo;
import com.winterframework.efamily.institution.dto.LocationBaseInfo;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;
import com.winterframework.efamily.institution.dto.UserLocationInfo;
import com.winterframework.efamily.institution.service.IMainPageManageService;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.modules.test.BaseTestCase;

public class MainPageManageServiceTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(MainPageManageServiceTest.class);

	@Resource(name="mainPageManageServiceImpl")
	private IMainPageManageService mainPageManageServiceImpl;
	
	@Resource(name="memberManageServiceImpl")
	private IMemberManageService memberManageServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		String orgId = "100";
		String memberId = "10000";
		Long userId=2121L;
		System.out.println("**************************START******************************");
/*		//*** 用户成员基本信息
		MemberInfoStruct memberInfoStruct =  memberManageServiceImpl.getMemberInfoStruct(Long.valueOf(memberId));
		System.out.println("memberInfoStruct : "+memberInfoStruct);*/
		
		//*** 用户健康数据
/*		 Map<String,Object> userHealthInfo = mainPageManageServiceImpl.getUserHealthInfo(Long.valueOf(2162), "2000-09-27", "2020-09-28");
		System.out.println("userHealthInfo : "+JsonUtils.toJson(userHealthInfo));*/
		
		//*** 用户定位数据
/*		Long userId = memberInfoStruct.getUserId();
		UserLocationInfo userLocationInfo = mainPageManageServiceImpl.getLastLocation(userId);
		System.out.println("userLocationInfo : "+userLocationInfo);*/
		
/*		Integer hourRecent = 100000;
		List<LocationBaseInfo> locationBaseInfoList = mainPageManageServiceImpl.getLocationList(userId, hourRecent);
		System.out.println("locationBaseInfoList : "+JsonUtils.toJson(locationBaseInfoList));*/
		// *** 
		List<UserHeartRateAndBloodPressure> memberInfoList = mainPageManageServiceImpl.getMainPageData(Long.valueOf(orgId),"41");
		System.out.println("memberInfoList : "+memberInfoList);
		//***
		//List<DeviceAlarmLastInfo> exceptionMemberInfo = mainPageManageServiceImpl.getDeviceAlarmExceptionData(Long.valueOf(orgId),0L);
		//System.out.println("exceptionMemberInfo : "+exceptionMemberInfo);
		  
		System.out.println("**************************END******************************");
		
	}
	
}
