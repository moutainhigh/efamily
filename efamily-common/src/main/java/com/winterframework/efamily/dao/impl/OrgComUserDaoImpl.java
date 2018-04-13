package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComUserDao;
import com.winterframework.efamily.entity.DeviceAlarmLastForPlatform;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.entity.UserHeartRateAndBloodPressure;
import com.winterframework.efamily.entity.WatchParamBaseInfo;


@Repository("orgComUserDaoImpl")
public class OrgComUserDaoImpl<E extends OrgUser> extends BaseDaoImpl<OrgUser> implements IOrgComUserDao{
	
	
	@Override
	public List<UserHeartRateAndBloodPressure> getUserHeartRateAndBloodPressureList(Long orgId,Integer status,String name,String phoneNumber) {
		OrgUser orgUser = new OrgUser();
		orgUser.setOrgId(orgId);
		orgUser.setStatus(status);
		orgUser.setName(name);
		orgUser.setPhoneNumber(phoneNumber);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserHeartRateAndBloodPressureList"), orgUser);
	}

	@Override
	public List<DeviceAlarmLastForPlatform> getDeviceAlarmLastForPlatformList(Long orgId,Integer status,Long time) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("orgId", orgId);
		map.put("time", time);
		
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getDeviceAlarmLastForPlatformList"), map);
	}
	
	public List<OrgBindDeviceBaseInfo> getOrgBindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String orgUserName,String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		map.put("orgUserName", orgUserName);
		map.put("imei", imei);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getOrgBindDeviceBaseInfo"), map);
	}

	public List<MemberSimpleInfoStruct> getMemberSimpleInfoStructList(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber,String idNumber) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		map.put("name", name);
		map.put("phoneNumber", phoneNumber);
		map.put("idNumber", idNumber);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getMemberSimpleInfoStructList"), map);
	}
	

	public List<OrgUnbindUserBaseInfo> getOrgUnbindUserBaseInfo(Long orgId,Integer start,Integer pagesize,String name,String phoneNumber) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		map.put("name", name);
		map.put("phoneNumber", phoneNumber);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getOrgUnbindUserBaseInfo"), map);
	}
	
	public WatchParamBaseInfo getWatchParamBaseInfo(Long orgUserId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getWatchParamBaseInfo"), orgUserId);
	}
	
	
	
	
}
