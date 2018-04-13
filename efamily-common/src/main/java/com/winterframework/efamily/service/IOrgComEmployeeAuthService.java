package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.OrgEmployeeAuth;

public interface IOrgComEmployeeAuthService  extends IBaseService<OrgEmployeeAuth> { 
	
	public void openOrgForEmployee(Long orgEmployeeId,String openOrgIds) throws BizException;

}
