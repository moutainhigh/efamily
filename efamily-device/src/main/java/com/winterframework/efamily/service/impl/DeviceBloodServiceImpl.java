package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.http.IHttpClient;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dao.IEjlComUserDao;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IDeviceBloodService;
import com.winterframework.efamily.service.IEjlComHealthHeartRateService;
import com.winterframework.efamily.utils.HealthUtil;
import com.winterframework.efamily.utils.NotificationUtil;
 

@Service("deviceBloodServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class DeviceBloodServiceImpl extends EjlComHealthBloodPressureServiceImpl implements IDeviceBloodService{

	@Resource(name = "ejlComUserDaoImpl")
	private IEjlComUserDao ejlComUserDaoImpl; 	
	@Resource(name="httpClientImpl")
	protected IHttpClient httpClientImpl;
	@Resource(name = "notificationUtil")
	private NotificationUtil notificationUtil;
	@Resource(name = "ejlComHealthHeartRateServiceImpl")
	private IEjlComHealthHeartRateService ejlComHealthHeartRateService;
	
	@Override
	public void save(Context ctx,Long userId,Long deviceId,List<EjlHealthBloodPressure> bloodList) throws BizException {
		if(null!=bloodList){
			save(ctx,bloodList);
			//保存心率数据
			ejlComHealthHeartRateService.save(ctx,getHeartRateList(bloodList));
		}
	}
	public static List<EjlHealthHeartRate> getHeartRateList(List<EjlHealthBloodPressure> bloodList){
		if(null==bloodList) return null;
		List<EjlHealthHeartRate> rateList=new ArrayList<EjlHealthHeartRate>();
		for(EjlHealthBloodPressure blood:bloodList){
			if(null!=blood.getRate() && blood.getRate()<=0) continue;
			
			EjlHealthHeartRate heartRate=new EjlHealthHeartRate();  
			heartRate.setUserId(blood.getUserId());
			heartRate.setDeviceId(blood.getDeviceId());
			heartRate.setRate(new Long(blood.getRate()));
			heartRate.setTimeSpan(new Long(HealthUtil.getTimeSpan(blood.getToTime(),blood.getFromTime())));
			heartRate.setFromTime(blood.getFromTime());
			heartRate.setToTime(blood.getToTime());
			rateList.add(heartRate);
		}
		return rateList;
	}
	
	public void notifyForHeartUpdate(Context ctx,Long userId,List<EjlHealthBloodPressure> bloodList) throws BizException{
		EjlHealthBloodPressure bloodPressure = getLastEjlHealthBloodPressure(bloodList);
		if(bloodPressure == null){
			return;
		}
		Map<String,String> data = new HashMap<String,String>();
		data.put("currentHeartRate", bloodPressure.getRate()+"");
		data.put("updateType", EfamilyConstant.UPDATE_HEALTHY_TYPE_BLOOD +"");
		data.put("userId",  userId+"");
		data.put("deviceId", ctx.getDeviceId()+"");
		data.put("systolicPressure", bloodPressure.getHigh()+"");
		data.put("diastolicPressure", bloodPressure.getLow()+"");
		EjlUser userWatch =  ejlComUserDaoImpl.getById(userId);
		EjlUser user = new EjlUser();
		user.setFamilyId(userWatch.getFamilyId());
		user.setType(EfamilyConstant.USER_TYPE_APP);
		List<EjlUser> userList = ejlComUserDaoImpl.selectListObjByAttribute(user);
		notifyUser(data,userList,userId,NoticeType.UPDATE_HEALTHY,null);
	}
	
	public EjlHealthBloodPressure getLastEjlHealthBloodPressure(List<EjlHealthBloodPressure> bloodList){
		if(bloodList!=null &&bloodList.size()>0){
			sort(bloodList,false);
			return bloodList.get(0);
		}
		return null;
	}
	protected void sort(List<EjlHealthBloodPressure> bloodList,final boolean isAsc){
		if(null!=bloodList && bloodList.size()>0){
			Collections.sort(bloodList, 
			new Comparator<EjlHealthBloodPressure>(){
				int k=isAsc?1:-1;
				@Override
				public int compare(EjlHealthBloodPressure o1, EjlHealthBloodPressure o2) {
					if(DateUtils.getDate(o1.getToTime()).after(DateUtils.getDate(o2.getToTime()))){
						return 1*k;
					}else{
						if(o1.getToTime().equals(o2.getToTime())){
							return 0;
						}
					}
					return -1*k;
				}
			});
		}
	}
	
	public void notifyUser(Map<String,String> data,List<EjlUser> userList,Long userId,NoticeType noticeType,Long notNoticeUserId){
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
				 
				notificationUtil.notification(notification);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("推送[notifyUser]时出现异常：  userId = "+userTemp.getId());
			}
   		}
     }
}
