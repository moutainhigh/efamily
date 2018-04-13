package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComAttentionUserDao;
import com.winterframework.efamily.entity.EjlAttentionUser;


@Repository("ejlComAttentionUserDaoImpl")
public class EjlComAttentionUserDaoImpl<E extends EjlAttentionUser> extends BaseDaoImpl<EjlAttentionUser>  implements IEjlComAttentionUserDao {
  
}
