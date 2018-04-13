package com.winterframework.efamily.dao;


import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlComUserDao extends IBaseDao<EjlUser> {

	//查询拥有设备的用户
	public List<EjlUser> findDeviceUser() throws Exception;

	public List<EjlUser> getAttentionUserByFamilyId(Long familyId) throws BizException ;
	
	public List<EjlUser> getUserByfenceId(Long fenceId) throws BizException;
	
	public List<EjlUser> getUserByDeviceAndApp(Long familyId) throws BizException ;
	
	public EjlUser getFamilyHostUser(Long familyId) throws BizException;
	
	/**
	 * 获取有效用户列表
	 * @param type 用户类型
	 * @return
	 * @throws BizException
	 */
	List<EjlUser> getValidListByType(Integer type) throws Exception;
	/**
	 * 功能：根据设备ID号，获取使用该设备的用户ID
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public EjlUser getUserByDeviceId(Long deviceId) throws Exception;
	
	public Integer updatePasswdByUserId(Context ctx,Long userId,String password) throws Exception;
	
	
	public EjlUser getAttentionUserByUserIdAndAttendionId(Long userId,Long attendionId) throws Exception;
	
}
