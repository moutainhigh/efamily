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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
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
public class EjlRemindServiceImpl extends EjlComRemindServiceImpl implements IEjlRemindService {

	/**
	* Title: queryAllDisposableRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllDisposableRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDisposableRemindForTask() throws Exception {
		return this.getEntityDao().queryAllDisposableRemindForTask();
	}

	/**
	* Title: queryAllRepeatRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllRepeatRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllRepeatRemindForTask() throws Exception {
		List<EjlRemind> allList = this.getEntityDao().queryAllRepeatRemindForTask();
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

	private Date getMonitDate(Date currentDate, Date configDate) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.HOUR_OF_DAY, DateUtils.getHours(configDate));
		cal.set(Calendar.MINUTE, DateUtils.getMinutes(configDate));
		cal.set(Calendar.SECOND, DateUtils.getSeconds(configDate));
		return cal.getTime();
	}

	/**
	* Title: update
	* Description:
	* @param ejlRemind
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlRemindService#update(com.winterframework.efamily.entity.EjlRemind) 
	*/
	@Override
	public void saveOrUpdate(Context ctx,EjlRemind ejlRemind) throws Exception {
		if (ejlRemind.getId() != null) {
			if (ejlRemind.getDeviceCommond() == null) {
				ejlRemind.setDeviceCommond(2l);
			}
			save(ctx,ejlRemind);
		} else {
			ejlRemind.setDeviceCommond(1l);
			save(ctx,ejlRemind);
		}
	}

	@Override
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws Exception {
		return this.getEntityDao().queryAllRemindsForUser(userId);
	}

	@Override
	public EjlRemind queryReminById(Long id, Long type) throws Exception {
		if (type.intValue() == 1) {
			return this.getEntityDao().getById(id);
		} else {
			return this.getEntityDao().getEjlRecordById(id);
		}
	}

	@Override
	public void deleteById(Context ctx,Long id, Long type) throws Exception {
		EjlRemind ejlRemind = new EjlRemind();
		ejlRemind.setId(id);
		ejlRemind.setDeleteStatus(1l);
		if (type.intValue() == 1) {
			ejlRemind.setDeviceCommond(3l);
			save(ctx,ejlRemind);
		} else {
			ejlRemind.setGmtModified(new Date());
			this.getEntityDao().updateRecord(ejlRemind);
		}
	}

	@Override
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws Exception {
		return this.getEntityDao().queryAllReceiveRemindsForUser(userId);
	}

	/**
	* Title: queryAllDeviceRemindForTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlRemindService#queryAllDeviceRemindForTask() 
	*/
	@Override
	public List<EjlRemind> queryAllDeviceRemindForTask() throws Exception {
		return this.getEntityDao().queryAllDeviceRemindForTask();
	}

	@Override
	public void insertRecord(EjlRemind ejlRemind) throws Exception {
		this.getEntityDao().insertRecord(ejlRemind);

	}

	@Override
	public void updateRecord(EjlRemind ejlRemind) throws Exception {
		this.getEntityDao().updateRecord(ejlRemind);
	}

}
