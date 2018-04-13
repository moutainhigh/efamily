package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IOrgComUserHealthyFileDao;
import com.winterframework.efamily.entity.OrgUserHealthyFile;


@Repository("orgComUserHealthyFileDaoImpl")
public class OrgComUserHealthyFileDaoImpl<E extends OrgUserHealthyFile> extends BaseDaoImpl<OrgUserHealthyFile> implements IOrgComUserHealthyFileDao{
		
}