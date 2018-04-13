package com.winterframework.efamily.service.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.PropertyUtil;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComLocationDao;
import com.winterframework.efamily.dao.IEjlComUserBarrierDao;
import com.winterframework.efamily.dao.IEjlComUserDeviceDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EfDeviceAlarm;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlMessage;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.entity.UserDeviceBarrierStruc;
import com.winterframework.efamily.service.IEfComLocationSemiService;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.IEjlComMessageService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.service.ITskDeviceAlarmService;
import com.winterframework.efamily.thirdparty.sms.ISmsService;
import com.winterframework.efamily.utils.LocationUtil;
import com.winterframework.efamily.utils.NotificationUtil;
import com.winterframework.efamily.utils.NotifyUserUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.utils.SpringContextHolder;

public class ElectronicFenceNoticeTask {

	private Logger log = LoggerFactory.getLogger(ElectronicFenceNoticeTask.class);
	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationServiceImpl;
	
	@Resource(name = "ejlComUserDeviceDaoImpl")
	private IEjlComUserDeviceDao ejlComUserDeviceDaoImpl;
	
	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name="ejlComLocationDaoImpl")
	private IEjlComLocationDao ejlComLocationDaoImpl;
	
	@Resource(name = "ejlComMessageServiceImpl")
	private IEjlComMessageService ejlComMessageServiceImpl;
	
	@Resource(name = "ejlComUserBarrierDaoImpl")
	private IEjlComUserBarrierDao ejlComUserBarrierDaoImpl;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "notificationUtil")
	protected NotificationUtil notificationUtil;
	
	@Resource(name="smsServiceImpl")
	private ISmsService smsService;
	
	@Resource(name="efComLocationSemiServiceImpl")
	private IEfComLocationSemiService efComLocationSemiServiceImpl;
	
	@PropertyConfig(value="sms.message.template.ElectronicFence")
	private String messageTemplate;
	
	private final PropertyUtil propertyUtil=SpringContextHolder.getBean("propertyUtil");
	@Resource(name = "tskDeviceAlarmServiceImpl")
	private ITskDeviceAlarmService tskDeviceAlarmService;
	
	@Resource(name="qrcodeServiceImpl")
	protected IQrcodeService qrcodeServiceImpl;	
	
	@Resource(name="ejlComDeviceServiceImpl")
	protected IEjlComDeviceService ejlComDeviceServiceImpl;	
	
	
	public void execute()throws Exception{
		Long userBarrierId = 0L;
		while(true){
			//*** 每次查500个围栏  并记录最后一个围栏的ID 
			List<UserDeviceBarrierStruc> userDeviceBarrierStrucList = ejlComUserBarrierDaoImpl.getUserBarrierList(userBarrierId,null);
			if(userDeviceBarrierStrucList!=null&&userDeviceBarrierStrucList.size()>0){
				log.debug("userDeviceBarrierStrucList : "+userDeviceBarrierStrucList.size());
				for(UserDeviceBarrierStruc userDeviceBarrierStruc:userDeviceBarrierStrucList){
					log.debug("userDeviceBarrierStruc : "+userDeviceBarrierStruc.toString());
					userBarrierId = userDeviceBarrierStruc.getUserBarrierId();
					//********  查找最后一条记录
					try{
					EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(userDeviceBarrierStruc.getUserId(), userDeviceBarrierStruc.getDeviceId());
					if(efLocationSemi==null){
						continue;
					}
					String type = redisClient.get(EfamilyConstant.LASTELECTRONICFENCENOTICE +"_"+userDeviceBarrierStruc.getDeviceId()+"_"+userDeviceBarrierStruc.getUserBarrierId());
				    if(type == null){
				    	type ="-1";
				    }
				    int currentType = getMoveDirection_back(efLocationSemi.getLocation(),userDeviceBarrierStruc.getLocation(),userDeviceBarrierStruc.getRadius());
				    log.debug("===============>current user status:"+currentType+"   last user status:"+type+" current user point userBarrierId:"+userBarrierId);
					if((currentType == 1||currentType == 2)&&currentType!=Integer.valueOf(type)){
						EjlUser user = ejlUserDaoImpl.getUserByUserId(userDeviceBarrierStruc.getUserId());
						EjlUser userSelect = new EjlUser();
						userSelect.setFamilyId(user.getFamilyId());
						userSelect.setType(EfamilyConstant.USER_TYPE_APP);
						List<EjlUser> userList = ejlUserDaoImpl.getEjlUserByAttribute(userSelect);
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("userId", userDeviceBarrierStruc.getUserId() + "");
						paramMap.put("deviceId", userDeviceBarrierStruc.getDeviceId() + "");
						paramMap.put("nickName", user.getNickName());
						paramMap.put("headImgUrl", user.getHeadImg());
						paramMap.put("type", String.valueOf(currentType));
						paramMap.put("remark", userDeviceBarrierStruc.getBarrierRemark());
						paramMap.put("time", DateUtils.convertDate2Long(efLocationSemi.getTimeEnd())+"");
						redisClient.set(EfamilyConstant.LASTELECTRONICFENCENOTICE +"_"+userDeviceBarrierStruc.getDeviceId()+"_"+userDeviceBarrierStruc.getUserBarrierId(),String.valueOf(currentType));
						if(userDeviceBarrierStruc.getOrgTag() == null||userDeviceBarrierStruc.getOrgTag()==0){
							//***** 新增一条消息发送到家庭聊天组  进入或者离开围栏  ***********
							EjlMessage message=new EjlMessage(); 
							message.setSendUserId( user.getId());
							message.setReceiveUserId(user.getFamilyId());
							message.setChatRoomId(user.getFamilyId());
							message.setContentType(Long.valueOf(EjlMessage.ContentType.TIPS.getCode())); 
							message.setChatType(new Long(EjlMessage.ChatType.FML_GROUP.getValue()));
							String nickName = StringUtils.isNotBlank(user.getNickName())?user.getNickName():user.getPhone();
							String content = "";
							if(currentType==1){
								content = propertyUtil.getProperty("notice.watch.fence.in").replace("@_0_@", nickName).replace("@_1_@", userDeviceBarrierStruc.getBarrierRemark());
							}else{
								content = propertyUtil.getProperty("notice.watch.fence.out").replace("@_0_@", nickName).replace("@_1_@", userDeviceBarrierStruc.getBarrierRemark());
							}
							message.setContent(content);
							message.setContentTime(0L);
							message.setAppSendTime(Long.valueOf(DateUtils.format(new Date(),"yyyyMMddHHmmssSSS")));
							message.setStatus(0);
							Context ctx = new Context();
							ctx.set("userId", user.getId());
							ejlComMessageServiceImpl.save(ctx,message);
							NotifyUserUtil.notifyUser(paramMap, userList, null, NoticeType.ELECTRONIC_WARNING, null,notificationUtil,false);
						}
						
						EjlDevice ejlDevice = ejlComDeviceServiceImpl.get(userDeviceBarrierStruc.getDeviceId());
						Qrcode qrcode = qrcodeServiceImpl.getByImei(ejlDevice.getCode());
						int customerNumber = qrcode.getCustomerNumber();
						
						if((userDeviceBarrierStruc.getOrgTag() != null && userDeviceBarrierStruc.getOrgTag()!=0)
							|| customerNumber==100009	){//赑勰
							Context ctx = new Context();
							ctx.set("userId", user.getId());
							log.info("围栏告警： fence generate device alarm.userId="+userDeviceBarrierStruc.getUserId()+" device_id="+userDeviceBarrierStruc.getDeviceId());
							EfDeviceAlarm deviceAlarm=new EfDeviceAlarm();
							deviceAlarm.setUserId(userDeviceBarrierStruc.getUserId());
							deviceAlarm.setDeviceId(userDeviceBarrierStruc.getDeviceId());
							deviceAlarm.setType(EfDeviceAlarm.Type.FENCE.getValue());
							Map<String,String> map=new HashMap<String,String>();
							map.put("location",new LatLng(userDeviceBarrierStruc.getLocation()).toString());
							map.put("radius", userDeviceBarrierStruc.getRadius()+"");
							map.put("type", currentType+"");
							deviceAlarm.setValue(JsonUtils.toJson(map));
							deviceAlarm.setTime(efLocationSemi.getTimeEnd().getTime());
							deviceAlarm.setStatus(EfDeviceAlarm.Status.UNHANDLED.getValue());
							tskDeviceAlarmService.save(ctx, deviceAlarm);
						}
					}
					}catch(Exception e){
						log.error("userDeviceBarrierStruc :" +userDeviceBarrierStruc.toString(), e);
					}
				}
			}else{
				break;
			}
			
		}
	}
	
	//0无  1进    2出    3里     4 外
	private int getMoveDirection_back(String locationA ,String locationB,Long distance){
		LatLng a = new LatLng(locationA);
		LatLng b = new LatLng(locationB);
		double distanceTemp = LocationUtil.getDistance(a, b)-distance;
		if(Math.abs(distanceTemp)>LocationUtil.JUHEDISTANCE){
			return distanceTemp>LocationUtil.JUHEDISTANCE?2:1;
		}
		return 0;
	}
	
	public static void main(String[] args){
		//double dd = Distance(113.949562,22.538105,113.94366600,22.54880633 );
		//1329.8726787404782  ：796.0259369740851   113.94366600 | 22.54880633  :814.3000668536665
		LatLng a = new LatLng("22.538105,113.949562");
		LatLng b = new LatLng("22.54880633,113.94366600");
		double tt = LocationUtil.getDistance(a, b);
		//System.err.println(dd);
		System.err.println(tt);
	}
	
}
