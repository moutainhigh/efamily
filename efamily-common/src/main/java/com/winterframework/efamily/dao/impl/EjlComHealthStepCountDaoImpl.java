/**   
* @Title: EjlHealthHeartRateDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午4:25:53 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;




import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComHealthStepCountDao;
import com.winterframework.efamily.entity.EjlHealthStepCount;

/** 
* @ClassName: EjlHealthHeartRateDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午4:25:53 
*  
*/
@Repository("ejlComHealthStepCountDaoImpl")
public class EjlComHealthStepCountDaoImpl<E extends EjlHealthStepCount> extends BaseDaoImpl<EjlHealthStepCount> implements IEjlComHealthStepCountDao {
	
	@Override
	public List<EjlHealthStepCount> getListByUserIdDeviceIdBetweenTime(	Long userId, Long deviceId, Date fromTime, Date toTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("fromTime", fromTime);
		map.put("toTime", toTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByUserIdDeviceIdBetweenTime"), map);
	}

}
