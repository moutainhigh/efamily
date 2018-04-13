package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComFenceDao;
import com.winterframework.efamily.entity.OrgFence;

@Repository("orgComFenceDaoImpl")
public class OrgComFenceDaoImpl<E extends OrgFence> extends BaseDaoImpl<OrgFence> implements IOrgComFenceDao{
		
	
}
