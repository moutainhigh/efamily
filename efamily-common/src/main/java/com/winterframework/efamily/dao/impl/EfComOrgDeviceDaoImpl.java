package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.enums.StatusBizCode;

@Repository("efComOrgDeviceDaoImpl")
public class EfComOrgDeviceDaoImpl<E extends EfOrgDevice> extends BaseDaoImpl<EfOrgDevice> implements IEfComOrgDeviceDao {

	@Override
	public void saveOrUpdate(EfOrgDevice entity) throws BizException{
		EfOrgDevice efOrgDevice = new EfOrgDevice();
		efOrgDevice.setImei(entity.getImei());
		efOrgDevice = this.selectOneObjByAttribute(efOrgDevice);
		if(efOrgDevice == null||efOrgDevice.getId()==null){
			this.insert(entity);
		}else{
			if(efOrgDevice.getStatus()==1&&(efOrgDevice.getOrgId()!= entity.getOrgId())){
				throw new BizException(StatusBizCode.ORG_BINDED_BY_OTHER.getValue());
			}
			entity.setId(efOrgDevice.getId());
			this.update(entity);
		}
	}
	
	@Override
	public EfOrgDevice getByImei(String imei) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("imei", imei);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByImei"), map);
	}
	@Override
	public EfOrgDevice getByDeviceId(Long deviceId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deviceId", deviceId);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByDeviceId"), map);
	}
	
	
	
	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,Integer pagesize,String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("start", start);
		map.put("pagesize", pagesize);
		map.put("imei", imei);
		
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getOrgUnbindDeviceBaseInfo"), map);
	}
	
	
}
