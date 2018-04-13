package com.winterframework.efamily.institution.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.dto.Contact;
import com.winterframework.efamily.dto.ContactReq;
import com.winterframework.efamily.dto.DeviceAddressListDto;
import com.winterframework.efamily.dto.UserInfoReq;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.dto.WatchDeviceManageResponse;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.entity.EfOrgDevice;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.EjlDeviceParmConfig;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.OrgBindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindDeviceBaseInfo;
import com.winterframework.efamily.entity.OrgUnbindUserBaseInfo;
import com.winterframework.efamily.entity.OrgUser;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.WatchParamBaseInfo;
import com.winterframework.efamily.entity.EjlFamilyUser.Position;
import com.winterframework.efamily.institution.service.IDeviceManageService;
import com.winterframework.efamily.institution.util.DataHandlerUtil;
import com.winterframework.efamily.service.IEfComOrgDeviceService;
import com.winterframework.efamily.service.IEfComOrgService;
import com.winterframework.efamily.service.IEjlComDeviceAddressListService;
import com.winterframework.efamily.service.IEjlComDeviceConfigService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComFamilyService;
import com.winterframework.efamily.service.IEjlComFamilyUserService;
import com.winterframework.efamily.service.IEjlComUserService;
import com.winterframework.efamily.service.IOrgComEmployeeService;
import com.winterframework.efamily.service.IOrgComUserService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.utils.BizNumberUtils;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Service("deviceManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceManageServiceImpl implements IDeviceManageService{

	@Resource(name="orgComEmployeeServiceImpl")
	private IOrgComEmployeeService orgComEmployeeServiceImpl;
	
	@Resource(name="ejlComDeviceConfigServiceImpl")
	private IEjlComDeviceConfigService ejlComDeviceConfigServiceImpl;
	
	@Resource(name="qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl;
	
	@Resource(name="ejlComDeviceAddressListServiceImpl")
	private IEjlComDeviceAddressListService ejlComDeviceAddressListServiceImpl;
	
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	@Resource(name="efComOrgDeviceServiceImpl")
	private IEfComOrgDeviceService efComOrgDeviceServiceImpl;

	@Resource(name="efComOrgServiceImpl")
	private IEfComOrgService efComOrgServiceImpl;
	
	@Resource(name="orgComUserServiceImpl")
	private IOrgComUserService orgComUserServiceImpl;
	
	@Resource(name="ejlComUserServiceImpl")
	private IEjlComUserService ejlComUserServiceImpl;
	
	@Resource(name="ejlComFamilyServiceImpl")
	private IEjlComFamilyService ejlComFamilyServiceImpl;
	
	@Resource(name="ejlComFamilyUserServiceImpl")
	private IEjlComFamilyUserService ejlComFamilyUserServiceImpl;
	
	@Resource(name = "bizNumberUtils")
	private BizNumberUtils bizNumberUtils;
	
	@PropertyConfig(value="family")
	private String family;
	
	@PropertyConfig("server.url.app")
	private String serverUrl;
	
	@PropertyConfig("app.server.userInfo")
	private String userInfoPath;
	
	@PropertyConfig("app.server.watchDeviceManage")
	private String watchDeviceManage;

	@PropertyConfig("app.server.setAlarm")
	private String urlPath;
	
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;
	
	private Logger log = LoggerFactory.getLogger(DeviceManageServiceImpl.class);
	
	public boolean bindOrUnbindDevice(Long orgId,Long orgUserId,Long orgDeviceId,Integer operType) throws BizException{
		
		boolean flag = false;
		//Integer operType = 1;//0禁用：包括删除和禁用1启用：包括增加，修改和启用
		
		//*** key 的查询  
		EfOrg org = efComOrgServiceImpl.get(orgId);
		EfOrgDevice efOrgDevice = efComOrgDeviceServiceImpl.get(orgDeviceId);
		
		OrgUser orgUser = orgComUserServiceImpl.get(orgUserId);
		List<UserInfoReq> data = new ArrayList<UserInfoReq>();
		UserInfoReq userInfoReq = new UserInfoReq();
		userInfoReq.setAge(orgUser.getAge());
		userInfoReq.setDevicePhone(orgUser.getPhoneNumber());//*** 这个电话号码是老人手机的  不是老人手表的 需要调整
		userInfoReq.setGuardianPhone(orgUser.getGuardianPhoneNumber());
		userInfoReq.setGuardianRelation(5);//与监护人关系(1父子 2 母子 3父女 4母女 5其他)
		userInfoReq.setHeight(orgUser.getHeight()==null?null:Integer.valueOf(orgUser.getHeight()+""));
		userInfoReq.setWeight(orgUser.getWeight()==null?null:Double.valueOf(orgUser.getWeight()+""));
		userInfoReq.setImei(efOrgDevice.getImei());
		userInfoReq.setName(orgUser.getName());
		userInfoReq.setOperType(operType);
		userInfoReq.setSex(orgUser.getSex());
		
		data.add(userInfoReq);
		
		Context ctx = new Context();
		ctx.set("orgKey", org.getIkey());
		ctx.set("userId", -1);
		Response<Object> bizRes = httpUtil.http(serverUrl,userInfoPath, ctx,data, Object.class);
		
		if(bizRes.getStatus().getCode() == StatusCode.OK.getValue()){
			//成功  org_user状态修改
			orgUser.setStatus(operType);
			orgComUserServiceImpl.save(ctx, orgUser);
			flag = true ;
		}
		
		return flag;
		
	}

	
	
	public EjlUser getEjlUserByPhoneNumber(String phoneNumber) throws BizException{
		EjlUser user = new EjlUser();
		user.setPhone(phoneNumber);
		user = ejlComUserServiceImpl.selectOneObjByAttribute(new Context(), user);
		return user;
	}
	
	
	/*public void ttt(Context ctx,Long orgUserId,Long orgDeviceId) throws BizException{
		
		OrgUser orgUser = orgComUserServiceImpl.get(orgUserId);
		EfOrgDevice efOrgDevice = efComOrgDeviceServiceImpl.get(orgDeviceId);
		
		//*** 获取到用户的紧急联系人
		EjlUser userEmergency = getEjlUserByPhoneNumber(orgUser.getEmergencyContactPhone());
		if(userEmergency == null){
			userEmergency = becomeSystemUser(ctx,orgUser.getEmergencyContactPhone(),orgUser.getEmergencyContact(),orgUser.getName());
		}
		
		//*** 
		
		
		
	}*/
	
	public EjlUser becomeSystemUser(Context ctx,String phoneNumber,String name,String familyName) throws BizException{
		
		//*** 创建用户
	    EjlUser euser = getEjlUserByPhoneNumber(phoneNumber);
	    if(euser == null){
		    euser = new EjlUser();
			euser.setAppType(1);//1 亿家联
			euser.setPhone(phoneNumber);
			euser.setStatus(0L);// 0:不在线    1:在线
			euser.setType(EfamilyConstant.USER_TYPE_APP);
			euser.setNickName(phoneNumber);
			euser.setHeadImg("1");//*** 最好给定一个默认的值  在没有头像的时候 统一显示一个头像
			ejlComUserServiceImpl.save(new Context(),euser);
	    }

	    
		//*** 创建家庭
	    EjlFamily ejlFamily = new EjlFamily();
		if(euser.getFamilyId()==null){
			String familyCode = bizNumberUtils.getNumber(BizNumberUtils.Type.EFAMILY_NUMBER);//要避免familycode重复的问题
			ejlFamily.setName(familyName);
			ejlFamily.setCode(familyCode);
			ejlComFamilyServiceImpl.save(ctx,ejlFamily);  
		}

		
		//*** 创建家庭用户关系
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setUserId(euser.getId());
		ejlFamilyUser.setFamilyId(ejlFamily.getId());
		ejlFamilyUser.setStatus(0L);
		ejlFamilyUser.setType(1L);
		ejlFamilyUser.setRoleId(1L);
		ejlFamilyUser.setManageType(EfamilyConstant.MANAGE_TYPE_AGREE);
		ejlFamilyUser.setPosition(Position.HOST.getValue());
		ejlFamilyUser.setIsForbitSpeak(2);
		
		//*** 确认加入家庭状态和创建者ID是否要添加
		ejlComFamilyUserServiceImpl.save(ctx,ejlFamilyUser);
		
		//*** 更新用户中的家庭字段
		euser.setFamilyId(ejlFamily.getId());
		ejlComUserServiceImpl.save(ctx,euser);
        
		return euser;
	}
	
	
	private static Integer pagesize = 20;
	
	public List<OrgUnbindDeviceBaseInfo> getOrgUnbindDeviceBaseInfo(Long orgId,Integer start,String imei){
		return efComOrgDeviceServiceImpl.getOrgUnbindDeviceBaseInfo(orgId, start*pagesize, pagesize,imei);
	}
	
	public List<OrgBindDeviceBaseInfo> getOrgBindDeviceBaseInfo(Long orgId,Integer start,String queryValue){
		
		String orgUserName = null;
		String imei = null;
		if(StringUtils.isNotBlank(queryValue)){
			if(DataHandlerUtil.isNumeric(queryValue)){
				imei = queryValue;
			}else{
				orgUserName = queryValue;
			}
		}
		return orgComUserServiceImpl.getOrgBindDeviceBaseInfo(orgId, start*pagesize, pagesize, orgUserName, imei);
	}
	
	public List<OrgUnbindUserBaseInfo> getOrgUnbindUserBaseInfo(Long orgId,Integer start,String queryValue){
		String name = null;
		String phoneNumber = null;
		if(StringUtils.isNotBlank(queryValue)){
			if(DataHandlerUtil.isNumeric(queryValue)){
				phoneNumber = queryValue;
			}else{
				name = queryValue;
			}			
		}
		
		return orgComUserServiceImpl.getOrgUnbindUserBaseInfo(orgId, start*pagesize, pagesize,name,phoneNumber);
	}
	
	public WatchParamBaseInfo getWatchParamBaseInfo(Long orgUserId) throws BizException{
		WatchParamBaseInfo watchParamBaseInfo = orgComUserServiceImpl.getWatchParamBaseInfo(orgUserId);
				
		 EjlDeviceParmConfig ejlDeviceParmConfig = ejlComDeviceConfigServiceImpl.getByDeviceIdAndKey(watchParamBaseInfo.getDeviceId(), "location_onff");//设备定位是否打开   ejl_device_parm_config.
		 
		 String  locationFlag = ejlDeviceParmConfig.getParamValue() ;
		 Qrcode qrcode = qrcodeServiceImpl.getByImei(watchParamBaseInfo.getImei());
		 String  deviceQrcode = qrcode.getQrcodeResourceId();//设备二维码   ef_qrcode.qrcode_resource_id
		 
		 List<DeviceAddressListDto> deviceAddressListDtoList =  getDeviceAddressListDto(watchParamBaseInfo.getUserId()); 
		 watchParamBaseInfo.setDeviceAddressBook(deviceAddressListDtoList);
		 watchParamBaseInfo.setDeviceQrcode(deviceQrcode);
		 watchParamBaseInfo.setLocationFlag(Integer.valueOf(locationFlag==null?"0":locationFlag));
		 
		return watchParamBaseInfo;		
	}

	
	public List<DeviceAddressListDto> getDeviceAddressListDto(Long userId) throws BizException{
		List<DeviceAddressListDto> deviceAddressListDtoList = new ArrayList<DeviceAddressListDto>();
		 List<EjlDeviceAddressList> ejlDeviceAddressListList = ejlComDeviceAddressListServiceImpl.getAddressListByUser(userId);
		 if(ejlDeviceAddressListList != null){
            for(EjlDeviceAddressList ejlDeviceAddressList:ejlDeviceAddressListList){
            	DeviceAddressListDto deviceAddressListDto = new DeviceAddressListDto();
            	deviceAddressListDto.setHeadImage(ejlDeviceAddressList.getHeadImage());
            	deviceAddressListDto.setIsSos(ejlDeviceAddressList.getIsSos());
            	deviceAddressListDto.setName(ejlDeviceAddressList.getName());
            	deviceAddressListDto.setUserId(ejlDeviceAddressList.getUserId());
            	deviceAddressListDto.setPhoneNumber(ejlDeviceAddressList.getPhoneNumber());
            	
            	deviceAddressListDtoList.add(deviceAddressListDto);
            }
		 }
		
		return deviceAddressListDtoList;
	}
	
	public WatchParamBaseInfo saveWatchParamBaseInfo(WatchParamBaseInfo watchParamBaseInfo) throws BizException{
		 WatchParamBaseInfo watchParamBaseInfoData = orgComUserServiceImpl.getWatchParamBaseInfo(watchParamBaseInfo.getOrgUserId());
		 Long userId = watchParamBaseInfo.getUserId();
		 Long deviceId = watchParamBaseInfo.getDeviceId();
		 EjlUser user = ejlComUserServiceImpl.get(userId);
		 Long hostUserId = ejlComFamilyUserServiceImpl.getFamilyHostUserId(user.getFamilyId());
		 
		 Context ctx = new Context();
		 ctx.set("userId", hostUserId);
		 
		 //***需要推送的参数
		 
		 //修改设备电话号码 100018
		 updateDeviceParm(ctx, userId, deviceId, "100018", watchParamBaseInfo.getSimPhoneNumber());
		 
		 //是否打开定位  400014
		 updateDeviceParm(ctx, userId, deviceId, "400014", watchParamBaseInfo.getLocationFlag()+"");		 
		 
		 //修改通讯录号码  100003
		 updateDeviceParm(ctx, userId, deviceId, "100003", JsonUtils.toJson(watchParamBaseInfo.getDeviceAddressBook()));		 
		 
		 //***  不需要推送的参数
		 EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(deviceId);
		 ejlDevice.setName(watchParamBaseInfo.getDeviceName());
		 ejlDevice.setPhoneNumber(watchParamBaseInfo.getSimPhoneNumber());
		 ejlComDeviceServiceImpl.save(ctx, ejlDevice);
		 
		 OrgUser orgUser = orgComUserServiceImpl.get(watchParamBaseInfoData.getOrgUserId());
		 orgUser.setName(watchParamBaseInfo.getOrgUserName());
		 orgUser.setPhoneNumber(watchParamBaseInfo.getPhoneNumber());
		 orgUser.setGuardianPhoneNumber(watchParamBaseInfo.getGuardianPhoneNumber());
		 orgComUserServiceImpl.save(ctx, orgUser);
		 
		 
		return watchParamBaseInfo;		
	}
	
	
	public void updateDevicePhoneNumber(Context ctx,Long userId,Long deviceId,String simPhoneNumber) throws BizException{
		 WatchDeviceManageRequest  bizReqList =  new WatchDeviceManageRequest();
		 EjlUser user = ejlComUserServiceImpl.get(userId);
		 bizReqList.setWatchId(deviceId+"");
		 bizReqList.setParameterIndex("100018");
		 bizReqList.setParameterContext(simPhoneNumber);
		 bizReqList.setFamilyId(user.getFamilyId()+"");
		 bizReqList.setOprType( 1L );
		 Response<WatchDeviceManageResponse> bizRes = httpUtil.http(serverUrl,watchDeviceManage,ctx,bizReqList,WatchDeviceManageResponse.class);
		 if(bizRes==null || bizRes.getStatus().getCode()!=0 ){
			 log.error("修改设备电话号码出现异常: simPhoneNumber = "+simPhoneNumber+" ; ");
			 throw new BizException();
		 }
	}
	
	public void updateDeviceParm(Context ctx,Long userId,Long deviceId,String parameterIndex,String parameterContext) throws BizException{
		 WatchDeviceManageRequest  bizReqList =  new WatchDeviceManageRequest();
		 EjlUser user = ejlComUserServiceImpl.get(userId);
		 bizReqList.setWatchId(deviceId+"");
		 bizReqList.setParameterIndex(parameterIndex);
		 bizReqList.setParameterContext(parameterContext);
		 bizReqList.setFamilyId(user.getFamilyId()+"");
		 bizReqList.setOprType( 1L );
		 Response<WatchDeviceManageResponse> bizRes = httpUtil.http(serverUrl,watchDeviceManage,ctx,bizReqList,WatchDeviceManageResponse.class);
		 if(bizRes==null || bizRes.getStatus().getCode()!=0 ){
			 log.error("修改设备参数时出现异常: parameterContext = "+parameterContext+" ; ");
			 throw new BizException();
		 }
	}
	
	
	public void updateDeviceAddressBook(String imei,List<EjlDeviceAddressList> deviceAddressBook,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JsonMapper om=new JsonMapper();
		try {
			List<WatchDeviceManageRequest> request = new ArrayList<WatchDeviceManageRequest>();
			WatchDeviceManageRequest wd = new WatchDeviceManageRequest();
			wd.setParameterContext(om.toJson(deviceAddressBook));
			wd.setParameterIndex(imei);
			request.add(wd);
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,urlPath, ctx,request, Object.class);
			map.put("resultCode", bizRes.getStatus().getCode());
			map.put("errMsg", "设置通讯录时出现异常");
		} catch (BizException e) {
			e.getCode();
			log.error(e.getCode() + " " + e.getMessage(), e);
			map.put("resultCode", e.getCode() + "");
			map.put("errMsg", e.getMessage());
		} catch (Exception e1) {
			map.put("resultCode", "-1");
			map.put("errMsg", "unknown error.");
			log.error(e1.getMessage());
		}
	}	
	
	
	Object contacts(ComOrgReq<List<ContactReq>> req,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JsonMapper om=new JsonMapper();
		try {
			List<ContactReq> data = new ArrayList<ContactReq>();

			List<WatchDeviceManageRequest> request = new ArrayList<WatchDeviceManageRequest>();
			for(ContactReq contactReq:data){
//List<EjlDeviceAddressList> deviceAddressBook
				EjlDeviceAddressList[] addressBook = new EjlDeviceAddressList[contactReq.getContacts().size()];
				int i=0;
				for(Contact contact:contactReq.getContacts()){
					EjlDeviceAddressList phoneAddressBookStruc = new EjlDeviceAddressList();
					phoneAddressBookStruc.setName(contact.getNickName());
					phoneAddressBookStruc.setPhoneNumber(contact.getPhoneNumber());
					phoneAddressBookStruc.setIsSos(Long.valueOf(contact.getIsSos()));
					phoneAddressBookStruc.setHeadImage("");
					addressBook[i++] = phoneAddressBookStruc;
				}
				if(addressBook.length>0){
					WatchDeviceManageRequest wd = new WatchDeviceManageRequest();
					wd.setParameterContext(om.toJson(addressBook));
					wd.setParameterIndex(contactReq.getImei());
					request.add(wd);
				}
			}
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,urlPath, ctx,request, Object.class);
			map.put("resultCode", bizRes.getStatus().getCode());
			map.put("errMsg", "设置通讯录时出现异常");
		} catch (BizException e) {
			e.getCode();
			log.error(e.getCode() + " " + e.getMessage(), e);
			map.put("resultCode", e.getCode() + "");
			map.put("errMsg", e.getMessage());
		} catch (Exception e1) {
			map.put("resultCode", "-1");
			map.put("errMsg", "unknown error.");
			log.error(e1.getMessage());
		}
		return map;
	}
	
	
	
}
