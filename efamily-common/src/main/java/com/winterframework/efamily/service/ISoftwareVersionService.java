package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;

public interface ISoftwareVersionService{ 
	/**
	 * 高于等于某个版本
	 * @param version
	 * @return
	 * @throws BizException
	 */
	boolean gt(Long deviceId,String version) throws BizException;
	boolean gt(String imei,String version) throws BizException;
}
