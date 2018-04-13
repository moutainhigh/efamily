package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlRoleDao;
import com.winterframework.efamily.dto.RoleListResponse;
import com.winterframework.efamily.entity.EjlRole;
import com.winterframework.efamily.service.IEjlRoleService;

@Service("ejlRoleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlRoleServiceImpl extends BaseServiceImpl<IEjlRoleDao,EjlRole> implements IEjlRoleService {
 
	@Resource(name="ejlRoleDaoImpl")
	private IEjlRoleDao ejlRoleDao;
	@Override
	protected IEjlRoleDao getEntityDao() { 
		return ejlRoleDao;
	}
			
	
	@Override
	public RoleListResponse getRoleList() {
		 List<EjlRole> list=new ArrayList<EjlRole>();
		 EjlRole role=new EjlRole();
		 role.setId(1L);
		 role.setName("父母");
		 EjlRole role2=new EjlRole();
		 role2.setId(2L);
		 role2.setName("父母2");
		 RoleListResponse reponse=new RoleListResponse();
		 reponse.setRoleList(list.toString());
		 return reponse;
	}
}


