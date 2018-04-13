package com.winterframework.efamily.institution.service.impl;

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
import com.winterframework.efamily.dto.EmployeeLoginInfo;
import com.winterframework.efamily.dto.EmployeeManageQueryConditionInfo;
import com.winterframework.efamily.entity.AccountNumberBaseInfo;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.OrgEmployee;
import com.winterframework.efamily.entity.OrgEmployeeBaseInfo;
import com.winterframework.efamily.entity.OrgRole;
import com.winterframework.efamily.entity.UrlAuthInfo;
import com.winterframework.efamily.institution.dto.EmployeeInfoStruct;
import com.winterframework.efamily.institution.dto.EmployeeOffWorkAndLeaveInfo;
import com.winterframework.efamily.institution.service.IEmployeeManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IOrgComEmployeeAuthService;
import com.winterframework.efamily.service.IOrgComEmployeeService;
import com.winterframework.efamily.service.IOrgComRoleService;

@Service("employeeManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EmployeeManageServiceImpl implements IEmployeeManageService{

	private Logger log = LoggerFactory.getLogger(EmployeeManageServiceImpl.class);
	
	@Resource(name="orgComEmployeeServiceImpl")
	private IOrgComEmployeeService orgComEmployeeServiceImpl;
	
	@Resource(name="orgComRoleServiceImpl")
	private IOrgComRoleService orgComRoleServiceImpl;
	
	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@Resource(name="orgComEmployeeAuthServiceImpl")
	private IOrgComEmployeeAuthService orgComEmployeeAuthServiceImpl;
	
	private static Integer pagesize = 20;
	
	public List<EfOrg> getEmployeeCanOptOrgList(Long orgEmployeeId) throws BizException{
		return efComOrgServiceImpl.getEmployeeCanOptOrgList(orgEmployeeId);
	}
	
	public List<EfOrg> getEmployeeCanOptOrgByAddressList(Long orgEmployeeId,String province,String city,String county) throws BizException{
		return efComOrgServiceImpl.getEmployeeCanOptOrgByAddressList(orgEmployeeId,province,city,county);
	}
	
	public List<OrgEmployeeBaseInfo> getOrgEmployeeBaseInfoList(Long orgId,Integer start,EmployeeManageQueryConditionInfo queryConditionInfo){
		
		//**** 处理查询参数   ****************
		if(StringUtils.isNotBlank(queryConditionInfo.getQueryValue())){
			if(DataHandlerUtil.isNumeric(queryConditionInfo.getQueryValue())){
				queryConditionInfo.setPhoneNumber(queryConditionInfo.getQueryValue());
			}else{
				queryConditionInfo.setName(queryConditionInfo.getQueryValue());
			}
		}
		return orgComEmployeeServiceImpl.getOrgEmployeeBaseInfoPage(orgId, start*pagesize, pagesize, queryConditionInfo);
	}
	
	public OrgEmployee getOrgEmployee(Long orgEmployeeId) throws BizException{
		return orgComEmployeeServiceImpl.get(orgEmployeeId);
	}
	
	
	public EmployeeInfoStruct getOrgEmployeeBaseInfo(Long orgEmployeeId) throws BizException{
		EmployeeInfoStruct employeeInfoStruct = new EmployeeInfoStruct();
		
		OrgEmployee orgEmployee = orgComEmployeeServiceImpl.get(orgEmployeeId);
		if(orgEmployee!=null){
			employeeInfoStruct.setOrgEmployeeId(orgEmployeeId);
			employeeInfoStruct.setOrgId(orgEmployee.getOrgId());
			employeeInfoStruct.setRoleId(orgEmployee.getRoleId());
			employeeInfoStruct.setName(orgEmployee.getName());
			employeeInfoStruct.setPassword(orgEmployee.getPassword());
			employeeInfoStruct.setHeadImg(orgEmployee.getHeadImg());
			employeeInfoStruct.setStatus(orgEmployee.getStatus());
			employeeInfoStruct.setLoginAuth(orgEmployee.getLoginAuth());
			employeeInfoStruct.setIdNumber(orgEmployee.getIdNumber());
			employeeInfoStruct.setBirthday(orgEmployee.getBirthday());
			employeeInfoStruct.setSex(orgEmployee.getSex());
			employeeInfoStruct.setAge(orgEmployee.getAge());
			employeeInfoStruct.setHeight(orgEmployee.getHeight());
			employeeInfoStruct.setWeight(orgEmployee.getWeight());
			employeeInfoStruct.setPhoneNumber(orgEmployee.getPhoneNumber());
			employeeInfoStruct.setEmergencyContact(orgEmployee.getEmergencyContact());
			employeeInfoStruct.setEmergencyContactPhone(orgEmployee.getEmergencyContactPhone());
			employeeInfoStruct.setEmergencyContactRelation(orgEmployee.getEmergencyContactRelation());
			employeeInfoStruct.setNations(orgEmployee.getNations());
			employeeInfoStruct.setMaritalStatus(orgEmployee.getMaritalStatus());
			employeeInfoStruct.setEducationDegree(orgEmployee.getEducationDegree());
			employeeInfoStruct.setPoliticalOutlook(orgEmployee.getPoliticalOutlook());
			employeeInfoStruct.setPart(orgEmployee.getPart());
			employeeInfoStruct.setDuty(orgEmployee.getDuty());
			employeeInfoStruct.setContractStartTime(orgEmployee.getContractStartTime());
			employeeInfoStruct.setContractEndTime(orgEmployee.getContractEndTime());
			employeeInfoStruct.setContractNumber(orgEmployee.getContractNumber());
			employeeInfoStruct.setPayRange(orgEmployee.getPayRange());
			employeeInfoStruct.setHouseholdAddress(orgEmployee.getHouseholdAddress());
			employeeInfoStruct.setLiveAddress(orgEmployee.getLiveAddress());
			employeeInfoStruct.setEntryTime(orgEmployee.getEntryTime());
			employeeInfoStruct.setLeaveTime(orgEmployee.getLeaveTime());
			employeeInfoStruct.setOffWorkStartTime(orgEmployee.getOffWorkStartTime());
			employeeInfoStruct.setOffWorkEndTime(orgEmployee.getOffWorkEndTime());
			employeeInfoStruct.setCreateAuthUser(orgEmployee.getCreateAuthUser());
			employeeInfoStruct.setCreateAuthTime(orgEmployee.getCreateAuthTime());
		}
		return employeeInfoStruct;
	}
	
	public OrgEmployee transferFromQueryData(EmployeeInfoStruct employeeInfoStruct){
		OrgEmployee orgEmployee = new OrgEmployee();
		if(employeeInfoStruct != null){
			orgEmployee.setOrgId(employeeInfoStruct.getOrgId());
			orgEmployee.setRoleId(employeeInfoStruct.getRoleId());
			orgEmployee.setName(employeeInfoStruct.getName());
			orgEmployee.setPassword(employeeInfoStruct.getPassword());
			orgEmployee.setHeadImg(employeeInfoStruct.getHeadImg());
			orgEmployee.setStatus(employeeInfoStruct.getStatus());
			orgEmployee.setLoginAuth(employeeInfoStruct.getLoginAuth());
			orgEmployee.setIdNumber(employeeInfoStruct.getIdNumber());
			orgEmployee.setBirthday(employeeInfoStruct.getBirthday());
			orgEmployee.setSex(employeeInfoStruct.getSex());
			orgEmployee.setAge(employeeInfoStruct.getAge());
			orgEmployee.setHeight(employeeInfoStruct.getHeight());
			orgEmployee.setWeight(employeeInfoStruct.getWeight());
			orgEmployee.setPhoneNumber(employeeInfoStruct.getPhoneNumber());
			orgEmployee.setEmergencyContact(employeeInfoStruct.getEmergencyContact());
			orgEmployee.setEmergencyContactPhone(employeeInfoStruct.getEmergencyContactPhone());
			orgEmployee.setEmergencyContactRelation(employeeInfoStruct.getEmergencyContactRelation());
			orgEmployee.setNations(employeeInfoStruct.getNations());
			orgEmployee.setMaritalStatus(employeeInfoStruct.getMaritalStatus());
			orgEmployee.setEducationDegree(employeeInfoStruct.getEducationDegree());
			orgEmployee.setPoliticalOutlook(employeeInfoStruct.getPoliticalOutlook());
			orgEmployee.setPart(employeeInfoStruct.getPart());
			orgEmployee.setDuty(employeeInfoStruct.getDuty());
			orgEmployee.setContractStartTime(employeeInfoStruct.getContractStartTime());
			orgEmployee.setContractEndTime(employeeInfoStruct.getContractEndTime());
			orgEmployee.setContractNumber(employeeInfoStruct.getContractNumber());
			orgEmployee.setPayRange(employeeInfoStruct.getPayRange());
			orgEmployee.setHouseholdAddress(employeeInfoStruct.getHouseholdAddress());
			orgEmployee.setLiveAddress(employeeInfoStruct.getLiveAddress());
			orgEmployee.setEntryTime(employeeInfoStruct.getEntryTime());
			orgEmployee.setLeaveTime(employeeInfoStruct.getLeaveTime());
			orgEmployee.setOffWorkStartTime(employeeInfoStruct.getOffWorkStartTime());
			orgEmployee.setOffWorkEndTime(employeeInfoStruct.getOffWorkEndTime());
			orgEmployee.setCreateAuthUser(employeeInfoStruct.getCreateAuthUser());
			orgEmployee.setCreateAuthTime(employeeInfoStruct.getCreateAuthTime());
		}
		
		return orgEmployee;
	}
	
	public boolean saveOrUpdateOrgEmployee(EmployeeInfoStruct employeeInfoStruct) throws BizException{
		boolean flag = false;
		Context ctx = new Context();
		ctx.set("userId", -1);
		if(employeeInfoStruct!=null){
			OrgEmployee orgEmployee = transferFromQueryData(employeeInfoStruct);
			orgComEmployeeServiceImpl.save(ctx, orgEmployee);
			flag = true;
		}
		return flag ;
	}
	
	
	public void openAccountForEmployee(Long orgEmployeeId,Long roleId,String openOrgIds) throws BizException{
		    //***修改角色
		    orgComEmployeeServiceImpl.updateEmployeeRole(orgEmployeeId, roleId);
		    //***开通可以查看的机构
			orgComEmployeeAuthServiceImpl.openOrgForEmployee(orgEmployeeId, openOrgIds);
	}
	
	
	public boolean offWorkOrLeaveForEmployee(EmployeeOffWorkAndLeaveInfo updateInfo) throws BizException{
		boolean flag = false;
		Context ctx = new Context();
		ctx.set("userId", -1);
		if(updateInfo!=null){
			OrgEmployee orgEmployee = orgComEmployeeServiceImpl.get(updateInfo.getOrgEmployeeId());
			if(updateInfo.getOptType()==1){
				orgEmployee.setOffWorkStartTime(DateUtils.parse(updateInfo.getOffWorkStartTime()));
				orgEmployee.setOffWorkEndTime(DateUtils.parse(updateInfo.getOffWorkEndTime()));
				orgEmployee.setRemark(updateInfo.getContent());
				orgEmployee.setStatus(2);
			}else if(updateInfo.getOptType()==2){
				orgEmployee.setStatus(9);
			}else{
				return false;
			}
			orgComEmployeeServiceImpl.save(ctx, orgEmployee);
			flag = true;
		}
		return flag ;
	}
	
	
 	public List<AccountNumberBaseInfo> getAccountNumberBaseInfoPage(Long orgId,Integer start,String queryValue){
		String name=null;
		String phoneNumber=null;
		if(StringUtils.isNotBlank(queryValue)){
			if(DataHandlerUtil.isNumeric(queryValue)){
					phoneNumber = queryValue;
			}else{
				name = queryValue;
			}
		}
		return orgComEmployeeServiceImpl.getAccountNumberBaseInfoPage(orgId, start, pagesize,name,phoneNumber);
	} 
	
 	
 	public boolean updateEmployeePassword(Long employeeId,String passwordOld,String passwordNew) throws BizException{
 		boolean flag = false;
 		OrgEmployee  orgEmployee = orgComEmployeeServiceImpl.get(employeeId);
        if(orgEmployee !=null && orgEmployee.getPassword().equals(passwordOld)){
        	orgEmployee.setPassword(passwordNew);
        	flag = true;
        }
 		return flag;
 	} 
 	
 	
 	public AccountNumberBaseInfo getAccountNumberBaseInfo(Long orgEmployeeId) throws BizException{
 		AccountNumberBaseInfo accountNumberBaseInfo = null;
 		OrgEmployee orgEmployee = orgComEmployeeServiceImpl.get(orgEmployeeId);
 		if(orgEmployee != null){
 			accountNumberBaseInfo = new AccountNumberBaseInfo();
 			accountNumberBaseInfo.setCreateAuthTime(orgEmployee.getCreateAuthTime());
 			accountNumberBaseInfo.setCreateAuthUser(orgEmployee.getCreateAuthUser());
 			accountNumberBaseInfo.setName(orgEmployee.getName());
 			accountNumberBaseInfo.setOrgId(orgEmployee.getOrgId());
 			accountNumberBaseInfo.setPhoneNumber(orgEmployee.getPhoneNumber());
 			accountNumberBaseInfo.setStatus(orgEmployee.getStatus());
 			accountNumberBaseInfo.setRoleName(getRoleNameBy(orgEmployee.getRoleId()));
			List<UrlAuthInfo> urlAuthInfoList = orgComRoleServiceImpl.getUrlAuthInfoList(orgEmployee.getRoleId());
			if(urlAuthInfoList != null){
				accountNumberBaseInfo.setUrlAuthInfoList(urlAuthInfoList);
			}
 		}
 		return accountNumberBaseInfo;
 	}
 	
 	
	public List<OrgRole> getOrgRoleList() throws BizException{
		OrgRole orgRole = new OrgRole();
		orgRole.setStatus(1);
		return orgComRoleServiceImpl.selectListObjByAttribute(new Context(), orgRole);
	}

   public String getRoleNameBy(Long roleId) throws BizException{
	   String roleName = "";
	   OrgRole orgRole = orgComRoleServiceImpl.get(roleId);
	   if(orgRole != null){
		   roleName = orgRole.getName();
	   }
	   return roleName;
   }
   
   public OrgEmployee getEmployeeByPhoneNumber(String phoneNumber) throws BizException{
	   OrgEmployee orgEmployee = new OrgEmployee();
	   orgEmployee.setPhoneNumber(phoneNumber);
	   orgEmployee = orgComEmployeeServiceImpl.selectOneObjByAttribute(new Context(), orgEmployee);
	   
	   return orgEmployee;
   }
   
   
   public EmployeeLoginInfo employeeLogin(String phoneNumber,String password) throws BizException{
	   EmployeeLoginInfo employeeLoginInfo = new EmployeeLoginInfo();
	   OrgEmployee orgEmployee = getEmployeeByPhoneNumber( phoneNumber);
	   if(orgEmployee == null){
		   log.error("登录时，帐号不存在。 phoneNumber = "+phoneNumber);
		   throw new BizException();
	   }else if(!password.equals(orgEmployee.getPassword())){
		   log.error("登录时，密码错误。  phoneNumber = "+phoneNumber +"  ;; password = "+password);
		   throw new BizException();
	   }
	   employeeLoginInfo.setLastRequestTime(DateUtils.getCurTime());
	   employeeLoginInfo.setLoginTime(DateUtils.getCurTime());
	   employeeLoginInfo.setOrgId(orgEmployee.getOrgId());
	   employeeLoginInfo.setOrgName("orgName");
	   employeeLoginInfo.setOrgEmployeeId(orgEmployee.getId());
	   employeeLoginInfo.setOrgEmployeeName(orgEmployee.getName());
	   
	   return employeeLoginInfo;
   }
	
}
