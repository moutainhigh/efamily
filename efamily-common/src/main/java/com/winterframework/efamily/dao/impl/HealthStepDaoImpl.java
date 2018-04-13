/**   
* @Title: HealthStepDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-10 上午10:16:44 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IHealthStepDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: HealthStepDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-10 上午10:16:44 
*  
*/
@Repository("healthStepDaoImpl")
public class HealthStepDaoImpl<E extends EjlHealthStepCount> extends BaseDaoImpl<EjlHealthStepCount> implements IHealthStepDao{

}
