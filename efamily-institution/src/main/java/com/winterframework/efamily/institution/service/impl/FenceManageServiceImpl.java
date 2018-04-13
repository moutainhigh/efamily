package com.winterframework.efamily.institution.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.entity.EjlUserFence;
import com.winterframework.efamily.entity.OrgFence;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.institution.dto.FenceUserIdAndNameInfo;
import com.winterframework.efamily.institution.dto.UserFenceBaseInfo;
import com.winterframework.efamily.institution.dto.UserFenceNameAndIdInfo;
import com.winterframework.efamily.institution.service.IFenceManageService;
import com.winterframework.efamily.institution.web.controller.FenceManageController;
import com.winterframework.efamily.service.IEjlComUserBarrierService;
import com.winterframework.efamily.service.IEjlComUserFenceService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IOrgComFenceService;
import com.winterframework.efamily.service.IOrgComUserService;

@Service("fenceManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FenceManageServiceImpl implements IFenceManageService{

	@Resource(name="orgComUserServiceImpl")
	private IOrgComUserService orgComUserServiceImpl;

	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserServiceImpl;

	@Resource(name="ejlComUserBarrierServiceImpl")
	private IEjlComUserBarrierService ejlComUserBarrierServiceImpl;

	@Resource(name="ejlComUserFenceServiceImpl")
	private IEjlComUserFenceService ejlComUserFenceServiceImpl;
	
	@Resource(name="orgComFenceServiceImpl")
	private IOrgComFenceService orgComFenceServiceImpl;	
	
	private Logger log = LoggerFactory.getLogger(FenceManageServiceImpl.class);
	
	public List<UserFenceNameAndIdInfo> getUserFenceNameAndIdList(Long orgId) throws BizException{
		 List<UserFenceNameAndIdInfo> userFenceNameAndIdList = new ArrayList<UserFenceNameAndIdInfo>();
		 OrgFence  orgFence = new  OrgFence();
		 orgFence.setOrgId(orgId);
		 orgFence.setStatus(1);
		 List<OrgFence> orgFenceList = orgComFenceServiceImpl.selectListObjByAttribute(new Context(), orgFence);
		 if(orgFenceList!=null){
			 for(OrgFence orgFenceTemp :orgFenceList ){
				 UserFenceNameAndIdInfo userFenceNameAndIdInfo = new UserFenceNameAndIdInfo();
				 userFenceNameAndIdInfo.setFenceId(orgFenceTemp.getId());
				 userFenceNameAndIdInfo.setName(orgFenceTemp.getName());

				 userFenceNameAndIdInfo.setAddress(orgFenceTemp.getAddress());
				 userFenceNameAndIdInfo.setName(orgFenceTemp.getName());
				 userFenceNameAndIdInfo.setOrgId(orgId);
				 userFenceNameAndIdInfo.setRadius(orgFenceTemp.getRadius());
				 userFenceNameAndIdInfo.setType(orgFenceTemp.getType());
				 userFenceNameAndIdInfo.setCreateTime(DateUtils.format(orgFenceTemp.getCreateTime(),DateUtils.DATE_FORMAT_PATTERN));
				 userFenceNameAndIdInfo.setLocation(orgFenceTemp.getLocation());

				 userFenceNameAndIdList.add(userFenceNameAndIdInfo);
			 }
		 }
		 return userFenceNameAndIdList;
	}
	
	public UserFenceBaseInfo  getUserFenceBaseInfo(Long fenceId) throws BizException{
		UserFenceBaseInfo userFenceBaseInfo= new UserFenceBaseInfo();

		OrgFence  orgFence = orgComFenceServiceImpl.get(fenceId);
		userFenceBaseInfo.setFenceId(fenceId);
		userFenceBaseInfo.setAddress(orgFence.getAddress());
		userFenceBaseInfo.setLocation(orgFence.getLocation());
		userFenceBaseInfo.setName(orgFence.getName());
		userFenceBaseInfo.setOrgId(orgFence.getOrgId());
		userFenceBaseInfo.setRadius(orgFence.getRadius());
		
		return userFenceBaseInfo;
	}
	
	public List<FenceUserIdAndNameInfo> getUserIdAndNameInFence(Long fenceId) throws BizException{
		List<FenceUserIdAndNameInfo> fenceUserIdAndNameInfoList = new ArrayList<FenceUserIdAndNameInfo>();
		List<EjlUser> userList = ejlComUserServiceImpl.getUserByfenceId(fenceId);
		if(userList!=null){
			for(EjlUser ejlUser : userList){
				OrgUser orgUser = orgComUserServiceImpl.getOrgUserByUserId(ejlUser.getId());
				FenceUserIdAndNameInfo fenceUserIdAndNameInfo = new FenceUserIdAndNameInfo();
				fenceUserIdAndNameInfo.setUserId(ejlUser.getId());
				fenceUserIdAndNameInfo.setName(orgUser.getName());
				fenceUserIdAndNameInfoList.add(fenceUserIdAndNameInfo);
			}
		}
		return fenceUserIdAndNameInfoList;
	}
	
	public List<FenceUserIdAndNameInfo> getUserIdAndNameAllBindInfoList(Long orgId) throws BizException{
		List<FenceUserIdAndNameInfo> fenceUserIdAndNameInfoList = new ArrayList<FenceUserIdAndNameInfo>();
		
		List<EjlUserFence> ejlUserFenceList = ejlComUserFenceServiceImpl.getAllUserFenceInFence(orgId);
		OrgUser user = new OrgUser();
		user.setOrgId(orgId);
        user.setStatus(OrgUser.Status.BIND.getValue());
		List<OrgUser> orgUserList = orgComUserServiceImpl.selectListObjByAttribute(new Context(), user);
		if(orgUserList!=null){
			for(OrgUser userTemp : orgUserList){
				if(isAlreadyInFence(ejlUserFenceList, userTemp.getUserId())){
					continue;
				}
				FenceUserIdAndNameInfo fenceUserIdAndNameInfo = new FenceUserIdAndNameInfo();
				fenceUserIdAndNameInfo.setUserId(userTemp.getUserId());
				fenceUserIdAndNameInfo.setName(userTemp.getName());
				fenceUserIdAndNameInfoList.add(fenceUserIdAndNameInfo);
			}
		}
		return fenceUserIdAndNameInfoList;
	}
	
	public boolean isAlreadyInFence(List<EjlUserFence> ejlUserFenceList,Long userId){
		boolean flag = false;
		if(ejlUserFenceList!=null && ejlUserFenceList.size()>0){
			for(EjlUserFence ejlUserFence:ejlUserFenceList){
				if(ejlUserFence.getUserId()!=null && ejlUserFence.getUserId().longValue() == userId.longValue()){
					flag = true;
					log.error("用户已经在围栏中  ：userId = "+userId+" ; ejlUserFenceList = "+ejlUserFenceList+" ; ");
					break;
				}
			}
		}
		
		return flag ;
	}
	
	public OrgFence saveUserFenceBaseInfo(Context ctx,UserFenceBaseInfo userFenceBaseInfo) throws BizException{
		OrgFence orgFence = new OrgFence();
		Long fenceId = userFenceBaseInfo.getFenceId();
		if(fenceId!=null){
			orgFence = orgComFenceServiceImpl.get(fenceId);
		}
		if(orgFence.getOrgId()==null){
			orgFence.setOrgId(userFenceBaseInfo.getOrgId());
		}
		orgFence.setAddress(userFenceBaseInfo.getAddress());
		orgFence.setLocation(getProcessLocation(userFenceBaseInfo.getLocation()));
		orgFence.setRadius(userFenceBaseInfo.getRadius());
		orgFence.setName(userFenceBaseInfo.getName());
		orgFence.setStatus(1);
		orgComFenceServiceImpl.save(ctx, orgFence);
		
		return orgFence;
	}
	
	public String getProcessLocation(String location){
		try{
			if(StringUtils.isNotBlank(location) && location.split(",").length>1){
				String[] locationArr = location.split(",");
				location = (locationArr[0].length()>12?locationArr[0].substring(0, 12):locationArr[0])+","+(locationArr[1].length()>12?locationArr[1].substring(0, 12):locationArr[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("截取定位字段时出现异常: location = "+location+" ; ",e.getMessage());
		}
		
		return location;
	}
	
	public void deleteFence(Context ctx,Long fenceId) throws BizException{
		
		orgComFenceServiceImpl.deleteOrgFence(ctx, fenceId);
		
		ejlComUserFenceServiceImpl.deleteByfenceId(fenceId, ctx.getUserId());
		
		ejlComUserBarrierServiceImpl.deleteByFenceId(fenceId, ctx.getUserId());
		
	}
	
	public boolean isExistFence(String userIds,Long orgId){
		boolean flag = false;
		if(StringUtils.isBlank(userIds) || orgId==null ){
			return false;
		}
		List<EjlUserFence> ejlUserFenceList = ejlComUserFenceServiceImpl.getAllUserFenceInFence(orgId);
		String[] userIdsArr = userIds.split(",");
		for(int i=0;i<userIdsArr.length;i++){
			Long userIdTemp = Long.valueOf(userIdsArr[i]);
			if(isAlreadyInFence(ejlUserFenceList, userIdTemp)){
				flag = true;
				break;
			}
		}
		return flag ;
	}
	
	
	public void addUserToFence(Context ctx,Long fenceId,String userIds) throws NumberFormatException, BizException{
		
		OrgFence orgFence = orgComFenceServiceImpl.get(fenceId);
		
		//*** 判断人员中 是否存在已经在围栏中的人员
		if(isExistFence(userIds, orgFence.getOrgId())){
			throw new BizException("添加的人员已经存在已经在围栏中的人员");
		}
		
		String[] userIdsArr = userIds.split(",");
		for(int i=0;i<userIdsArr.length;i++){
			Long userIdTemp = Long.valueOf(userIdsArr[i]);
			Long barrierId = null;
            EjlUserBarrier userBarrier = null;
			EjlUserFence ejlUserFence = ejlComUserFenceServiceImpl.getEjlUserFenceBy(fenceId, userIdTemp);
			if(ejlUserFence==null){
				ejlUserFence = new EjlUserFence();
			}else{
				barrierId = ejlUserFence.getBarrierId();
				if(barrierId != null){
					userBarrier = ejlComUserBarrierServiceImpl.get(barrierId);
				}
			}
			if(userBarrier == null){
				userBarrier = new EjlUserBarrier(); 
			}
			
			userBarrier.setUserId(userIdTemp);
			userBarrier.setLocation(orgFence.getLocation());
			userBarrier.setRadius(orgFence.getRadius());
			userBarrier.setStatus(1);
			userBarrier.setOrgTag(1);
			userBarrier.setType(orgFence.getType());
			userBarrier.setRemark(orgFence.getName());
			ejlComUserBarrierServiceImpl.save(ctx, userBarrier);
			
			ejlUserFence.setBarrierId(userBarrier.getId());
			ejlUserFence.setFenceId(fenceId);
			ejlUserFence.setUserId(userIdTemp);
			ejlUserFence.setStatus(1);
			ejlComUserFenceServiceImpl.save(ctx, ejlUserFence);
		}
	}
	
	public void subUserFromFence(Context ctx,Long fenceId,String userIds) throws BizException{
		String[] userIdsStrArr = userIds.split(",");
		for(String userIdStr: userIdsStrArr){
            Long userIdTemp = Long.valueOf(userIdStr);
            EjlUserFence ejlUserFence = ejlComUserFenceServiceImpl.getEjlUserFenceBy(fenceId, userIdTemp);
            if(ejlUserFence !=null){
            	ejlUserFence.setStatus(0);
            	ejlComUserFenceServiceImpl.save(ctx, ejlUserFence);
            	
                EjlUserBarrier userBarrier = ejlComUserBarrierServiceImpl.get(ejlUserFence.getBarrierId());
                if(userBarrier !=null ){
                	userBarrier.setStatus(0);
                	ejlComUserBarrierServiceImpl.save(ctx, userBarrier);
                }
            }
		}
		
	}
	
	public void saveFenceIdAndUserId(Context ctx,OrgFence orgFence,String userIds) throws BizException{
		//*** 判断人员中 是否存在已经在围栏中的人员
		if(isExistFence(userIds, orgFence.getOrgId())){
			throw new BizException("添加的人员已经存在已经在围栏中的人员");
		}
		String[] userIdsArr = userIds.split(",");
		for(String userId : userIdsArr){
			EjlUserBarrier userBarrier = new EjlUserBarrier();
			userBarrier.setLocation(orgFence.getLocation());
			userBarrier.setRadius(orgFence.getRadius());
			userBarrier.setStatus(1);
			userBarrier.setOrgTag(1);
			userBarrier.setUserId(Long.valueOf(userId));
			userBarrier.setRemark(orgFence.getName());
			ejlComUserBarrierServiceImpl.save(ctx, userBarrier);
			
			EjlUserFence ejlUserFence = new EjlUserFence();
			ejlUserFence.setBarrierId(userBarrier.getId());
			ejlUserFence.setUserId(Long.valueOf(userId));
			ejlUserFence.setFenceId(orgFence.getId());
			ejlUserFence.setStatus(1);
			ejlComUserFenceServiceImpl.save(ctx, ejlUserFence);
		}
	}
	
	public void deleteFenceIdAndUserId(Context ctx,Long fenceId) throws BizException{
		ejlComUserFenceServiceImpl.deleteByfenceId(fenceId,ctx.getUserId());
	}
	
}