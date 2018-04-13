package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEfResourceDao;
import com.winterframework.efamily.entity.EfResourceAssist;
import com.winterframework.efamily.entity.FmlResource;

@Repository("efReourceDaoImpl")
public class EfReourceDaoImpl extends ResourceDaoImpl<FmlResource> implements IEfResourceDao {

	@Override
	public List<FmlResource> getResourceByAssistantTypeAndStatus(EfResourceAssist efResourceAssist)throws BizException {
			return this.sqlSessionTemplate.selectList(this.getQueryPath("getResourceByAssistantTypeAndStatus"), efResourceAssist);
	}
  
}