package com.winterframework.efamily.api.service;

import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.base.exception.BizException;

public interface ILoadCacheFromDatabaseService {

	/**
	 * 功能：从数据库中
	 * @throws BizException
	 */
	public void loadAuthAndFrequencyFromData() throws BizException;
	
	
	/**
	 * 功能：
	 * @param key
	 * @param requestUri
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public ResultCode authAndFrequencyLimitCheck(String key,String requestUri,String ip) throws Exception;
	
}
