package com.winterframework.efamily.service;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;

public interface IEfComOrgDeviceService extends IBaseService<EfOrgDevice> {
	
	public EfOrgDevice getByImei(String imei) throws BizException;
	public EfOrgDevice getValidByImei(String imei) throws BizException;
	public EfOrgDevice getByDeviceId(Long deviceId) throws BizException;
	public EfOrgDevice getValidByDeviceId(Long deviceId) throws BizException;
	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String imei);

}
