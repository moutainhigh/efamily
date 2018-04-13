package com.winterframework.efamily.dao.impl;


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
	public int deleteByDeviceSwitch(EjlUserDevice ejlUserDevice){
		return this.sqlSessionTemplate.delete(this.getQueryPath("deleteByDeviceSwitch"), ejlUserDevice);
	}
	
	@Override
	public int updateByAttribute(EjlUserDevice ejlUserDevice){
		return this.sqlSessionTemplate.update(this.getQueryPath("updateByAttribute"), ejlUserDevice);
	}
	
	@Override
	public EjlUserDevice getByAttribute(EjlUserDevice ejlUserDevice){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getObjByAttribute"), ejlUserDevice);
	}
	
	@Override
	public EjlUserDevice getEjlUserDeviceByDeviceCode(String deviceCode) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEjlUserDeviceByDeviceCode"), deviceCode);
	}
	
}
