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

import com.winterframework.efamily.dao.ITskLocationDao;
import com.winterframework.efamily.entity.EjlLocation;

 
@Repository("tskLocationDaoImpl")
public class TskLocationDaoImpl extends EjlComLocationDaoImpl<EjlLocation> implements ITskLocationDao {
	@Override
	public List<EjlLocation> getListByUserIdDeviceIdAfterTime(Long userId,
			Long deviceId, Date time) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("deviceId", deviceId);
		map.put("time", time);
		return this.sqlSessionTemplate.selectList(getQueryPath("getListByUserIdDeviceIdAfterTime"), map);
	}
}

