package com.winterframework.efamily.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComOrgDao;
import com.winterframework.efamily.entity.EfOrg;

@Repository("efComOrgDaoImpl")
public class EfComOrgDaoImpl<E extends EfOrg> extends BaseDaoImpl<EfOrg> implements IEfComOrgDao {
	
	@Override
	public EfOrg getEfOrgByImei(String imei) throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getEfOrgByImei"), imei);
	}
	@Override
	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEmployeeCanOptOrgList"), orgEmployeeId);
	}
	@Override
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgEmployeeId", orgEmployeeId);
		map.put("province", province);
		map.put("city", city);
		map.put("county", county);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getEmployeeCanOptOrgByAddressList"), map);
	}
}
