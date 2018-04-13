package com.winterframework.efamily.service;


import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;

public interface IEjlComUserService extends IBaseService<EjlUser> {

	void updateUserStatus(List<EjlUser> list) throws Exception;
	
	/**
	 * 获取有效设备用户列表
	 * @return
	 * @throws BizException
	 */
	List<EjlUser> getValidDeviceList() throws BizException;
	
	public List<EjlUser> getUserByFamilyAndType(Long family,Long type) throws BizException;
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId,boolean isRealTime);
	
	public List<EjlUser> getAttentionUserByFamilyId(Long fenceId) throws BizException;
	
	public List<EjlUser> getUserByfenceId(Long fenceId) throws BizException;
}
