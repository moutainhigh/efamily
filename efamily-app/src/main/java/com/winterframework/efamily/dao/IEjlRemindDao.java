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

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlRemind;

/** 
* @ClassName: IEjlRemindDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午2:01:34 
*  
*/
public interface IEjlRemindDao extends IEjlComRemindDao {
	public List<EjlRemind> queryAllDisposableRemindForTask() throws BizException;;
	public List<EjlRemind> queryAllRepeatRemindForTask() throws BizException;;
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws BizException;;
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws BizException;;
	public List<EjlRemind> queryAllDeviceRemindForTask() throws BizException;;
	public void insertRecord(EjlRemind ejlRemind) throws BizException;;
	public void updateRecord(EjlRemind ejlRemind) throws BizException;;
	public EjlRemind getEjlRecordById(Long id) throws BizException;;
	/*public List<EjlRemind> queryAllWeekRemindForTask() throws BizException;;
	public List<EjlRemind> queryAllMonthRemindForTask() throws BizException;;
	public List<EjlRemind> queryAllYearRemindForTask() throws BizException;;*/
}
