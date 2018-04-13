package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.OrgFence;

public interface IOrgComFenceService  extends IBaseService<OrgFence> { 
	
	public void deleteOrgFence(Context ctx,Long fenceId) throws BizException;
	
}
