package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.PhoneAddressBookStruc;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserFriendService;
import com.winterframework.efamily.utils.StringHelper;

@Service("ejlUserFriendServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserFriendServiceImpl extends BaseServiceImpl<IEjlUserFriendDao,EjlUserFriend> implements IEjlUserFriendService {

	private static final Logger log = LoggerFactory.getLogger(EjlUserFriendServiceImpl.class);
	
	@Resource(name="ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;
	
	@Resource(name="ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Override
	protected IEjlUserFriendDao getEntityDao() {
		return ejlUserFriendDaoImpl;
	}
	
	/**
	* 
	* @Title: manageUserFriend 
	* @Description: TODO(管理好友的添加和接受操作) 
	* @param userId
	* @param customerId
	* @param oprType
	 * @throws Exception 
	 */
	public void manageUserFriend(Context ctx,Long userId,Long customerId,Long oprType) throws Exception{
		EjlUser userOperate = ejlUserDaoImpl.getById(userId);
		EjlUser userFriend = ejlUserDaoImpl.getById(customerId);
		if(userOperate == null || userFriend == null){
			log.error("操作的用户不存在，操作失败。 userOperate = "+userOperate+" ; userFriend = "+userFriend);
        	throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
		}
		if(userOperate.getFamilyId() != null && userFriend.getFamilyId()!=null && userOperate.getFamilyId().longValue() == userFriend.getFamilyId().longValue()){
			log.error("同一个家庭的用户不能成为好友 。 userId = "+userOperate.getId()+" ; friendUserId = "+userFriend.getId()+" ; familyId = "+userFriend.getFamilyId());
        	throw new BizException(StatusBizCode.USER_FRIEND_SAME_FAMILY.getValue());
		}
		if( userOperate.getId().longValue() == userFriend.getId().longValue()){
			log.error("同一个用户不能与自己成为好友 。 userId = "+userOperate.getId()+" ; friendUserId = "+userFriend.getId());
        	throw new BizException(StatusBizCode.USER_FRIEND_SAME_USER.getValue());
		}
		
		EjlUserFriend user = new EjlUserFriend();
		EjlUserFriend friend = new EjlUserFriend();
		user.setUserId(userId);
		user.setFriendId(customerId);
		friend.setUserId(customerId);
		friend.setFriendId(userId);
		
		if(EfamilyConstant.USER_FRIEND_ADD == oprType){
			EjlUserFriend userFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(user); 
			if(userFriendCheck != null){
				//***  已经添加过 (申请中   添加成功  已删除   被对方申请)  
				if(userFriendCheck.getStatus() == EfamilyConstant.USER_FRIEND_STATUS_APPLY ){
					//***   已申请中 （重复申请）
					if(DateUtils.calcHoursBetween(userFriendCheck.getGmtModified(), new Date())>EfamilyConstant.APPLY_FRIEND_TIME_INTERVAL){
						user.setGmtModified(new Date());
						ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
						
						friend.setGmtModified(new Date());
						ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
					}else{
						log.error("用户好友管理，已是申请中，重复申请加为好友，操作失败。 userId = "+userId+" ; customerId = "+customerId+" ; status = "+EfamilyConstant.USER_FRIEND_STATUS_APPLY);
						throw new BizException(StatusBizCode.USER_FRIEND_REPEAT_APPLY.getValue());
					}
				}else if(userFriendCheck.getStatus() == EfamilyConstant.USER_FRIEND_STATUS_SUCCESS){
					//***   已添加成功 （重复申请）
					log.error("用户好友管理，已是好友，重复申请加为好友，操作失败。 userId = "+userId+" ; customerId = "+customerId+" ; status = "+EfamilyConstant.USER_FRIEND_STATUS_APPLY);
					throw new BizException(StatusBizCode.USER_FRIEND_ALREADY_SUCCESS.getValue());
					
				}else if(userFriendCheck.getStatus() == EfamilyConstant.USER_FRIEND_STATUS_DELETE){
					//***   已删除
					log.info("用户好友管理，已删除的好友关系，再次添加。用户   userId = "+userId+" , friend = "+customerId+" ");
					user.setGmtModified(new Date());
					user.setSource(EfamilyConstant.USER_FRIEND_SOURCE_ACTIVE);
					user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_APPLY);
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
					
					friend.setGmtModified(new Date());
					friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_PASSIVE);
					friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_INVITE);
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
					
				}else if(userFriendCheck.getStatus() == EfamilyConstant.USER_FRIEND_STATUS_INVITE){
					//***   已被对方申请  (此时应该是点击接受，但是如果此时点击申请，也会将两人建为好友)
					log.info("用户   userId = "+userId+" 已经被好友  friend = "+customerId+" 申请中,则直接把两人建为好友");
					user.setGmtModified(new Date());
					user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
					
					friend.setGmtModified(new Date());
					friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
					
				}else{
					//***   其他未考虑到的情况  操作失败
					log.error("用户好友管理出现未定义操作，操作失败。 userId = "+userId+" ; customerId = "+customerId+" ; status = ");
					throw new BizException(StatusBizCode.FAILED.getValue());
				}
			}else{
				//*** 初次添加
				log.info("用户好友管理操作，初次添加。 userId = "+userId+" ; customerId = "+customerId);
				user.setGmtCreated(new Date());
				user.setGmtModified(new Date());
				user.setSource(EfamilyConstant.USER_FRIEND_SOURCE_ACTIVE);
				user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_APPLY);
				save(ctx,user);
				
				friend.setGmtCreated(new Date());
				friend.setGmtModified(new Date());
				friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_PASSIVE);
				friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_INVITE);
				EjlUserFriend friendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
				if(friendCheck!=null){
					friend.setId(friendCheck.getId());
				}
				save(ctx,friend);
			}
		}else if(EfamilyConstant.USER_FRIEND_ACCEPT == oprType){
			//*** 接收好友请求
			log.info("用户好友管理操作，接收好友请求。 userId = "+userId+" ; customerId = "+customerId);

			//*** 同意时，该申请已经过期，需要重新申请或邀请
			Date updateTime = user.getUpdateTime()!=null?user.getUpdateTime():user.getCreateTime();
			if(DateUtils.calcHoursBetween(updateTime, new Date())>EfamilyConstant.APPLY_FRIEND_TIME_INTERVAL_APPLY){
				log.error("加为好友同意时，该申请已经过期，需要重新申请或邀请，操作失败。  userId = "+userId);
	        	throw new BizException(StatusBizCode.FAMILY_USER_REPEAT_APPLY.getValue());
			}
			
			user.setGmtModified(new Date());
			user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			user.setSource(EfamilyConstant.USER_FRIEND_SOURCE_PASSIVE);
			ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
			
			friend.setGmtModified(new Date());
			friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_ACTIVE);
			ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
			
		}else if(EfamilyConstant.USER_FRIEND_DELETE == oprType){
			//*** 删除好友
			log.info("用户好友管理操作，删除好友。 userId = "+userId+" ; customerId = "+customerId);
			user.setGmtModified(new Date());
			user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_DELETE);
			ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
			
			friend.setGmtModified(new Date());
			friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_DELETE);
			ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
			
		}else{
			log.error("管理用户好友时，操作类型未定义。oprType = "+oprType);
        	throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		}
	}
	
	
	/**
	 * 
	* Title: systemAddUserFriend
	* Description:
	* @param userId
	* @param addressBook
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlUserFriendService#systemAddUserFriend(java.lang.Long, com.winterframework.efamily.server.dto.PhoneAddressBookStruc[])
	 */
	public List<EjlUser> systemAddUserFriend(Context ctx,Long userId,PhoneAddressBookStruc[] addressBook) throws Exception{
		List<EjlUser> userFriendList = new ArrayList<EjlUser>();
		if(addressBook == null || addressBook.length ==0){
			log.info("上传通讯录操作用户好友时，通讯录为空，操作失败。addressBook = "+addressBook);
			return null;
		}
		Map<String,String> mapPram = new HashMap<String,String>();
		List<String> phoneList = new ArrayList<String>();
		for(int i=0;i<addressBook.length;i++){
			String phone = addressBook[i].getPhoneNumber();
			if(StringUtils.isNotBlank(phone)){
				phone = StringHelper.getPhone(phone);
				phoneList.add(phone);
				mapPram.put(phone, addressBook[i].getName());
			}
			if((i+1)%100==0){
				userFriendList.addAll(addUserFriendByPhone(ctx,userId,phoneList,mapPram));
				phoneList.clear();
				mapPram.clear();
			}
		}
		if(phoneList.size()>0){
			userFriendList.addAll(addUserFriendByPhone(ctx,userId,phoneList,mapPram));
			phoneList.clear();
		}
		return userFriendList ;
	}

	/**
	 * 
	* @Title: addUserFriendByPhone 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param userId
	* @param phones
	* @throws Exception
	 */
	public List<EjlUser> addUserFriendByPhone(Context ctx,Long userId,List<String> phones,Map<String,String> mapPram) throws Exception{
		//*** 查询系统用户 
		List<EjlUser> userFriendList = new ArrayList<EjlUser>(); 
		List<EjlUser> userList = ejlUserDaoImpl.getUserByAddressBookPhone(phones);
		if(userList==null ||userList.size()==0){
			log.info("通讯录中的此批好友，不是系统用户： phones = "+phones.toString());
			return userFriendList;
		}
		
		EjlUser userOperate = ejlUserDaoImpl.getById(userId);
		//*** 添加查询到的用户
		for(EjlUser userFriend:userList){
			if(userOperate.getFamilyId() != null && userFriend.getFamilyId()!=null && userOperate.getFamilyId().longValue() == userFriend.getFamilyId().longValue()){
				log.info("同一个家庭的用户不能成为好友 。 userId = "+userOperate.getId()+" ; friendUserId = "+userFriend.getId()+" ; familyId = "+userFriend.getFamilyId());
				continue;
			}
			if(userOperate.getId() != null && userOperate.getId().longValue() == userFriend.getId().longValue()){
				log.info("同一个用户不能与自己成为好友 。 userId = "+userOperate.getId()+" ; friendUserId = "+userFriend.getId());
				continue;
			}
			userFriendList.add(userFriend);
			String friendNickName = mapPram.get(userFriend.getPhone());
			EjlUserFriend user = new EjlUserFriend();
			EjlUserFriend friend = new EjlUserFriend();
			user.setUserId(userId);
			user.setFriendId(userFriend.getId());
			friend.setUserId(userFriend.getId());
			friend.setFriendId(userId);
			
			EjlUserFriend userCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(user); 
			EjlUserFriend friendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend); 
			if(userCheck != null || friendCheck!=null ){
				log.info("用户   userId = "+userId+" 已经被好友  friend = "+userFriend.getId()+" 添加过,则直接把两人建为好友 ; userCheck = "+userCheck.toString()+" ; friendCheck = "+friendCheck.toString()+" ; ");
				//if( EfamilyConstant.USER_FRIEND_STATUS_SUCCESS != userCheck.getStatus()){
					user.setGmtModified(new Date());
					user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
					user.setRemarkName(StringUtils.isNotBlank(friendNickName)?friendNickName:"");
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(user);
				//}
				
				//if(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS  != friendCheck.getStatus()){
					friend.setGmtModified(new Date());
					friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
					//friend.setRemarkName(StringUtils.isNotBlank(userOperate.getNickName())?userOperate.getNickName():String.valueOf(userOperate.getId()));
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
				//}
			    continue;
			}
			
			user.setGmtCreated(new Date());
			user.setGmtModified(new Date());
			user.setSource(EfamilyConstant.USER_FRIEND_SOURCE_SYSTEM);
			user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			user.setRemarkName(StringUtils.isNotBlank(friendNickName)?friendNickName:"");
			save(ctx,user);
			
			friend.setGmtCreated(new Date());
			friend.setGmtModified(new Date());
			friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_SYSTEM);
			friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			//friend.setRemarkName(userOperate.getNickName());

			save(ctx,friend);
			
		}
		return userFriendList;
	}

	/**
	* Title: updateUserFriend
	* Description:
	* @param entity
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlUserFriendService#updateUserFriend(com.winterframework.efamily.entity.EjlUserFriend) 
	*/
	@Override
	public void updateUserFriend(Context ctx,Long userId,Long friendId,String remarkName) throws Exception {
		
		EjlUserFriend user = new EjlUserFriend();
		user.setUserId(userId);
		user.setFriendId(friendId);
		EjlUserFriend userFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(user); 
			
		if(userFriendCheck !=null){
			user = userFriendCheck;
		}else{
			user.setStatus(EfamilyConstant.USER_FRIEND_STATUS_FAMILY);
			user.setSource(EfamilyConstant.USER_FRIEND_SOURCE_FAMILY_REMARK);
		}	
		user.setRemarkName(remarkName);
		save(ctx,user);
	}

}
