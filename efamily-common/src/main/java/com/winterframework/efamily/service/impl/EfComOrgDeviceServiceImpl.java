package com.winterframework.efamily.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.service.IEfComOrgDeviceService;



@Service("efComOrgDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComOrgDeviceServiceImpl extends BaseServiceImpl<IEfComOrgDeviceDao, EfOrgDevice>implements IEfComOrgDeviceService{

	@Resource(name="efComOrgDeviceDaoImpl")
	private IEfComOrgDeviceDao dao;
	@Override
	protected IEfComOrgDeviceDao getEntityDao() {
		return dao;
	}
	
	@Override
	public EfOrgDevice getByImei(String imei) throws BizException {
		try{
			return getEntityDao().getByImei(imei);
		}catch(Exception e){
			log.error("dao error(getByImei).imei="+imei,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	
	@Override
	public EfOrgDevice getValidByImei(String imei) throws BizException {
		EfOrgDevice orgDevice= getByImei(imei);
		return getValid(orgDevice);
	}
	
	@Override
	public EfOrgDevice getByDeviceId(Long deviceId) throws BizException {
		try{
			return getEntityDao().getByDeviceId(deviceId);
		}catch(Exception e){
			log.error("dao error(getByDeviceId).deviceId="+deviceId,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	
	@Override
	public EfOrgDevice getValidByDeviceId(Long deviceId) throws BizException {
		EfOrgDevice orgDevice= getByDeviceId(deviceId);
		return getValid(orgDevice);
	}

	private EfOrgDevice getValid(EfOrgDevice orgDevice) {
		if(orgDevice.getStatus().intValue()==YesNo.YES.getValue()){
			return orgDevice;
		}
		return null;
	}
	
	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String imei) {
		return getEntityDao().getOrgUnbindDeviceBaseInfo(orgId, start, pagesize, imei);
	}
	
}
