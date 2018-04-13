package com.winterframework.efamily.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserFenceDao;
import com.winterframework.efamily.entity.EjlUserFence;
import com.winterframework.efamily.entity.UserBarrierStruc;

@Repository("ejlComUserFenceDaoImpl")
public class EjlComUserFenceDaoImpl<E extends EjlUserFence> extends BaseDaoImpl<EjlUserFence> implements IEjlComUserFenceDao {
	
	public void deleteByfenceId(Long fenceId,Long updatorId) throws BizException {
		  Map map = new HashMap();
		  map.put("fenceId", fenceId);
		  map.put("updatorId", updatorId);
		  map.put("updatorTime", new Date());
		  this.sqlSessionTemplate.delete(this.getQueryPath("deleteByfenceId"), fenceId);
	}

	
	public List<EjlUserFence> getAllUserFenceInFence(Long orgId){
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAllUserFenceInFence"), orgId);
	}
	
	
	
}
