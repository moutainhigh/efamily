package com.winterframework.efamily.service.impl;



import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEJLDeviceConfigDao;
import com.winterframework.efamily.dao.IEjlDeviceAddressListDao;
import com.winterframework.efamily.dao.IEjlDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.AddressBookStruc;
import com.winterframework.efamily.dto.DeviceBatteryInfo;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.WatchBindInfo;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.device.DeviceContactsRequest;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
import com.winterframework.efamily.dto.device.param.DeviceParamFrequency;
import com.winterframework.efamily.dto.device.param.DeviceParamHealth;
import com.winterframework.efamily.entity.EfDeviceSetting;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.EjlDeviceMonitor;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.converter.ParameterConvertor;
import com.winterframework.efamily.entity.converter.ParameterConvertor.ParamIndex;
import com.winterframework.efamily.enums.DeviceOnffAdapter;
import com.winterframework.efamily.enums.ParameterMap;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfDeviceSettingService;
import com.winterframework.efamily.service.IEjlDeviceAddressListService;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlDeviceMonitorService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IInstitutionUserService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.utils.StringHelper;

@Service("deviceConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlDeviceConfigServiceImpl extends BaseServiceImpl<IEJLDeviceConfigDao,EjlDeviceParmConfig> implements IEjlDeviceConfigService {
   
	@Resource(name="deviceConfigDaoImpl")
	private IEJLDeviceConfigDao deviceConfigDaoImpl;
	
	@Resource(name="ejlUserDeviceDaoImpl")
	private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
	
	@Resource(name="ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService ejlUserDeviceServiceImpl;
	
	@Resource(name="ejlDeviceDaoImpl")
	private IEjlDeviceDao ejlDeviceDaoImpl;
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;
	
	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name = "ejlDeviceAddressListDaoImpl")
	private IEjlDeviceAddressListDao ejlDeviceAddressListDaoImpl;

	@Resource(name = "ejlDeviceAddressListServiceImpl")
	private IEjlDeviceAddressListService ejlDeviceAddressListServiceImpl;
	
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@Resource(name="ejlDeviceMonitorServiceImpl")
	private IEjlDeviceMonitorService ejlDeviceMonitorServiceImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;  
	@Resource(name = "efDeviceSettingServiceImpl")
	private IEfDeviceSettingService efDeviceSettingService;
	
	
	@Resource(name = "institutionUserServiceImpl")
	private IInstitutionUserService institutionUserServiceImpl;
	
	@Override
	protected IEJLDeviceConfigDao getEntityDao() {
		return deviceConfigDaoImpl;
	}

	@Override
	public EjlUserDevice getEjlUserDevice(Long deviceId){
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setDeviceId(deviceId);
		
		return ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDevice);
	}
	
	
	public boolean openMonitorNotifyDevice(long userId,long deviceId,String phoneNumber,Context ctx) throws Exception {
        boolean flag = true;
        if(StringUtils.isBlank(phoneNumber)){
        	log.warn("打开监听电话时，电话号码为空。userId = "+userId+" ; phoneNumber = "+phoneNumber+" ; ");
        	return false;
        }
        //*** 调用httpClient端接口，设置设备参数  ***
        try {
        	EjlDeviceMonitor ejlDeviceMonitor = new EjlDeviceMonitor();
        	ejlDeviceMonitor.setUserId(ctx.getUserId());
        	ejlDeviceMonitor.setDeviceId(deviceId);
        	ejlDeviceMonitor.setDeviceUserId(userId);
        	ejlDeviceMonitor.setCreateTime(new Date());
        	ejlDeviceMonitor.setStartTime(new Date());
        	ejlDeviceMonitor.setStatus(1);
        	ejlDeviceMonitorServiceImpl.save(ctx, ejlDeviceMonitor);
        	
        	
			Map<String,String> map = new HashMap<String,String>();
			map.put("phoneNumber", phoneNumber);
			map.put("userId", ctx.getUserId()+"");
			ejlUserServiceImpl.pushDevice(userId, deviceId, map, NoticeType.APP_DEVICE_MONITOR.getValue(), NotyMessage.Type.OPER,true);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			log.info("打开监听电话时出现异常：  userId = "+userId,e);
		}
        return flag;
	}
	
	
	public void notifyAppForUnbindDevice(Long optUserId,Long deviceUserId,Long watchId,List<EjlUser> userList) throws BizException{
		Map<String, String> paramMap = new HashMap<String,String>();
		if(deviceUserId==null || watchId==null){
			log.info("通知用户解绑设备 ,参数为空： deviceUserId = "+deviceUserId+" ; optUserId = "+optUserId+" ; watchId = "+watchId+" ; ");
			return;
		}
		EjlDevice device = ejlDeviceServiceImpl.get(watchId);
		EjlUser operUser = ejlUserServiceImpl.get(optUserId);
		
		EjlUser deviceUser = ejlUserServiceImpl.get(deviceUserId);
      	paramMap.put("initiativeUserId", deviceUser.getId()+"");
      	paramMap.put("initiativeName", deviceUser.getNickName()+"");
      	paramMap.put("initiativeuserIcon", deviceUser.getHeadImg());

      	paramMap.put("oprUserId", operUser.getId()+"");
      	paramMap.put("oprUserName", operUser.getNickName()+"");
      	paramMap.put("oprUserIcon", operUser.getHeadImg());
      	    	
      	paramMap.put("deviceCode", device.getCode());
      	paramMap.put("oprType", EfamilyConstant.UPDATE_WATCH_UN_BIND+"");
      	log.info("通知用户解绑设备 ： watchId = "+watchId+" ; paramMap = "+paramMap+" ; userId = "+optUserId+" ; ");
      	ejlUserServiceImpl.notifyForUnbindDevice(paramMap, optUserId,userList);
	}
	
	
	public void setWatchAdderssBookList(Context ctx,List<WatchDeviceManageRequest> list) throws BizException{
		for(WatchDeviceManageRequest watchDeviceManageRequest:list){
			String parameterContext = watchDeviceManageRequest.getParameterContext();
			String imei = watchDeviceManageRequest.getParameterIndex();
			EjlDevice ejlDevice = ejlDeviceServiceImpl.getByImei(imei);
			if(ejlDevice == null){
				log.error("当前操作的设备没有使用。imei = "+imei);
				throw new BizException(StatusBizCode.DEVICE_KEY_INVALID.getValue());
			}
			
			String key = String.valueOf(ctx.get("orgKey"));
			
			if(!institutionUserServiceImpl.isImeiContainOrg(imei,key)){
				log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+imei);
				throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
			}
			
			Long watchId = ejlDevice.getId();
			Long deviceUserId = null;
			ObjectMapper om=new ObjectMapper();
			EjlDeviceAddressList[] addressBookWatch;
			try {
				addressBookWatch = om.readValue(parameterContext, EjlDeviceAddressList[].class);
			} catch (Exception e){
				throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
			}
			EjlUserDevice ejlUserDevice =new EjlUserDevice();
			ejlUserDevice.setDeviceId(watchId);
			ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			ejlUserDevice = ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
			if(ejlUserDevice == null){
				log.error("当前操作的设备没有用户使用。watchId = "+watchId);
				throw new BizException(StatusBizCode.USER_NOT_BIND_DEVICE.getValue());
			}
			deviceUserId = ejlUserDevice.getUserId();
			int deleteOldUserIdCount = ejlDeviceAddressListServiceImpl.deleteByUserId(deviceUserId);
			log.info("更换通讯录时，删除数据： deleteOldUserIdCount = "+deleteOldUserIdCount+" ;  插入数据 ： insertNewUserIdCount = "+addressBookWatch.length+"  ;   ");
			for(int i=0;i<addressBookWatch.length;i++){
				EjlDeviceAddressList ejlDeviceAddressList = new EjlDeviceAddressList();
	    		ejlDeviceAddressList.setUserId(deviceUserId);
	    		ejlDeviceAddressList.setPhoneNumber(addressBookWatch[i].getPhoneNumber());
	    		EjlDeviceAddressList ejlDeviceAddressListData = ejlDeviceAddressListDaoImpl.selectOneObjByAttribute(ejlDeviceAddressList);
	    		if(ejlDeviceAddressListData != null){
	    			ejlDeviceAddressList.setId(ejlDeviceAddressListData.getId());
	    		}else{
	    			ejlDeviceAddressList.setCreator(-1l);
	    		}
	    		ejlDeviceAddressList.setHeadImage("");
	    		ejlDeviceAddressList.setName(addressBookWatch[i].getName());
	    		ejlDeviceAddressList.setIsSos(addressBookWatch[i].getIsSos());
	    		ejlDeviceAddressListServiceImpl.save(ctx,ejlDeviceAddressList);
			}
			
			//--- 设置通讯录
			NotyMessage.Type notyType=NotyMessage.Type.SETT;
			int command=NoticeType.APP_DEVICE_ADDRESSLIST_WATCH.getValue(); 
			List<DeviceContactsRequest> cttReqList=JsonUtils.fromJson2List(parameterContext.replace("name", "nickName"),DeviceContactsRequest.class);
			if(null!=cttReqList && cttReqList.size()>0){
				for(DeviceContactsRequest cttReq:cttReqList){
					cttReq.setUserId(0L);
					cttReq.setHeadImage("aasaaaaa");
					EjlUser u=ejlUserServiceImpl.getValidUserByPhone(cttReq.getPhoneNumber());
					if(null!=u){
						cttReq.setUserId(u.getId());
					}
				}
			}
			Map map = new HashMap();
			map.put("data", JsonUtils.toJson(cttReqList));
			map.put("userName", "机构");
			ejlUserServiceImpl.pushDevice(deviceUserId, watchId, map,command, notyType,false);
		}
	}
	
	@Override
	public boolean updateWatchDeviceParaBy(Context ctx,long watchId, String parameterIndex, String parameterContext,long userId) throws Exception {
        log.info("修改设备参数 start ： watchId = "+watchId+" ; parameterIndex = "+parameterIndex+" ; parameterContext = "+parameterContext+" ; userId = "+userId +" ; ");
        EjlUser operUser = ejlUserServiceImpl.get(userId);
        boolean flag = true;
        String paramKey = ParameterMap.paraIndexKeyMap.get(Integer.valueOf(parameterIndex));
        if(StringUtils.isBlank(paramKey)){
        	log.warn("设置设备参数 ，映射的命令不存在，修改设备参数失败。watchId = "+watchId+" ; parameterIndex = "+parameterIndex+" ; ");
        	return false;
        }
        Long deviceUserId = null;
		EjlUserDevice ejlUserDevice =new EjlUserDevice();
		ejlUserDevice.setDeviceId(watchId);
		ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		ejlUserDevice = ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
		if(ejlUserDevice != null){
			EjlUser deviceUser = ejlUserServiceImpl.get(ejlUserDevice.getUserId());
			if(operUser.getFamilyId()==null || operUser.getFamilyId().longValue()!=deviceUser.getFamilyId().longValue()){
				 log.error("用户不是家庭成员，操作失败。 parameterIndex = "+ parameterIndex +" ; userId = "+userId+" ; operUser.getFamilyId() = "+operUser.getFamilyId()+" ; deviceUser.getFamilyId() = "+deviceUser.getFamilyId());
				 throw new BizException(StatusBizCode.USER_UN_FAMILY_MEMBER.getValue());
			}
			if(EfamilyConstant.APP_DEVICE_UN_BIND.equals(parameterIndex) && !ejlFamilyUserServiceImpl.checkCurUserIsHost(userId)){
				 log.error("用户不是家庭群主，操作失败。 parameterIndex = "+ parameterIndex +" ; userId = "+userId);
				 throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
			}else{
				deviceUserId = ejlUserDevice.getUserId();
			}
		}else{
			log.error("当前操作的设备没有用户使用。watchId = "+watchId);
			return false;
		} 
        
        //*** 修改或者保存设备参数 ***
    	if(EfamilyConstant.DEVICE_ADDRESS_BOOK.equals(parameterIndex)){
    		//*** 保存手表通讯录到另一张表
    		if(StringUtils.isNotBlank(parameterContext)){
    			ObjectMapper om=new ObjectMapper();
        		EjlDeviceAddressList[] addressBookWatch = om.readValue(parameterContext, EjlDeviceAddressList[].class);  
        		int deleteOldUserIdCount = ejlDeviceAddressListServiceImpl.deleteByUserId(deviceUserId);
        		log.info("更换通讯录时，删除数据： deleteOldUserIdCount = "+deleteOldUserIdCount+" ;  插入数据 ： insertNewUserIdCount = "+addressBookWatch.length+"  ;   ");
        		for(int i=0;i<addressBookWatch.length;i++){
					EjlDeviceAddressList ejlDeviceAddressList = new EjlDeviceAddressList();
            		ejlDeviceAddressList.setUserId(addressBookWatch[i].getUserId());
            		ejlDeviceAddressList.setPhoneNumber(addressBookWatch[i].getPhoneNumber());
            		EjlDeviceAddressList ejlDeviceAddressListData = ejlDeviceAddressListDaoImpl.selectOneObjByAttribute(ejlDeviceAddressList);
            		if(ejlDeviceAddressListData != null){
            			ejlDeviceAddressList.setId(ejlDeviceAddressListData.getId());
            		}else{
            			ejlDeviceAddressList.setCreator(userId);
            		}
            		ejlDeviceAddressList.setHeadImage(addressBookWatch[i].getHeadImage());
            		ejlDeviceAddressList.setName(addressBookWatch[i].getName());
            		ejlDeviceAddressList.setIsSos(addressBookWatch[i].getIsSos());
            		ejlDeviceAddressListServiceImpl.save(ctx,ejlDeviceAddressList);
				}
    		}else{
    			log.info("保存通讯录时，通讯录为空，保存失败。 userId = "+userId);
    			flag = false;
    		}
    	}else if(EfamilyConstant.APP_DEVICE_UN_BIND.equals(parameterIndex)){
			//*** 手表解除绑定 ***
			EjlDevice device = ejlDeviceServiceImpl.get(watchId);
			if(device != null){
				EjlUser deviceUser = ejlUserServiceImpl.get(deviceUserId);
				if(device.getFamilyId().longValue() == deviceUser.getFamilyId().longValue() && device.getFamilyId().longValue() == operUser.getFamilyId().longValue()){
					//*** 解绑设备
					log.info("解绑设备操作数据库： watchId = "+watchId+" ; ejlUserDevice.getUserId() = "+ejlUserDevice.getUserId()+" ; operUser.getFamilyId() = "+operUser.getFamilyId()+" ; ");
					ejlUserDeviceServiceImpl.updateUnbindDeviceByAttribute(ctx,ejlUserDevice.getUserId(), Long.valueOf(watchId),operUser.getFamilyId(),EfamilyConstant.USER_DEVICE_STATUS_STOP);
				}else{
					log.error("解除此设备的用户，不在同一个家庭中，更换设备失败 : userId = "+userId+" ; watchId = "+watchId+" ; operUserId = "+userId);
					throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
				}
			}else{
				log.error("解除绑定时，未绑定此设备，不需解除 : userId = "+userId+" ; watchId = "+watchId+" ; " );
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_BIND.getValue());
			}
    	}else{ 
    		//*** 检查电话号码是否重复
    		if(EfamilyConstant.APP_DEVICE_PHONE_NUMBER.equals(parameterIndex)){
    			EjlUser userCheck = ejlUserServiceImpl.getUserByPhone(parameterContext);
    			if(userCheck!=null && userCheck.getId().longValue()!=deviceUserId){
    				log.error("用户手机号码已经存在，保存用户失败：phoneNumber = "+parameterContext+" ; watchId = "+watchId+" ; userId = "+userId);
    				throw new BizException(StatusBizCode.USER_PHONE_EXIST.getValue());
    			}
    			EjlUser user = ejlUserServiceImpl.get(deviceUserId);
    			user.setPhone(parameterContext);
    			ejlUserServiceImpl.save(ctx, user);
    			EjlDevice device = ejlDeviceServiceImpl.get(watchId);
    			device.setPhoneNumber(parameterContext);
    			ejlDeviceServiceImpl.save(ctx, device);
    			return flag;
    		}
    		
    		EjlDevice deviceObj = new EjlDevice();
    		String attrParamKey = getAttrParamKey(paramKey);
    		Field f = null;
    		try {
				f = deviceObj.getClass().getDeclaredField(attrParamKey);
    		}catch(Exception ex) {
    			log.info("读取反射属性时，属性未定义：field = "+f);
    		}
			log.info("设备属性判断： attrParamKey = "+attrParamKey);
			if(f!=null){
				f.setAccessible(true);	
				deviceObj.setId(Long.valueOf(watchId));
				String type = f.getType().toString();//得到此属性的类型  
		        if (type.endsWith("String")) {  
		            f.set(deviceObj,parameterContext) ;        
		        }else if(type.endsWith("int") || type.endsWith("Integer")){  
		            f.set(deviceObj,Integer.valueOf(parameterContext)) ;       
		        }else if(type.endsWith("long") || type.endsWith("Long")){  
		            f.set(deviceObj,Long.valueOf(parameterContext)) ;      
		        }else{  
		        	log.error("反射设置属性时，类型未定义：type = "+type);
		        	throw new BizException(StatusBizCode.TYPE_UNDEFINED.getValue());
		        } 
				ejlDeviceServiceImpl.save(ctx, deviceObj);
			}else{
				EjlDeviceParmConfig ejlDeviceParmConfig = new EjlDeviceParmConfig();
				EjlDeviceParmConfig ejlDeviceParmConfigCheck = deviceConfigDaoImpl.getByDeviceIdAndKey(Long.valueOf(watchId),paramKey); 
				if(ejlDeviceParmConfigCheck != null){
					ejlDeviceParmConfig.setId(ejlDeviceParmConfigCheck.getId());
				}
				ejlDeviceParmConfig.setParamKey(paramKey);
				ejlDeviceParmConfig.setParamValue(parameterContext);
				ejlDeviceParmConfig.setDeviceId(Long.valueOf(watchId));
				this.save(ctx,ejlDeviceParmConfig);
			}
    	}
		
    	//***************************************   发送命令到设备： ***************************************
        //*** 调用httpClient端接口，设置设备参数  ***
        try {
			Map<String,String> map = new HashMap<String,String>(); 
			EjlUser user = ejlUserServiceImpl.get(userId) ;
			int command=0;
			boolean isRealTime = false;
			NotyMessage.Type notyType=null;
			if(EfamilyConstant.DEVICE_ADDRESS_BOOK.equals(parameterIndex)){
				//--- 设置通讯录
				notyType=NotyMessage.Type.SETT;
				command=NoticeType.APP_DEVICE_ADDRESSLIST_WATCH.getValue(); 
				List<DeviceContactsRequest> cttReqList=JsonUtils.fromJson2List(parameterContext.replace("name", "nickName"),DeviceContactsRequest.class);
				if(null!=cttReqList && cttReqList.size()>0){
					for(DeviceContactsRequest cttReq:cttReqList){
						cttReq.setUserId(0L);
						EjlUser u=ejlUserServiceImpl.getValidUserByPhone(cttReq.getPhoneNumber());
						if(null!=u){
							cttReq.setUserId(u.getId());
						}
					}
				}
				map.put("data", JsonUtils.toJson(cttReqList));
				map.put("userName", user.getNickName());
				
			}else if(EfamilyConstant.APP_DEVICE_ON_OFF_WATCH.equals(parameterIndex)){
				//--- 手动关机
				notyType=NotyMessage.Type.OPER;
				command=NoticeType.APP_DEVICE_ON_OFF_WATCH.getValue();
				map.put("userName", user.getNickName());
				
			}else if(EfamilyConstant.APP_DEVICE_UN_BIND.equals(parameterIndex)){
				//--- 设备解绑
				isRealTime = false;	//改成非实时
				log.info("解绑设备1： userId = "+userId+" ; deviceId = "+parameterIndex+" ; familyId = "+user.getFamilyId());
				notyType=NotyMessage.Type.OPER;
				command=NoticeType.APP_DEVICE_UNBIND_WATCH.getValue();
				map.put("fromId", userId+"");
				map.put("nickname", user.getNickName());
				map.put("familyName", ejlFamilyServiceImpl.get(user.getFamilyId()).getName());
				log.info("解绑设备2： userId = "+userId+" ; deviceId = "+parameterIndex+" ; ");
				
			}else if(EfamilyConstant.APP_DEVICE_RESTORE_SETTING.equals(parameterIndex)){
				//--- 设备恢复出厂设置
				notyType=NotyMessage.Type.OPER;
				command=NoticeType.APP_DEVICE_RESTORE_SETTING.getValue();
				map.put("fromId", userId+"");
				map.put("nickname", user.getNickName());
				
			}else if(EfamilyConstant.APP_DEVICE_AUTO_ON_WATCH.equals(parameterIndex)){
				//--- 自动开机
				notyType=NotyMessage.Type.SETT;
				command=NoticeType.APP_DEVICE_AUTO_ON_OFF_WATCH.getValue();
				/*Map<String,String> mapTemp = new HashMap<String,String>();
				mapTemp.put("ons", parameterContext);//*** 自动开机时间   ["8:00","14:00"]
				mapTemp.put("offs", "");//*** 自动关机时间   ["8:00","14:00"]
                map.put("data", JsonUtils.toJson(mapTemp)); */
                map.put("data", "{\"ons\":"+parameterContext+"}");
			}else if(EfamilyConstant.APP_DEVICE_AUTO_OFF_WATCH.equals(parameterIndex)){
				//--- 自动关机
				notyType=NotyMessage.Type.SETT;
				command=NoticeType.APP_DEVICE_AUTO_ON_OFF_WATCH.getValue();	
				/*Map<String,String> mapTemp = new HashMap<String,String>();
				mapTemp.put("ons", "");//*** 自动开机时间   ["8:00","14:00"]
				mapTemp.put("offs", parameterContext);//*** 自动关机时间   ["8:00","14:00"]
*/				map.put("data", "{\"offs\":"+parameterContext+"}");
				
			}else{
				//--- 普通参数设置
				notyType=NotyMessage.Type.SETT;
				command=NoticeType.APP_DEVICE_PARAMETER_MANAGE.getValue(); 
				
				EjlDevice device=ejlDeviceServiceImpl.get(watchId);
				if(null!=device){
					String version=device.getSoftwareVersion();
					if(null!=version){	//兼容旧版手表软件
						command=NoticeType.APP_DEVICE_PARAMETER_MANAGE_NEW.getValue(); 
						setDeviceParam(ctx, device.getUserId(),watchId, parameterIndex, parameterContext,map);
					}else{
						map.put("code", ParameterConvertor.get(Integer.valueOf(parameterIndex))+"");
						map.put("value",DeviceOnffAdapter.get(ParameterConvertor.get(Integer.valueOf(parameterIndex)), parameterContext));	
					}
				}
			}
			log.info("更换设备参数，进行推送： pushDevice ");
			ejlUserServiceImpl.pushDevice(deviceUserId, watchId, map,command, notyType,isRealTime);
			log.info("解绑设备3： userId = "+userId+" ; deviceId = "+parameterIndex+" ; ");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("设置设备参数时出现异常：  userId = "+deviceUserId,e);
			throw new BizException(StatusBizCode.DEVICE_SEND_COMMAND_EXCEPTION.getValue(),e);
		}
    	
    	
		return flag;
	}
	
	
	
	private String getDeviceParam(Long userId,long watchId,
			String parameterIndex) throws BizException {
		Integer moduleCode= ParameterConvertor.getModuleCode(ParameterConvertor.get(Integer.valueOf(parameterIndex)));
		if(null==moduleCode){
			return null;
		}
		EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(userId,watchId);
		if(deviceSetting==null){
			return null;
		}
		String value=null;
		if(moduleCode.intValue()==EfDeviceSetting.ModuleCode.COMMON.getValue()){
			String commonSett=deviceSetting.getCommon();
			DeviceParamCommon paramCommon=JsonUtils.fromJson(commonSett, DeviceParamCommon.class);
			if(paramCommon==null){
				return null;
			}
			if(parameterIndex.equals(ParamIndex.BRIGHT.getValue()+"")){
				value =  paramCommon.getBright()==null?null:String.valueOf(paramCommon.getBright());
			}else if(parameterIndex.equals(ParamIndex.SOUND.getValue()+"")){
				value = paramCommon.getSound() == null?null:String.valueOf(paramCommon.getSound());
			}else if(parameterIndex.equals(ParamIndex.SHAKE.getValue()+"")){
				value = paramCommon.getShake() == null?null:String.valueOf(paramCommon.getShake());
			}else if(parameterIndex.equals(ParamIndex.QUIET.getValue()+"")){
				value = paramCommon.getQuiet() == null?null:String.valueOf(paramCommon.getQuiet());
			}else if(parameterIndex.equals(ParamIndex.LOCATION_ONFF.getValue()+"")){
				value = paramCommon.getLocationOnff() == null?null:String.valueOf(paramCommon.getLocationOnff());
			}else if(parameterIndex.equals(ParamIndex.HEART_ONFF.getValue()+"")){
				value = paramCommon.getHeartOnff() == null?null:String.valueOf(paramCommon.getHeartOnff());
			}else if(parameterIndex.equals(ParamIndex.WALK_ONFF.getValue()+"")){
				value = paramCommon.getWalkOnff() == null?null:String.valueOf(paramCommon.getWalkOnff());
			}else if(parameterIndex.equals(ParamIndex.SITTING_ONFF.getValue()+"")){
				value = paramCommon.getSittingOnff() == null?null:String.valueOf(paramCommon.getSittingOnff());
			}else if(parameterIndex.equals(ParamIndex.SLEEP_ONFF.getValue()+"")){
				value = paramCommon.getSleepOnff() == null?null:String.valueOf(paramCommon.getSleepOnff());
			}
		}else if(moduleCode.intValue()==EfDeviceSetting.ModuleCode.FREQUENCY.getValue()){
			String freqSett=deviceSetting.getFrequency();
			DeviceParamFrequency paramFreq=JsonUtils.fromJson(freqSett, DeviceParamFrequency.class);
			if(paramFreq==null){
				return null;
			}
			if(parameterIndex.equals(ParamIndex.SIGNAL_GFREQ.getValue()+"")){
				value = paramFreq.getSignalGfreq() == null?null:String.valueOf(paramFreq.getSignalGfreq());
			}else if(parameterIndex.equals(ParamIndex.SIGNAL_UFREQ.getValue()+"")){
				value = paramFreq.getSignalUfreq() == null?null:String.valueOf(paramFreq.getSignalUfreq());
			}else if(parameterIndex.equals(ParamIndex.BATTERY_GFREQ.getValue()+"")){
				value = paramFreq.getBatteryGfreq() == null?null:String.valueOf(paramFreq.getBatteryGfreq());
			}else if(parameterIndex.equals(ParamIndex.BATTERY_UFREQ.getValue()+"")){
				value = paramFreq.getBatteryUfreq() == null?null:String.valueOf(paramFreq.getBatteryUfreq());
			}else if(parameterIndex.equals(ParamIndex.HEART_GFREQ.getValue()+"")){
				value = paramFreq.getHeartGfreq() == null?null:String.valueOf(paramFreq.getHeartGfreq());
			}else if(parameterIndex.equals(ParamIndex.HEART_UFREQ.getValue()+"")){
				value = paramFreq.getHeartUfreq() == null?null:String.valueOf(paramFreq.getHeartUfreq());
			}else if(parameterIndex.equals(ParamIndex.WALK_UFREQ.getValue()+"")){
				value = paramFreq.getWalkUfreq() == null?null:String.valueOf(paramFreq.getWalkUfreq());
			}else if(parameterIndex.equals(ParamIndex.LOCATION_GFREQ.getValue()+"")){				
				value = paramFreq.getLocationGfreq() == null?null:String.valueOf(paramFreq.getLocationGfreq());
			}else if(parameterIndex.equals(ParamIndex.LOCATION_UFREQ.getValue()+"")){
				value = paramFreq.getLocationUfreq() == null?null:String.valueOf(paramFreq.getLocationUfreq());
			} 
		}
		return value;
	}

	private void setDeviceParam(Context ctx, Long userId,long watchId,
			String parameterIndex, String parameterContext,
			Map<String, String> map) throws BizException {
		Integer moduleCode= ParameterConvertor.getModuleCode(ParameterConvertor.get(Integer.valueOf(parameterIndex)));
		if(null==moduleCode){
			throw new BizException(StatusBizCode.PARAM_CODE_INVALID.getValue());
		}
		EfDeviceSetting deviceSetting=efDeviceSettingService.getByUserIdAndDeviceId(userId,watchId);
		if(deviceSetting==null){
			deviceSetting=new EfDeviceSetting();
			deviceSetting.setUserId(userId);
			deviceSetting.setDeviceId(watchId);
		}
		String moduleValue=null;
		if(moduleCode.intValue()==EfDeviceSetting.ModuleCode.COMMON.getValue()){
			String commonSett=deviceSetting.getCommon();
			DeviceParamCommon paramCommon=JsonUtils.fromJson(commonSett, DeviceParamCommon.class);
			if(paramCommon==null){
				paramCommon=new DeviceParamCommon();
			}
			if(parameterIndex.equals(ParamIndex.BRIGHT.getValue()+"")){
				paramCommon.setBright(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.SOUND.getValue()+"")){
				paramCommon.setSound(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.SHAKE.getValue()+"")){
				paramCommon.setShake(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.QUIET.getValue()+"")){
				paramCommon.setQuiet(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.LOCATION_ONFF.getValue()+"")){
				paramCommon.setLocationOnff(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.HEART_ONFF.getValue()+"")){
				paramCommon.setHeartOnff(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.WALK_ONFF.getValue()+"")){
				paramCommon.setWalkOnff(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.SITTING_ONFF.getValue()+"")){
				paramCommon.setSittingOnff(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.SLEEP_ONFF.getValue()+"")){
				paramCommon.setSleepOnff(Integer.valueOf(parameterContext));
			}
			moduleValue=JsonUtils.toJson(paramCommon);
			deviceSetting.setCommon(moduleValue);
		}else if(moduleCode.intValue()==EfDeviceSetting.ModuleCode.HEALTH.getValue()){
			String healthSett=deviceSetting.getHealth();
			DeviceParamHealth paramHealth=JsonUtils.fromJson(healthSett, DeviceParamHealth.class);
			if(paramHealth==null){
				paramHealth=new DeviceParamHealth();
			}
			if(parameterIndex.equals(ParamIndex.SITTING.getValue()+"")){
				paramHealth.setSittingTime(Integer.valueOf(parameterContext));
				paramHealth.setSittingSpan(new String[]{parameterContext}); 
			}else if(parameterIndex.equals(ParamIndex.SLEEP_SPAN.getValue()+"")){
				paramHealth.setSleepSpan(new String[]{parameterContext});
			} 
			moduleValue=JsonUtils.toJson(paramHealth);
			deviceSetting.setHealth(moduleValue);
		}else if(moduleCode.intValue()==EfDeviceSetting.ModuleCode.FREQUENCY.getValue()){
			String freqSett=deviceSetting.getFrequency();
			DeviceParamFrequency paramFreq=JsonUtils.fromJson(freqSett, DeviceParamFrequency.class);
			if(paramFreq==null){
				paramFreq=new DeviceParamFrequency();
			}
			if(parameterIndex.equals(ParamIndex.SIGNAL_GFREQ.getValue()+"")){
				paramFreq.setSignalGfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.SIGNAL_UFREQ.getValue()+"")){
				paramFreq.setSignalUfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.BATTERY_GFREQ.getValue()+"")){
				paramFreq.setBatteryGfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.BATTERY_UFREQ.getValue()+"")){
				paramFreq.setBatteryUfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.HEART_GFREQ.getValue()+"")){
				paramFreq.setHeartGfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.HEART_UFREQ.getValue()+"")){
				paramFreq.setHeartUfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.WALK_UFREQ.getValue()+"")){
				paramFreq.setWalkUfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.LOCATION_GFREQ.getValue()+"")){
				paramFreq.setLocationGfreq(Integer.valueOf(parameterContext));
			}else if(parameterIndex.equals(ParamIndex.LOCATION_UFREQ.getValue()+"")){
				paramFreq.setLocationUfreq(Integer.valueOf(parameterContext));
			} 
			moduleValue=JsonUtils.toJson(paramFreq);
			deviceSetting.setFrequency(moduleValue);
		}
		efDeviceSettingService.save(ctx, deviceSetting);
		map.put("code", moduleCode+"");
		map.put("value",moduleValue);
	}
	
	public String getAttrParamKey(String paramKey){
		StringBuffer sb = new StringBuffer();
		char underline = '_';
		boolean flag = false;
		if(paramKey!=null){
			for(int i=0;i<paramKey.length();i++){
				char c = paramKey.charAt(i);
				if(underline == c){
					flag = true;
				}else{
					if(flag){
						sb.append(Character.toUpperCase(c)); 
						flag = false;
					}else{
						sb.append(c); 
					}
				}
				
			}
		}
		return sb.toString() ;
	}
	
	public int saveOrUpdateDeviceParm(Context ctx,Long watchId,String paramKey,String parameterContext) throws BizException{
		try {
			EjlDeviceParmConfig ejlDeviceParmConfig = deviceConfigDaoImpl.getByDeviceIdAndKey(watchId,paramKey);
			ejlDeviceParmConfig.setParamValue(parameterContext);
			return this.save(ctx,ejlDeviceParmConfig);
		} catch (Exception e) {
			log.error("Device param config dao error",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 		
	}
	
	
	@Override
	public EjlDeviceParmConfig getWatchDeviceParaBy(long watchId, String parameterIndex,String parameterValue) throws NumberFormatException, Exception {
        
		
		String paramKey = ParameterMap.paraIndexKeyMap.get(Integer.valueOf(parameterIndex));
        EjlDeviceParmConfig ejlDeviceParmConfig = new EjlDeviceParmConfig();
		if(EfamilyConstant.DEVICE_ADDRESS_BOOK.equals(parameterIndex)){
			List<AddressBookStruc> result = new ArrayList<AddressBookStruc>();
			ObjectMapper mapper = new ObjectMapper();
			
			List<EjlDeviceAddressList> list = ejlDeviceAddressListServiceImpl.getAddressListByUser(Long.valueOf(parameterValue));
			if(list !=null && list.size()>0){
				for(EjlDeviceAddressList ejlDeviceAddressList:list){
					AddressBookStruc struc = new AddressBookStruc();
					struc.setName(ejlDeviceAddressList.getName());
					struc.setUserId(ejlDeviceAddressList.getUserId());
					struc.setHeadImage(ejlDeviceAddressList.getHeadImage());
					struc.setPhoneNumber(ejlDeviceAddressList.getPhoneNumber());
					struc.setIsSos(ejlDeviceAddressList.getIsSos());
					result.add(struc);
				}
			}else{
				log.info("通讯录为空： watchId = "+watchId+" ; parameterIndex = "+parameterIndex+" ; parameterValue = "+parameterValue+"  ;   ");
			}
			
			parameterValue = mapper.writeValueAsString(result);
			ejlDeviceParmConfig.setParamKey(paramKey);
			ejlDeviceParmConfig.setParamValue(parameterValue);
		}else{
			EjlDevice device=ejlDeviceServiceImpl.get(watchId);
			if(null!=device){
				String version=device.getSoftwareVersion();
				if(null!=version){	//兼容旧版手表软件
					EjlUserDevice eud = new EjlUserDevice();
					eud.setDeviceId(watchId);
					eud.setStatus(1l);
					Long userId = ejlUserDeviceDaoImpl.getEjlUserDevice(eud).getUserId();
					String value = this.getDeviceParam(userId, watchId, parameterIndex);
					if(value != null){
						ejlDeviceParmConfig.setParamKey(paramKey);
						ejlDeviceParmConfig.setParamValue(value);
					}
				}else{
					EjlDevice deviceObj = new EjlDevice();
					String attrParamKey = getAttrParamKey(paramKey);
					Field f = deviceObj.getClass().getDeclaredField(attrParamKey);
					if(f!=null){
						f.setAccessible(true);	
						deviceObj.setId(Long.valueOf(watchId));
						deviceObj = ejlDeviceDaoImpl.selectOneObjByAttribute(deviceObj);
						ejlDeviceParmConfig.setParamKey(paramKey);
						ejlDeviceParmConfig.setParamValue(f.get(deviceObj).toString());
					}else{
						ejlDeviceParmConfig = deviceConfigDaoImpl.getByDeviceIdAndKey(Long.valueOf(watchId),paramKey);
					}
				}
			}	
		}
		return ejlDeviceParmConfig ;
		
	}
	
	
	public DeviceBatteryInfo getDeviceBatteryInfo(Long deviceId,String paramKey) throws Exception{
		DeviceBatteryInfo deviceBatteryInfo = new DeviceBatteryInfo();
		EjlDeviceParmConfig ejlDeviceParmConfig = new EjlDeviceParmConfig();
		ejlDeviceParmConfig = deviceConfigDaoImpl.getByDeviceIdAndKey(deviceId,paramKey);
		if(ejlDeviceParmConfig != null){
			deviceBatteryInfo.setBattery(ejlDeviceParmConfig.getParamValue());
			deviceBatteryInfo.setBatteryTime(String.valueOf( ejlDeviceParmConfig.getUpdateTime()!=null?ejlDeviceParmConfig.getUpdateTime().getTime():ejlDeviceParmConfig.getCreateTime().getTime()));
			deviceBatteryInfo.setDeviceId(deviceId+"");
		}
		return deviceBatteryInfo ;
	} 
	
	
	public Map<String,String> getWatchDeviceNewParaAll(long watchId, long userId) throws BizException{
		Map<String,String> paraMap = new HashMap<String,String>();
		for(Integer parameterIndex:ParameterMap.paraIndexKeyMap.keySet()){
			String paramKey = ParameterMap.paraIndexKeyMap.get(Integer.valueOf(parameterIndex));
			String value = this.getDeviceParam(userId, watchId, parameterIndex+"");
			if(value != null){
				paraMap.put(paramKey, value);
			}
		}
		List<EjlDeviceAddressList> ejlDeviceAddressList = ejlDeviceAddressListDaoImpl.getAddressListByUser(userId);
        
		try{
			ObjectMapper mapper = new ObjectMapper();
			paraMap.put("addressBook", mapper.writeValueAsString(ejlDeviceAddressList) );
        }catch(Exception e){
        	log.error("获取设备所有参数时，转换出现异常。watchId = "+watchId +" ; userId = "+userId + "   ;  ");
        }
        
		EjlUserDevice ejlUserDeviceParm = new EjlUserDevice();
		ejlUserDeviceParm.setDeviceId(Long.valueOf(watchId));
		ejlUserDeviceParm.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		ejlUserDeviceParm = ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDeviceParm);
		paraMap.put("bindWatchUserId",   String.valueOf(ejlUserDeviceParm.getOptBindUserId()));
		return paraMap;
	}
	
	@Override
	public Map<String,String> getWatchDeviceParaAll(long watchId, long userId) throws BizException {
		Map<String,String> paraMap = new HashMap<String,String>();
		EjlDevice device=ejlDeviceServiceImpl.get(watchId);
		if(null!=device){
			String version=device.getSoftwareVersion();
			if(null!=version){	//兼容旧版手表软件
				paraMap = this.getWatchDeviceNewParaAll(watchId, userId);
			}else{
				  EjlDeviceParmConfig ejlDeviceParmConfig = new EjlDeviceParmConfig();
					ejlDeviceParmConfig.setDeviceId(Long.valueOf(watchId));
					List<EjlDeviceParmConfig> ejlDeviceParmConfigList = deviceConfigDaoImpl.getObjByAttribute(ejlDeviceParmConfig);
					if(ejlDeviceParmConfigList != null && ejlDeviceParmConfigList.size()>0){
						for(EjlDeviceParmConfig ejlDeviceParmConfigTemp : ejlDeviceParmConfigList){
							paraMap.put(ejlDeviceParmConfigTemp.getParamKey(), ejlDeviceParmConfigTemp.getParamValue());
						}
						Map<String,String> deviceParamMap = ejlDeviceServiceImpl.getBaseDeviceParamMapBy(Long.valueOf(watchId));
						if(deviceParamMap != null){
							paraMap.putAll(deviceParamMap);
						}
						
						List<EjlDeviceAddressList> ejlDeviceAddressList = ejlDeviceAddressListDaoImpl.getAddressListByUser(userId);
			            
						try{
							ObjectMapper mapper = new ObjectMapper();
							paraMap.put("addressBook", mapper.writeValueAsString(ejlDeviceAddressList) );
			            }catch(Exception e){
			            	log.error("获取设备所有参数时，转换出现异常。watchId = "+watchId +" ; userId = "+userId + "   ;  ");
			            }
			            
			    		EjlUserDevice ejlUserDeviceParm = new EjlUserDevice();
			    		ejlUserDeviceParm.setDeviceId(Long.valueOf(watchId));
			    		ejlUserDeviceParm.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			    		ejlUserDeviceParm = ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDeviceParm);
			    		paraMap.put("bindWatchUserId",   String.valueOf(ejlUserDeviceParm.getOptBindUserId()));
					}else{
						log.info("该设备没有设置参数，获取参数失败。watchId = "+watchId +" ; userId = "+userId + "   ;  ");
						throw new BizException(StatusBizCode.DEVICE_PARAM_IS_NULL.getValue());
					}
			}
		}
		return paraMap;
	}
	@Override
	public WatchBindInfo queryWatchBindInfo(String deviceCode) throws BizException {
		WatchBindInfo watchBindInfo = new WatchBindInfo();
        long bindWatchType = EfamilyConstant.BIND_WATCH_STATUS_NOT;		
        Qrcode qrcode = qrcodeServiceImpl.getByImei(deviceCode);
        if(qrcode != null ){
        	if(qrcode.getStatus().longValue() == EfamilyConstant.USER_DEVICE_STATUS_USING){
        		EjlDevice ejlDevice = new EjlDevice();
        		ejlDevice.setCode(deviceCode);
        		ejlDevice = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
                if(ejlDevice != null){
            		EjlUserDevice ejlUserDeviceParm = new EjlUserDevice();
            		ejlUserDeviceParm.setDeviceId(ejlDevice.getId());
            		ejlUserDeviceParm.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
            		EjlUserDevice ejlUserDevice = ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDeviceParm);
            		if(ejlUserDevice != null){
            				bindWatchType = EfamilyConstant.BIND_WATCH_STATUS_YES;
            				if(ejlDevice.getFamilyId()!=null){
            					EjlFamily ejlFamily =  ejlFamilyServiceImpl.get(ejlDevice.getFamilyId());
            					watchBindInfo.setFamilyCode(ejlFamily.getCode());
            					watchBindInfo.setFamilyName(ejlFamily.getName());
            				}else{
            					log.warn("获取手表绑定详情时，已经绑定的手表，不存在任何家庭中：ejlDevice.getFamilyId()=null ,deviceCode="+deviceCode);
            				}
            		}
                }
        	}else{
        		bindWatchType = EfamilyConstant.BIND_WATCH_STATUS_NO_EFFECT;	
        	}
        }else{
        	bindWatchType = EfamilyConstant.BIND_WATCH_STATUS_NO;	
        }
        watchBindInfo.setBindWatchType(bindWatchType);
        
		return watchBindInfo;
	}
	
	public String getDevicePhoneBy(String deviceCode) throws Exception{
		String phone = "";
		Qrcode qrcode = qrcodeServiceImpl.getByImei(deviceCode);
		if(qrcode !=null && qrcode.getPhoneNumber()!= null){
			phone = qrcode.getPhoneNumber();
		}
		return phone;
	}
	
	
	/**
	 * 功能：设备参数修改时，参数检查
	 * @param ctx
	 * @param request
	 * @return
	 */
	public boolean checkParam(WatchDeviceManageRequest request,Long userId){
		boolean flag = false;
		String familyId = request.getFamilyId();//家庭ID
		String watchId = request.getWatchId();//手表ID
		String parameterIndex = request.getParameterIndex();//功能参数的索引位置
		String parameterContext = request.getParameterContext();//功能参数的内容
		if(StringUtils.isNotBlank(familyId)&&StringUtils.isNotBlank(watchId)&&StringUtils.isNotBlank(parameterIndex)&&(StringUtils.isNotBlank(parameterContext)||Long.valueOf(parameterIndex)%2==0)){
			//*** 判断该设备是否是该操作人所有
			EjlDevice ejlDevice = new EjlDevice();
			ejlDevice.setId(Long.valueOf(watchId));
			ejlDevice.setUserId(userId);
			ejlDevice.setFamilyId(Long.valueOf(familyId));
			ejlDevice = ejlDeviceDaoImpl.getByFamilyAndDevice(ejlDevice);
			if(ejlDevice != null){
				if(EfamilyConstant.USER_DEVICE_STATUS_USING == ejlDevice.getStatus() || (Long.valueOf(parameterIndex)%2==0)){
					flag = true;
				}else{
					flag = false;
					log.info("设备参数修改：设备已被停用，修改失败。userId = "+userId+" ; watchId = "+watchId+ " ; ");
				}
			}else{
				flag = false;
				log.warn("设备参数修改：不是该用户家庭的设备，不允许修改。userId = "+userId+" ; watchId = "+watchId+ " ; ");
			}
		}else{
			log.info("设备参数修改：传递参数不完整。data = "+request.toString()+ " ; ");
		}
		
		return flag ;
	}

	/**
	 * 功能：根据设备编码获取设备信息
	 * @param code
	 * @return
	 */
	public EjlDevice getEjlDeviceByCode(String code){
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setCode(code);
		ejlDevice = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
		return ejlDevice ;
	}
	
	
	/**
	 * 功能：修改手表的拥有者
	 * @param userId
	 * @param deviceId
	 * @throws BizException 
	 */
	public Map<String, String> updateWatchOwner(Context ctx,Long userId,Long deviceCode,Long userIdAA,Long userIdBB,Long oprType,String newUserName,String phoneNumber) throws BizException{
		
		// 约定: type 1 : 人换表  ;  2： 表换人   ; 3： 表换人 (新增加人员)  ;  把设备ID号 都换成    deviceCode 
		Map<String, String> paramMapResult = new HashMap<String,String>();
    	
		EjlUser userOperate = ejlUserServiceImpl.get(userId);
		EjlUser userAA = ejlUserServiceImpl.get(userIdAA);
		EjlUser userBB = null;
		Long deviceIdAA = null;
		Long deviceIdBB = null;
		if(oprType.longValue() == EfamilyConstant.UPDATE_WATCH_SWITCH_WATCH){  
			//*********************************  人换表   ********************
			// *** 条件：这块新扫描进来的表  没有绑定用户  所以这个新设备肯定不在线 不需要考虑通道更换的问题
			EjlDevice ejlDevice = new EjlDevice();
			ejlDevice.setCode(String.valueOf(deviceCode));
			EjlDevice device = ejlDeviceDaoImpl.getEjlDeviceByParm(ejlDevice);
			
			if(device != null && device.getFamilyId().longValue() != 0L ){
				if(device.getFamilyId().longValue() != userOperate.getFamilyId().longValue()){
					log.info("操作者跟设备不在同一个家庭，不允许修改设备，操作设备。userId = "+userId+" ; deviceCode = "+deviceCode+" ; familyIdOperate = "+userOperate.getFamilyId()+" ; familyIdDevice = "+device.getFamilyId());
					throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
				}
			}
			
			if(device != null){
				//*** 扫描进来的表  存在  则需检查 是否有解绑   ***
				EjlUserDevice userDeviceObj = new EjlUserDevice();
				userDeviceObj.setDeviceId(device.getId()); 
				userDeviceObj.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
				userDeviceObj = ejlUserDeviceDaoImpl.getEjlUserDevice(userDeviceObj);
				if(userDeviceObj != null  ){
					if(userDeviceObj.getUserId().longValue() == userIdAA.longValue()){
						log.info("更换用户手表时，扫描进来的表，已经绑定到该用户，重复绑定。userId = "+userId+" ; deviceId = "+device.getId()+" ; familyIdOperate = "+userOperate.getFamilyId()+" ; familyIdDevice = "+device.getFamilyId());
						throw new BizException(StatusBizCode.DEVICE_USER_ALREADY_BIND.getValue());
					}else{
						log.info("更换用户手表时，扫描进来的表，未解绑，不允许更换用户。userId = "+userId+" ; deviceId = "+device.getId()+" ; familyIdOperate = "+userOperate.getFamilyId()+" ; familyIdDevice = "+device.getFamilyId());
						throw new BizException(StatusBizCode.DEVICE_USER_NOT_BIND.getValue());
					}
				}
			}

			//*** 禁用userAA 的设备
			EjlUserDevice deviceOld = new EjlUserDevice();
			deviceOld.setUserId(userIdAA);
			deviceOld.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			deviceOld = ejlUserDeviceDaoImpl.getEjlUserDevice(deviceOld);
			if(deviceOld != null ){
				//*** 解绑USERAA旧的设备
				deviceIdAA = deviceOld.getDeviceId();
				deviceOld.setStatus(EfamilyConstant.USER_DEVICE_STATUS_STOP);
				ejlUserDeviceServiceImpl.save(ctx,deviceOld);
				
				EjlDevice ejlDeviceAA = new EjlDevice();
				ejlDeviceAA.setId(deviceOld.getDeviceId());
				ejlDeviceAA.setUserId(0L);
				ejlDeviceAA.setFamilyId(0L);
				ejlDeviceAA.setStatus(0L);
				
				ejlDeviceServiceImpl.save(ctx,ejlDeviceAA);
			}else{
				log.info("此人之前没有绑定设备： userId = "+userId+" ; deviceId = "+device.getId()+" ; familyIdOperate = "+userOperate.getFamilyId()+" ; familyIdDevice = "+device.getFamilyId());
				throw new BizException(StatusBizCode.USER_NOT_BIND_DEVICE.getValue());
			}
			 
			//*** 绑定新手表 插入设备 和设备用户关系
			Long deviceIdNew = ejlUserDeviceServiceImpl.saveEjlDevice(ctx,userIdAA, userAA.getFamilyId(), String.valueOf(deviceCode),phoneNumber);
			ejlUserDeviceServiceImpl.saveUserDevice(ctx,userIdAA, deviceIdNew,EfamilyConstant.USER_DEVICE_STATUS_WAIT_CONFIRM);
			//*** 在设备确认之前  用户是无设备用户
			ejlUserServiceImpl.updateUserTypeAndPhone(ctx,userIdAA, EfamilyConstant.USER_TYPE_NO_WATCH,"");
			ejlFamilyUserServiceImpl.updateFamilyUserType(userIdAA, userAA.getFamilyId(), EfamilyConstant.USER_TYPE_NO_WATCH);
			//***初始化新手表数据    	
			initDeviceConfig(ctx,deviceIdNew);
			paramMapResult.put("passiveDeviceCode",  StringHelper.LongToStr(deviceCode, ""));
			paramMapResult.put("passiveUserId",  StringHelper.LongToStr(userIdAA, ""));
			paramMapResult.put("passiveDeviceId",  StringHelper.LongToStr(deviceIdNew, ""));
			
		}else if(oprType.longValue() == EfamilyConstant.UPDATE_WATCH_SWITCH_USER || oprType.longValue() == EfamilyConstant.UPDATE_WATCH_SWITCH_NEW_USER){
			//*********************************  表换人 : APP用户替换为手机用户，手表用户直接更换手表，前提是 这些设备和用户 都需要在一个家庭中  ********************
			if(oprType.longValue() == EfamilyConstant.UPDATE_WATCH_SWITCH_NEW_USER){
				//*** 使用newUserName 新建一个用户  并加入当前家庭  ，新建的userId 赋值给userIdBB 即可
				if(StringUtils.isBlank(newUserName)){
					newUserName = "";
				}
				userIdBB = ejlUserServiceImpl.saveInitUser(ctx,userOperate.getFamilyId(), EfamilyConstant.USER_TYPE_NO_WATCH,null,newUserName);
    			ejlFamilyUserServiceImpl.deviceJoinFamily(ctx,userIdBB, userOperate.getFamilyId(), EfamilyConstant.MANAGE_TYPE_AGREE, userOperate.getId(),EfamilyConstant.RESPONSE_STATUS_SUCCESS,EfamilyConstant.USER_TYPE_WATCH);
			}
			
			// 约定: type 1 : 人换表  ;  2： 表换人   ;  把设备ID号 都换成    watchCode 
			userBB = ejlUserServiceImpl.get(userIdBB);
			if(userAA.getFamilyId().longValue() != userOperate.getFamilyId().longValue() || userBB.getFamilyId().longValue() != userOperate.getFamilyId().longValue()){
				log.info("操作者跟其他两个用户不在同一个家庭，不允许更换设备，操作失败。userId = "+userId+" ; familyIdOperate = "+userOperate.getFamilyId()+" ; familyIdAA = "+userAA.getFamilyId()+" ; familyIdBB = "+userBB.getFamilyId());
				throw new BizException(StatusBizCode.DEVICE_USER_NOT_IN_FAMILY.getValue());
			}
			
			EjlUserDevice deviceAAOld = new EjlUserDevice();
			deviceAAOld.setUserId(userIdAA);
			deviceAAOld.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			deviceAAOld = ejlUserDeviceDaoImpl.getEjlUserDevice(deviceAAOld);
			if(deviceAAOld != null){
				deviceIdAA = deviceAAOld.getDeviceId();
			}
			
			EjlUserDevice deviceBBOld = new EjlUserDevice();
			deviceBBOld.setUserId(userIdBB);
			deviceBBOld.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			deviceBBOld = ejlUserDeviceDaoImpl.getEjlUserDevice(deviceBBOld);
            if(deviceBBOld != null){
            	deviceIdBB = deviceBBOld.getDeviceId();
			}
			
            paramMapResult.put("passiveUserId", StringHelper.LongToStr(userIdBB, ""));
			paramMapResult.put("passiveName", userBB.getNickName());
			paramMapResult.put("passiveUserIcon", userBB.getHeadImg());

			paramMapResult.put("passiveDeviceId",  StringHelper.LongToStr(deviceIdBB, ""));
			paramMapResult.put("passivePhoneNumber", userBB.getPhone());
			paramMapResult.put("passiveNickName", userBB.getNickName());
			paramMapResult.put("passiveUserType", StringHelper.LongToStr(userBB.getType(), ""));
			//--------------------- 用户AA 用户BB 更换设备 ----------------------------
			switchDevice(ctx, userIdAA, deviceIdAA, userIdBB,  deviceIdBB,userOperate.getFamilyId());
			
		}else{
			log.info("更换用户手表时，传递的操作类型未定义，操作失败。userId = "+userId+" ; deviceCode = "+deviceCode+" ; familyIdOperate = "+userOperate.getFamilyId());
			throw new BizException(StatusBizCode.FAILED.getValue());
		}
		EjlFamily family = ejlFamilyServiceImpl.get(userOperate.getFamilyId());
		paramMapResult.put("initiativeUserId", StringHelper.LongToStr(userAA.getId(), ""));
		paramMapResult.put("initiativeName", userAA.getNickName());
		paramMapResult.put("initiativeuserIcon", userAA.getHeadImg());
		
		paramMapResult.put("initiativeDeviceId", StringHelper.LongToStr(deviceIdAA, ""));
		paramMapResult.put("initiativePhoneNumber", userAA.getPhone());
		paramMapResult.put("initiativeFamilyName", family.getName());
		paramMapResult.put("initiativeNickName", userAA.getNickName());

		paramMapResult.put("passiveFamilyName",  family.getName());
		
		paramMapResult.put("oprUserId", StringHelper.LongToStr(userOperate.getId(), ""));
		paramMapResult.put("oprUserName", userOperate.getNickName());
		paramMapResult.put("oprUserIcon", userOperate.getHeadImg());
		paramMapResult.put("oprFamilyId", userOperate.getFamilyId()+"");
		paramMapResult.put("oprPhoneNumber", userOperate.getPhone());
		paramMapResult.put("oprFamilyName", family.getName());
		
		paramMapResult.put("deviceCode", StringHelper.LongToStr(deviceCode, ""));
		paramMapResult.put("oprType", StringHelper.LongToStr(oprType, ""));
    	
		return paramMapResult;
	}
	
	/**
	 * 
	* @Title: switchOtherWatch 
	* @Description: TODO(将用户USERBB的设备DEVICEBB，给用户USERAA使用) 
	* @param userAA
	* @param userBB
	* @param deviceBB
	* @throws BizException
	 */
	public void switchOtherWatch(Context ctx,Long userAA,Long userBB,Long  deviceBB) throws BizException{
		EjlUser userAAObj = ejlUserServiceImpl.get(userAA);
		if(deviceBB == null){
			//*** 成员BB上 没有手表，用户AA 则直接变为 TYPE=3(没有手表和APP的用户) 的用户
			EjlUser ejlUser= ejlUserServiceImpl.get(userAA);
			ejlUser.setType(EfamilyConstant.USER_TYPE_NO_WATCH);
			ejlUserServiceImpl.save(ctx,ejlUser);
		}else{
			//*** 成员BB上 有手表 则把手表给成员AA 
			
			//禁用成员BB设备
			EjlUserDevice userObjBB = new EjlUserDevice();
			userObjBB.setDeviceId(deviceBB);
			userObjBB.setUserId(userBB);
			EjlUserDevice userObjBBParm =  ejlUserDeviceDaoImpl.selectOneObjByAttribute(userObjBB);
			if(userObjBBParm != null){
				userObjBB.setId(userObjBBParm.getId());
				userObjBB.setStatus(EfamilyConstant.USER_DEVICE_STATUS_STOP);
				ejlUserDeviceServiceImpl.save(ctx,userObjBB);	
			}
			
			//建立成员AA跟BB设备的关系
			EjlUserDevice ejlUserDevice = new EjlUserDevice();
			ejlUserDevice.setUserId(userAA);
			ejlUserDevice.setDeviceId(deviceBB);
			EjlUserDevice ejlUserDeviceCheck = ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDevice);
			if(ejlUserDeviceCheck != null){
				ejlUserDevice.setId(ejlUserDeviceCheck.getId());
			}
			ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
			ejlUserDeviceServiceImpl.save(ctx,ejlUserDevice);
			
			//更换设备拥有者
			EjlDevice ejlDevice = new EjlDevice();
			ejlDevice.setId(deviceBB);
			ejlDevice.setUserId(userAA);
			ejlDevice.setFamilyId(userAAObj.getFamilyId());
			ejlDeviceServiceImpl.save(ctx,ejlDevice);
			
			//用户类型更改
			EjlUser ejlUser= ejlUserServiceImpl.get(userAA);
			ejlUser.setType(EfamilyConstant.USER_TYPE_WATCH);
			ejlUserServiceImpl.save(ctx,ejlUser);
		}
	}

	public void switchDevice(Context ctx,Long userAA,Long deviceAA,Long userBB,Long deviceBB,Long familyId) throws BizException{
		
		if(deviceAA != null){
			//*** 解绑      AA  设备  ***
			unbingDeviceForUpdate(ctx,userAA,deviceAA);
		}
		if(deviceBB != null){
			//*** 解绑      BB  设备 ***
			unbingDeviceForUpdate(ctx,userBB,deviceBB);
		}
		
		if(deviceAA != null){
			//*** BB 用户绑定   AA 设备  ***
			bindDeviceForUpdate(ctx,userBB,deviceAA);
		}else{
			//*** 成员AA上 没有手表，用户BB 则直接变为 TYPE=3(没有手表和APP的用户) 的用户
			ejlUserServiceImpl.updateUserType(ctx,userBB, EfamilyConstant.USER_TYPE_NO_WATCH);
			ejlFamilyUserServiceImpl.updateFamilyUserType(userBB, familyId, EfamilyConstant.USER_TYPE_NO_WATCH);
		}
		
		if(deviceBB != null){
			//*** AA 用户绑定   BB 设备 ***
			bindDeviceForUpdate(ctx,userAA,deviceBB);
		}else{
			//*** 成员BB上 没有手表，用户AA 则直接变为 TYPE=3(没有手表和APP的用户) 的用户
			ejlUserServiceImpl.updateUserType(ctx,userAA, EfamilyConstant.USER_TYPE_NO_WATCH);
			ejlFamilyUserServiceImpl.updateFamilyUserType(userAA, familyId, EfamilyConstant.USER_TYPE_NO_WATCH);
		}
		
		//************ 更换两个user 的手机号码  ******************
		ejlUserServiceImpl.switchUserPhone(ctx, userAA, userBB);
	}
	
	public void unbingDeviceForUpdate(Context ctx,Long userId,Long deviceId)throws BizException{
		//禁用成员BB设备
		EjlUserDevice userObjBB = new EjlUserDevice();
		userObjBB.setDeviceId(deviceId);
		userObjBB.setUserId(userId);
		EjlUserDevice userObjBBParm =  ejlUserDeviceDaoImpl.selectOneObjByAttribute(userObjBB);
		if(userObjBBParm != null){
			userObjBB.setId(userObjBBParm.getId());
			userObjBB.setStatus(EfamilyConstant.USER_DEVICE_STATUS_STOP);
			ejlUserDeviceServiceImpl.save(ctx,userObjBB);	
		}
	}
	
	
	public void bindDeviceForUpdate(Context ctx,Long userId,Long deviceId)throws BizException{
		//建立成员AA跟BB设备的关系
		EjlUser user = ejlUserDaoImpl.getById(userId);
		EjlUserDevice ejlUserDevice = new EjlUserDevice();
		ejlUserDevice.setUserId(userId);
		ejlUserDevice.setDeviceId(deviceId);
		EjlUserDevice ejlUserDeviceCheck = ejlUserDeviceDaoImpl.getEjlUserDevice(ejlUserDevice);
		if(ejlUserDeviceCheck != null){
			ejlUserDevice.setId(ejlUserDeviceCheck.getId());
		}
		ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
		ejlUserDeviceServiceImpl.save(ctx,ejlUserDevice);
		
		//更换设备拥有者
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setId(deviceId);
		ejlDevice.setUserId(userId);
		ejlDevice.setName(StringUtils.isNotEmpty(user.getNickName())?user.getNickName():user.getId()+"");
		ejlDeviceServiceImpl.save(ctx,ejlDevice);
		
		//用户类型更改
		EjlUser ejlUser= ejlUserServiceImpl.get(userId);
		ejlUser.setType(EfamilyConstant.USER_TYPE_WATCH);
		ejlUserServiceImpl.save(ctx,ejlUser);
		
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setFamilyId(user.getFamilyId());
		ejlFamilyUser.setUserId(userId);
		ejlFamilyUser.setType(EfamilyConstant.USER_TYPE_WATCH);
		ejlFamilyUserServiceImpl.updateAttrByUserIdAndFamilyId(ejlFamilyUser);
		
	}
	
	@Override
	public void initDeviceConfig(Context ctx,Long deviceId) throws BizException {
		EjlDeviceParmConfig deviceParmConfigObj = null;
		try {
			EjlDeviceParmConfig epc1 = new EjlDeviceParmConfig();
			deviceParmConfigObj = deviceConfigDaoImpl.getByDeviceIdAndKey(deviceId,"heart_rate");
            if(deviceParmConfigObj !=null){
            	epc1.setId(deviceParmConfigObj.getId());
            }		
            epc1.setDeviceId(deviceId);
			epc1.setParamKey("heart_rate");
			epc1.setParamValue("0");
			this.save(ctx,epc1);
		} catch (Exception e) {
			log.error("Device param config dao error",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 
		try {
			EjlDeviceParmConfig epc2 = new EjlDeviceParmConfig();
			deviceParmConfigObj = deviceConfigDaoImpl.getByDeviceIdAndKey(deviceId,"record_steps");
            if(deviceParmConfigObj !=null){
            	epc2.setId(deviceParmConfigObj.getId());
            }		
            epc2.setDeviceId(deviceId);
            epc2.setParamKey("record_steps");
            epc2.setParamValue("0");
			this.save(ctx,epc2);
			
		} catch (Exception e) {
			log.error("Device param config dao error",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 		
		try {
			EjlDeviceParmConfig epc3 = new EjlDeviceParmConfig();
			deviceParmConfigObj = deviceConfigDaoImpl.getByDeviceIdAndKey(deviceId,"location_onff");
            if(deviceParmConfigObj !=null){
            	epc3.setId(deviceParmConfigObj.getId());
            }		
            epc3.setDeviceId(deviceId);
            epc3.setParamKey("location_onff");
            epc3.setParamValue("1");
			this.save(ctx,epc3);
			
		} catch (Exception e) {
			log.error("Device param config dao error",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		} 		
	}
	
}


