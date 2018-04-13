package com.winterframework.efamily.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComOrgDao;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.dto.LoginRequest;
import com.winterframework.efamily.dto.LoginResponse;
import com.winterframework.efamily.dto.ScanWatchResponse;
import com.winterframework.efamily.dto.UserInfoReq;
import com.winterframework.efamily.dto.UserNotice;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.IEfUserHealthSettingService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserDeviceService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IInstitutionUserService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.thirdparty.sms.IBaseSmsService;
import com.winterframework.efamily.thirdparty.sms.ISmsService;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("institutionUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class InstitutionUserServiceImpl extends BaseServiceImpl<IEfComOrgDeviceDao,EfOrgDevice> implements IInstitutionUserService {
 
	@Resource(name="efComOrgDeviceDaoImpl")
	private IEfComOrgDeviceDao efComOrgDeviceDaoImpl;
	
	@Resource(name="efComOrgDaoImpl")
	private IEfComOrgDao efComOrgDaoImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@Resource(name="ejlComUserDeviceServiceImpl")
	private IEjlComUserDeviceService ejlComUserDeviceService;
	
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserService;
	
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserService;
	
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	
	@Resource(name="efKeyServiceImpl")
	private IEfKeyService efKeyService;
	
	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;
	
	
	@Resource(name="notificationUtil")
	private NotificationUtil notificationUtil; 
	
	@Resource(name="smsServiceImpl")
	private ISmsService smsService;
	
	@Resource(name = "kangdooSmsServiceImpl")
	private IBaseSmsService kangdooSmsService;
	
	
	@PropertyConfig(value="sms.message.template_join")
	private String messageTemplate;
	
	@Resource(name="efUserHealthSettingServiceImpl")
	private IEfUserHealthSettingService efUserHealthSettingService;
	
	@Override
	protected IEfComOrgDeviceDao getEntityDao() { 
		return efComOrgDeviceDaoImpl;
	}
	
	public boolean isImeiContainOrg(String imei,String ikey) throws BizException{
		Qrcode qrcode = qrcodeServiceImpl.getByImei(imei);
		if(qrcode == null){
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
		int number = qrcode.getCustomerNumber();
		Long customerId = null;
		try{
		 customerId = efComCustomerService.getEfCustomerBy(number).getId();
		}catch(Exception e){
			throw new BizException(StatusBizCode.DEVICE_CUSTOMER_NO_EXIST.getValue());
		}
		
		Context ctx = new Context();
		ctx.set("userId", -1);
		
		EfOrg org = new EfOrg();
		org.setIkey(ikey);
		org.setStatus(1);
		org = efComOrgServiceImpl.selectOneObjByAttribute(ctx, org);
		if(org == null||org.getId()==null){
			throw new BizException(StatusBizCode.ORG_KEY_INVALID.getValue());
		}
		EfKey efKey = new EfKey();
		efKey.setCustomerId(customerId);
		efKey.setUkey(org.getUkey());
		return efKeyService.selectOneObjByAttribute(ctx, efKey) != null;
		
	}
	@Override
	public void institutionUserManage(Context ctx,List<UserInfoReq> list,String key)
			throws BizException {
		for(UserInfoReq userInfoReq:list){
			if(!isImeiContainOrg(userInfoReq.getImei(),key)){
				log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+userInfoReq.getImei());
				throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
			}
			
			//删除用户，直接解绑用户设备
			if(userInfoReq.getOperType()==0){
				ejlUserDeviceServiceImpl.unBindDevice(ctx, userInfoReq.getImei(),key);
			}
			//保存或者修改
			if(userInfoReq.getOperType()==1){
				String phone = userInfoReq.getGuardianPhone();
				//监护人
				EjlUser user = ejlUserServiceImpl.getUserByPhone(phone);
				//无监护人注册一个监护人
				if(user == null){
					LoginRequest loginRequest = new LoginRequest();
					loginRequest.setOprType(Integer.valueOf(EfamilyConstant.LOGIN_ORG_REGISTER));
					loginRequest.setPassword("123456");
					loginRequest.setPhoneNumber(phone);
					LoginResponse loginResponse = ejlUserServiceImpl.login(ctx, loginRequest);
					user = ejlUserServiceImpl.get(loginResponse.getUserId());
				}
				//监护人是不是app用户，找到家主
				if(EfamilyConstant.USER_TYPE_APP != user.getType()){
					user = ejlUserServiceImpl.get(ejlFamilyUserServiceImpl.getHostByCurUserId(user.getId()));
				}
				//用户绑定设备
				ScanWatchResponse scw = ejlUserDeviceServiceImpl.bindOrgDevice(ctx, user.getId(), userInfoReq.getImei(), userInfoReq.getDevicePhone(), userInfoReq.getName());
				EjlUser deviceUser = null;
				if(scw != null){
					//绑定确认
					ejlComUserDeviceService.bindDeviceForConfirm(ctx, scw.getDeviceUserId(), scw.getDeviceId());
					//推送给所有家庭成员
					try{
						pushBind(ctx,scw.getDeviceUserId(),YesNo.YES.getValue(),userInfoReq.getName());
						String content=StringUtils.replace(StringUtils.replace(messageTemplate, "{1}", userInfoReq.getName()), "{2}", user.getPasswd());
						kangdooSmsService.send(user.getPhone(), content);
						//smsService.send(user.getPhone(), content);
						log.info("send sms for org bind device imei:"+userInfoReq.getImei() +" send phone:"+user.getPhone());
					}catch(Exception e){
						log.error("绑定确认通知发送失败",e);
					}
					deviceUser = ejlUserServiceImpl.get(scw.getDeviceUserId());
				}else{
					deviceUser = ejlUserDeviceServiceImpl.getDeviceUser(ctx, userInfoReq.getImei());
				}
				//更新用户信息
				updateUserInfo(deviceUser,userInfoReq);
			}
			//更新设备机构关系
			this.saveOrUpdate(userInfoReq, key);
		}
	}

	public void pushBind(Context ctx, Long userId, int status,String deviceUserName) throws BizException {
		//推送所有家庭成员
		EjlFamilyUser userFamily=ejlComFamilyUserService.getEffectiveByUserId(userId);
		if(null==userFamily){
			throw new BizException(StatusBizCode.DEVICE_USER_NO_FAMILY.getValue());
		}
		//String deviceUserName=null==ctx.get("nickName")?null:(String)ctx.get("nickName");
		Long familyId=userFamily.getFamilyId();
		List<EjlFamilyUser> familyUserList= ejlComFamilyUserService.getEffectiveByFamilyId(familyId);
		if(null!=familyUserList){
			for(EjlFamilyUser familyUser:familyUserList){
				EjlUser user=ejlComUserService.get(familyUser.getUserId()); 
				try {
					NotyTarget t=new NotyTarget(familyUser.getUserId(),null);  
					Map<String,String> data=new HashMap<String,String>(); 
					String noticeType=(YesNo.YES.getValue()==status?UserNotice.NoticeType.BIND_ACCEPT.getValue():UserNotice.NoticeType.BIND_REFUSED.getValue())+"";
					data.put("noticeType", noticeType); 
					data.put("deviceUserName", deviceUserName);
					data.put("userName", user.getNickName());
					data.put("oprTime", DateUtils.getCurTime()+""); 
					
					NotyMessage message=new NotyMessage();
					message.setId(null);
					message.setType(NotyMessage.Type.NOTICE);
					message.setCommand(EfamilyConstant.PUSH);
					message.setData(data);
					Notification notification=new Notification();
					notification.setTarget(t);
					notification.setMessage(message);
					 
					notificationUtil.notification(notification);
				} catch (Exception e) {
					log.error("push error:",e);
				}
			}
		}
	}
	
	private void updateUserInfo(EjlUser deviceUser,UserInfoReq userInfoReq)throws BizException{
		/*private String imei;
		private String devicePhone;
		private String name;
		private Integer sex;
		private Double weight;
		private Integer height;
		private Integer age;*/
		deviceUser.setPhone(userInfoReq.getDevicePhone());
		deviceUser.setUserName(userInfoReq.getName());
		deviceUser.setSex(Long.valueOf(userInfoReq.getSex()-1));
		deviceUser.setWeight(userInfoReq.getWeight());
		deviceUser.setHeight(Long.valueOf(userInfoReq.getHeight()));
		deviceUser.setAge(Long.valueOf(userInfoReq.getAge()));
		Context ctx = new Context();
		ctx.set("userId", -1);
		ejlUserServiceImpl.save(ctx, deviceUser);
		
		EfUserHealthSetting userHealthSett=efUserHealthSettingService.getByUserId(deviceUser.getId());
		if(null==userHealthSett){
			userHealthSett = new EfUserHealthSetting();
		}
	    userHealthSett.setHeight(Integer.valueOf(userInfoReq.getHeight()+""));
		userHealthSett.setWeight(userInfoReq.getWeight());
		userHealthSett.setAge(userInfoReq.getAge());
		efUserHealthSettingService.save(ctx, userHealthSett);
		
	}
	
	private  void saveOrUpdate(UserInfoReq userInfoReq,String key) throws BizException{
		EfOrg entity = new EfOrg();
		entity.setIkey(key);
		entity.setStatus(1);
		EfOrg efOrg = efComOrgDaoImpl.selectOneObjByAttribute(entity);
		if(efOrg != null){
			EfOrgDevice efOrgDevice = new EfOrgDevice();
			efOrgDevice.setOrgId(efOrg.getId());
			efOrgDevice.setImei(userInfoReq.getImei());
			efOrgDevice.setStatus(userInfoReq.getOperType());
			efOrgDevice.setPhone(userInfoReq.getGuardianPhone());
			efOrgDevice.setCreatorId(-1l);
			efOrgDevice.setCreateTime(DateUtils.currentDate());
			efOrgDevice.setUpdatorId(-1l);
			efOrgDevice.setUpdateTime(DateUtils.currentDate());
			efOrgDevice.setAge(Long.valueOf(userInfoReq.getAge()));
			efOrgDevice.setDevicePhone(userInfoReq.getDevicePhone());
			efOrgDevice.setGuardianRelation(userInfoReq.getGuardianRelation());
			efOrgDevice.setHeight(Long.valueOf(userInfoReq.getHeight()));
			efOrgDevice.setName(userInfoReq.getName());
			efOrgDevice.setSex(Long.valueOf(userInfoReq.getSex()-1));
			efOrgDevice.setWeight(userInfoReq.getWeight());
			try {
				efComOrgDeviceDaoImpl.saveOrUpdate(efOrgDevice);
			}catch(BizException e){
				throw e;
			}catch (Exception e) {
				log.error("保持机构设备错误 key="+key,e);
				throw new BizException(StatusBizCode.SAVE_FAILED.getValue());
			}
		}else{
			log.error("机构未注册key="+key);
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
	}
}
