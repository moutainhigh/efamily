/**   
* @Title: EjlRemindDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午2:41:39 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlRemindDao;
import com.winterframework.efamily.entity.EjlRemind;


/** 
* @ClassName: EjlRemindDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午2:41:39 
*  
*/
@Repository("ejlRemindDaoImpl")
public class EjlRemindDaoImpl extends EjlComRemindDaoImpl<EjlRemind> implements IEjlRemindDao{

	/**
	* Title: queryAllDisposableRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlRemindDao#queryAllDisposableRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDisposableRemindForTask()throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryAllDisposableRemindForTask"));
	}

	/**
	* Title: queryAllRepeatRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlRemindDao#queryAllRepeatRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllRepeatRemindForTask()throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("queryAllRepeatRemindForTask"));
	}

	/**
	* Title: queryAllRemindsForUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlRemindDao#queryAllRemindsForUser(java.lang.Long) 
	*/
	@Override
	public List<EjlRemind> queryAllRemindsForUser(Long userId)throws BizException {
		return this.sqlSessionTemplate.selectList("queryAllRemindsForUser", userId);
	}

	@Override
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId)throws BizException {
		return this.sqlSessionTemplate.selectList("queryAllReceiveRemindsForUser", userId);
	}

	/**
	* Title: queryAllDeviceRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlRemindDao#queryAllDeviceRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDeviceRemindForTask()throws BizException {
		return this.sqlSessionTemplate.selectList("queryAllDeviceRemindForTask");
	}

	@Override
	public void insertRecord(EjlRemind ejlRemind)throws BizException {
		 this.sqlSessionTemplate.insert("insertRemindRecord", ejlRemind);
		
	}

	@Override
	public void updateRecord(EjlRemind ejlRemind)throws BizException {
		this.sqlSessionTemplate.update("updateRemindRecord", ejlRemind);
		
	}

	@Override
	public EjlRemind getEjlRecordById(Long id)throws BizException {
		return this.sqlSessionTemplate.selectOne("getEjlRecordById", id);
	}
	
}
