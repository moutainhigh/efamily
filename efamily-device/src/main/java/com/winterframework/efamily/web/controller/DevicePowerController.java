package com.winterframework.efamily.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.dto.device.DevicePowerRequest;
import com.winterframework.efamily.dto.device.DevicePowerResponse;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IDeviceParamConfigService;
import com.winterframework.efamily.service.IDeviceSittingService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.modules.utils.SpringContextHolder;
 
@Controller("devicePowerController")
@RequestMapping("/device/system")
public class DevicePowerController {
	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl; 	
	@Resource(name="httpClientImpl")
	protected IHttpClient httpClientImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	@Resource(name = "deviceParamConfigServiceImpl")
	private IDeviceParamConfigService deviceParamConfigService;
	
	@Resource(name = "ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceServiceImpl;
	
	@Resource(name="ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageService; 
	
	@Resource(name = "deviceSittingServiceImpl")
	private IDeviceSittingService deviceSittingServiceImpl;
	
	private final PropertyUtil propertyUtil=SpringContextHolder.getBean("propertyUtil");
	
	private static final Logger log = LoggerFactory.getLogger(DevicePowerController.class);
	
	@RequestMapping("/power/onff")
	@ResponseBody
	public Object onff(@RequestBody Request<DevicePowerRequest> req) throws BizException{
		/**
		 * ?????保存至设备表
		 */
		DevicePowerRequest bizReq=req.getData();
		Response<DevicePowerResponse> res=new Response<DevicePowerResponse>(req);
		Context ctx = new Context();
		ctx.set("userId", req.getUserId());
		Map<String,String> data = new HashMap<String,String>();
		data.put("userId",  req.getUserId()+"");
		data.put("deviceId",  req.getDeviceId()+"");
		data.put("type", bizReq.getStatus()+"");
		EjlUser userWatch =  ejlComUserDaoImpl.getById( req.getUserId());
		String nickName = userWatch.getNickName()==null?userWatch.getPhone():userWatch.getNickName();
		data.put("nickName", nickName);
		data.put("time", DateUtils.convertDate2Long(DateUtils.currentDate())+"");
		EjlUser user = new EjlUser();
		user.setFamilyId(userWatch.getFamilyId());
		user.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> userList = ejlComUserDaoImpl.selectListObjByAttribute(user);
		EjlDevice ejlDevice = new EjlDevice();
		ejlDevice.setId(req.getDeviceId());
		ejlDevice.setOnlineStatus(bizReq.getStatus());
		//关机把睡眠状态设置为未开始 ,久坐开关设置为未开始
		if(bizReq.getStatus().intValue()!=1){
			try{
				ejlDevice.setSleeplockStatus(0);
				deviceSittingServiceImpl.saveSitSwitch(ctx, req.getUserId(), req.getDeviceId(), 0);
			}catch(Exception e){
				log.error("offline set sitswitch and sleepswitch off", e);
			}
		} 
		ejlComDeviceServiceImpl.save(ctx, ejlDevice);
		notifyUser(data,userList,req.getUserId(),NoticeType.DEVICE_ONOFF,null,false);
		res.setStatus(new Status(StatusCode.OK.getValue()));
		
		//***** 新增一条消息发送到家庭聊天组  手表开关机  ***********
		EjlMessage entity=new EjlMessage(); 
		entity.setSendUserId( req.getUserId());
		entity.setReceiveUserId(userWatch.getFamilyId());
		entity.setChatRoomId(userWatch.getFamilyId());
		entity.setContentType(Long.valueOf(EjlMessage.ContentType.TIPS.getCode())); 
		entity.setChatType(new Long(EjlMessage.ChatType.FML_GROUP.getValue()));
		String content = "";
		if(bizReq.getStatus().intValue()==1){
			content = propertyUtil.getProperty("notice.watch.power.no").replace("@_0_@", nickName);
		}else{
			content = propertyUtil.getProperty("notice.watch.power.off").replace("@_0_@", nickName);
		}
		entity.setContent(content);
		entity.setContentTime(0L);
		entity.setAppSendTime(Long.valueOf(DateUtils.format(new Date(),"yyyyMMddHHmmssSSS")));
		entity.setStatus(0);
		ejlComMessageService.save(ctx,entity);
		
		return res;
	}
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId,boolean isRealTime){
    	if(userList == null || userList.size()==0){
			log.info("需要推送的用户为空。 userId = "+userId+" ; noticeType = "+noticeType);
			return;
    	}
   		for(EjlUser userTemp : userList){
   			if(notNoticeUserId!=null && notNoticeUserId.longValue() == userTemp.getId().longValue()){
   				continue;
   			} 
   			try { 
				NotyTarget t=new NotyTarget(userTemp.getId(),null); 
				
				//Map<String,String> paramMap=userNotice.getParamMap();
				Map<String,String> data2=new HashMap<String,String>(); 
				data2.put("noticeType", noticeType.getValue()+"");
				data2.putAll(data);
				
				/*String content=propertyUtil.getProperty(userNotice.getNoticeType().getValue()+"");
				data.put("content", replaceParam(content,paramMap));*/
				
				NotyMessage message=new NotyMessage();
				message.setId(null);
				message.setType(NotyMessage.Type.NOTICE);
				message.setCommand(EfamilyConstant.PUSH);
				message.setData(data2);
				Notification notification=new Notification();
				notification.setTarget(t);
				notification.setMessage(message);
				notification.setRealTime(isRealTime);
				
				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("推送[notifyUser]时出现异常：  userId = "+userTemp.getId());
			}
   		}
     }
}
