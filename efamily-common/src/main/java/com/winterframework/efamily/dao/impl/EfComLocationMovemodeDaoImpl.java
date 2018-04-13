/**   
* @Title: DeviceBatteryRecordDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午3:44:17 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.winterframework.efamily.core.base.BaseDaoImpl;

import com.winterframework.efamily.dao.IEfComLocationMovemodeDao;

import com.winterframework.efamily.entity.EfLocationMovemode;


/** 
* @ClassName: DeviceBatteryRecordDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-15 下午3:44:17 
*  
*/
@Repository("efComLocationMovemodeDaoImpl")
public class EfComLocationMovemodeDaoImpl<E extends EfLocationMovemode> extends BaseDaoImpl<EfLocationMovemode> implements IEfComLocationMovemodeDao{

	@Override
	public EfLocationMovemode getLastEfLocationMovemode(Long userId,
			Long deviceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		map.put("userId", userId); 
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastEfLocationMovemode"), map);
	}

	@Override
	public List<EfLocationMovemode> getMoveDeviceList() throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getMoveDeviceList"));
	}

	@Override
	public List<EfLocationMovemode> getLastEfLocationMovemodeList(Long userId,
			Long deviceId, int count) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", deviceId); 
		map.put("userId", userId); 
		map.put("count", count);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getLastEfLocationMovemodeList"), map);
	}

	@Override
	public List<EfLocationMovemode> getStilDeviceList() throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getStilDeviceList"));
	}
}
