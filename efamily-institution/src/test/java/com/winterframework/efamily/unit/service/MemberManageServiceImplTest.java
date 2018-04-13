package com.winterframework.efamily.unit.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.modules.test.BaseTestCase;

public class MemberManageServiceImplTest extends BaseTestCase {
	Logger log=LoggerFactory.getLogger(MemberManageServiceImplTest.class);
	
	@Resource(name="memberManageServiceImpl")
	private IMemberManageService memberManageServiceImpl;
	
	@Test
	@Rollback(false)
	public void test() throws Exception{
		Context ctx=new Context();
		ctx.set("userId", 100L);
		Long orgId = 100L;
		Integer start = 1;
		String queryValue = "";
		Long orgUserId = 2L;
		System.out.println("**************************START******************************");
		List<MemberSimpleInfoStruct> memberSimpleInfoStructList = memberManageServiceImpl.getMemberSimpleInfoStructList(orgId, start, queryValue);
		System.out.println("memberSimpleInfoStructList : "+ JsonUtils.toJson(memberSimpleInfoStructList));
		
		MemberInfoStruct memberInfoStruct = memberManageServiceImpl.getMemberInfoStruct(orgUserId);
		System.out.println("memberInfoStruct : "+memberInfoStruct.toString());
		memberInfoStruct.setAge(100);
		memberInfoStruct.setDietTaboo("什么都不能吃");
		memberManageServiceImpl.saveOrUpdateMemberInfo(ctx, memberInfoStruct);
		
		memberManageServiceImpl.deleteMemberInfo(ctx, orgUserId);
		
		System.out.println("**************************END******************************");
		
	}
	
}
