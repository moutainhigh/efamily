package com.winterframework.efamily.service;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.RoleListResponse;
import com.winterframework.efamily.entity.EjlRole;


public interface IEjlRoleService extends IBaseService<EjlRole>{
	
	/**
	 * 获取家庭角色列表，此为初始化数据
	 */
	public RoleListResponse getRoleList();
 
}
