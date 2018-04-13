package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.Qrcode;

public interface IQrcodeDao  extends IBaseDao<Qrcode>{ 
	Qrcode getByImsi(String imsi) throws BizException;
	Qrcode getByImei(String imei) throws BizException;
	Qrcode getByDeviceId(Long deviceId) throws BizException;
	List<Qrcode> getByImeiList(List<String> imeiList) throws BizException;
	public int insertBatchEntity(List<Qrcode> entitys) throws BizException;
}
