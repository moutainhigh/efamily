package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.UserInfoReq;
import com.winterframework.efamily.entity.EfOrgDevice;

public interface IInstitutionUserService extends IBaseService<EfOrgDevice>{
	public void institutionUserManage(Context ctx,List<UserInfoReq> list,String key) throws BizException;
	
	public boolean isImeiContainOrg(String imei,String ikey) throws BizException;
}
