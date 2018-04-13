package com.winterframework.efamily.institution.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.WatchParamBaseInfo;

public interface IDeviceManageService {

	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,String imei);
	
	public List<OrgBindDeviceBaseInfo> getOrgBindDeviceBaseInfo(Long orgId,Integer start,String queryValue);
	
	public List<OrgUnbindUserBaseInfo> getOrgUnbindUserBaseInfo(Long orgId,Integer start,String queryValue);
	
	public WatchParamBaseInfo getWatchParamBaseInfo(Long orgUserId) throws BizException;
	
	public WatchParamBaseInfo saveWatchParamBaseInfo(WatchParamBaseInfo watchParamBaseInfo) throws BizException;
	
	public boolean bindOrUnbindDevice(Long orgId,Long orgUserId,Long orgDeviceId,Integer operType) throws BizException;
	
}
