package com.winterframework.efamily.dao;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.StatisticUserConnect;

public interface IStatisticComUserConnectDao  extends IBaseDao<StatisticUserConnect>{ 
	
	public StatisticUserConnect getNewestRecord();

	public StatisticUserConnect getNewestIpRecord(String ip);
	
}
