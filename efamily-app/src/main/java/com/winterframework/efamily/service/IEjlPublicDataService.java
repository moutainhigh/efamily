package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlPublicData;


public interface IEjlPublicDataService extends IBaseService<EjlPublicData> {

	public String getUpdateFlagByAppVersion(String appVersion,String updateFlagStr,byte clinetType );
}
