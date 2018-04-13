 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.dao.impl;


import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IStatisticComUserConnectDao;
import com.winterframework.efamily.entity.StatisticUserConnect;

@Repository("statisticComUserConnectDao")
public class StatisticComUserConnectDaoImpl<E extends StatisticUserConnect> extends BaseDaoImpl<StatisticUserConnect> implements IStatisticComUserConnectDao{

	@Override
	public StatisticUserConnect getNewestRecord() {
		return this.sqlSessionTemplate.selectOne(getQueryPath("getNewestRecord"));
	}

	@Override
	public StatisticUserConnect getNewestIpRecord(String ip) {
		return this.sqlSessionTemplate.selectOne(getQueryPath("getNewestIpRecord"),ip);
	}
	
}
