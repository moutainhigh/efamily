package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.utils.StringUtils;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlFamilyUserDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserFamilyMemberDao;
import com.winterframework.efamily.dao.IEjlUserFriendDao;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.dto.UserDeviceInfo;
import com.winterframework.efamily.dto.UserStruc;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlFamilyUser.Position;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserFamilyMember;
import com.winterframework.efamily.entity.EjlUserFriend;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserFamilyMemberService;
import com.winterframework.efamily.service.IEjlUserFriendService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlFamilyUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlFamilyUserServiceImpl extends EjlComFamilyUserServiceImpl implements IEjlFamilyUserService {
	
	
	private static final Logger log = LoggerFactory.getLogger(EjlFamilyUserServiceImpl.class); 
	
	@PropertyConfig(value="family")
	private String family;
	
	@Resource(name = "ejlFamilyUserDaoImpl")
	private IEjlFamilyUserDao ejlFamilyUserDao;

	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name = "ejlUserFriendDaoImpl")
	private IEjlUserFriendDao ejlUserFriendDaoImpl;
	
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;

	@Resource(name="ejlUserFamilyMemberDaoImpl")
	private IEjlUserFamilyMemberDao ejlUserFamilyMemberDaoImpl;
	
	@Resource(name="ejlUserFriendServiceImpl")
	private IEjlUserFriendService ejlUserFriendServiceImpl;
	
	@Resource(name="ejlUserFamilyMemberServiceImpl")
	private IEjlUserFamilyMemberService ejlUserFamilyMemberServiceImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;

	@Resource(name="ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;
	
	@Resource(name="efComOrgDeviceServiceImpl")
	private IEfComOrgDeviceService efComOrgDeviceServiceImpl;

	 
	public boolean checkCurUserIsHost(Long userId)  throws BizException {
		 boolean flag = false;
		 EjlUser user = ejlUserDaoImpl.getById(userId);
		 if(user.getFamilyId()!=null){
			 EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
			 ejlFamilyUser.setUserId(userId);
			 ejlFamilyUser.setFamilyId(user.getFamilyId());
			 ejlFamilyUser.setPosition(Position.HOST.getValue());
			 ejlFamilyUser.setStatus(0L);
			 ejlFamilyUser =  ejlFamilyUserDao.getHostByfamilyIdAndPosition(ejlFamilyUser);
			 if(ejlFamilyUser!=null){
                 flag = true;
			 }else{
				 log.error("用户不是家庭群主，操作失败。 familyId = "+user.getFamilyId()+" ; userId = "+userId);
				 throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
			 }
		 }else{
			    log.error("用户不存在家庭中，操作失败。 familyId = "+user.getFamilyId()+" ; userId = "+userId);
				throw new BizException(StatusBizCode.USER_NO_FAMILY.getValue());
		 }
		return flag;
	}
	
	
	 public Long getHostByCurUserId(Long userId)  throws BizException {
		 Long hostId = null;
		 if(userId==null){
			 return null;
		 }
		 EjlUser user = ejlUserDaoImpl.getById(userId);
		 if(user.getFamilyId()!=null){
			 hostId = getHostByFamilyId(user.getFamilyId());
		 }
		 return hostId;
	 }
	
	 public Long getHostByFamilyId(Long familyId)  throws BizException {
		 Long hostId = null;
		 if(familyId==null){
			 return null;
		 }

		 EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		 ejlFamilyUser.setFamilyId(familyId);
		 ejlFamilyUser.setPosition(Position.HOST.getValue());
		 ejlFamilyUser =  ejlFamilyUserDao.getHostByfamilyIdAndPosition(ejlFamilyUser);
		 if(ejlFamilyUser!=null){
			 hostId = ejlFamilyUser.getUserId();
		 }

		 return hostId;
	 }
	
	@Override
	public List<UserStruc> getFamilyUserList(Long userId,Long familyId,Long userType) throws BizException {
		//***userType 1 手机用户（此状态暂时未用到） 2 手表用户（不包含3无手表用户）  3 手表用户无手表（僵尸手表用户） 9 全部用户包含3 僵尸用户（僵尸用户的type返回为3）
		EjlUser user = new EjlUser();
		user.setFamilyId(familyId);
		user.setId(userId);
		user.setType(userType);
		List<UserStruc> list= ejlUserDaoImpl.getEjlFamilyListByFamilyIdAndType(user);
		if(list==null||list.isEmpty()){
			list = new ArrayList<UserStruc>(); 
			log.info("获取家庭用户列表，家庭成员数为空。 familyId = "+familyId+" ; userType = "+userType );
		}

		return list;
	}

	public List<UserDeviceInfo> getDeviceList(Long userId) throws BizException {
		EjlUser user = new EjlUser();
		user.setId(userId);
		user = ejlUserDaoImpl.getById(userId);
		List<UserDeviceInfo> list= new ArrayList<UserDeviceInfo>(); 
		if(user.getFamilyId()!=null){
			List<UserDeviceInfo> listDeviceFamily = ejlUserDaoImpl.getDeviceList(user);
			if(listDeviceFamily!=null){
				list.addAll(listDeviceFamily); 
			}else{
				log.info("获取家庭设备列表为空。  userId = "+userId );
			}
		}
		
		List<UserDeviceInfo> attentionList= ejlUserDaoImpl.getAttentionDeviceList(user);
		if(attentionList!=null){
			list.addAll(attentionList);
		}else{
			log.debug("当前用户未关注设备：  userId = "+userId );
		}
		for(UserDeviceInfo uinfo:list){
			EfOrgDevice entity = new EfOrgDevice();
			entity.setImei(uinfo.getDeviceCode());
			entity.setStatus(1);
			Context ctx = new Context();
			ctx.set("userId", -1);
			EfOrgDevice efd = efComOrgDeviceServiceImpl.selectOneObjByAttribute(ctx, entity);
			if(efd != null){
				uinfo.setOrgTag(1);
			}else{
				uinfo.setOrgTag(0);
			}
			if(StringUtils.isNotEmpty(uinfo.getDeviceId())){
				UserDeviceInfo deviceModule=ejlUserDaoImpl.getModeleTypeByDeviceId(Long.valueOf(uinfo.getDeviceId()));
				if(deviceModule!=null){
					uinfo.setModuleType(deviceModule.getCustomerId()+"_"+deviceModule.getGlevel());
				}
			}
			
			if(uinfo.getBattery()==null){
				uinfo.setBattery("100");
			}
		}
		return list;
	}
	@Override
	public Long quitFamily(Context ctx,Long userId, Long familyId,Integer isAttentionOldFamilyDevice) throws BizException {
		Long familyIdTemp = familyId;
		
		EjlUser ejlUser = ejlUserDaoImpl.getById(userId);
		// 先查找之前家庭的成员
		EjlUser userQuery = new EjlUser();
		userQuery.setFamilyId(familyId);
		userQuery.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> oldFamilyUser = ejlUserDaoImpl.getEjlUserByAttribute(userQuery);
		
		//*** 退出家庭时 需要把之前的家庭APP成员 加为好友
		createFriendRelationShip(ctx,ejlUser, oldFamilyUser);
		
		//*** 把之前家庭中的设备  默认变为关注的设备
		if(EfamilyConstant.ATTENTION_OLD_FAMILY_DEVICE_YES == isAttentionOldFamilyDevice){
			//***** 查找之前家庭的所有设备
			List<UserDeviceInfo> list = getUserDeviceInfoList(userId);
			//***** 关注之前家庭的所有设备
			if(list!=null){
				for(UserDeviceInfo userDeviceInfo:list){
				    ejlAttentionUserServiceImpl.attentionDevice(ctx, userId, EfamilyConstant.ATTENTION_OPRTYPE_AGREE, userDeviceInfo.getDeviceCode(), userId);
				}
			}
		}
		
		//*** 解除家庭关系 
		severFamilyRelation( ctx, userId,  familyId);
		
		return familyIdTemp;
	}
	
	public List<UserDeviceInfo> getUserDeviceInfoList(Long userId) throws BizException{
		EjlUser user = new EjlUser();
		user.setId(userId);
		user = ejlUserDaoImpl.getById(userId);
		return ejlUserDaoImpl.getDeviceList(user);
	}
	
	public void severFamilyRelation(Context ctx,Long userId, Long familyId)throws BizException{
		//*** 解除家庭关系
		EjlUser entity = ejlUserServiceImpl.get(userId); 
		entity.setFamilyId(null);
		ejlUserServiceImpl.save(ctx,entity);
		
		ejlFamilyUserDao.quitFamily(userId, familyId);
		EjlUserFamilyMember ejlUserFamilyMember = new EjlUserFamilyMember();
		ejlUserFamilyMember.setUserId(userId);
		ejlUserFamilyMember.setFamilyId(familyId);
		ejlUserFamilyMember.setStatus(1L);
		ejlUserFamilyMemberDaoImpl.updateByUserAndMemberId(ejlUserFamilyMember);
	}
	
	public void fromFamilyMemberToFriendUser(Context ctx,Long userId,String nickName,Long familyId) throws BizException{
		// 先查找之前家庭的APP成员
		EjlUser userQuery = new EjlUser();
		userQuery.setFamilyId(familyId);
		userQuery.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> oldFamilyUser = ejlUserDaoImpl.getEjlUserByAttribute(userQuery);

        if(oldFamilyUser != null && oldFamilyUser.size()>0){
			//*** 之前家庭成员转为好友关系
			for(EjlUser userTemp : oldFamilyUser){
				if(userTemp.getId().longValue() == userId.longValue()){
					continue;
				}
				EjlUserFriend userFriend = new EjlUserFriend();
				EjlUserFriend friend = new EjlUserFriend();
				friend.setUserId(userTemp.getId());
				friend.setFriendId(userId);
				
				userFriend.setUserId(userId);
				userFriend.setFriendId(userTemp.getId());
				EjlUserFriend ejlUserFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(userFriend);
				
				userFriend.setGmtCreated(new Date());
				userFriend.setGmtModified(new Date());
				userFriend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
				userFriend.setRemarkName(userTemp.getNickName() );
				
				friend.setGmtCreated(new Date());
				friend.setGmtModified(new Date());
				friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
				friend.setRemarkName(nickName);
				
				if(ejlUserFriendCheck != null){
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(userFriend);
					ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
				}else{
					userFriend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_FAMILY);
					friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_FAMILY);
					ejlUserFriendServiceImpl.save(ctx,userFriend);
					ejlUserFriendServiceImpl.save(ctx,friend);
				}
				
			}
	    }
		
		
	}
	
	public void createFriendRelationShip(Context ctx,EjlUser user,List<EjlUser> friendList) throws BizException{
		if(user==null || friendList == null){
			log.info("创建好友关系时，参数为空，创建失败。user = "+user+ " ;  friendList = "+friendList);
			return;
		}
		Long userId = user.getId();
		//*** 之前家庭成员转为好友关系
		for(EjlUser userTemp : friendList){
			if(userTemp.getId().longValue() == user.getId().longValue()){
				continue;
			}
			//手机APP用户才能成为好友，其他不允许
			if(userTemp.getType().longValue() != EfamilyConstant.USER_TYPE_APP){
				continue;
			}
			EjlUserFriend userFriend = new EjlUserFriend();
			EjlUserFriend friend = new EjlUserFriend();
			friend.setUserId(userTemp.getId());
			friend.setFriendId(userId);
			userFriend.setUserId(userId);
			userFriend.setFriendId(userTemp.getId());
			EjlUserFriend ejlUserFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(userFriend);
			EjlUserFriend ejlFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(friend);
			
			userFriend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			if(ejlUserFriendCheck != null){
				ejlUserFriendDaoImpl.updateByUserIdAndFriendId(userFriend);
			}else{
				userFriend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_FAMILY);
				ejlUserFriendServiceImpl.save(ctx,userFriend);
			}
			
			friend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_SUCCESS);
			if(ejlFriendCheck != null){
				ejlUserFriendDaoImpl.updateByUserIdAndFriendId(friend);
			}else{
				friend.setSource(EfamilyConstant.USER_FRIEND_SOURCE_FAMILY);
				ejlUserFriendServiceImpl.save(ctx,friend);
			}
			
		}
		
	}
	
	@Override
	public ManageJoinFamilyResponse  manageFamily(Context ctx,Long userId, Long familyId, Long manageType, Long operator) throws BizException {
		ManageJoinFamilyResponse manageJoinFamilyResponse = new ManageJoinFamilyResponse();
		Long familyIdResult = familyId;
		List<UnbindDeviceInfo> unbindDeviceInfoList = new ArrayList<UnbindDeviceInfo>();
		EjlFamilyUser familyUser = ejlFamilyUserDao.getFamilyUserByParm(userId, familyId);
		EjlUser user = ejlUserDaoImpl.getById(userId);
		if(user == null){
			log.error("用户加入家庭操作,申请加入家庭的用户不存在，操作失败。 manageType = "+manageType+" ; userId = "+userId);
			throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
		}
		if(EfamilyConstant.MANAGE_TYPE_AGREE == manageType || EfamilyConstant.MANAGE_TYPE_INVITE_AGREE == manageType || EfamilyConstant.MANAGE_TYPE_REFUSE == manageType){
			//*** 同意或者拒绝加入
			if(EfamilyConstant.MANAGE_TYPE_AGREE == manageType && !checkCurUserIsHost(operator)){
				 log.error("用户不是家庭群主，不能操作同意别人加入家庭，操作失败。 userId = "+ userId +" ; operator = "+operator+" ; familyId = "+familyId);
				 throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
			}
			
			if(EfamilyConstant.MANAGE_TYPE_AGREE == manageType || EfamilyConstant.MANAGE_TYPE_INVITE_AGREE == manageType){
				//*** 如果是同意加入,需要检查该用户之前有没有加入其它家庭,如有，则需清除或覆盖掉之前家庭信息 
				
				//*** 在同意之前  该用户必须是一个游客   无家庭的
				if(user.getFamilyId() != null){
					log.error("用户加入家庭操作,同意用户加入家庭之前，用户不是无家庭人员，操作失败。 manageType = "+manageType+" ; userId = "+userId);
					throw new BizException(StatusBizCode.FAMILY_NOT_JOIN_OTHER_FAMILY.getValue());
				}
				
				//*** 如果该用户已经在 家庭中 ，说明已经有人同意了，抛出已经存在的异常
				EjlFamilyUser ejlFamilyUser = ejlFamilyUserDao.getFamilyUserByParm(userId, familyIdResult);
				if(ejlFamilyUser != null && EfamilyConstant.USER_IN_FAMILY == ejlFamilyUser.getStatus().longValue()){
					log.error("用户加入家庭操作,用户已经在家庭中，操作失败。 manageType = "+manageType+" ; userId = "+userId+" ; familyId = "+familyIdResult);
					throw new BizException(StatusBizCode.FAMILY_USER_ALREADY_IN_FAMILY.getValue());
				}
				
				//*** 同意时，该申请已经过期，需要重新申请或邀请
				Date updateTime = ejlFamilyUser.getUpdateTime()!=null?ejlFamilyUser.getUpdateTime():ejlFamilyUser.getCreateTime();
				if(DateUtils.calcHoursBetween(updateTime, new Date())>EfamilyConstant.APPLY_FAMILY_TIME_INTERVAL_APPLY){
					log.error("加入家庭同意时，该申请已经过期，需要重新申请或邀请，操作失败。 manageType = "+manageType+" ; userId = "+userId);
		        	throw new BizException(StatusBizCode.FAMILY_USER_PAST_DUE.getValue());
				}
				
				//*** 获取当前家庭的所有设备
				Long userIdHost = getHostByFamilyId(familyId);
				List<UserDeviceInfo> newFamilyDeviceList = getUserDeviceInfoList(userIdHost);
				
                //*** 解除新家庭设备的关注
				if(newFamilyDeviceList!=null){
					for(UserDeviceInfo userDeviceInfo:newFamilyDeviceList){
					    ejlAttentionUserServiceImpl.attentionDevice(ctx, userId, EfamilyConstant.ATTENTION_OPRTYPE_BE_CALCELED, userDeviceInfo.getDeviceCode(), operator);
					}
				}
                
				//*** 加入新的家庭
				EjlUser entity = ejlUserServiceImpl.get(userId); 
				entity.setFamilyId(familyId);
				ejlUserServiceImpl.save(ctx,entity);
				EjlUser entityQuery = new EjlUser();
				entityQuery.setFamilyId(familyId);
				List<EjlUser> userList = ejlUserDaoImpl.getEjlUserByAttribute(entityQuery); 
				
				if(userList != null && userList.size()>0){
					for(EjlUser userTemp : userList){
						//*** 去除新家庭成员的好友关系
						EjlUserFriend ejlUserFriend = new  EjlUserFriend();
						ejlUserFriend.setUserId(userId);
						ejlUserFriend.setFriendId(userTemp.getId());
						EjlUserFriend ejlUserFriendCheck = ejlUserFriendDaoImpl.selectOneObjByAttribute(ejlUserFriend);
						if(ejlUserFriendCheck != null){
							ejlUserFriend.setStatus(EfamilyConstant.USER_FRIEND_STATUS_DELETE);
							ejlUserFriendDaoImpl.updateByUserIdAndFriendId(ejlUserFriend);
							ejlUserFriend.setUserId(userTemp.getId());
							ejlUserFriend.setFriendId(userId);
							ejlUserFriendDaoImpl.updateByUserIdAndFriendId(ejlUserFriend);
						}
					}
				}
				
			}
			familyUser.setPosition(Position.MEMBER.getValue());
			familyUser.setIsForbitSpeak(2);
			familyUser.setManageType(manageType);
			familyUser.setStatus((manageType == EfamilyConstant.MANAGE_TYPE_AGREE || EfamilyConstant.MANAGE_TYPE_INVITE_AGREE == manageType) ? Long.valueOf(EfamilyConstant.RESPONSE_STATUS_SUCCESS) : Long.valueOf(EfamilyConstant.RESPONSE_STATUS_FAIL));
			familyUser.setUpdateTime(new Date());
			familyUser.setUpdatorId(ctx.getUserId());
			ejlFamilyUserDao.updateStatusAndManageType(familyUser);
			
		}else if(EfamilyConstant.MANAGE_TYPE_APPLY == manageType || EfamilyConstant.MANAGE_TYPE_INVITE == manageType || EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY == manageType){
			long userFamilyStatus = EfamilyConstant.STATUS_FAIL ;
			long userFamilyManageType =  manageType ;
			
			//*******  申请 ： 
			if(familyUser != null ){
				
				if(EfamilyConstant.MANAGE_TYPE_APPLY == manageType || EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY == manageType){
					if(familyUser.getManageType()==EfamilyConstant.MANAGE_TYPE_APPLY || familyUser.getManageType()==EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY){
						//*** 重复申请   (时间间隔)
						if(DateUtils.calcHoursBetween(familyUser.getGmtModified(), new Date())>EfamilyConstant.APPLY_FAMILY_TIME_INTERVAL){
							familyUser.setUpdateTime(new Date());
							familyUser.setUpdatorId(ctx.getUserId());
							ejlFamilyUserDao.updateStatusAndManageType(familyUser);
						}else{
							log.error("用户短时间内，重复申请加入家庭，申请失败。 manageType = "+manageType+" ; userId = "+userId);
				        	throw new BizException(StatusBizCode.FAMILY_USER_REPEAT_APPLY.getValue());
						}
					}else if(familyUser.getManageType()==EfamilyConstant.MANAGE_TYPE_INVITE){
						//*** 申请时 有被人邀请过  (在规定时间间隔以内) 则直接加为好友 
						if(DateUtils.calcHoursBetween(familyUser.getGmtModified(), new Date())<EfamilyConstant.APPLY_FAMILY_TIME_INTERVAL){
							familyUser.setUpdateTime(new Date());
							familyUser.setUpdatorId(ctx.getUserId());
							ejlFamilyUserDao.updateStatusAndManageType(familyUser);
							return manageFamily(ctx,userId,  familyId,  EfamilyConstant.MANAGE_TYPE_AGREE,  operator);
						}
					}
				}else if(EfamilyConstant.MANAGE_TYPE_INVITE == manageType && familyUser.getManageType()==EfamilyConstant.MANAGE_TYPE_INVITE){
						//*** 重复邀请  (时间间隔)
						if(DateUtils.calcHoursBetween(familyUser.getGmtModified(), new Date()) < EfamilyConstant.APPLY_FAMILY_TIME_INTERVAL){
							log.error("短时间内，重复邀请用户加入家庭，邀请失败。 manageType = "+manageType+" ; userId = "+userId);
				        	throw new BizException(StatusBizCode.FAMILY_USER_REPEAT_APPLY.getValue());
						}
				}
			}
			
			//*** 申请加入
			EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
			ejlFamilyUser.setUserId(userId);
			ejlFamilyUser.setFamilyId(familyId);
            if(familyUser != null){
            	ejlFamilyUser.setId(familyUser.getId());  
            	ejlFamilyUser.setGmtModified(new Date());
            }else{
            	ejlFamilyUser.setCreator(operator);
            	ejlFamilyUser.setGmtCreated(new Date());
            }
			ejlFamilyUser.setStatus(userFamilyStatus);
			ejlFamilyUser.setManageType(userFamilyManageType);
			ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_APP);
			ejlFamilyUser.setRoleId(0L);
			ejlFamilyUser.setPosition(Position.MEMBER.getValue());
			ejlFamilyUser.setIsForbitSpeak(2);
			save(ctx,ejlFamilyUser);
			if(EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY == manageType){
			    //*** 创建临时家庭
				EjlUser ejlUser = ejlUserDaoImpl.getById(userId);
				if(ejlUser.getFamilyId() == null || ejlUser.getFamilyId().longValue()==0){
					String familyName = ejlUser.getNickName()+family;
					CreateFamilyResponse createFamilyResponse =  ejlFamilyServiceImpl.createFamily(ctx,userId, familyName);
					familyIdResult =  createFamilyResponse.getFamilyId();
				}else{
					familyIdResult = ejlUser.getFamilyId();
				}
			}
		}else{
			log.error("用户加入家庭操作,管理类型未定义，操作失败。 manageType = "+manageType+" ; userId = "+userId);
        	throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		}
		manageJoinFamilyResponse.setFamilyId(familyIdResult);
		manageJoinFamilyResponse.setUnbindDeviceInfo(unbindDeviceInfoList);
		return manageJoinFamilyResponse;
	}

	
	public void deleteUserFromFamily(Context ctx,String userIds,Long familyId) throws BizException{
		
		//*** 判断是否还在家庭中
		String[] userIdArr = userIds.split(",");
		for(String userIdStr:userIdArr){
			EjlUser ejlUser = ejlUserDaoImpl.getById(Long.valueOf(userIdStr));
			// 先查找之前家庭的成员
			EjlUser userQuery = new EjlUser();
			userQuery.setFamilyId(familyId);
			userQuery.setType(EfamilyConstant.USER_TYPE_APP);
			List<EjlUser> oldFamilyUser = ejlUserDaoImpl.getEjlUserByAttribute(userQuery);
			//*** 退出家庭时 需要把之前的家庭APP成员 加为好友
			createFriendRelationShip(ctx,ejlUser, oldFamilyUser);
			//*** 解除家庭关系 
			severFamilyRelation(ctx, Long.valueOf(userIdStr),  familyId);
		}
	}
	
	public boolean checkIsFamilyHost(Long userId,Long familyId) throws BizException{
		boolean flag = false ;
		EjlFamilyUser familyUser = ejlFamilyUserDao.getFamilyUserByParm(userId, familyId);
		if(familyUser!=null && familyUser.getPosition() !=null && familyUser.getPosition().intValue()==Position.HOST.getValue()){
			flag = true;
		}
		return flag ;
	}
	
	public List<UnbindDeviceInfo> unbindFamilyAllDeviceByMemberQuit(Context ctx,Long userId) throws BizException{
		List<UnbindDeviceInfo> listInfo = new ArrayList<UnbindDeviceInfo>();
		EjlUser ejlUser = ejlUserDaoImpl.getById(userId);
		// 先查找之前家庭的成员
		EjlUser userQuery = new EjlUser();
		userQuery.setFamilyId(ejlUser.getFamilyId());
		userQuery.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> oldFamilyUser = ejlUserDaoImpl.getEjlUserByAttribute(userQuery);
		//*** 如果当前退出的成员为家主   但是不是最后一个成员   则不允许退出家庭
		//该处业务逻辑有点乱  对于普通成员退出 没必要进入这个业务方法  进入该逻辑的必定是家主而且是最后一成员  需改进   
		if(oldFamilyUser!=null && oldFamilyUser.size()>1 && checkIsFamilyHost(userId, ejlUser.getFamilyId())){
			log.error("当前退出的成员为家主,但是不是最后一个成员,则不允许退出家庭： userId = "+userId+" ; familyId = "+ejlUser.getFamilyId());
			throw new BizException(StatusBizCode.FAMILY_QUIT_FAIL_FOR_HOST.getValue());
		}
		//*** 如果是最后一个APP用户退出家庭的成员  需要解绑掉该家庭中的所有设备
		if(oldFamilyUser!=null && oldFamilyUser.size()==1){
			//*** 解绑该家庭中的所有设备 
			listInfo = ejlUserDeviceServiceImpl.unbindAllDeviceInFamily(ctx,ejlUser.getFamilyId(),userId);
		}		
		return listInfo;
	}
	
	@Override
	public void deviceJoinFamily(Context ctx,Long userId, Long familyId, Long manageType,Long operator,int status,long userType) throws BizException {
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(familyId);
		EjlFamilyUser familyUser = ejlFamilyUserDao.getFamilyUserByParm(userId, familyId);
		if(familyUser != null){
	     	ejlFamilyUser.setId(familyUser.getId());  
	     	ejlFamilyUser.setGmtModified(new Date());
	    }else{
	     	ejlFamilyUser.setCreator(operator);
	     	ejlFamilyUser.setGmtCreated(new Date());
	    }
		ejlFamilyUser.setCreator(operator);
		ejlFamilyUser.setStatus(Long.valueOf(status));
		ejlFamilyUser.setType(userType);
		ejlFamilyUser.setManageType(manageType);
		ejlFamilyUser.setGmtCreated(new Date());
		ejlFamilyUser.setPosition(Position.MEMBER.getValue());
		ejlFamilyUser.setIsForbitSpeak(2);
		save(ctx,ejlFamilyUser);
	}
	
	public void zombieUserJoinFamily(Context ctx,Long userId, Long familyId,int status,long userType) throws BizException {
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(familyId);
		EjlFamilyUser familyUser = ejlFamilyUserDao.getFamilyUserByParm(userId, familyId);
	    ejlFamilyUser.setId(familyUser.getId());  
	    ejlFamilyUser.setGmtModified(new Date());
		ejlFamilyUser.setStatus(Long.valueOf(status));
		ejlFamilyUser.setType(userType);
		ejlFamilyUser.setGmtCreated(new Date());
		ejlFamilyUser.setPosition(Position.MEMBER.getValue());
		ejlFamilyUser.setIsForbitSpeak(2);
		save(ctx,ejlFamilyUser);
	}
	
	public EjlFamilyUser getEjlFamilyUserBy(Long userId,Long familyId) throws BizException{
          return ejlFamilyUserDao.getFamilyUserByParm(userId, familyId);		
	}

	@Override
	public int updateAttrByUserIdAndFamilyId(EjlFamilyUser ejlFamilyUser ) throws BizException {
		return ejlFamilyUserDao.updateAttrByUserIdAndFamilyId(ejlFamilyUser);
	}
	 
	public int updateFamilyUserType(Long userId,Long familyId,Long userType) throws BizException {
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setFamilyId(familyId);
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setType(userType);
		return updateAttrByUserIdAndFamilyId(ejlFamilyUser);
	}

	
	public void manageForbitSpeak(Context ctx,Long userId,Long chatType,Long chatRoomId,Integer oprType) throws BizException{
		
		EjlUser user = ejlUserServiceImpl.get(userId);
		//*** 判断是家人还是关注者  
		if(EfamilyConstant.CHAT_TYPE_FAMILY == chatType.longValue()){
			if(user.getFamilyId()!=null && user.getFamilyId().longValue()==chatRoomId.longValue()){
				//*** 家庭成员
				EjlFamilyUser familyUser = new EjlFamilyUser();
				familyUser.setFamilyId(chatRoomId);
				familyUser.setUserId(userId);
				familyUser.setIsForbitSpeak(oprType);
				ejlFamilyUserDao.updateAttrByUserIdAndFamilyId(familyUser);
				
			}else{
				//*** 关注成员
				ejlAttentionUserServiceImpl.updateAttentionUserBy(ctx, userId, chatRoomId, oprType);
			}
			
		}else{
			log.error("管理禁言时，类型未定义： chatType = "+chatType);
			throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		}
		
	}
	
	
	public void TransferFamilyHost(Context ctx,Long familyId,Long userId,Long oprUserId) throws BizException{
		EjlFamilyUser familyUserOpr = new EjlFamilyUser();
		familyUserOpr.setFamilyId(familyId);
		familyUserOpr.setUserId(oprUserId);
		familyUserOpr = ejlFamilyUserDao.selectOneObjByAttribute(familyUserOpr);
		
		if(familyUserOpr.getPosition().intValue()==Position.HOST.getValue()){
			EjlFamilyUser familyUser = new EjlFamilyUser();
			familyUser.setFamilyId(familyId);
			familyUser.setUserId(userId);
			familyUser = ejlFamilyUserDao.selectOneObjByAttribute(familyUser);
			if(familyUser!=null){
				familyUserOpr.setPosition(Position.MEMBER.getValue());
				save(ctx,familyUserOpr);
				familyUser.setPosition(Position.HOST.getValue());
				save(ctx,familyUser);
			}
		}else{
			log.error("管理转让家庭时，操作者不是家主： familyId = "+familyId+" ; oprUserId = "+oprUserId);
			throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
		}
	}
	
	public EjlFamily  getEjlFamilyByUserId(Long userId)throws BizException{
		EjlFamily family = null;
		EjlUser user  =  ejlUserDaoImpl.getById(userId);
		if(user!=null && user.getFamilyId()!=null){
			family = ejlFamilyServiceImpl.get(user.getFamilyId());
		}
		return family;
	}
}

