package com.winterframework.efamily.service.impl;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlFamilyDao;
import com.winterframework.efamily.dao.IEjlFamilyUserDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.dto.FamilyAttentionUserStruc;
import com.winterframework.efamily.dto.GetFamilyAttentionResponse;
import com.winterframework.efamily.dto.ManageJoinFamilyCheck;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlFamilyUser.Position;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.utils.BizNumberUtils;
import com.winterframework.efamily.utils.StringHelper;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlFamilyServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlFamilyServiceImpl extends BaseServiceImpl<IEjlFamilyDao,EjlFamily> implements IEjlFamilyService {
	
	private static final Logger logger = LoggerFactory.getLogger(EjlFamilyServiceImpl.class);
	
	@PropertyConfig(value="family")
	private String family;
	
	@Resource(name="ejlFamilyDaoImpl")
	private IEjlFamilyDao ejlFamilyDao;
	@Resource(name="ejlFamilyUserDaoImpl")
	private IEjlFamilyUserDao ejlFamilyUserDao;
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name = "bizNumberUtils")
	private BizNumberUtils bizNumberUtils;
	
	
	@Override
	protected IEjlFamilyDao getEntityDao() { 
		return ejlFamilyDao;
	}
	@Override
	public CreateFamilyResponse createFamily(Context ctx,Long userId,String familyName) throws BizException {
		
		//*** 创建家庭
		EjlUser user = ejlUserServiceImpl.get(userId);
		EjlFamily ejlFamily = new EjlFamily();
		String familyCode = bizNumberUtils.getNumber(BizNumberUtils.Type.EFAMILY_NUMBER);
		familyName = (StringUtils.isBlank(familyName)?user.getNickName()+family:familyName);
		ejlFamily.setName(familyName);
		ejlFamily.setCreator(userId);
		ejlFamily.setGmtCreated(new Date());
		ejlFamily.setCode(familyCode);
		save(ctx,ejlFamily);  

		//*** 创建家庭用户关系
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setFamilyId(ejlFamily.getId());
		ejlFamilyUser.setStatus(0L);
		ejlFamilyUser.setType(1L);
		ejlFamilyUser.setRoleId(1L);
		ejlFamilyUser.setCreator(userId);
		ejlFamilyUser.setManageType(EfamilyConstant.MANAGE_TYPE_AGREE);
		ejlFamilyUser.setPosition(Position.HOST.getValue());
		ejlFamilyUser.setIsForbitSpeak(2);
		
		//*** 确认加入家庭状态和创建者ID是否要添加
		ejlFamilyUserServiceImpl.save(ctx,ejlFamilyUser);
		
		//*** 更新用户中的家庭字段
		user = ejlUserServiceImpl.get(userId);
		user.setFamilyId(ejlFamily.getId());
		ejlUserServiceImpl.save(ctx,user);
		
		CreateFamilyResponse response=new CreateFamilyResponse();
		response.setFamilyId(ejlFamily.getId());
		response.setName(familyName);
		response.setFamilyCode(ejlFamily.getCode());
		return response;
	}
	
	@Override
	public Long saveOrUpdateFamily(Context ctx,EjlFamily ejlFamily,Long userId) throws BizException{
        Long familyId = ejlFamily.getId();
		if(familyId == null){
			CreateFamilyResponse createFamilyResponse =  createFamily(ctx,userId,ejlFamily.getName());
		    familyId = createFamilyResponse.getFamilyId();
		}else{
			save(ctx,ejlFamily);
		}
		return familyId;
	}
	
	
	public ManageJoinFamilyCheck checkManageFamily(Long userId, String applyUseridX, String familyId,String familyCode,String manageType, Long isPhoneNumber) throws BizException {
		ManageJoinFamilyCheck manageJoinFamilyCheck = null;
		//*** 检查参数 ***
		if(EfamilyConstant.USER_SYSTEM != isPhoneNumber.longValue() && EfamilyConstant.USER_PHONE != isPhoneNumber.longValue()){
			logger.info("isPhoneNumber未定义，操作失败。userId = "+userId+" ; familyCode = "+familyCode);
		    throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		}
		//*** 判断家庭是否合法  ***
		EjlFamily ejlFamily = getEjlFamilyByCodeOrFamilyId(manageType,familyCode,familyId);
		if(ejlFamily == null){
			logger.info("家庭不存在，操作失败。userId = "+userId+" ; familyCode = "+familyCode);
			throw new BizException(StatusBizCode.FAMILY_UN_VALID.getValue());
		}
		//*** 获取 applyUserId 值  ***
		Long applyUserid = null;
		if(EfamilyConstant.USER_PHONE == isPhoneNumber){
			//*** 判断此手机号码是否为系统用户
			applyUseridX = StringHelper.getPhone(applyUseridX);
			EjlUser userPhone = ejlUserServiceImpl.getUserByPhone(applyUseridX);
			if(userPhone != null){
				applyUserid = userPhone.getId();
			}else{
				logger.info("此手机号码不是系统用户 只发送短信 进行提醒 : ----- userId = "+userId+" =====>>>>  applyUserid = "+applyUseridX);
				throw new BizException(StatusBizCode.USER_UN_VALID.getValue());
			}
		}else{
			applyUserid = Long.valueOf(applyUseridX);
		}
		//*** 判断用户是否在家庭中 ***		
		EjlUser userCheck = ejlUserServiceImpl.get(applyUserid);
		if(userCheck.getFamilyId()!=null && userCheck.getFamilyId().longValue()==ejlFamily.getId().longValue()){
			logger.info("用户已经在家庭中  userId = "+userId+" =====>>>>  applyUserid = "+applyUseridX+" : familyId = "+userCheck.getFamilyId());
			throw new BizException(StatusBizCode.FAMILY_USER_ALREADY_IN_FAMILY.getValue());
		}
		//***** 如果是邀请或者同意操作 必须是家主  *********
		//*** 判断当前操作人是否家庭群主
		if( (EfamilyConstant.MANAGE_TYPE_AGREE == Long.valueOf(manageType) || EfamilyConstant.MANAGE_TYPE_INVITE == Long.valueOf(manageType))
			 &&	
			 !ejlFamilyUserServiceImpl.checkIsFamilyHost(userId, Long.valueOf(familyId))
		  ){
			logger.error("用户不是家庭群主，不能邀请或同意家庭成员，操作失败。 manageType = "+manageType+" ; userId = "+userId);
			throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
		   }
		
		manageJoinFamilyCheck = new ManageJoinFamilyCheck();
		manageJoinFamilyCheck.setApplyUserId(applyUserid);		
		manageJoinFamilyCheck.setOldFamilyId(userCheck.getFamilyId());
		manageJoinFamilyCheck.setOptFamilyId(ejlFamily.getId());
		
      	return manageJoinFamilyCheck;
	}
	
	
	@Override
	public ManageJoinFamilyResponse manageFamily(Context ctx,Long userId, Long applyUserid, Long familyId,String manageType) throws BizException {
		ManageJoinFamilyResponse manageJoinFamilyResponse = new ManageJoinFamilyResponse();
				
		//------- 管理用户加入家庭操作  
		manageJoinFamilyResponse = ejlFamilyUserServiceImpl.manageFamily(ctx,applyUserid, familyId, Long.valueOf(manageType), userId);
      	
      	return manageJoinFamilyResponse;
	}
	
	public void notifyForManageJoinFamily(Long optfamilyId,Long oldFamilyId,Long userId, Long applyUserid,String familyCode,String manageType)throws BizException{
	        //------ 1 申请同意,2 拒绝,3 申请,4 申请加入临时家庭,并创建临时家庭,5 邀请,6  退出 ,7 邀请同意
		    EjlFamily ejlFamily = get(optfamilyId);
			EjlUser applyUser = ejlUserServiceImpl.get(applyUserid);
	      	Map<String, String> paramMap = new HashMap<String,String>();
	      	paramMap.put("userId", String.valueOf(applyUserid));
	      	paramMap.put("userName", applyUser.getNickName());
	      	paramMap.put("icon", applyUser.getHeadImg());
	      	paramMap.put("familyId", String.valueOf(ejlFamily.getId()));
	      	paramMap.put("familyName", ejlFamily.getName());
	      	paramMap.put("type", manageType);
	      	paramMap.put("sex", applyUser.getSex()==null?"1":String.valueOf(applyUser.getSex()));
	      	paramMap.put("familyCode", familyCode);
	      	paramMap.put("phone", applyUser.getPhone());
	      	
	      	EjlUser operateUser = ejlUserServiceImpl.get(userId);
	      	paramMap.put("operateUserId", String.valueOf(operateUser.getId()));
	      	paramMap.put("operateUserName", operateUser.getNickName());
	      	paramMap.put("operateIcon", operateUser.getHeadImg());
	      
	      	NoticeType noticeType = null;
	      	if(EfamilyConstant.MANAGE_TYPE_AGREE == Long.valueOf(manageType)
	      			||EfamilyConstant.MANAGE_TYPE_INVITE_AGREE == Long.valueOf(manageType)){
	      		paramMap.put("phone", applyUser.getPhone());
	      		noticeType = NoticeType.MANAGE_FAMILY_USER_AGREE;
	      	}else if(EfamilyConstant.MANAGE_TYPE_APPLY == Long.valueOf(manageType)
	      			||EfamilyConstant.MANAGE_TYPE_INVITE == Long.valueOf(manageType)){
	      		noticeType = NoticeType.MANAGE_FAMILY_USER;
	      	} 
	      	//**** 需要通知家庭中的成员有新成员加入
	      	ejlUserServiceImpl.notifyForManageJoinFamily(paramMap, Long.valueOf(applyUserid), ejlFamily.getId(), noticeType,Long.valueOf(manageType),userId);
	}
	
	
	@Override
	public EjlFamily getEjlFamilyByCodeOrFamilyId(String manageType,String code,String familyId){
		EjlFamily ejlFamily = new EjlFamily();
		if(StringUtils.isBlank(code) && StringUtils.isBlank(familyId)){
			log.info("根据 familyCode 或者 familyId 获取家庭时，参数为空.");
			return null;
		}
		if(EfamilyConstant.MANAGE_TYPE_APPLY == Long.valueOf(manageType)||EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY == Long.valueOf(manageType)){
			ejlFamily.setCode(code);
		}else{
			ejlFamily.setId(Long.valueOf(familyId));
		}
		ejlFamily = ejlFamilyDao.getFamilyByParm(ejlFamily);
		return ejlFamily;
	}
	
	
	public GetFamilyAttentionResponse getFamilyAttentionDetail(Long userId,Long familyId) throws BizException{
 		GetFamilyAttentionResponse getFamilyAttentionResponse = new GetFamilyAttentionResponse();
 		EjlFamily family = ejlFamilyDao.getById(familyId);
 		List<FamilyAttentionUserStruc> userList = ejlUserDaoImpl.getDeviceAndAppUserByFamilyId(userId,familyId);
		List<FamilyAttentionUserStruc> attentionList =  ejlUserDaoImpl.getDeviceAttentionUserByFamilyId(userId,familyId);
		ObjectMapper mapper=new ObjectMapper();
		try {
			String familyUserList = "";
			String attentionUserList = "";
			if(userList != null){
				familyUserList = mapper.writeValueAsString(userList);
			}
			if(attentionList!=null){
				attentionUserList = mapper.writeValueAsString(attentionList);
			}
			getFamilyAttentionResponse.setFamilyUserList(familyUserList);
			getFamilyAttentionResponse.setAttentionUserList(attentionUserList);
		} catch (JsonProcessingException e) {
			log.error("查询家庭及关注者时出现异常：message = "+e.getMessage());
			e.printStackTrace();
		}
		getFamilyAttentionResponse.setFamilyName(family.getName());
	    return getFamilyAttentionResponse;
	}
	
}


