package com.winterframework.efamily.institution.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.institution.dto.FenceUserIdAndNameInfo;
import com.winterframework.efamily.institution.dto.UserFenceBaseInfo;
import com.winterframework.efamily.institution.dto.UserFenceNameAndIdInfo;

public interface IFenceManageService {

	public List<UserFenceNameAndIdInfo> getUserFenceNameAndIdList(Long orgId) throws BizException;
	
	public UserFenceBaseInfo  getUserFenceBaseInfo(Long fenceId) throws BizException;
	
	public List<FenceUserIdAndNameInfo> getUserIdAndNameInFence(Long fenceId) throws BizException;
	
	public List<FenceUserIdAndNameInfo> getUserIdAndNameAllBindInfoList(Long orgId) throws BizException;
	
	public OrgFence saveUserFenceBaseInfo(Context ctx,UserFenceBaseInfo userFenceBaseInfo) throws BizException;
	
	public void deleteFenceIdAndUserId(Context ctx,Long fenceId) throws BizException;
	
	public void addUserToFence(Context ctx,Long fenceId,String userIds) throws NumberFormatException, BizException;
	
	public void subUserFromFence(Context ctx,Long fenceId,String userIds) throws BizException;
	
	public void deleteFence(Context ctx,Long fenceId) throws BizException;
	
	public void saveFenceIdAndUserId(Context ctx,OrgFence orgFence,String userIds) throws BizException;
}
