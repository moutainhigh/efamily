package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.Qrcode;

public interface IQrcodeService extends IBaseService<Qrcode> { 
	Qrcode getByImsi(String imsi) throws BizException;
	Qrcode getByImei(String imei) throws BizException;
}
