package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfComOrgDeviceDao;
import com.winterframework.efamily.dao.IEjlDeviceDao;
import com.winterframework.efamily.dao.IEjlFamilyUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.dto.NotificationBind;
import com.winterframework.efamily.dto.ScanWatchResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EfPlatformDeviceSetting;
import com.winterframework.efamily.entity.EfPlatformDeviceVersion;
import com.winterframework.efamily.entity.EfPlatformHealthSetting;
import com.winterframework.efamily.entity.EfUserHealthSetting;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.AgeLevel;
import com.winterframework.efamily.enums.DeviceType;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.IEfPlatformDeviceSettingService;
import com.winterframework.efamily.service.IEfPlatformDeviceVersionService;
import com.winterframework.efamily.service.IEfPlatformHealthSettingService;
import com.winterframework.efamily.service.IEfUserHealthSettingService;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.service.ISoftwareVersionService;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlUserDeviceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlUserDeviceServiceImpl extends BaseServiceImpl<IEjlUserDeviceDao,EjlUserDevice> implements IEjlUserDeviceService {

	
	@PropertyConfig(value="family")
	private String family;
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
	
	@Resource(name="ejlDeviceDaoImpl")
	private IEjlDeviceDao ejlDeviceDaoImpl;
	
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@Resource(name="ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name="qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl; 
	
	@Resource(name = "ejlFamilyUserDaoImpl")
	private IEjlFamilyUserDao ejlFamilyUserDaoImpl;

	@Resource(name = "ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;
	
	@Resource(name="efKeyServiceImpl")
	private IEfKeyService efKeyService;
	
	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerService;
	
	@Resource(name="efComOrgDeviceDaoImpl")
	private IEfComOrgDeviceDao efComOrgDeviceDaoImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	
	
	@PropertyConfig("server.url")
	private String serverUrl;
	@PropertyConfig("server.removeToken")
	private String urlPath;
	@Resource(name="httpClientImpl")
	private IHttpClient httpClient;
	

	@Resource(name="efPlatformDeviceSettingServiceImpl")
	private IEfPlatformDeviceSettingService efPlatformDeviceSettingService;
	@Resource(name="efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	
	@Resource(name="efPlatformHealthSettingServiceImpl")
	private IEfPlatformHealthSettingService efPlatformHealthSettingService;
	@Resource(name="efUserHealthSettingServiceImpl")
	private IEfUserHealthSettingService efUserHealthSettingService;
	@Resource(name="efPlatformDeviceVersionServiceImpl")
	private IEfPlatformDeviceVersionService efPlatformDeviceVersionService;
	@Resource(name="softwareVersionServiceImpl")
	private ISoftwareVersionService softwareVersionService;
	

	@Override
	protected IEjlUserDeviceDao getEntityDao() {
		return ejlUserDeviceDaoImpl;
	}

	@Override
	public Long saveUserDevice(Context ctx,Long userId, Long deviceId,Long status) throws BizException {
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setUserId(userId);
		ejlUserDevice.setDeviceId(deviceId);
		EjlUserDevice ejlUserDeviceData = ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
		if(ejlUserDeviceData == null){
			ejlUserDevice.setGmtCreated(new Date());
		}else{
			ejlUserDevice.setId(ejlUserDeviceData.getId());
		}
		ejlUserDevice.setGmtModified(new Date());
		ejlUserDevice.setStatus(status);
		ejlUserDevice.setOptBindUserId(ctx.getUserId());
		save(ctx,ejlUserDevice);
		return ejlUserDevice.getId();
	}
	
	@Override
	public Long saveEjlDevice(Context ctx,Long userId,Long familyId,String deviceCode,String phoneNumber) throws BizException{
		EjlUser user = ejlUserServiceImpl.get(userId);
		String deviceName = StringUtils.isNotEmpty(user.getNickName())?user.getNickName():String.valueOf(user.getId());
		Qrcode qrcode = qrcodeServiceImpl.getByImei(deviceCode);
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setCode(deviceCode);
		EjlDevice ejlDeviceData = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
		if(ejlDeviceData == null){
			ejlDevice.setGmtCreated(new Date());
		}else{
			ejlDevice.setId(ejlDeviceData.getId());
		}
		ejlDevice.setName(deviceName);//设备名字没有确定 先用用户ID填充
		ejlDevice.setUserId(userId);
		ejlDevice.setFamilyId(familyId);
		ejlDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		ejlDevice.setType(qrcode.getType());
		ejlDevice.setPhoneNumber(StringUtils.isBlank(phoneNumber)?"":phoneNumber);
		ejlDevice.setOnlineStatus(1);
		
		//String softwareVersion=null==ejlDevice.getSoftwareVersion()?qrcode.getSoftwareVersion():ejlDevice.getSoftwareVersion();
		EfPlatformDeviceVersion efPlatformDeviceVersion =efPlatformDeviceVersionService.getByDeviceTypeAndDeviceModelAndDeviceVersion(qrcode.getType(), qrcode.getModel(), qrcode.getSoftwareVersion());
		if(null==efPlatformDeviceVersion){
			throw new BizException(StatusBizCode.APP_DEVICE_PLATFORM_VERSION_MISSING.getValue());
		}
		ejlDevice.setSoftwareVersion(efPlatformDeviceVersion.getOpenVersion());
		
		//ejlDevice.setSoftwareVersion(qrcode.getSoftwareVersion());
		ejlDevice.setRunningMode(0);
		if(StringUtils.isBlank(ejlDevice.getQrcodeResourceId())){
			ejlDevice.setQrcodeResourceId(qrcode.getQrcodeResourceId());
		}
		ejlDevice.setBindUserId(ctx.getUserId());
		ejlDevice.setBindTime(DateUtils.currentDate());
		
		ejlDeviceServiceImpl.save(ctx,ejlDevice);
		return ejlDevice.getId();
	}
	
	@Override
	public int updateUnbindDeviceByAttribute(Context ctx,Long userId,Long deviceId,Long familyId,Long status) throws BizException{
		
		EjlDevice device = new EjlDevice();
		device.setId(deviceId);
		device.setUserId(0L);
		device.setFamilyId(0L);
		device.setStatus(status);
		device.setBindOnOffTime(new Date());
		device.setUpdateTime(new Date());
		device.setUpdatorId(ctx.getUserId());
		device.setBindUserId(null);
		device.setBindTime(null);
		device.setBindFinishTime(null);
		device.setRemark("bind off by userId:"+ctx.getUserId()+" time:"+DateUtils.currentDate());
		ejlDeviceDaoImpl.update(device);

		EjlUser ejlUser=ejlUserServiceImpl.get(userId);
		ejlUser.setPhone("");
		ejlUser.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
		//解绑设备的时候清除用户的健康设置
		ejlUser.setWeight(0d);
		ejlUser.setHeight(0l);
		ejlUserServiceImpl.save(ctx,ejlUser);
		
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setFamilyId(familyId);
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
		ejlFamilyUser.setUpdateTime(new Date());
		ejlFamilyUser.setUpdatorId(ctx.getUserId());
		ejlFamilyUserServiceImpl.updateAttrByUserIdAndFamilyId(ejlFamilyUser);
		
		//*** 解绑设备时 把关注此设备的用户取消掉
		ejlAttentionUserServiceImpl.updateForCancelAttentionUser(ctx, userId, EfamilyConstant.ATTENTION_OPRTYPE_CALCEL);
		
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setUserId(userId);
		ejlUserDevice.setDeviceId(deviceId);
		ejlUserDevice.setStatus(status);
		ejlUserDevice.setUpdateTime(new Date());
		ejlUserDevice.setUpdatorId(ctx.getUserId());
		return ejlUserDeviceDaoImpl.updateByAttribute(ejlUserDevice);
	}
	
	@Override
	public EjlUserDevice getByAttribute(Long userId,Long deviceId,Long status) throws BizException{
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setUserId(userId);
		ejlUserDevice.setDeviceId(deviceId);
		ejlUserDevice.setStatus(status);
		return ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
	}
	
	public List<UnbindDeviceInfo> unbindAllDeviceInFamily(Context ctx,Long familyId,Long userId) throws BizException{
		List<UnbindDeviceInfo> listInfo = new ArrayList<UnbindDeviceInfo>();

		List<EjlUserDevice> ejlUserDeviceList =  getEntityDao().getEjlUserDeviceByFamilyAndStatus(familyId);
		EjlUser ejlUser = ejlUserServiceImpl.get(userId);
		boolean isRealTime = false;
		for(EjlUserDevice ejlUserDeviceTemp:ejlUserDeviceList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("fromId", userId+"");
			map.put("nickname", ejlUser.getNickName());
			map.put("familyName", ejlFamilyServiceImpl.get(familyId)+"");
		    ejlUserServiceImpl.pushDevice(ejlUserDeviceTemp.getUserId(), ejlUserDeviceTemp.getDeviceId(), map,NoticeType.APP_DEVICE_UNBIND_WATCH.getValue(), NotyMessage.Type.OPER,isRealTime);
		    
		    //*** 将解绑的设备 传递到前端 进行 通道 和 TOKEN的清除
		    UnbindDeviceInfo unbindDeviceInfo = new UnbindDeviceInfo();
		    unbindDeviceInfo.setDeviceId(ejlUserDeviceTemp.getDeviceId());
		    unbindDeviceInfo.setUserId(ejlUserDeviceTemp.getUserId());
		    listInfo.add(unbindDeviceInfo);
		    
			//*** 设备解绑通知家庭成员  和  关注此设备的成员  
            List<EjlUser> userList = ejlUserServiceImpl.getEjlUserFamilyAndAttentionList(ctx.getUserId());
			updateUnbindDeviceByAttribute(ctx,ejlUserDeviceTemp.getUserId(),ejlUserDeviceTemp.getDeviceId(),familyId,EfamilyConstant.USER_DEVICE_STATUS_STOP);
			deviceConfigServiceImpl.notifyAppForUnbindDevice(ctx.getUserId(), ejlUserDeviceTemp.getUserId(), ejlUserDeviceTemp.getDeviceId(),userList);
		}
		
		//**** 清空掉待定设备的电话号码 ejl_user  ejl_device
		if(familyId!=null){
			ejlUserServiceImpl.clearPhoneForUnconfirmUser(ctx, familyId);
		}
		
		return listInfo;
	}

	public Long getUserUseingDeviceId(Long userId)  throws BizException{
		Long deviceId = null;
		EjlUserDevice userDevice = new EjlUserDevice();
		userDevice.setUserId(userId);
		userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		userDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(userDevice);
		if(userDevice != null){
			deviceId = userDevice.getDeviceId();
		}
		return deviceId;
	}
	
	public Long getDeviceUseingUserId(Long deviceId)  throws BizException{
		if(deviceId == null){
			return null;
		}
		Long userId = null;
		EjlUserDevice userDevice = new EjlUserDevice();
		userDevice.setDeviceId(deviceId);
		userDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		userDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(userDevice);
		if(userDevice != null){
			userId = userDevice.getUserId();
		}
		return userId;
	}

	
	public Long getDeviceUseingUserIdByCode(String deviceCode)  throws BizException{
		if(deviceCode == null){
			return null;
		}
		Long userId = null;
		EjlUserDevice userDevice = ejlUserDeviceDaoImpl.getEjlUserDeviceByDeviceCode(deviceCode);
		if(userDevice != null){
			userId = userDevice.getUserId();
		}
		return userId;
	}
	
	
	@Override
	public List<EjlUserDevice> getAllByEntity(EjlUserDevice ejlUserDevice) throws Exception {
		return ejlUserDeviceDaoImpl.getAllByEntity(ejlUserDevice);
	}

	@Override
	public EjlUserDevice getEjlUserDeviceByDeviceIdAndStatus(Long deviceId,Long status) throws BizException{
		EjlUserDevice userDeviceObjParm = new EjlUserDevice();
        userDeviceObjParm.setDeviceId(deviceId);
        userDeviceObjParm.setStatus(status);
        return getEntityDao().selectOneObjByAttribute(userDeviceObjParm);
	}
	
	public EjlUserDevice getEjlUserDeviceByUserIdAndStatus(Long userId,Long status) throws BizException{
		EjlUserDevice userDeviceObjParm = new EjlUserDevice();
        userDeviceObjParm.setUserId(userId);
        userDeviceObjParm.setStatus(status);
        return getEntityDao().selectOneObjByAttribute(userDeviceObjParm);
	}
	
	@Override
	public List<EjlUserDevice> getEjlUserDeviceBy(Long userId,Long deviceId,Long status) throws BizException{
		EjlUserDevice userDeviceObjParm = new EjlUserDevice();
		userDeviceObjParm.setUserId(userId);
        userDeviceObjParm.setDeviceId(deviceId);
        userDeviceObjParm.setStatus(status);
        return getEntityDao().selectListObjByAttribute(userDeviceObjParm);
	}
	
	public boolean checkBindDeviceBy(Long optBindUserId) throws BizException{
		EjlUserDevice userDeviceObjParm = new EjlUserDevice();
        userDeviceObjParm.setOptBindUserId(optBindUserId);
        userDeviceObjParm.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
        List<EjlUserDevice> userDeviceObjList = getEntityDao().selectListObjByAttribute(userDeviceObjParm);
        if(userDeviceObjList!=null && userDeviceObjList.size()>0){
        	return true;
        }
        return false;
	}
	
	@Override	
	public boolean checkScanWatch(Context ctx,Long userId,String watchCode,String nickName,String phoneNumber,String zombieUserId) throws BizException {
		boolean flag = true;
    	if(StringUtils.isBlank(watchCode)){
    		log.info("扫描手表时，参数不正确，扫描添加手表失败。 userId = "+userId+" ; watchCode = "+watchCode+" ; phoneNumber = "+phoneNumber );
        	throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
    	}
		EjlDevice  ejlDevice   = deviceConfigServiceImpl.getEjlDeviceByCode(watchCode);
        if(ejlDevice != null){
        	EjlUserDevice userDeviceObj = getEjlUserDeviceByDeviceIdAndStatus(ejlDevice.getId(),EfamilyConstant.USER_DEVICE_STATUS_USING);
        	if(userDeviceObj != null){
        		log.info("扫描手表时，此手表已经存在，扫描添加手表失败。 userId = "+userId+" ; watchCode = "+watchCode );
            	throw new BizException(StatusBizCode.DEVICE_USER_ALREADY_BIND.getValue());
        	}
        }
        //****  检查手机号码
		if(StringUtils.isNotEmpty(phoneNumber) ){
			EjlUser userCheck = ejlUserServiceImpl.getUserByPhone(phoneNumber);
			if(userCheck!=null){
				if(ejlDevice!=null){
					List<EjlUserDevice> userDeviceList = getEjlUserDeviceBy(userCheck.getId(),ejlDevice.getId(),EfamilyConstant.USER_DEVICE_STATUS_WAIT_CONFIRM) ;
    				if(userDeviceList!=null && userDeviceList.size()>0){
    		    		log.info("扫描手表时，此手表已经存在，当前电话号码对应的手表已经在等待确认中,扫描添加手表失败。 userId = "+userCheck.getId()+" ; watchCode = "+watchCode+" ; phoneNumber = "+phoneNumber );
    		        	throw new BizException(StatusBizCode.DEVICE_WAIT_CONFIRM_THIS_PHONE.getValue());
    				}
				}else{
					log.error("用户手机号码已经存在，保存用户失败：phoneNumber = "+phoneNumber+" ; watchCode = "+watchCode+" ; userId = "+userId);
					throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
				}
			}
		}
		
		return flag ;
	}
	
	
	public boolean checkScanWatch(Context ctx,Long userId,String watchCode,String phoneNumber,String key)throws BizException{
		boolean flag = true;
		EjlDevice  ejlDevice   = deviceConfigServiceImpl.getEjlDeviceByCode(watchCode);
		if(ejlDevice != null){
        	EjlUserDevice userDeviceObj = getEjlUserDeviceByDeviceIdAndStatus(ejlDevice.getId(),EfamilyConstant.USER_DEVICE_STATUS_USING);
        	if(userDeviceObj != null){
        		EjlUser faUser = ejlUserServiceImpl.get(userId);
        		EjlUser deviceUser = ejlUserServiceImpl.get(userDeviceObj.getUserId());
        		//用户已经扫过该表或者与该表已经在同一个家庭
        		if(deviceUser!= null && faUser.getFamilyId()!= null && deviceUser.getFamilyId()!= null && faUser.getFamilyId().equals(deviceUser.getFamilyId())){
        			if(StringUtils.isNotEmpty(phoneNumber) ){
        				EjlUser userCheck = ejlUserServiceImpl.getUserByPhone(phoneNumber);
        				if(userCheck!=null&&!userCheck.getId().equals(deviceUser.getId())){
        					EjlUserDevice phoneDeviceUser = getEjlUserDeviceByUserIdAndStatus(userCheck.getId(),EfamilyConstant.USER_DEVICE_STATUS_USING);
            				if(phoneDeviceUser!=null){
            					/*EjlDevice deivcePhone = ejlDeviceServiceImpl.get(phoneDeviceUser.getDeviceId());
            					if(!this.isImeiContainOrg(deivcePhone.getCode(),String.valueOf(ctx.get("orgKey")))){
            						log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+deivcePhone.getCode());
        							throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
            					}
            					this.unBindDevice(ctx, deivcePhone.getCode(),key);
            					ejlDevice.setPhoneNumber(phoneNumber);
            					ejlDeviceServiceImpl.save(ctx, ejlDevice);*/
            					throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
            				}
        				}
        			}
        			return false;
        		}else{
        			//该表被别的家庭用户扫过了,直接解绑
        			this.unBindDevice(ctx, watchCode,key);
        			
        			//该表手机号码被设备用了，直接解绑
        			if(StringUtils.isNotEmpty(phoneNumber) ){
        				EjlUser userCheck = ejlUserServiceImpl.getUserByPhone(phoneNumber);
        				if(userCheck!=null){
        				EjlUserDevice phoneDeviceUser = getEjlUserDeviceByUserIdAndStatus(userCheck.getId(),EfamilyConstant.USER_DEVICE_STATUS_USING);
        				if(phoneDeviceUser!=null){
        					/*EjlDevice deivcePhone = ejlDeviceServiceImpl.get(phoneDeviceUser.getDeviceId());
        					if(!this.isImeiContainOrg(deivcePhone.getCode(),String.valueOf(ctx.get("orgKey")))){
        						log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+deivcePhone.getCode());
    							throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
        					}
        					this.unBindDevice(ctx, deivcePhone.getCode(),key);*/
        					throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
        				}
        				}
        			}
        		}
        	}
        }
		 //****  检查手机号码
		if(StringUtils.isNotEmpty(phoneNumber) ){
			EjlUser userCheck = ejlUserServiceImpl.getUserByPhone(phoneNumber);
			if(userCheck!=null){
				if(ejlDevice!=null){
					List<EjlUserDevice> userDeviceList = getEjlUserDeviceBy(userCheck.getId(),ejlDevice.getId(),EfamilyConstant.USER_DEVICE_STATUS_WAIT_CONFIRM) ;
    				if(userDeviceList!=null && userDeviceList.size()>0){
    		    		log.info("扫描手表时，此手表已经存在，当前电话号码对应的手表已经在等待确认中,扫描添加手表失败。 userId = "+userCheck.getId()+" ; watchCode = "+watchCode+" ; phoneNumber = "+phoneNumber );
    		        	throw new BizException(StatusBizCode.DEVICE_WAIT_CONFIRM_THIS_PHONE.getValue());
    				}
				}else{
					log.error("用户手机号码已经存在，保存用户失败：phoneNumber = "+phoneNumber+" ; watchCode = "+watchCode+" ; userId = "+userId);
					throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
				}
			}
		}
		return flag;
	}
	
	
	@Override
	public ScanWatchResponse manageScanWatch(Context ctx,Long userId,String watchCode,String nickName,String phoneNumber,String zombieUserId) throws BizException {
			ScanWatchResponse scanWatchResponse = new ScanWatchResponse();
			Long familyId = null;
			EjlUser ejlUser  = ejlUserServiceImpl.get(userId);
			
        	//*** 判断用户是否有家庭，没有则创建
        	if(ejlUser.getFamilyId()!=null && ejlUser.getFamilyId()>0){
        		familyId = ejlUser.getFamilyId();
        	}else{
        		String familyName = ejlUser.getNickName()+family;  
        		CreateFamilyResponse  createFamilyResponse  = ejlFamilyServiceImpl.createFamily(ctx,userId, familyName);
        		familyId = createFamilyResponse.getFamilyId();
        	}

        	Long deviceUserId = null;
        	if(StringUtils.isBlank(zombieUserId)){
        		//*** 给设备创建一个用户
    			deviceUserId = ejlUserServiceImpl.saveInitUser(ctx,familyId, EfamilyConstant.USER_TYPE_NO_WATCH,phoneNumber,nickName);
    			//*** 设备加入到家庭
    			ejlFamilyUserServiceImpl.deviceJoinFamily(ctx,deviceUserId, familyId, EfamilyConstant.MANAGE_TYPE_APPLY_TEMPORARY, userId,EfamilyConstant.RESPONSE_STATUS_SUCCESS,EfamilyConstant.USER_TYPE_NO_WATCH);
        	}else{
                //*** 检查僵尸用户是否在
        		deviceUserId = Long.valueOf(zombieUserId);
        		EjlUser zombieUser = ejlUserServiceImpl.get(deviceUserId);
        		if(zombieUser.getFamilyId().longValue()!=familyId.longValue()){
        			log.info("扫描手表给僵尸用户时，僵尸用户不是该家庭的用户：family_id="+familyId);
        			throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
        		}
        		//*******************         修改僵尸用户的状态   以及跟家庭的状态                 *************************
        		ejlUserServiceImpl.zombieToDeviceUser(ctx,deviceUserId, EfamilyConstant.USER_TYPE_NO_WATCH, familyId,phoneNumber);
    			ejlFamilyUserServiceImpl.zombieUserJoinFamily(ctx,deviceUserId, familyId, EfamilyConstant.RESPONSE_STATUS_SUCCESS, EfamilyConstant.USER_TYPE_NO_WATCH);
        	}
			
			//*** 插入设备 和设备用户关系
			Long deviceId = saveEjlDevice(ctx,deviceUserId, familyId, String.valueOf(watchCode),phoneNumber);
        	saveUserDevice(ctx,deviceUserId, deviceId,EfamilyConstant.USER_DEVICE_STATUS_WAIT_CONFIRM);
			//*** 初始化手表部分参数
			deviceConfigServiceImpl.initDeviceConfig(ctx,deviceId);
			
			scanWatchResponse.setFamilyId(familyId);
			scanWatchResponse.setFamilyHostUserId(ejlFamilyUserServiceImpl.getHostByFamilyId(familyId));
			scanWatchResponse.setDeviceUserId(deviceUserId);
			scanWatchResponse.setDeviceId(deviceId);
			
			/**
			 * 添加设备初始化参数
			 * 添加健康设置
			 */
			EfPlatformDeviceSetting pltDeviceSett=efPlatformDeviceSettingService.getByDeviceType(DeviceType.WATCH.getValue());
			if(null==pltDeviceSett){
				throw new BizException(StatusBizCode.PLATFORM_DEVICE_SETT_MISSING.getValue());
			}
			EfDeviceSetting deviceSett=new EfDeviceSetting();
			deviceSett.setUserId(deviceUserId);
			deviceSett.setDeviceId(deviceId);
			deviceSett.setCommon(pltDeviceSett.getCommon());
			deviceSett.setConnect(pltDeviceSett.getConnect());
			deviceSett.setFrequency(pltDeviceSett.getFrequency());
			deviceSett.setHealth(pltDeviceSett.getHealth());
			efDeviceSettingService.save(ctx, deviceSett);
			
			EfPlatformHealthSetting pltHealthSett=efPlatformHealthSettingService.getByAgeLevel(AgeLevel.FS.getValue());
			if(null==pltHealthSett){
				throw new BizException(StatusBizCode.PLATFORM_HEALTH_SETT_MISSING.getValue());
			}
			EfUserHealthSetting userHealthSett=new EfUserHealthSetting();
			userHealthSett.setUserId(deviceUserId);
			userHealthSett.setBloodLowSpan(pltHealthSett.getBloodLowSpan());
			userHealthSett.setBloodHighSpan(pltHealthSett.getBloodHighSpan());
			userHealthSett.setRateSpan(pltHealthSett.getRateSpan());
			efUserHealthSettingService.save(ctx, userHealthSett);
			
        /*暂时禁用20009
			if(softwareVersionService.gt(deviceId, "v2.0")){ //v1.0版本开始支持
				NotificationBind notyBind=new NotificationBind();
				notyBind.setImei(watchCode);
				notyBind.setUserId(deviceUserId);
				notyBind.setDeviceId(deviceId);
				notyBind.setNickname(nickName);
				notyBind.setPhoneNumber(phoneNumber);
				notyBind.setFamilyName(ejlFamilyServiceImpl.get(familyId).getName());
				notificationUtil.notificationBind(notyBind);
			}else{*/
				//------------------  存储到redis中，等待device链接时，进行绑定确认，确认以后进行数据库修改 和 用户通知  绑表请求保留3天  ---------------------------
				redisClient.set(watchCode, userId+"",3*24*3600);	
			//}
			
            return scanWatchResponse;
	}

	@Override
	public void unBindDevice(Context ctx, Long watchId) throws BizException {
		//*** 手表解除绑定 ***
		EjlUserDevice ejlUserDevice =new EjlUserDevice();
		ejlUserDevice.setDeviceId(watchId);
		ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		ejlUserDevice = ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
		EjlDevice device = ejlDeviceServiceImpl.get(watchId);
		if(device != null||ejlUserDevice==null){
			EjlUser deviceUser = ejlUserServiceImpl.get(ejlUserDevice.getUserId());
			//*** 解绑设备
			log.info("解绑设备操作数据库： watchId = "+watchId+" ; ejlUserDevice.getUserId() = "+ejlUserDevice.getUserId()+" ; operUser.getFamilyId() = "+deviceUser.getFamilyId()+" ; ");
			this.updateUnbindDeviceByAttribute(ctx,ejlUserDevice.getUserId(), Long.valueOf(watchId),deviceUser.getFamilyId(),EfamilyConstant.USER_DEVICE_STATUS_STOP);
			boolean isRealTime = false;
			Map<String,String> map = new HashMap<String,String>();
			map.put("fromId", "-1");
			map.put("nickname", deviceUser.getNickName());
			map.put("familyName", ejlFamilyServiceImpl.get(deviceUser.getFamilyId())+"");
		    ejlUserServiceImpl.pushDevice(deviceUser.getId(), device.getId(), map,NoticeType.APP_DEVICE_UNBIND_WATCH.getValue(), NotyMessage.Type.OPER,isRealTime);
		    Map<String,Long> mapp = new HashMap<String,Long>();
		    mapp.put("userId", deviceUser.getId());
		    mapp.put("deviceId", device.getId());
		    try {
				httpClient.invokeHttp(serverUrl+urlPath, ctx, mapp, Object.class);
			} catch (Exception e) {
				log.error("解绑设备清空token失败 userId:"+deviceUser.getId()+" deviceId:"+device.getId(), e);
				throw new BizException(StatusBizCode.UNKNOW.getValue());
			}
		}else{
			log.error("解除绑定时，未绑定此设备，不需解除 : userId = 机构 ; watchId = "+watchId+" ; " );
			throw new BizException(StatusBizCode.DEVICE_USER_NOT_BIND.getValue());
		}
	}

	@Override
	public void unBindDevice(Context ctx, String imei,String key) throws BizException {
		EfOrgDevice efOrgDevice = new EfOrgDevice();
		efOrgDevice.setImei(imei);
		efOrgDevice.setStatus(1);
		efOrgDevice = efComOrgDeviceDaoImpl.selectOneObjByAttribute(efOrgDevice);
		if(efOrgDevice == null||efOrgDevice.getId()==null){
			log.error("解除绑定时，设备与机构未绑定无效 imei="+imei);
			throw new BizException(StatusBizCode.IMEI_NOT_BIND_ORG.getValue());
		}
		if(!key.equals(efComOrgServiceImpl.get(efOrgDevice.getOrgId()).getIkey())){
			log.error("解除绑定时，设备不在这个机构 imei="+imei);
			throw new BizException(StatusBizCode.ORG_KEY_NO_PERMINT.getValue());
		}
		
		efOrgDevice.setStatus(0);
		efComOrgDeviceDaoImpl.update(efOrgDevice);
		
		
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setCode(imei);
		EjlDevice ejlDeviceData = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
		if(ejlDeviceData != null){
			this.unBindDevice(ctx, ejlDeviceData.getId());
		}else{
			log.error("解除绑定时，设备无效 imei="+imei);
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
	}
	
	
	public EjlUser getDeviceUser(Context ctx, String imei) throws BizException{
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setCode(imei);
		EjlDevice ejlDeviceData = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
		if(ejlDeviceData != null){
			EjlUserDevice entity =new EjlUserDevice();
			entity.setDeviceId(ejlDeviceData.getId());
			entity.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			EjlUserDevice ejlUserDevice = ejlUserDeviceDaoImpl.selectOneObjByAttribute(entity);
			if(ejlUserDevice!=null){
				EjlUser deviceUser = ejlUserServiceImpl.get(ejlUserDevice.getUserId());
				return deviceUser;
			}else{
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_BIND.getValue());
			}
		}else{
			log.error("设备无效 imei="+imei);
			throw new BizException(StatusBizCode.DEVICE_UN_VALID.getValue());
		}
	}

	@Override
	public ScanWatchResponse bindOrgDevice(Context ctx, Long userId, String watchCode,
			String phoneNumber,String nickName) throws BizException {
		ScanWatchResponse scw = null;
		String key = (String)ctx.get("orgKey");
		if(this.checkScanWatch(ctx, userId, watchCode, phoneNumber,key)){
			scw=this.manageScanWatch(ctx, userId, watchCode, nickName, phoneNumber, null);
		}
		return scw;
	}
	
	private boolean isImeiContainOrg(String imei,String ikey) throws BizException{
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
}
