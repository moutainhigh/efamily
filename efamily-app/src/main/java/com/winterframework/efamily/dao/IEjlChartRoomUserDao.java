package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.entity.EjlChartRoomUser;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlChartRoomUserDao extends IEjlComChartRoomUserDao {

	/**
	 * 
	* @Title: updateStatusByUserIdAndChartId 
	* @Description: TODO(安装用户和群组ID修改信息) 
	* @param ejlChartRoomUser
	* @return
	* @throws BizException
	 */
	public int updateStatusByUserIdAndChartId(EjlChartRoomUser ejlChartRoomUser) throws BizException ;
	
	/**
	 * 
	* @Title: getEjlChartRoomUserByAttribute 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param ejlChartRoomUser
	* @return
	* @throws BizException
	 */
	public List<EjlUser> getChartRoomUserByRoomIdAndStatus(EjlChartRoomUser ejlChartRoomUser) throws BizException ;
}
