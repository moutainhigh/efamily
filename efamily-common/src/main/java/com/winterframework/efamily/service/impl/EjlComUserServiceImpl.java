package com.winterframework.efamily.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.utils.NotificationUtil;

@Service("ejlComUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComUserServiceImpl extends BaseServiceImpl<IEjlComUserDao, EjlUser> implements IEjlComUserService {

	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDao;

	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	@Override
	protected IEjlComUserDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlComUserDao;
	}

	@Override
	public void updateUserStatus(List<EjlUser> list) throws Exception {
		ejlComUserDao.updateBatch(list);
		
	}
	
	@Override
	public List<EjlUser> getValidDeviceList() throws BizException {
		try{
			return ejlComUserDao.getValidListByType(EjlUser.Type.WATCH.getCode());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<EjlUser> getAttentionUserByFamilyId(Long fenceId) throws BizException{
		try{
			return ejlComUserDao.getAttentionUserByFamilyId(fenceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
    
	public List<EjlUser> getUserByFamilyAndType(Long family,Long type) throws BizException {
		try{
			EjlUser user = new EjlUser();
			user.setFamilyId(family);
			user.setType(type);
			return ejlComUserDao.selectListObjByAttribute(user);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	public List<EjlUser> getUserByfenceId(Long fenceId) throws BizException{
		try{
			return ejlComUserDao.getUserByfenceId(fenceId);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId,boolean isRealTime){
    	if(userList == null || userList.size()==0){
			log.info("需要推送的用户为空。 userId = "+userId+" ; noticeType = "+noticeType);
			return;
    	}
   		for(EjlUser userTemp : userList){
   			if(notNoticeUserId!=null && notNoticeUserId.longValue() == userTemp.getId().longValue()){
   				continue;
   			} 
   			try { 
				NotyTarget t=new NotyTarget(userTemp.getId(),null); 
				
				//Map<String,String> paramMap=userNotice.getParamMap();
				Map<String,String> data2=new HashMap<String,String>(); 
				data2.put("noticeType", noticeType.getValue()+"");
				data2.putAll(data);
				
				/*String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
				data.put("content", replaceParam(content,paramMap));*/
				
				NotyMessage message=new NotyMessage();
				message.setId(null);
				message.setType(NotyMessage.Type.NOTICE);
				message.setCommand(EfamilyConstant.PUSH);
				message.setData(data2);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				notification.setRealTime(isRealTime);
				
				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("推送[notifyUser]时出现异常：  userId = "+userTemp.getId());
			}
   		}
     }
}
