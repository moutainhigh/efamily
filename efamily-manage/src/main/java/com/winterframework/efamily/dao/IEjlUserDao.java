package com.winterframework.efamily.dao;


import java.util.List;

import com.winterframework.efamily.dto.CustomerDeviceStatisticsDto;
import com.winterframework.efamily.dto.manage.UserMonitor;
import com.winterframework.efamily.dto.manage.UserStruc;

public interface IEjlUserDao extends IEjlComUserDao {

	List<UserMonitor> getStatistics() throws Exception;
	List<UserStruc> getUserList() throws Exception;
	
	public List<CustomerDeviceStatisticsDto> getNumberModelStatistcsList() throws Exception;
	public List<CustomerDeviceStatisticsDto> getNumberModelStatusStatistcsList() throws Exception ;
}
