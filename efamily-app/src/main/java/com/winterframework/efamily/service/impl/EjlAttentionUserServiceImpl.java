package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlAttentionUserDao;
import com.winterframework.efamily.dto.AttentionDeviceStruc;
import com.winterframework.efamily.dto.AttentionUserStruc;
import com.winterframework.efamily.entity.EjlAttentionUser;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;

@Service("ejlAttentionUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlAttentionUserServiceImpl extends BaseServiceImpl<IEjlAttentionUserDao,EjlAttentionUser> implements IEjlAttentionUserService {
	private static final Logger logger = LoggerFactory.getLogger(EjlAttentionUserServiceImpl.class);
	@Resource(name="ejlAttentionUserDaoImpl")
	private IEjlAttentionUserDao ejlAttentionUserDaoImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;

	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;

	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Override
	protected IEjlAttentionUserDao getEntityDao() { 
		return ejlAttentionUserDaoImpl;
	}
	
	public int updateAttentionUserBy(Context ctx,Long userId,Long familyId,Integer isForbitSpeak) throws BizException{
		return ejlAttentionUserDaoImpl.updateAttentionUserBy(ctx, userId, familyId, isForbitSpeak);
	}
	
	public List<AttentionUserStruc> getAttentionUserByUserIdAndFamilyId(Long userId,Long familyId) throws BizException{
		return ejlAttentionUserDaoImpl.getAttentionUserByUserIdAndFamilyId( userId, familyId);
	}
	
	public boolean checkAttentionIsForbitSpeak(Long userId,Long familyId) throws BizException{
        boolean flag = false;
		List<EjlAttentionUser> attentionUserStrucList = ejlAttentionUserDaoImpl.getAttentionByUserIdAndFamilyId( userId, familyId);
		if(attentionUserStrucList!=null && attentionUserStrucList.size()>0 ){
			EjlAttentionUser ejlAttentionUser = attentionUserStrucList.get(0);
			logger.error("--------ejlAttentionUser:"+ejlAttentionUser);
			Integer isForbitSpeak = ejlAttentionUser.getIsForbitSpeak();
			logger.error("--------isForbitSpeak:"+isForbitSpeak);
			if(isForbitSpeak!=null && isForbitSpeak.intValue()==1){
				flag = true;
			}  
		}
		return flag ;
	}
	
	public List<EjlAttentionUser> getAttentionByUserIdAndFamilyId(Long userId,Long familyId) throws BizException{
		return ejlAttentionUserDaoImpl.getAttentionByUserIdAndFamilyId( userId, familyId);
	}
	public void attentionDevice(Context ctx,Long userId,Long oprType,String deviceCode,Long oprUserId) throws BizException{
		
		    Long attentionId = ejlUserDeviceServiceImpl.getDeviceUseingUserIdByCode(deviceCode);
			EjlAttentionUser ejlAttentionUser = new EjlAttentionUser();
			ejlAttentionUser.setAttentionId(attentionId);
			ejlAttentionUser.setUserId(userId);
			EjlAttentionUser ejlAttentionUserCheck = getEntityDao().selectOneObjByAttribute(ejlAttentionUser);
			if(ejlAttentionUserCheck !=null){
				ejlAttentionUser.setId(ejlAttentionUserCheck.getId());
			}
			if(oprType == EfamilyConstant.ATTENTION_OPRTYPE_APPLY){
				//*** 申请关注 
				ejlAttentionUser.setIsForbitSpeak(EfamilyConstant.ATTENTION_FORBIT_SPEAK_NO);
				ejlAttentionUser.setStatus(EfamilyConstant.ATTENTION_DEVICE_APPLY);
				ejlAttentionUser.setApplyTime(new Date());
				save(ctx, ejlAttentionUser);
			}else if(oprType == EfamilyConstant.ATTENTION_OPRTYPE_AGREE){
				//*** 同意关注 
				ejlAttentionUser.setIsForbitSpeak(EfamilyConstant.ATTENTION_FORBIT_SPEAK_NO);
				ejlAttentionUser.setStatus(EfamilyConstant.ATTENTION_DEVICE_YES);
				ejlAttentionUser.setApplyTime(new Date());
				save(ctx, ejlAttentionUser);
				
			}else if(oprType == EfamilyConstant.ATTENTION_OPRTYPE_CALCEL || oprType == EfamilyConstant.ATTENTION_OPRTYPE_BE_CALCELED){
				//*** 取消关注 
				ejlAttentionUser.setIsForbitSpeak(EfamilyConstant.ATTENTION_FORBIT_SPEAK_NO);
				ejlAttentionUser.setStatus(EfamilyConstant.ATTENTION_DEVICE_CANCEL);
				ejlAttentionUser.setApplyTime(new Date());
				if(ejlAttentionUserCheck !=null){
					save(ctx, ejlAttentionUser);
				}
				
				
			}else{
				log.error("关注类型为定义，关注失败：oprType = "+oprType+" ; deviceCode = "+deviceCode+" ; oprUserId = "+oprUserId);
				throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
			}
	}
	
	public int updateForCancelAttentionUser(Context ctx,Long attentionId, Long status) throws BizException {
		return ejlAttentionUserDaoImpl.updateForCancelAttentionUser(ctx, attentionId, status);
	}
	
	
	public boolean checkAttentionDevice(Context ctx,Long userId,Long oprType,String deviceCode,Long oprUserId) throws BizException{
		boolean flag = true;
		EjlDevice deviceObj = new EjlDevice();
		deviceObj.setCode(deviceCode);
		deviceObj = ejlDeviceServiceImpl.selectOneObjByAttribute(ctx, deviceObj);
		
		EjlUser user = ejlUserServiceImpl.get(userId);
		EjlUser attention = ejlUserServiceImpl.get(deviceObj.getUserId());
		if(user.getFamilyId()!=null && user.getFamilyId().longValue()==attention.getFamilyId().longValue()){
			log.error("设备已经在家庭中  不需要关注：attentionId = "+deviceObj.getUserId()+" ; oprType = "+oprType+" ; deviceCode = "+deviceCode);
			throw new BizException(StatusBizCode.USER_ATTENTION_IN_FAMILY.getValue());
		}
		
		EjlAttentionUser ejlAttentionUser = new EjlAttentionUser();
		ejlAttentionUser.setAttentionId(deviceObj.getUserId());
		ejlAttentionUser.setUserId(userId);
		EjlAttentionUser ejlAttentionUserCheck = ejlAttentionUserDaoImpl.selectOneObjByAttribute(ejlAttentionUser);
		if(ejlAttentionUserCheck!=null ){
			Date updateTime = ejlAttentionUserCheck.getUpdateTime()!=null?ejlAttentionUserCheck.getUpdateTime():ejlAttentionUserCheck.getCreateTime();
			if(oprType == EfamilyConstant.ATTENTION_OPRTYPE_APPLY){
				    //*** 申请关注 
					if(ejlAttentionUserCheck.getStatus().intValue()==EfamilyConstant.ATTENTION_DEVICE_YES){
						log.error("用户已经同意关注,再次申请失败：attentionId = "+deviceObj.getUserId()+" ; oprType = "+oprType+" ; deviceCode = "+deviceCode);
						throw new BizException(StatusBizCode.USER_ATTENTION_YES.getValue());
					}else if(ejlAttentionUserCheck.getStatus().intValue()==EfamilyConstant.ATTENTION_OPRTYPE_APPLY && 
							DateUtils.calcHoursBetween(updateTime, new Date())<EfamilyConstant.APPLY_ATTENTION_TIME_INTERVAL_APPLY){
						log.error("用户在有效期内，重复申请：attentionId = "+deviceObj.getUserId()+" ; oprType = "+oprType+" ; deviceCode = "+deviceCode);
						//throw new BizException(StatusBizCode.USER_ATTENTION_REPEAT_APPLY.getValue());
					}
				
			}else if(oprType == EfamilyConstant.ATTENTION_OPRTYPE_AGREE){
				    //*** 判断设备是否还在当前家庭中
				    EjlUser deviceUser = ejlUserServiceImpl.get(deviceObj.getUserId());
				    EjlUser oprUserIdUser = ejlUserServiceImpl.get(oprUserId);
				    if(oprUserIdUser.getFamilyId()==null || oprUserIdUser.getFamilyId().longValue() != deviceUser.getFamilyId().longValue()){
						 log.error("操作的设备和操作者不在同一个家庭，操作失败。 deviceCode = "+ deviceCode +" ; oprUserId = "+oprUserId);
						 throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
				    }
				    //*** 同意关注  操作 同意的这个人必须是家主
					if(!ejlFamilyUserServiceImpl.checkCurUserIsHost(oprUserId)){
						 log.error("用户不是家庭群主，操作失败。 deviceCode = "+ deviceCode +" ; oprUserId = "+oprUserId);
						 throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
					}
					if(ejlAttentionUserCheck.getStatus().intValue()==EfamilyConstant.ATTENTION_OPRTYPE_AGREE){
						log.error("用户已经同意关注,再次同意失败：attentionId = "+deviceObj.getUserId()+" ; oprType = "+oprType+" ; deviceCode = "+deviceCode);
						throw new BizException(StatusBizCode.USER_ATTENTION_YES.getValue());
					}else if(DateUtils.calcHoursBetween(updateTime, new Date())>EfamilyConstant.APPLY_ATTENTION_TIME_INTERVAL_APPLY){
						log.error("用户申请关注已经过期，同意失败：attentionId = "+deviceObj.getUserId()+" ; oprType = "+oprType+" ; deviceCode = "+deviceCode);
						throw new BizException(StatusBizCode.USER_ATTENTION_PAST_DUE.getValue());
					}
			}
		
		}
		return flag;
	}
	
	public List<AttentionUserStruc> getAttentionUser(Long userId) throws BizException {
		return getEntityDao().getAttentionUser(userId);
	}
	public List<AttentionDeviceStruc> getAttentionDevice(Long userId) throws BizException {
		return getEntityDao().getAttentionDevice(userId);
	}
	
}
