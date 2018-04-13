/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlRemindDao;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.service.IEjlRemindService;

/** 
* @ClassName: EjlRemindServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-5 下午4:30:28 
*  
*/
@Service("ejlRemindServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlRemindServiceImpl extends BaseServiceImpl<IEjlRemindDao, EjlRemind> implements IEjlRemindService {

	@Resource(name = "ejlRemindDaoImpl")
	private IEjlRemindDao ejlRemindDaoImpl;

	/**
	* Title: queryAllDisposableRemindForTask
	* Description:
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllDisposableRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDisposableRemindForTask() throws BizException {
		return ejlRemindDaoImpl.queryAllDisposableRemindForTask();
	}

	/**
	* Title: queryAllRepeatRemindForTask
	* Description:
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllRepeatRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllRepeatRemindForTask() throws BizException {
		List<EjlRemind> allList = ejlRemindDaoImpl.queryAllRepeatRemindForTask();
		List<EjlRemind> sendList = new ArrayList<EjlRemind>();
		Date currentDate = DateUtils.currentDate();//获取当前时间
		int currentWeek = DateUtils.getDayValue(currentDate);//获取当前是周几
		int currentMonth = DateUtils.getDaysOfMonth(currentDate);//获取当天是多少号
		int currentYear = DateUtils.getDayOfYear(currentDate);
		for (EjlRemind ejlRemind : allList) {
			Date configDate = ejlRemind.getSendTime();
			Date monitDate = this.getMonitDate(currentDate, configDate);
			if (monitDate.compareTo(currentDate) <= 0
					&& (ejlRemind.getReceiveTime() == null || !DateUtils.getStartTimeOfDate(ejlRemind.getReceiveTime())
							.equals(DateUtils.getStartTimeOfDate(currentDate)))) {
				if (ejlRemind.getSendPeriod() == 2) {//每日
					sendList.add(ejlRemind);
					ejlRemind.setReceiveTime(monitDate);
				} else if (ejlRemind.getSendPeriod() == 3) {//工作日
					if (currentWeek != 6 && currentWeek != 7) {
						sendList.add(ejlRemind);
						ejlRemind.setReceiveTime(monitDate);
					}
				} else if (ejlRemind.getSendPeriod() == 4) {//每周
					if (currentWeek == DateUtils.getDayValue(configDate)) {
						sendList.add(ejlRemind);
						ejlRemind.setReceiveTime(monitDate);
					}
				} else if (ejlRemind.getSendPeriod() == 5) {
					if (currentMonth == DateUtils.getDaysOfMonth(configDate)) {
						sendList.add(ejlRemind);
						ejlRemind.setReceiveTime(monitDate);
					}
				} else if (ejlRemind.getSendPeriod() == 6) {
					if (currentYear == DateUtils.getDayOfYear(configDate)) {
						sendList.add(ejlRemind);
						ejlRemind.setReceiveTime(monitDate);
					}
				}

			}
		}
		return sendList;
	}

	private Date getMonitDate(Date currentDate, Date configDate) throws BizException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.HOUR_OF_DAY, DateUtils.getHours(configDate));
		cal.set(Calendar.MINUTE, DateUtils.getMinutes(configDate));
		cal.set(Calendar.SECOND, DateUtils.getSeconds(configDate));
		return cal.getTime();
	}

	/**
	* Title: getEntityDao
	* Description:
	* @return 
	* @see com.winterframework.efamily.base.BaseServiceImpl#getEntityDao() 
	*/
	@Override
	protected IEjlRemindDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlRemindDaoImpl;
	}

	/**
	* Title: update
	* Description:
	* @param ejlRemind
	* @throws BizException 
	* @see com.winterframework.efamily.service.IEjlRemindService#update(com.winterframework.efamily.entity.EjlRemind) 
	*/
	@Override
	public void saveOrUpdate(Context ctx,EjlRemind ejlRemind) throws BizException {
		if (ejlRemind.getId() != null) {
			if (ejlRemind.getDeviceCommond() == null) {
				ejlRemind.setDeviceCommond(2l);
			}
			ejlRemind.setReceiveTime(DateUtils.addDays(DateUtils.currentDate(), -1));
			save(ctx,ejlRemind);
		} else {
			ejlRemind.setDeviceCommond(1l);
			save(ctx,ejlRemind);
		}
	}

	@Override
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws BizException {
		return ejlRemindDaoImpl.queryAllRemindsForUser(userId);
	}

	@Override
	public EjlRemind queryReminById(Long id, Long type) throws BizException {
		if (type.intValue() == 1) {
			return ejlRemindDaoImpl.getById(id);
		} else {
			return ejlRemindDaoImpl.getEjlRecordById(id);
		}
	}

	@Override
	public void deleteById(Context ctx,Long id, Long type) throws BizException {
		EjlRemind ejlRemind = new EjlRemind();
		ejlRemind.setId(id);
		ejlRemind.setDeleteStatus(1l);
		if (type.intValue() == 1) {//创建的提醒
			ejlRemind.setDeviceCommond(3l);
			save(ctx,ejlRemind);
		} else {//收到的提醒
			ejlRemind.setGmtModified(new Date());
			ejlRemindDaoImpl.updateRecord(ejlRemind);
		}
	}

	@Override
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws BizException {
		return ejlRemindDaoImpl.queryAllReceiveRemindsForUser(userId);
	}

	/**
	* Title: queryAllDeviceRemindForTask
	* Description:
	* @return
	* @throws BizException 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllDeviceRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDeviceRemindForTask() throws BizException {
		return ejlRemindDaoImpl.queryAllDeviceRemindForTask();
	}

	@Override
	public void insertRecord(EjlRemind ejlRemind) throws BizException {
		ejlRemindDaoImpl.insertRecord(ejlRemind);

	}

	@Override
	public void updateRecord(EjlRemind ejlRemind) throws BizException {
		ejlRemindDaoImpl.updateRecord(ejlRemind);
	}

}
