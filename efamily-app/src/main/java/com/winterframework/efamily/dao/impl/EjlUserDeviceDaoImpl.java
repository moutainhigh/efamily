package com.winterframework.efamily.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.entity.EjlUserDevice;

@Repository("ejlUserDeviceDaoImpl")
public class EjlUserDeviceDaoImpl extends EjlComUserDeviceDaoImpl<EjlUserDevice> implements IEjlUserDeviceDao {

	@Override
	public EjlUserDevice getEjlUserDevice(EjlUserDevice ejlUserDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlUserDevice"), ejlUserDevice);
	}

	@Override
	public EjlUserDevice getEjlUserDeviceByuserIdOrDeviceId(EjlUserDevice ejlUserDevice) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlUserDeviceByuserIdOrDeviceId"), ejlUserDevice);
	}

	@Override
	public EjlUserDevice getEjlUserDeviceByDeviceCode(String deviceCode) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlUserDeviceByDeviceCode"), deviceCode);
	}
	
	
	@Override
	public List<EjlUserDevice> getEjlUserDeviceByFamilyAndStatus(Long familyId) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEjlUserDeviceByFamilyAndStatus"), familyId);
	}
	
	@Override
	public int deleteByDeviceSwitch(EjlUserDevice ejlUserDevice){
		return this.sqlSessionTemplate.delete(this.getQueryPath("deleteByDeviceSwitch"), ejlUserDevice);
	}
	
	@Override
	public int updateByAttribute(EjlUserDevice ejlUserDevice){
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByAttribute"), ejlUserDevice);
	}
	
}
