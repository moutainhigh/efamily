package com.winterframework.efamily.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserBarrierDao;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.entity.UserBarrierStruc;
import com.winterframework.efamily.entity.UserDeviceBarrierStruc;

@Repository("ejlComUserBarrierDaoImpl")
public class EjlComUserBarrierDaoImpl <E extends EjlUserBarrier> extends BaseDaoImpl<EjlUserBarrier> implements IEjlComUserBarrierDao {

	public EjlUserBarrier getOneBarrierByUserId(Long userId,Integer orgTag){
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("orgTag", orgTag);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getOneBarrierByUserId"), map);
	}
	
	public List<UserBarrierStruc> getListBarrierByUserId(Long userId,Integer orgTag){
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("orgTag", orgTag);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getListBarrierByUserId"), map);
	}
	
	public List<UserDeviceBarrierStruc> getUserBarrierList(Long userBarrierId,Integer orgTag){
		Map map = new HashMap();
		map.put("userBarrierId", userBarrierId);
		map.put("orgTag", orgTag);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserBarrierList"), map);
	}

	@Override
	public void deleteByUserAndOrgTag(Long userId, Integer orgTag) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("orgTag", orgTag);
		this.sqlSessionTemplate.selectList(this.getQueryPath("deleteByUserAndOrgTag"), map);		
	}

	public void deleteByFenceId(Long fenceId,Long updateId) {
		Map map = new HashMap();
		map.put("fenceId", fenceId);
		map.put("updatorId", updateId);
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.delete("deleteByFenceId", map);	
	}
	
	
}
