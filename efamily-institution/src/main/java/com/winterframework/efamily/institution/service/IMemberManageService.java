package com.winterframework.efamily.institution.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;

public interface IMemberManageService {

	public Long getUserIdBy(Long memberId) throws BizException;
	
	public List<MemberSimpleInfoStruct>  getMemberSimpleInfoStructList(Long orgId,Integer start,String queryValue) throws BizException;
	
	public MemberInfoStruct  getMemberInfoStruct(Long orgUserId) throws BizException ;
	
	public void saveOrUpdateMemberInfo(Context ctx,MemberInfoStruct memberInfoStruct) throws BizException;
	
	public void deleteMemberInfo(Context ctx,Long orgUserId) throws BizException;
	
}
