/**   
* @Title: DeviceStepDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-10 上午10:56:21 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.dao.IDeviceStepDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: DeviceStepDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-10 上午10:56:21 
*  
*/
@Repository("deviceStepDaoImpl")
public class DeviceStepDaoImpl extends HealthStepDaoImpl<EjlHealthStepCount> implements IDeviceStepDao{

}
