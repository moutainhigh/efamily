package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComResourceAssistDao;
import com.winterframework.efamily.entity.EfResourceAssist;



@Repository("efComResourceAssistDaoImpl")
public class EfComResourceAssistDaoImpl<E extends EfResourceAssist> extends BaseDaoImpl<EfResourceAssist>  implements IEfComResourceAssistDao {
  
}