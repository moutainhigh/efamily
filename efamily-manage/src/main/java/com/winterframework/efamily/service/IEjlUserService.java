package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dto.CustomerStatisticsDataInfo;
import com.winterframework.efamily.dto.CustomerStatisticsPageShowInfo;
import com.winterframework.efamily.dto.manage.UserMonitorStruc;
import com.winterframework.efamily.dto.manage.UserStruc;

public interface IEjlUserService extends IEjlComUserService {

	/**
	 * 用户在线统计
	 * @return
	 * @throws BizException
	 */
	UserMonitorStruc getStatistics() throws BizException;
	/**
	 * 获取用户信息列表
	 * @return
	 * @throws BizException
	 */
	List<UserStruc> getUserList() throws BizException;
	
	public CustomerStatisticsPageShowInfo getDeviceStatistics()throws Exception;
}
