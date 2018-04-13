package com.winterframework.efamily.institution.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.entity.MemberSimpleInfoStruct;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.institution.dto.MemberInfoStruct;
import com.winterframework.efamily.institution.service.IMemberManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;
import com.winterframework.efamily.service.IOrgComUserService;


@Service("memberManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class MemberManageServiceImpl implements IMemberManageService{

	@Resource(name = "RedisClient")
	private RedisClient redisClient; 
	
	@Resource(name="orgComUserServiceImpl")
	private IOrgComUserService orgComUserServiceImpl;

	private static Integer pagesize =5 ;
	
	public Long getUserIdBy(Long orgUserId) throws BizException{
		Long userId=null;
		OrgUser orgUser = orgComUserServiceImpl.get(orgUserId);
		if(orgUser!=null){
			userId = orgUser.getUserId();
		}
		return userId;
	}
	
	public List<MemberSimpleInfoStruct>  getMemberSimpleInfoStructList(Long orgId,Integer start,String queryValue) throws BizException {
		String name = null;
		String phoneNumber = null;
		String idNumber = null;
		if(StringUtils.isNotBlank(queryValue)){
			if(DataHandlerUtil.isNumeric(queryValue)){
				if(queryValue.length()<12){
					phoneNumber = queryValue;
				}else{
					idNumber = queryValue;
				}
			}else{
				name = queryValue;
			}
		}
		return orgComUserServiceImpl.getMemberSimpleInfoStructList(orgId, start*pagesize, pagesize, name, phoneNumber, idNumber);
	}
	
	public MemberInfoStruct  getMemberInfoStruct(Long orgUserId) throws BizException {
		MemberInfoStruct memberInfoStruct = new MemberInfoStruct();
		OrgUser orgUser = orgComUserServiceImpl.get(orgUserId);
		if(orgUser!=null){
			memberInfoStruct.setOrgUserId(orgUser.getId());
			memberInfoStruct.setOrgId(orgUser.getOrgId());
			memberInfoStruct.setUserId(orgUser.getUserId());
			memberInfoStruct.setName(orgUser.getName());
			memberInfoStruct.setStatus(orgUser.getStatus());
			memberInfoStruct.setSex(orgUser.getSex());
			memberInfoStruct.setAge(orgUser.getAge());
			memberInfoStruct.setPhoneNumber(orgUser.getPhoneNumber());
			memberInfoStruct.setEmergencyContact(orgUser.getEmergencyContact());
			memberInfoStruct.setEmergencyContactPhone(orgUser.getEmergencyContactPhone());
			memberInfoStruct.setEmergencyContactRelation(orgUser.getEmergencyContactRelation());
			memberInfoStruct.setBirthday(orgUser.getBirthday());
			memberInfoStruct.setBloodType(orgUser.getBloodType());
			memberInfoStruct.setBodyStatus(orgUser.getBodyStatus());
			memberInfoStruct.setDietTaboo(orgUser.getDietTaboo());
			memberInfoStruct.setEducationDegree(orgUser.getEducationDegree());
			memberInfoStruct.setGuardianName(orgUser.getGuardianName());
			memberInfoStruct.setEmergencyContactPhone(orgUser.getEmergencyContactPhone());
			memberInfoStruct.setHeight(orgUser.getHeight());
			memberInfoStruct.setHouseholdAddress(orgUser.getHouseholdAddress());
			memberInfoStruct.setIncomeSource(orgUser.getIncomeSource());
			memberInfoStruct.setInterests(orgUser.getInterests());
			memberInfoStruct.setLiveAddress(orgUser.getLiveAddress());
			memberInfoStruct.setMaritalStatus(orgUser.getMaritalStatus());
			memberInfoStruct.setName(orgUser.getName());
			memberInfoStruct.setNations(orgUser.getNations());
			memberInfoStruct.setWorkUnit(orgUser.getWorkUnit());
			memberInfoStruct.setWeight(orgUser.getWeight());
			memberInfoStruct.setSex(orgUser.getSex());
			memberInfoStruct.setRhNegative(orgUser.getRhNegative());
			memberInfoStruct.setRemark(orgUser.getRemark());
			memberInfoStruct.setProfession(orgUser.getProfession());
			memberInfoStruct.setPhoneNumber(orgUser.getPhoneNumber());
			memberInfoStruct.setIdNumber(orgUser.getIdNumber());
		}
		return memberInfoStruct;
	}
	
	public void saveOrUpdateMemberInfo(Context ctx,MemberInfoStruct memberInfoStruct) throws BizException{
		OrgUser orgUser = new OrgUser();
		if(memberInfoStruct.getOrgUserId()!=null && memberInfoStruct.getOrgUserId()>0){
			orgUser = orgComUserServiceImpl.get(memberInfoStruct.getOrgUserId());
		}	 
		if(orgUser == null){
			new OrgUser();
		}
		orgUser.setOrgId(memberInfoStruct.getOrgId());
		orgUser.setUserId(memberInfoStruct.getUserId());
		orgUser.setName(memberInfoStruct.getName());
		orgUser.setStatus(memberInfoStruct.getStatus());
		orgUser.setSex(memberInfoStruct.getSex());
		orgUser.setAge(memberInfoStruct.getAge());
		orgUser.setPhoneNumber(memberInfoStruct.getPhoneNumber());
		orgUser.setEmergencyContact(memberInfoStruct.getEmergencyContact());
		orgUser.setEmergencyContactPhone(memberInfoStruct.getEmergencyContactPhone());
		orgUser.setEmergencyContactRelation(memberInfoStruct.getEmergencyContactRelation());
		orgUser.setBirthday(memberInfoStruct.getBirthday());
		orgUser.setBloodType(memberInfoStruct.getBloodType());
		orgUser.setBodyStatus(memberInfoStruct.getBodyStatus());
		orgUser.setDietTaboo(memberInfoStruct.getDietTaboo());
		orgUser.setEducationDegree(memberInfoStruct.getEducationDegree());
		orgUser.setGuardianName(memberInfoStruct.getGuardianName());
		orgUser.setEmergencyContactPhone(memberInfoStruct.getEmergencyContactPhone());
		orgUser.setHeight(memberInfoStruct.getHeight());
		orgUser.setHouseholdAddress(memberInfoStruct.getHouseholdAddress());
		orgUser.setIncomeSource(memberInfoStruct.getIncomeSource());
		orgUser.setInterests(memberInfoStruct.getInterests());
		orgUser.setLiveAddress(memberInfoStruct.getLiveAddress());
		orgUser.setMaritalStatus(memberInfoStruct.getMaritalStatus());
		orgUser.setName(memberInfoStruct.getName());
		orgUser.setNations(memberInfoStruct.getNations());
		orgUser.setWorkUnit(memberInfoStruct.getWorkUnit());
		orgUser.setWeight(memberInfoStruct.getWeight());
		orgUser.setSex(memberInfoStruct.getSex());
		orgUser.setRhNegative(memberInfoStruct.getRhNegative());
		orgUser.setRemark(memberInfoStruct.getRemark());
		orgUser.setProfession(memberInfoStruct.getProfession());
		orgUser.setPhoneNumber(memberInfoStruct.getPhoneNumber());
		orgUser.setIdNumber(memberInfoStruct.getIdNumber());
		
		orgComUserServiceImpl.save(ctx, orgUser);
	}
	
	public void deleteMemberInfo(Context ctx,Long orgUserId) throws BizException{

		OrgUser orgUser = new OrgUser();
		orgUser.setId(orgUserId);
		orgUser.setStatus(9);
		orgComUserServiceImpl.save(ctx, orgUser);
	}
	
}
