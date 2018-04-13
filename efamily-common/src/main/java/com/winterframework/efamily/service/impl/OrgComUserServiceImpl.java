package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IOrgComUserDao;
import com.winterframework.efamily.entity.DeviceAlarmLastForPlatform;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.entity.WatchParamBaseInfo;
import com.winterframework.efamily.service.IOrgComUserService;

@Service("orgComUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OrgComUserServiceImpl  extends BaseServiceImpl<IOrgComUserDao,OrgUser> implements IOrgComUserService {
	@Resource(name="orgComUserDaoImpl")
	private IOrgComUserDao dao;
	@Override
	protected IOrgComUserDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public OrgUser getOrgUserByUserId(Long userId) throws BizException{
		OrgUser orgUser = new OrgUser();
		orgUser.setUserId(userId);
		return dao.selectOneObjByAttribute(orgUser);
	}
	
	public List<UserHeartRateAndBloodPressure> getUserHeartRateAndBloodPressureList(Long orgId,Integer status,String name,String phoneNumber){
		return getEntityDao().getUserHeartRateAndBloodPressureList(orgId, status, name, phoneNumber);
	}

	public List<DeviceAlarmLastForPlatform> getDeviceAlarmLastForPlatformList(Long orgId,Integer status,Long time){
		return getEntityDao().getDeviceAlarmLastForPlatformList(orgId, status, time);
	}
	
	public List<OrgBindDeviceBaseInfo> getOrgBindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String orgUserName,String imei){
		return dao.getOrgBindDeviceBaseInfo(orgId, start, pagesize, orgUserName, imei);
	}
	public List<OrgUnbindUserBaseInfo> getOrgUnbindUserBaseInfo(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber){
		
		return dao.getOrgUnbindUserBaseInfo(orgId, start, pagesize,name,phoneNumber);
	}
	
	public WatchParamBaseInfo getWatchParamBaseInfo(Long orgUserId){
		return dao.getWatchParamBaseInfo(orgUserId);
	}
	
	public List<MemberSimpleInfoStruct> getMemberSimpleInfoStructList(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber,String idNumber){
		return dao.getMemberSimpleInfoStructList(orgId, start, pagesize, name, phoneNumber, idNumber);
	}
	
	public OrgUser getOrgUserByPhoneNumber(String phoneNumber) throws BizException{
		OrgUser orgUser = new OrgUser();
		orgUser.setPhoneNumber(phoneNumber);
		return dao.selectOneObjByAttribute(orgUser);
	}
	
}
