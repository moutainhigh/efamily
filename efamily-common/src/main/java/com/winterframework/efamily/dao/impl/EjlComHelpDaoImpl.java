/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComHelpDao;
import com.winterframework.efamily.entity.EjlHelp;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Repository("ejlComHelpDaoImpl")
public class EjlComHelpDaoImpl<E extends EjlHelp> extends BaseDaoImpl<EjlHelp> implements IEjlComHelpDao {

}
