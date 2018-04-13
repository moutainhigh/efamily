/**   
* @Title: IDeviceBatteryRecordDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:40:52 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import java.util.Date;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.EfLunar;
import com.winterframework.efamily.entity.EfWeather;

/** 
* @ClassName: IDeviceBatteryRecordDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:40:52 
*  
*/
public interface IEfComLunarDao extends IBaseDao<EfLunar>{
	public EfLunar getEfLunarByDate(Date date) throws Exception;
	public void deleteByDate(Date date) throws Exception;
}
