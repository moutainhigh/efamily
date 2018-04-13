package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IQrcodeDao;
import com.winterframework.efamily.entity.Qrcode;
 
@Repository("qrcodeDaoImpl")
public class QrcodeDaoImpl<E extends Qrcode> extends BaseDaoImpl<Qrcode> implements IQrcodeDao{
	@Override
	public Qrcode getByImsi(String imsi) throws BizException {
		return sqlSessionTemplate.selectOne(getQueryPath("getByImsi"),imsi);
	}
	@Override
	public Qrcode getByImei(String imei) throws BizException {
		return sqlSessionTemplate.selectOne(getQueryPath("getByImei"),imei);
	}
	@Override
	public Qrcode getByDeviceId(Long deviceId) throws BizException {
		return sqlSessionTemplate.selectOne(getQueryPath("getByDeviceId"), deviceId);
	}
	@Override
	public List<Qrcode> getByImeiList(List<String> imeiList) throws BizException {
		return sqlSessionTemplate.selectList(getQueryPath("getByImeiList"),imeiList);
	}
	
	@Override
	public int insertBatchEntity(List<Qrcode> entitys) throws BizException {
		return sqlSessionTemplate.insert(getQueryPath("insertBatchEntity"),entitys);
	}
}
