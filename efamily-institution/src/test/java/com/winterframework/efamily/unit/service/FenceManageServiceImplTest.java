package com.winterframework.efamily.unit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.institution.dto.FenceUserIdAndNameInfo;
import com.winterframework.efamily.institution.dto.UserFenceBaseInfo;
import com.winterframework.efamily.institution.dto.UserFenceNameAndIdInfo;
import com.winterframework.efamily.institution.service.IFenceManageService;
import com.winterframework.modules.test.BaseTestCase;



public class FenceManageServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(FenceManageServiceImplTest.class);

	@Resource(name="fenceManageServiceImpl")
	private IFenceManageService fenceManageServiceImpl;
	/*

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
	

	@Test
	@Rollback(false)
	public void deleteFence() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		System.out.println("*************************START******************************");
		Long fenceId = 4L;
		fenceManageServiceImpl.deleteFence(ctx, fenceId);
		System.out.println("**************************END******************************");
	}
	
	
	
	@Test
	@Rollback(false)
	public void updateFence() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		String orgId = "100";
		Integer isFenceBaseFlag = 1;
		Long fenceId = 5L;
		String userIdsAddFence = "";
		String userIdsSubFence = "";
		System.out.println("*************************START******************************");
		String userFenceBaseInfoStr = "{\"orgId\":100,\"fenceId\":5,\"name\":\"围栏名称22\",\"location\":\"22.537638,113.943649\",\"radius\":500,\"address\":\"围栏地址22\",\"type\":2}";
		UserFenceBaseInfo userFenceBaseInfo = JsonUtils.fromJson(userFenceBaseInfoStr, UserFenceBaseInfo.class);
		
		if(isFenceBaseFlag.intValue() == 1){
			fenceManageServiceImpl.saveUserFenceBaseInfo(ctx, userFenceBaseInfo);
		}
		
		if(StringUtils.isNotBlank(userIdsAddFence)){
			fenceManageServiceImpl.addUserToFence(ctx, userFenceBaseInfo.getFenceId(), userIdsAddFence);
		}
		
		if(StringUtils.isNotBlank(userIdsSubFence)){
			fenceManageServiceImpl.subUserFromFence(ctx, userFenceBaseInfo.getFenceId(), userIdsSubFence);
		}
		System.out.println("**************************END******************************");
	}
	
	*/

	@Test
	@Rollback(false)
	public void getFence() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		Long orgId = 100L;
		String memberId = "10000";
		System.out.println("*************************START******************************");
		
		Map<String,Object> map = new HashMap<String,Object>();
		UserFenceBaseInfo userFenceBaseInfo = new UserFenceBaseInfo();
		List<UserFenceNameAndIdInfo> userFenceNameAndIdInfoList = fenceManageServiceImpl.getUserFenceNameAndIdList(orgId);
		if(userFenceNameAndIdInfoList!=null && userFenceNameAndIdInfoList.size()>0){
			userFenceBaseInfo = fenceManageServiceImpl.getUserFenceBaseInfo(userFenceNameAndIdInfoList.get(0).getFenceId());
			map.put("resultCode", "0");
			map.put("userFenceNameAndIdInfoList", userFenceNameAndIdInfoList);
			map.put("userFenceBaseInfo", userFenceBaseInfo);
			map.put("userIdAndNameInFence", fenceManageServiceImpl.getUserIdAndNameInFence(userFenceNameAndIdInfoList.get(0).getFenceId()));
			map.put("userIdAndNameAllBindInfo", fenceManageServiceImpl.getUserIdAndNameAllBindInfoList(orgId));
		}else{
			map.put("resultCode", "10");//*** 数据为空
		}
		System.out.println("map : "+JsonUtils.toJson(map));
		System.out.println("**************************END******************************");
	}
}

