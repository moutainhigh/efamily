package com.winterframework.efamily.service;
import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfOrg;

public interface IEfComOrgService extends IBaseService<EfOrg> {
	/**
	 * 功能：检查imei是否属于此key
	 * @param imei
	 * @param key
	 * @return
	 * @throws BizException
	 */
	public boolean checkImeiIsBelongKeyBy(String imei,String key) throws BizException;
	
	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException;
	
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException;
}
