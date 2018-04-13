/**   
* @Title: IEjlRemindDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午2:01:34 
* @version V1.0   
*/
package com.winterframework.efamily.dao;


import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlRemind;

/** 
* @ClassName: IEjlRemindDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午2:01:34 
*  
*/
public interface IEjlComRemindDao extends IBaseDao<EjlRemind> {

	public List<EjlRemind> queryAllDisposableRemindForTask() throws Exception;
	public List<EjlRemind> queryAllRepeatRemindForTask() throws Exception;
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws Exception;
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws Exception;
	public List<EjlRemind> queryAllDeviceRemindForTask() throws Exception;
	public void insertRecord(EjlRemind ejlRemind) throws Exception;
	public void updateRecord(EjlRemind ejlRemind) throws Exception;
	public EjlRemind getEjlRecordById(Long id) throws Exception;
	/*public List<EjlRemind> queryAllWeekRemindForTask() throws Exception;
	public List<EjlRemind> queryAllMonthRemindForTask() throws Exception;
	public List<EjlRemind> queryAllYearRemindForTask() throws Exception;*/
}
