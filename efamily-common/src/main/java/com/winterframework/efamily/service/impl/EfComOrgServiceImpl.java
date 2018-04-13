package com.winterframework.efamily.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComOrgDao;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.service.IEfComOrgService;


@Service("efComOrgServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfComOrgServiceImpl extends BaseServiceImpl<IEfComOrgDao, EfOrg>implements IEfComOrgService{

	@Resource(name="efComOrgDaoImpl")
	private IEfComOrgDao dao;
	@Override
	protected IEfComOrgDao getEntityDao() {
		return dao;
	}
	
	public boolean checkImeiIsBelongKeyBy(String imei,String key) throws BizException{
		boolean flag = false;
		if(StringUtils.isBlank(imei)||StringUtils.isBlank(key)){
			return false;
		}
		EfOrg org = getEntityDao().getEfOrgByImei(imei);
		if(org != null){
			if(key.equals(org.getIkey())||key.equals(org.getUkey())){
				flag = true;
			}
		}else{
			log.error("根据imei查找机构不存在 imei = "+imei+" ; ");
		}
		
		return flag;
	}
	
	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException{
		return dao.getEmployeeCanOptOrgList(orgEmployeeId);
	}
	
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException{
		return dao.getEmployeeCanOptOrgByAddressList(orgEmployeeId,province,city,county);
	} 
	
}
