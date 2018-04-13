package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;

public interface IEfComOrgDeviceDao extends IBaseDao<EfOrgDevice>{
	public void saveOrUpdate(EfOrgDevice entity) throws BizException;
	
	EfOrgDevice getByImei(String imei) throws Exception;
	EfOrgDevice getByDeviceId(Long deviceId) throws Exception;
	
	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String imei) ;
}
