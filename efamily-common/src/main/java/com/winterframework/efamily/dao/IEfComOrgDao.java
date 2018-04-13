package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EfOrg;

public interface IEfComOrgDao extends IBaseDao<EfOrg>{

	public EfOrg getEfOrgByImei(String imei) throws BizException;
	
	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException;
	
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException;
}
