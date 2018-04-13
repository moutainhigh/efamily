package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.redis.RedisQueue;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEjlLocationDao;
import com.winterframework.efamily.dto.GetUserLocationResponse;
import com.winterframework.efamily.dto.LocationStruc;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.enums.QueuePrefix;
import com.winterframework.efamily.service.IEfComLocationSemiService;
import com.winterframework.efamily.service.IEjlLocationService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlLocationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlLocationServiceImpl  extends EjlComLocationServiceImpl implements IEjlLocationService {
	@Resource(name="ejlLocationDaoImpl")
	private IEjlLocationDao ejlLocationDao;
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@Resource(name="efComLocationSemiServiceImpl")
	private IEfComLocationSemiService efComLocationSemiServiceImpl;
	@Resource(name = "RedisClient")
	private RedisClient redisClient;
	@Resource(name = "redisQueue")
	private RedisQueue redisQueue;   
	
	@PropertyConfig("location.query.notice.forward.seconds")
	private Integer forwardSeconds; 
	
	@Override
	protected IEjlLocationDao getEntityDao() { 
		return ejlLocationDao;
	}

	@Override
	public GetUserLocationResponse getUserLastLocation(Context ctx,Long userId, Long deviceId,Long time) throws Exception{
		GetUserLocationResponse getUserLocationResponse = new GetUserLocationResponse();
		EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(userId,deviceId);
		getUserLocationResponse.setDeviceId(deviceId);
		getUserLocationResponse.setUserId(userId);
		if(null!=efLocationSemi){
			if(DateUtils.isBefore(DateUtils.currentDate(), DateUtils.addSeconds(efLocationSemi.getTimeEnd(), forwardSeconds))){
				getUserLocationResponse.setNeedWait(2);
			}else{
				getUserLocationResponse.setNeedWait(1);
			}
			getUserLocationResponse.setAddressDesc(efLocationSemi.getAddress());
			getUserLocationResponse.setLat(String.valueOf(efLocationSemi.getLatitude()));
			getUserLocationResponse.setLng(String.valueOf(efLocationSemi.getLongitude()));
			getUserLocationResponse.setPrecision(efLocationSemi.getRadius()+"");
			getUserLocationResponse.setTime(efLocationSemi.getTimeEnd().getTime()+"");
			getUserLocationResponse.setType(efLocationSemi.getType());
		}else{
			getUserLocationResponse.setNeedWait(1);
		}
		if(1==getUserLocationResponse.getNeedWait()){
			int handleSecds=5*60;	//处理秒数  最多5分钟处理时间
			String target=userId+Separator.vertical+deviceId;
			//*** 此处有个redis事务并发问题需要处理   多人同时操作同一设备
			//查询定位的用户加入队列 静候推送（不管能不能拿到锁）
			String queueKey=QueuePrefix.QUEUE_LOCATION_QUERY+target;
			redisQueue.add(queueKey, ctx.getUserId()+Separator.vertical+time);	//加入待推送队列		
			final String key=QueuePrefix.QUEUE_LOCATION_QUERY_LOCK+target;
			if(redisClient.lock(key, handleSecds)){		//拿到锁就处理 没拿到锁则等待已在处理的推送即可（有可能本次推送的不一定是最新的)
				//如果没有处理中  则进入处理逻辑
				try{
					Map<String,String> map = new HashMap<String,String>();
					map.put("userId", userId+"");
					map.put("deviceId", deviceId+"");
					map.put("commond", "20626");
					ejlUserServiceImpl.pushDevice(userId,deviceId, map,20626, NotyMessage.Type.OPER);
				}catch(Exception e){
					log.error("user Locaiton query error",e);
					redisClient.unlock(key);
				}
			}
		}
		return getUserLocationResponse;
	}
	
	
	@Override
	public  List<LocationStruc> getUserLocation(Long userId, Long watchId,
			Long recentHourType, Long queryType) throws Exception {
		List<LocationStruc> locations=new ArrayList<LocationStruc>();
		if(queryType.intValue()==1){
			EfLocationSemi efLocationSemi = efComLocationSemiServiceImpl.getLastLocation(userId,watchId);
			if(efLocationSemi!=null){
				LocationStruc struc=new LocationStruc();
				struc.setLocation(efLocationSemi.getLocation());
				struc.setType(efLocationSemi.getType());
				struc.setTime(efLocationSemi.getTimeEnd().getTime());
				struc.setAddressDesc(efLocationSemi.getAddress());
				struc.setDuration(DateUtils.calcMinutesBetween(efLocationSemi.getTimeBegin(), efLocationSemi.getTimeEnd()));
				locations.add(struc);
			}
		}else{
			Date time=DateUtils.addHours(new Date(), -recentHourType.intValue());
			List<EjlLocation> ejlLocations=ejlLocationDao.getUserLocation(userId, watchId,time);
			if(!ejlLocations.isEmpty()){
				EjlLocation locationBefore = null;
				for(int i=0;i<ejlLocations.size();i++){
					EjlLocation lo = ejlLocations.get(i);
					LocationStruc struc=new LocationStruc();
					struc.setLocation(lo.getLocation());
					struc.setType(lo.getType());
					struc.setTime(DateUtils.addSeconds(lo.getTime(),lo.getTimeStay()).getTime());
					struc.setAddressDesc(lo.getAddress());
					struc.setDuration(Long.valueOf(lo.getTimeStay()/60));
					if(locationBefore!=null){
						if(locationBefore.getTime().getTime()/60 == lo.getTime().getTime()/60){
							//*** 剔除时间相等的位置信息  ***
							log.info("[EjlLocationServiceImpl ] : 剔除时间相等的位置信息 ");
							continue;
						}
					}
					locations.add(struc);
					locationBefore = lo;
				}
				
			}
		}
		return locations;
	}



	@Override
	public List<LocationStruc> getUserLocationBetweenTime(Long userId,
			Long watchId, Date beginTime, Date endTime) {
		List<EjlLocation> ejlLocations=ejlLocationDao.getUserLocationBetweenTime(userId, watchId, beginTime, endTime);
		List<LocationStruc> locations=new ArrayList<LocationStruc>();
		if(!ejlLocations.isEmpty()){
			EjlLocation locationBefore = null;
			for(int i=0;i<ejlLocations.size();i++){
				EjlLocation lo = ejlLocations.get(i);
				LocationStruc struc=new LocationStruc();
				struc.setLocation(lo.getLocation());
				struc.setType(lo.getType());
				
				if(locationBefore!=null){
					if(locationBefore.getTime().getTime()/60 == lo.getTime().getTime()/60){
						//*** 剔除时间相等的位置信息  ***
						log.info("[EjlLocationServiceImpl ] : 剔除时间相等的位置信息 ");
						continue;
					}
				}
				locations.add(struc);
				locationBefore = lo;
			}
			
		}
		return locations;
	}
}


