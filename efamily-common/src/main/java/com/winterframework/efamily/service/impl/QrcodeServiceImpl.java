package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IQrcodeDao;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.service.IQrcodeService;

@Service("qrcodeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class QrcodeServiceImpl  extends BaseServiceImpl<IQrcodeDao,Qrcode> implements IQrcodeService {
	@Resource(name="qrcodeDaoImpl")
	private IQrcodeDao dao;
	@Override
	protected IQrcodeDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	@Override
	public Qrcode getByImsi(String imsi) throws BizException {
		return getEntityDao().getByImsi(imsi);
	}
	@Override
	public Qrcode getByImei(String imei) throws BizException {
		return getEntityDao().getByImei(imei);
	}
}
