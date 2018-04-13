 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.dao.impl;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComApiDao;
import com.winterframework.efamily.entity.EfApi;



@Repository("efComApiDaoImpl")
public class EfComApiDaoImpl <E extends EfApi> extends BaseDaoImpl<EfApi> implements IEfComApiDao{


}
