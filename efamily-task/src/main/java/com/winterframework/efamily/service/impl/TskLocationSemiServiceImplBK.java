/*package com.winterframework.efamily.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEfComLocationOriginDao;
import com.winterframework.efamily.dao.ITskLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationAssist;
import com.winterframework.efamily.entity.EfLocationOrigin;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.service.IEfLocationOriginService;
import com.winterframework.efamily.service.IEfLocationSemiService;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.ITskLocationAssistService;
import com.winterframework.efamily.service.ITskLocationSemiService;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.efamily.utils.LocationUtil;

@Service("tskLocationSemiServiceImplBK")
@Transactional(rollbackFor = Exception.class)
public class TskLocationSemiServiceImplBK extends EfComLocationSemiServiceImpl implements ITskLocationSemiService {
	@Resource(name="tskLocationSemiDaoImpl")
	private ITskLocationSemiDao tskLocationSemiDao;
	@Resource(name = "efLocationSemiServiceImpl")
	private IEfLocationSemiService efLocationSemiService;
	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationService;
	@Resource(name = "tskLocationAssistServiceImpl")
	private ITskLocationAssistService tskLocationAssistService;
	@Resource(name="efComLocationOriginDaoImpl")
	private IEfComLocationOriginDao efComLocationOriginDaoImpl;
	@Resource(name="efLocationOriginServiceImpl")
	private IEfLocationOriginService efLocationOriginService;
	
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherServiceImpl;
	
	
	@Override
	public List<Long> getUnhandledDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.UNHANDLE.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getUnhandledMaxMinTimeByDeviceId(Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(deviceId, EfLocationSemi.Flag.UNHANDLE.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getUnhandledListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.UNHANDLE.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getFilteredListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.FILTERED.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getLinkedListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.LINKED.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Long> getAggregationDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.JUHEFINISH.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getAggregationMaxMinTimeByDeviceId(Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(deviceId, EfLocationSemi.Flag.JUHEFINISH.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getAggregationListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.JUHEFINISH.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Long> getDetailedDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.DETAIL.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getSameList(EfLocationSemi locationSemi) throws BizException {
		try{
			return tskLocationSemiDao.getSameListById(locationSemi.getId());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getDetailedMaxMinTimeByDeviceId(Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(deviceId, EfLocationSemi.Flag.DETAIL.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getDetailedListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.DETAIL.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<Long> getRetainedDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.RETAIN.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getRetainedMaxMinTimeByDeviceId(Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(deviceId, EfLocationSemi.Flag.RETAIN.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getRetainedListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(deviceId, timeFrom, timeTo, EfLocationSemi.Flag.RETAIN.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getRetainedListNearByDeviceIdAndLatLngAndOffset(
			Long deviceId, double longitude, double latitude, double offset)
			throws BizException {
		try{
			double longitudeFrom=longitude-offset;
			double longitudeTo=longitude+offset;
			double latitudeFrom=latitude-offset;
			double latitudeTo=latitude+offset;
			return tskLocationSemiDao.getListNearByDeviceIdAndLatLngAndFlag(deviceId, longitudeFrom, longitudeTo, latitudeFrom, latitudeTo, EfLocationSemi.Flag.RETAIN.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getValidListNearByDeviceIdAndLatLngAndOffset(
			Long deviceId, double longitude, double latitude, double offset)
			throws BizException {
		try{
			double longitudeFrom=longitude-offset;
			double longitudeTo=longitude+offset;
			double latitudeFrom=latitude-offset;
			double latitudeTo=latitude+offset;
			List<Integer> flags=getValidFlags();
			return tskLocationSemiDao.getListNearByDeviceIdAndLatLngAndFlags(deviceId, longitudeFrom, longitudeTo, latitudeFrom, latitudeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getValidPreNextListByIdAndCount(Long id, int count) throws BizException {
		try{ 
			List<Integer> flags=getValidFlags();
			return tskLocationSemiDao.getPreNextListByIdAndCountAndFlags(id, count, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getBtsListNearByDeviceIdAndTimeAndOffset(
			Long deviceId, Date time, int offset) throws BizException {
		try{
			List<EfLocationSemi> locationSemiList=getListNearByDeviceIdAndTimeAndOffset(deviceId, time, offset);
			if(null!=locationSemiList){
				Iterator<EfLocationSemi> iter=locationSemiList.iterator();
				while(iter.hasNext()){
					EfLocationSemi locationSemi=iter.next();
					if(locationSemi.getSource()!=EjlLocation.Source.TOWER.getCode()){
						iter.remove();
					}
				}
			}
			return locationSemiList;
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getListNearByDeviceIdAndTimeAndOffset(
			Long deviceId, Date time, int offset) throws BizException {
		try{
			Date timeFrom=DateUtils.addSeconds(DateUtils.addMinutes(time, -1*offset),1);
			Date timeTo=DateUtils.addSeconds(DateUtils.addMinutes(time, offset),-1);
			return tskLocationSemiDao.getListNearByDeviceIdAndTime(deviceId, timeFrom, timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public void processAggregation(List<EfLocationSemi> locationSemiList,boolean isInitial)
			throws BizException {
		try{
			efLocationSemiService.juheEjlLocation(locationSemiList,isInitial);
		}catch(Exception e){
			log.error("aggregation failed.",e);
			throw new BizException(e);
		}
	}
	@Override
	public List<EfLocationSemi> getRetainedList(Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByTimeFromToAndFlag(timeFrom,timeTo,EfLocationSemi.Flag.RETAIN.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Long> getDetailedAndSuspectDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getDeviceIdListByFlags(timeFrom,timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getDetailedAndSuspectMaxMinTimeByDeviceId(Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlags(deviceId, timeFrom,timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getRetainedFirstByDeviceId(Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.RETAIN.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getDetailedAndSuspectFirstByDeviceId(Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getFilterFirstByDeviceId(Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.FILTERED.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getLinkedFirstByDeviceId(Long deviceId, Date timeFrom,
			Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.LINKED.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getUnhandledFirstByDeviceId(Long deviceId, Date timeFrom,
			Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.UNHANDLE.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getDetailedAndSuspectListByDeviceIdAndTimeFromTo(
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlags(deviceId, timeFrom, timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public void dispose(Context ctx,EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationSemi locationSemiNext,boolean isInitial)
			throws BizException {
		try{
			EjlLocation location=ejlComLocationService.getBySourceId(locationSemi.getId());
			if(null!=location){
				ejlComLocationService.remove(ctx, location.getId());
			}
			if(null==locationSemiPre){
				locationSemiPre=getPreviousById(locationSemi);
			}
			if(null==locationSemiNext){
				locationSemiNext=getNextById(locationSemi);
			}
			if(null!=locationSemiPre){
				if(null!=locationSemiNext){
					locationSemiPre.setNextId(locationSemiNext.getId());
				}else{
					locationSemiPre.setNextId(null);
				}
				locationSemiPre.setTimeEnd(locationSemi.getTimeEnd());
				save(ctx, locationSemiPre);
			}
			if(null!=locationSemiNext){
				locationSemiNext.setPreviousId(locationSemiPre.getId());
				locationSemiNext.setDistance(calcDistance(locationSemiPre,locationSemiNext));
				locationSemiNext.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, locationSemiNext); 
				processDetail(ctx, locationSemiNext);
			}
			locationSemi.setFlag(isInitial?EfLocationSemi.Flag.DELETE.getValue():EfLocationSemi.Flag.DISPOSE.getValue());
			save(ctx, locationSemi);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	///////////////////////////////////////////////////////////
	public void doProcess() throws BizException {
		final int days=9;	//7天之内 根据实际有意义的轨迹要求(24小时)++天数
		Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		Context ctx=new Context();
		ctx.set("userId", -1);
		log.debug("process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		process(ctx,timeFrom,timeTo);
		log.debug("doProcessAggregationLatLon start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		doProcessAggregationLatLon(ctx,timeFrom, timeTo);
		log.debug("doProcessLocation start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		doProcessLocation(ctx,timeFrom,timeTo);
	}
	
	public void process(Context ctx, Date timeFrom, Date timeTo)
			throws BizException {
		log.debug("doProcessDetail start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		doProcessDetail(ctx,timeFrom,timeTo);
		log.debug("doProcessAdjust start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		doProcessAdjust(ctx,timeFrom,timeTo);
		log.debug("doProcessAggregation start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		doProcessAggregation(ctx,timeFrom, timeTo);
		//若还有聚合的点需要处理 则递归处理
		if(isNeedProcess(ctx,timeFrom, timeTo)){
			log.debug("again process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
			process(ctx,timeFrom, timeTo);
		}
	}
	protected void doProcessLocation(Context ctx,Date timeFrom,Date timeTo)throws BizException{
		List<EfLocationSemi> locationSemiList=getRetainedList(timeFrom, timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			EjlLocation location=null;
			List<EjlLocation> locationList=new ArrayList<EjlLocation>();
			for(EfLocationSemi locationSemi:locationSemiList){
				location=ejlComLocationService.getBySourceId(locationSemi.getId());
				if(null==location){
					location=new EjlLocation();
					location.setSourceId(locationSemi.getId());
				}
				location.setUserId(locationSemi.getUserId());
				location.setDeviceId(locationSemi.getDeviceId());
				location.setType(locationSemi.getType());
				location.setSource(locationSemi.getSource());
				location.setLocation(locationSemi.getLocation());
				location.setTime(locationSemi.getTime());
				location.setStatus(YesNo.YES.getValue());
				location.setRemark(locationSemi.getRemark());
				location.setTimeStay((int)DateUtils.calcSecondsBetween(locationSemi.getTimeBegin(),locationSemi.getTimeEnd()));
				location.setAddress(locationSemi.getAddress());
				location.setCity(locationSemi.getCity());
				locationList.add(location);
				locationSemi.setFlag(EfLocationSemi.Flag.FINISH.getValue());
				//用户新位置或者位置变化，需要发送天气  --新建task 处理
				//weatherServiceImpl.userWeatherCityHander(location);
			} 
			ejlComLocationService.save(ctx, locationList);
			save(ctx, locationSemiList); 
		}
	}
	protected boolean isNeedProcess(Context ctx,Date timeFrom,Date timeTo) throws BizException{
		List<Long> deviceIdList=getAggregationDeviceIdList(timeFrom, timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		return null!=deviceIdList && deviceIdList.size()>0;
	}
	
	@Override
	public void doProcessDetail(Context ctx,Date timeFrom,Date timeTo)throws BizException{
		final int interval=30;	//30分钟
		List<Long> deviceIdList=getAggregationDeviceIdList(timeFrom,timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		if(null!=deviceIdList){
			for(Long deviceId:deviceIdList){
				Map<Date,Date> dateMap=getAggregationMaxMinTimeByDeviceId(deviceId, timeFrom,timeTo);
				if(null!=dateMap && dateMap.size()>0){
					Date minTime=dateMap.get("minTime");
					Date maxTime=dateMap.get("maxTime");
					Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
					int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
					for(int i=0;i<count;i++){				
						Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
						Date timeTo2=DateUtils.addMinutes(timeFrom2,interval);
						List<EfLocationSemi> locationSemiList=getAggregationListByDeviceIdAndTimeFromTo(deviceId,timeFrom2,timeTo2);
						if(null!=locationSemiList){
							sort(locationSemiList, true);
							for(EfLocationSemi locationSemi:locationSemiList){ 
								processDetail(ctx, locationSemi);
							}
						}
					}
				}
			}
		}
	}
	@Override
	public void doProcessAdjust(Context ctx,Date timeFrom,Date timeTo) throws BizException {
		final int totalCount=2000;	//一次处理记录数 
		int count=0;	//处理数 
		List<Long> deviceIdList=getDetailedAndSuspectDeviceIdList(timeFrom, timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		if(null!=deviceIdList){
			for(Long deviceId:deviceIdList){
				EfLocationSemi locationSemi=getDetailedAndSuspectFirstByDeviceId(deviceId, timeFrom,timeTo);
				while(null!=locationSemi){	
					if(locationSemi.getTime().getTime()>timeTo.getTime()) break;
					processAdjustNew(ctx,locationSemi);
					count++;
					if(count>totalCount || null==locationSemi.getNextId()){
						break;
					}
					locationSemi=get(locationSemi.getNextId());
				}
				
				//此处如果是早期的少量点过来 没依次递推处理（不够智能，应该如果后续的点不受这些点影响之后 停止处理）--待优化
				List<EfLocationSemi> locationSemiList=getDetailedAndSuspectListByDeviceIdAndTimeFromTo(deviceId, timeFrom, timeTo);
				if(null!=locationSemiList){
					sort(locationSemiList, true);
					for(EfLocationSemi locationSemi:locationSemiList){
						if(++count>totalCount){
							break;
						}
						//可能在上一节点处理时已经发生信息变化
						locationSemi=get(locationSemi.getId());
						if(locationSemi.getFlag().intValue()==EfLocationSemi.Flag.DETAIL.getValue() 
								|| locationSemi.getFlag().intValue()==EfLocationSemi.Flag.SUSPECT.getValue()){
							processAdjustNew(ctx,locationSemi);
						}
					}
				}
			}	
		}
	}
	
	protected void processAdjustNew(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationSemi locationSemiPre=null; 
		EfLocationSemi locationSemiPrePre=null; 
		EfLocationAssist locationAssistPrePre=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistTmp=null;
		if(null==locationSemi.getPreviousId()){
			locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
		}else{
			locationSemiPre=getPreviousById(locationSemi);
			locationAssistPre=tskLocationAssistService.getByLocationId(locationSemiPre.getId());
			locationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
			if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.SUSPECT.getValue()){
				//可疑点必有前点
				locationSemiPrePre=getPreviousById(locationSemiPre);
				double speedP20=calcSpeed(locationSemiPrePre, locationSemi);
				locationAssistPrePre=tskLocationAssistService.getByLocationId(locationSemiPrePre.getId());
				try {
					locationAssistTmp=locationAssist.clone();
				} catch (CloneNotSupportedException e) {
					log.error("location assist clone failed.",e);
					throw new BizException(StatusCode.UNKNOW.getValue(),e);
				}
				locationAssistTmp.setSpeed(speedP20);
				locationAssistTmp.setAcceleration(calcAcceleration(locationSemiPrePre, locationSemi, locationAssistPrePre, locationAssistTmp));
				locationAssistTmp.setSlopeDegree(calcSlopeDegree(locationSemiPrePre, locationSemi));
				locationAssistTmp.setMoveMode(getMoveMode(speedP20));
				locationAssistTmp.setDirection(getDirection(locationSemiPrePre, locationSemi));
				locationAssistTmp.setRemark("last info:speed="+locationAssist.getSpeed()
						+",slopeDegree="+locationAssist.getSlopeDegree()
						+",moveMode="+locationAssist.getMoveMode()
						+",direction="+locationAssist.getDirection()
						+",previousId="+locationSemi.getPreviousId()
						+",nextId="+locationSemi.getNextId());
				
				boolean isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,locationAssistTmp);
				boolean isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,locationAssistTmp);
				boolean isAccTooBigP200=isAccTooBig(locationAssistTmp);
				boolean isSpeedTooBigP201=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssistTmp);
				boolean isDegreeTooBigP201=isDegreeTooBig(locationAssistPre,locationAssistTmp);
				
				if((isSpeedTooBigP200  || isDegreeTooBigP200) && !isSpeedTooBigP201 && !isDegreeTooBigP201 && !isAccTooBigP200){
					locationSemiPre.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					locationSemiPre.setRemark("A可疑点处理P1：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200
							+ " isSpeedTooBigP201"+isSpeedTooBigP201+ " isDegreeTooBigP201"+isDegreeTooBigP201+ " isAccTooBigP200"+isAccTooBigP200);
					save(ctx, locationSemiPre);
				}else if ((!isSpeedTooBigP200 && !isDegreeTooBigP200) || isSpeedTooBigP201 || isDegreeTooBigP201 || isAccTooBigP200){
					locationSemiPre.setRemark("B可疑点处理P1：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200
							+ " isSpeedTooBigP201"+isSpeedTooBigP201+ " isDegreeTooBigP201"+isDegreeTooBigP201+ " isAccTooBigP200"+isAccTooBigP200);
					dispose(ctx, locationSemiPrePre,locationSemiPre,locationSemi,false);
					
					//以下if逻辑对于当前点停留时间较长 则不起作用
					if((locationSemi.getTimeEnd().getTime()-locationSemi.getTimeBegin().getTime())/1000<LocationUtil.TIME_STAY*60){
						
						//重新计算后一点的坐标详情--即保存locationAssistTmp
						tskLocationAssistService.save(ctx, locationAssistTmp);
						//重新设置前后点关系
						locationSemi.setDistance(calcDistance(locationSemiPrePre,locationSemi));
						save(ctx, locationSemiPrePre);
						EfLocationAssist lassist=tskLocationAssistService.getByLocationId(locationSemi.getId());
						isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,lassist);
						isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,lassist);
						isAccTooBigP200=isAccTooBig(lassist); 
						
						if(isSpeedTooBigP200 && isAccTooBigP200){
							locationSemi.setRemark("C可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isAccTooBigP200="+isAccTooBigP200);
							dispose(ctx, locationSemiPrePre,locationSemi,null, false);
						}else if(isSpeedTooBigP200||isDegreeTooBigP200){
							locationSemi.setRemark("D可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200);
							locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
						}else if(!isSpeedTooBigP200 && !isDegreeTooBigP200){
							locationSemi.setRemark("E可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200);
							locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
						}
					}else{	//当前点停留时间较长 则保留
						locationSemi.setRemark("F直接保留P2.");
						locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					}				
				}
				
			}
			//当前点停留时间较长 则保留
			if((locationSemi.getTimeEnd().getTime()-locationSemi.getTimeBegin().getTime())/1000<LocationUtil.TIME_STAY*60){
				if(isLocationOk(locationSemiPre.getFlag().intValue())){
					boolean isSpeedTooBigP21=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssist);
					boolean isAccTooBigP21=isAccTooBig(locationAssist);
					boolean isDegreeTooBigP21=isDegreeTooBig(locationAssistPre,locationAssist);
					if(isSpeedTooBigP21 && isAccTooBigP21){
						locationSemi.setRemark("F保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isAccTooBigP21="+isAccTooBigP21);
						dispose(ctx, locationSemiPre,locationSemi,null,false);
					}else if(isSpeedTooBigP21 || isDegreeTooBigP21){
						locationSemi.setRemark("G保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isDegreeTooBigP21="+isDegreeTooBigP21);
						locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
					}else if(!isSpeedTooBigP21 && !isDegreeTooBigP21){
						locationSemi.setRemark("H保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isDegreeTooBigP21="+isDegreeTooBigP21);
						locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					}
				}
			}else{
				locationSemi.setRemark("G直接保留P2.");
				locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
			}
		}
		save(ctx, locationSemi);
	}
	private boolean isLocationOk(int flag){
		if(flag==EfLocationSemi.Flag.RETAIN.getValue() || flag==EfLocationSemi.Flag.FINISH.getValue()){
			return true;
		}
		return false;
	}
	protected void processAdjust(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationSemi locationSemiPre=null; 
		EfLocationSemi locationSemiPrePre=null; 
		EfLocationAssist locationAssistPrePre=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistTmp=null;
		if(null==locationSemi.getPreviousId()){
			locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
		}else{
			locationSemiPre=getPreviousById(locationSemi);
			locationAssistPre=tskLocationAssistService.getByLocationId(locationSemiPre.getId());
			locationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
			if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.SUSPECT.getValue()){
				//可疑点必有前点
				locationSemiPrePre=getPreviousById(locationSemiPre);
				double speedP20=calcSpeed(locationSemiPrePre, locationSemi);
				locationAssistPrePre=tskLocationAssistService.getByLocationId(locationSemiPrePre.getId());
				try {
					locationAssistTmp=locationAssist.clone();
				} catch (CloneNotSupportedException e) {
					log.error("location assist clone failed.",e);
					throw new BizException(StatusCode.UNKNOW.getValue(),e);
				}
				locationAssistTmp.setSpeed(speedP20);
				locationAssistTmp.setAcceleration(calcAcceleration(locationSemiPrePre, locationSemi,locationAssistPrePre,locationAssistTmp));
				locationAssistTmp.setSlopeDegree(calcSlopeDegree(locationSemiPrePre, locationSemi));
				locationAssistTmp.setMoveMode(getMoveMode(speedP20));
				locationAssistTmp.setDirection(getDirection(locationSemiPrePre, locationSemi));
				locationAssistTmp.setRemark("last info:speed="+locationAssist.getSpeed()
						+",slopeDegree="+locationAssist.getSlopeDegree()
						+",moveMode="+locationAssist.getMoveMode()
						+",direction="+locationAssist.getDirection()
						+",previousId="+locationSemi.getPreviousId()
						+",nextId="+locationSemi.getNextId());
				
				boolean isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,locationAssistTmp);
				boolean isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,locationAssistTmp);
				boolean isAccTooBigP200=isAccTooBig(locationAssistTmp);
				boolean isSpeedTooBigP201=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssistTmp);
				
				if((isSpeedTooBigP200  || isDegreeTooBigP200) && !isSpeedTooBigP201 && !isAccTooBigP200){
					locationSemiPre.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					save(ctx, locationSemiPre);
				}else if ((!isSpeedTooBigP200 && !isDegreeTooBigP200) || isSpeedTooBigP201 || isAccTooBigP200){
					dispose(ctx, locationSemiPrePre,locationSemiPre,locationSemi,false);
					//重新计算后一点的坐标详情--即保存locationAssistTmp
					tskLocationAssistService.save(ctx, locationAssistTmp);
					//重新设置前后点关系
					locationSemi.setDistance(calcDistance(locationSemiPrePre,locationSemi));
					save(ctx, locationSemiPrePre);
					
					if(isSpeedTooBigP200 && isAccTooBigP200){
						dispose(ctx, locationSemiPrePre,locationSemi,null, false);
					}else if(isSpeedTooBigP200||isDegreeTooBigP200){
						locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
					}else if(!isSpeedTooBigP200 && !isDegreeTooBigP200){
						locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					}
					
				}
				
			}
			if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.RETAIN.getValue()){
				boolean isSpeedTooBigP21=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssist);
				boolean isAccTooBigP21=isAccTooBig(locationAssist);
				boolean isDegreeTooBigP21=isDegreeTooBig(locationAssistPre,locationAssist);
				if(isSpeedTooBigP21 && isAccTooBigP21){
					dispose(ctx, locationSemiPre,locationSemi,null,false);
				}else if(isSpeedTooBigP21 || isDegreeTooBigP21){
					locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
				}else if(!isSpeedTooBigP21 && !isDegreeTooBigP21){
					locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
				}
			}
		}
		save(ctx, locationSemi);
	}
	protected void processDetail(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationSemi locationSemiPre=null; 
		locationSemiPre=getPreviousById(locationSemi);
		double speed=calcSpeed(locationSemiPre, locationSemi);
		String direction=getDirection(locationSemiPre, locationSemi);	
		int slopeDegree=calcSlopeDegree(locationSemiPre, locationSemi);	
		locationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
		if(null!=locationSemiPre){
			locationAssistPre=tskLocationAssistService.getByLocationId(locationSemiPre.getId());
		}
		if(null==locationAssist){
			locationAssist=new EfLocationAssist();
			locationAssist.setLocationId(locationSemi.getId());
		}
		locationAssist.setSpeed(speed);
		locationAssist.setAcceleration(calcAcceleration(locationSemiPre,locationSemi, locationAssistPre, locationAssist));
		locationAssist.setMoveMode(getMoveMode(speed));
		locationAssist.setDirection(direction);
		locationAssist.setSlopeDegree(slopeDegree);
		locationAssist.setRemark("");
		
		tskLocationAssistService.save(ctx, locationAssist);
		
		locationSemi.setDistance(calcDistance(locationSemiPre, locationSemi));
		locationSemi.setFlag(EfLocationSemi.Flag.DETAIL.getValue());
		save(ctx, locationSemi);
	}
	@Override
	public void doProcessAggregation(Context ctx,Date timeFrom,Date timeTo) throws BizException {
		final int interval=30;	//30分钟
		List<Long> deviceIdList=getRetainedDeviceIdList(timeFrom,timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		if(null!=deviceIdList){
			for(Long deviceId:deviceIdList){
				Map<Date,Date> dateMap=getRetainedMaxMinTimeByDeviceId(deviceId, timeFrom,timeTo);
				if(null!=dateMap && dateMap.size()>0){
					Date minTime=dateMap.get("minTime");
					Date maxTime=dateMap.get("maxTime");
					Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
					int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
					for(int i=0;i<count;i++){				
						Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
						Date timeTo2=DateUtils.addMinutes(timeFrom2,interval); 
						List<EfLocationSemi> locationSemiList=getRetainedListByDeviceIdAndTimeFromTo(deviceId,timeFrom2,timeTo2);
						if(null!=locationSemiList){
							sort(locationSemiList, true);
							if(null!=locationSemiList){
								for(EfLocationSemi locationSemi:locationSemiList){
									//可能在前点处理后发生了变化
									locationSemi=get(locationSemi.getId());
									if(locationSemi.getFlag().intValue()==EfLocationSemi.Flag.RETAIN.getValue()){
										aggregation(ctx, locationSemi,true);
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	@Override
	public void doProcessAggregationLatLon(Context ctx,Date timeFrom,Date timeTo) throws BizException {
		final int interval=30;	//30分钟
		List<Long> deviceIdList=getRetainedDeviceIdList(timeFrom,timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		if(null!=deviceIdList){
			for(Long deviceId:deviceIdList){
				Map<Date,Date> dateMap=getRetainedMaxMinTimeByDeviceId(deviceId, timeFrom,timeTo);
				if(null!=dateMap && dateMap.size()>0){
					Date minTime=dateMap.get("minTime");
					Date maxTime=dateMap.get("maxTime");
					Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
					int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
					for(int i=0;i<count;i++){				
						Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
						Date timeTo2=DateUtils.addMinutes(timeFrom2,interval);
						List<EfLocationSemi> locationSemiList=getRetainedListByDeviceIdAndTimeFromTo(deviceId,timeFrom2,timeTo2);
						if(null!=locationSemiList){
							processAggregationLatLon(ctx,locationSemiList);
						}
					}
				}
			}	
		}
	}
	protected void processAggregationLatLon(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		
		 * 升序点集合
		 * map中寻找经纬度，若无则往前找到最近一个与P点坐标接近的点 (第3位小数加减3~=500m)，map记录经纬度
		 * 			          若有则更新经纬度
		 * 
		 
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			Map<String,LatLng> latlngMap=new HashMap<String,LatLng>();
			List<EfLocationSemi> locationSemiTmpList =new ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				double lat=LocationUtil.format(locationSemi.getLatitude(),LocationUtil.PREC_LATLON);
				double lon=LocationUtil.format(locationSemi.getLongitude(),LocationUtil.PREC_LATLON);
				String key=lat+Separator.comma+lon;
				
				double offset=getLatLonOffSet();
				LatLng latlng=null;
				List<EfLocationSemi> locationSemiPreList=getRetainedListNearByDeviceIdAndLatLngAndOffset(locationSemi.getDeviceId(), lon, lat, offset);
				if(null!=locationSemiPreList){
					sort(locationSemiPreList, true);
					for(EfLocationSemi locationSemiPre:locationSemiPreList){
						if(locationSemiPre.getId().equals(locationSemi.getId())) {
							continue;
						}
						if(isCanAggregation(locationSemiPre, locationSemi)){
							latlng=latlngMap.get(key);
							if(null==latlng){
								latlng=new LatLng(locationSemiPre.getLatitude(), locationSemiPre.getLongitude());
								latlngMap.put(key, latlng);
							}
							break;
						}
					}
				}
				if(null!=latlng){
					locationSemi.setLongitude(latlng.getLongitude());
					locationSemi.setLatitude(latlng.getLatitude());
				}
				locationSemiTmpList.add(locationSemi);
			}
			save(ctx, locationSemiTmpList);
		}
	}
	private boolean isCanAggregation(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi){
		double d=Math.abs(calcDistance(locationSemiPre, locationSemi));
		return d-LocationUtil.JUHEDISTANCE<=0;
	}
	private double getLatLonOffSet(){
		double v=1;
		for(int i=0;i<LocationUtil.PREC_LATLON;i++){
			v=v*0.1;
		}
		return v*LocationUtil.OFFSET_LATLON;
	}
	protected void processAggregation(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		processAggregation(locationSemiList,false);
		
		 * 1.与前点距离 在聚合范围之内 则聚合成一点
		 * 2.计算坐标详情
		 * 3.更新状态：待详情处理
		
		Context ctx=new Context();
		Map<Long,EfLocationSemi> locationSemiMap=toMap(locationSemiList);
		EfLocationSemi locationSemiPre=null;
		EfLocationSemi locationSemiNext=null;
		EfLocationSemi locationSemi=null;
		for(Map.Entry<Long,EfLocationSemi> entry:locationSemiMap.entrySet()){
			locationSemi=entry.getValue();
			if(null==locationSemi.getPreviousId()){
				continue;
			}else{
				locationSemiPre=locationSemiMap.get(locationSemi.getPreviousId());
				locationSemiNext=locationSemiMap.get(locationSemi.getNextId());
				if(null==locationSemiPre){
					//需要聚合前面的批次
					continue;
				}
				double distance=calcDistance(locationSemiPre, locationSemi);
				if(distance<=LocationUtil.JUHEDISTANCE){
					//设置结点关系+计算详情
					locationSemiPre.setTimeEnd(locationSemi.getTimeEnd());
					locationSemiPre.setNextId(locationSemi.getNextId());
					locationSemiMap.put(locationSemi.getId(), locationSemiPre);	//设置成前点
					if(null!=locationSemiNext){
						locationSemiNext.setPreviousId(locationSemiPre.getId());
					}else{
						//需要聚合后面的批次
					}
				}				
			}
		}
	}
	protected void processAdjust_bk(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		EfLocationSemi locationSemiPre=null; 
		EfLocationSemi locationSemiPrePre=null; 
		EfLocationAssist locationAssistPrePre=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistTmp=null;
		for(EfLocationSemi locationSemi:locationSemiList){
			if(null==locationSemi.getPreviousId()){
				locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
			}else{
				locationSemiPre=getPreviousById(locationSemi);
				locationAssistPre=tskLocationAssistService.getByLocationId(locationSemiPre.getId());
				locationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
				if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.SUSPECT.getValue()){
					//可疑点必有前点
					locationSemiPrePre=getPreviousById(locationSemiPre);
					double speedP20=calcSpeed(locationSemiPrePre, locationSemi);
					locationAssistPrePre=tskLocationAssistService.getByLocationId(locationSemiPrePre.getId());
					try {
						locationAssistTmp=locationAssist.clone();
					} catch (CloneNotSupportedException e) {
						log.error("location assist clone failed.",e);
						throw new BizException(StatusCode.UNKNOW.getValue(),e);
					}
					locationAssistTmp.setSpeed(speedP20);
					locationAssistTmp.setAcceleration(calcAcceleration(locationSemiPrePre, locationSemi,locationAssistPrePre,locationAssistTmp));
					locationAssistTmp.setSlopeDegree(calcSlopeDegree(locationSemiPrePre, locationSemi));
					locationAssistTmp.setMoveMode(getMoveMode(speedP20));
					locationAssistTmp.setDirection(getDirection(locationSemiPrePre, locationSemi));
					locationAssistTmp.setRemark("last info:speed="+locationAssist.getSpeed()
							+",slopeDegree="+locationAssist.getSlopeDegree()
							+",moveMode="+locationAssist.getMoveMode()
							+",direction="+locationAssist.getDirection()
							+",previousId="+locationSemi.getPreviousId()
							+",nextId="+locationSemi.getNextId());
					boolean isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,locationAssistTmp);
					boolean isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,locationAssistTmp);
					boolean isAccTooBigP200=isAccTooBig(locationAssistTmp);
					boolean isSpeedTooBigP201=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssistTmp);
					
					if((isSpeedTooBigP200  || isDegreeTooBigP200) && !isSpeedTooBigP201 && !isAccTooBigP200){
						locationSemiPre.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
						save(ctx, locationSemiPre);
					}else if ((!isSpeedTooBigP200 && !isDegreeTooBigP200) || isSpeedTooBigP201 || isAccTooBigP200){
						dispose(ctx, locationSemiPrePre,locationSemiPre,locationSemi,false);
						//重新计算后一点的坐标详情--即保存locationAssistTmp
						tskLocationAssistService.save(ctx, locationAssistTmp);
						//重新设置前后点关系
						locationSemi.setDistance(calcDistance(locationSemiPrePre,locationSemi));
						save(ctx, locationSemiPrePre);
						
						if(isSpeedTooBigP200 && isAccTooBigP200){
							dispose(ctx, locationSemiPrePre,locationSemi,null, false);
						}else if(isSpeedTooBigP200||isDegreeTooBigP200){
							locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
						}else if(!isSpeedTooBigP200 && !isDegreeTooBigP200){
							locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
						}
						
					}
					
				}
				if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.RETAIN.getValue()){
					boolean isSpeedTooBigP21=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssist);
					boolean isAccTooBigP21=isAccTooBig(locationAssist);
					boolean isDegreeTooBigP21=isDegreeTooBig(locationAssistPre,locationAssist);
					if(isSpeedTooBigP21 && isAccTooBigP21){
						dispose(ctx, locationSemiPre,locationSemi,null,false);
					}else if(isSpeedTooBigP21 || isDegreeTooBigP21){
						locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
					}else if(!isSpeedTooBigP21 && !isDegreeTooBigP21){
						locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					}
				}
			}
			save(ctx, locationSemi);
		}
	}
	public void doProcessAdjust_bk(Context ctx,Date timeFrom,Date timeTo) throws BizException {
		final int interval=30;	//30分钟
		List<Long> deviceIdList=getDetailedAndSuspectDeviceIdList(timeFrom, timeTo);
		deviceIdList=getDeviceId(deviceIdList);
		for(Long deviceId:deviceIdList){
			Map<Date,Date> dateMap=getDetailedAndSuspectMaxMinTimeByDeviceId(deviceId, timeFrom,timeTo);
			Date minTime=dateMap.get("minTime");
			Date maxTime=dateMap.get("maxTime");
			Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
			int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
			for(int i=0;i<count;i++){				
				Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
				Date timeTo2=DateUtils.addMinutes(timeFrom2,interval);
				List<EfLocationSemi> locationSemiList=getDetailedAndSuspectListByDeviceIdAndTimeFromTo(deviceId, timeFrom2, timeTo2);
				for(EfLocationSemi locationSemi:locationSemiList){
					processAdjust(ctx,locationSemi);
				}
			}
		}	
	}
	private List<Long> getDeviceId(List<Long> deviceIdList){
		List<Long> l=new ArrayList<Long>();		
		l.add(446L);
		if(!deviceIdList.containsAll(l)){
			l.remove(0);
		}
		return deviceIdList;
	}
	private String getDirection(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi){
		if(null==locationSemiPre){
			return "EE";
		}
		LatLng latlngPre=new LatLng(locationSemiPre.getLatitude(),locationSemiPre.getLongitude());
		LatLng latlng=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
		return LocationUtil.getDirection(latlngPre, latlng);
	}
	private double calcDistance(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi){
		if(null==locationSemiPre){
			return 0;
		}
		LatLng latlngPre=new LatLng(locationSemiPre.getLatitude(),locationSemiPre.getLongitude());
		LatLng latlng=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
		return LocationUtil.getDistance(latlngPre, latlng);
	}
	private double calcSpeed(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi){
		if(null==locationSemiPre){
			return LocationUtil.MIN_VALUE;
		}
		LatLng latlngPre=new LatLng(locationSemiPre.getLatitude(),locationSemiPre.getLongitude());
		LatLng latlng=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
		double distance=LocationUtil.getDistance(latlngPre, latlng);
		//速度=距离/(第2坐标的最早时间-第1坐标的最晚时间)
		long timeSpan=locationSemi.getTimeBegin().getTime()-locationSemiPre.getTimeEnd().getTime();
		return LocationUtil.calcSpeed(distance, timeSpan);
	}
	private double calcAcceleration(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		if(null==locationSemiPre){
			return LocationUtil.MIN_VALUE;
		}
		double respeed=getRespeed(locationSemiPre, locationSemi, locationAssistPre, locationAssistPre);
		double speedSpan=locationAssist.getSpeed()-respeed;
		//速度=距离/(第2坐标的最早时间-第1坐标的最晚时间)		
		long timeSpan=locationSemi.getTimeBegin().getTime()-locationSemiPre.getTimeEnd().getTime();
		if(getMoveMode(respeed)==EfLocationAssist.MoveMode.STAY.getValue()){
			timeSpan=timeSpan/2;
		}
		return LocationUtil.calcAcceleration(speedSpan, timeSpan);
	}
	private double getRespeed(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		//距离<速度*停留时间 && 停留时间>3min 则速度置为0 
		double speedReal=LocationUtil.MIN_VALUE;
		if(null!=locationSemiPre){
			int timeStay=(int)DateUtils.calcMinutesBetween(locationSemiPre.getTimeBegin(), locationSemiPre.getTimeEnd());
			double averSpeed=(locationAssistPre.getSpeed()+locationAssist.getSpeed())/2;
			speedReal=locationAssistPre.getSpeed();
			if(timeStay>LocationUtil.TIME_STAY && averSpeed*timeStay*1.0/60>locationSemi.getDistance()){
				speedReal=LocationUtil.MIN_VALUE;
			}
		}
		return speedReal;
	}	
	private int calcSlopeDegree(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi){
		if(null==locationSemiPre){
			return 0;
		}
		LatLng latlngPre=new LatLng(locationSemiPre.getLatitude(),locationSemiPre.getLongitude());
		LatLng latlng=new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
		return LocationUtil.calcSlopeDegree(latlngPre, latlng);
	}
	*//**
	 * @param locationSemiList
	 * @param isAsc
	 *//*
	public void sort(List<EfLocationSemi> locationSemiList,final boolean isAsc){
		if(null!=locationSemiList && locationSemiList.size()>0){
			Collections.sort(locationSemiList, 
			new Comparator<EfLocationSemi>(){
				int k=isAsc?1:-1;
				@Override
				public int compare(EfLocationSemi o1, EfLocationSemi o2) {
					if(o1.getTime().after(o2.getTime())){
						return 1*k;
					}
					return -1*k;
				}
			});
		}
	}
	private int getMoveMode(double speed){
		if(speed>(EfLocationAssist.MoveMode.WALK.getSpeed()-EfLocationAssist.MoveMode.WALK.getPrecision()) && speed<(EfLocationAssist.MoveMode.WALK.getSpeed()+EfLocationAssist.MoveMode.WALK.getPrecision())){
			return EfLocationAssist.MoveMode.WALK.getValue();
		}
		if(speed>(EfLocationAssist.MoveMode.BIKE.getSpeed()-EfLocationAssist.MoveMode.BIKE.getPrecision()) && speed<(EfLocationAssist.MoveMode.BIKE.getSpeed()+EfLocationAssist.MoveMode.BIKE.getPrecision())){
			return EfLocationAssist.MoveMode.BIKE.getValue();
		}
		if(speed>(EfLocationAssist.MoveMode.BUS.getSpeed()-EfLocationAssist.MoveMode.BUS.getPrecision()) && speed<(EfLocationAssist.MoveMode.BUS.getSpeed()+EfLocationAssist.MoveMode.BUS.getPrecision())){
			return EfLocationAssist.MoveMode.BUS.getValue();
		}
		if(speed>(EfLocationAssist.MoveMode.TRAIN.getSpeed()-EfLocationAssist.MoveMode.TRAIN.getPrecision()) && speed<(EfLocationAssist.MoveMode.TRAIN.getSpeed()+EfLocationAssist.MoveMode.TRAIN.getPrecision())){
			return EfLocationAssist.MoveMode.TRAIN.getValue();
		}
		if(speed>(EfLocationAssist.MoveMode.PLANE.getSpeed()-EfLocationAssist.MoveMode.PLANE.getPrecision()) && speed<(EfLocationAssist.MoveMode.PLANE.getSpeed()+EfLocationAssist.MoveMode.PLANE.getPrecision())){
			return EfLocationAssist.MoveMode.PLANE.getValue();
		}
		return EfLocationAssist.MoveMode.STAY.getValue();
	}
	private boolean isSpeedTooBig(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		EfLocationAssist.MoveMode moveModePrePre=EfLocationAssist.MoveMode.getMoveMode(locationAssistPrePre.getMoveMode());
		EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(locationAssist.getMoveMode());
		
		int maxSpeedPrePre=moveModePrePre.getSpeed()+moveModePrePre.getPrecision();
		int minSpeedPrePre=moveModePrePre.getSpeed()-moveModePrePre.getPrecision();
		
		int maxSpeed=moveMode.getSpeed()+moveMode.getPrecision();
		int minSpeed=moveMode.getSpeed()-moveMode.getPrecision();
		double realSpeedPre=getRespeed(locationSemiPre, locationSemi, locationAssistPre, locationAssist);
		int mMode=locationAssistPre.getMoveMode();
		if(realSpeedPre!=locationAssistPre.getSpeed()){ 
			mMode=getMoveMode(realSpeedPre);
		}
		int value=locationAssist.getMoveMode()>mMode?mMode:locationAssist.getMoveMode();
		EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(value);
		final double SMALL=moveMode.getPrecision();
		return Math.abs(locationAssist.getSpeed()-realSpeedPre)>SMALL;
		//return Math.abs(locationAssistPrePre.getMoveMode()-locationAssist.getMoveMode())>0;
	}
	private boolean isAccTooBig(EfLocationAssist locationAssist){ 
		EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(locationAssist.getMoveMode());
		final double ACC=moveMode.getAcceleration();
		//return !(ACC/2 <Math.abs(locationAssist.getAcceleration()) && Math.abs(locationAssist.getAcceleration())<ACC*3/2);
		return !(Math.abs(locationAssist.getAcceleration())<ACC*3/2);
	}
	private boolean isDegreeTooBig(EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		final int SMALL=30;
		final int MIDDLE=45;
		final int LARGE=60;
		final int FULL=90;
		final int HALFC=180;
		String directionPre=locationAssistPre.getDirection();
		String direction=locationAssist.getDirection();
		if(directionPre.substring(1).equals(direction.substring(1)) && !directionPre.substring(0,1).equals(direction.substring(0,1))){
			return (HALFC-Math.abs(locationAssistPre.getSlopeDegree()-locationAssist.getSlopeDegree()))>MIDDLE;
		}
		if(!directionPre.substring(1).equals(direction.substring(1)) && !directionPre.substring(0,1).equals(direction.substring(0,1))){
			return (HALFC-Math.abs(locationAssistPre.getSlopeDegree()-locationAssist.getSlopeDegree()))>MIDDLE;
		}
		return Math.abs(locationAssistPre.getSlopeDegree()-locationAssist.getSlopeDegree())>MIDDLE;
	}
	
	
	////////////////////////////优先级、聚合逻辑////////////////////////////////
	private List<EfLocationSemi> getInitLocationSemi(List<EfLocationOrigin> list) throws Exception{
		List<EfLocationSemi> semiList = new ArrayList<EfLocationSemi>();
		for(EfLocationOrigin efLocationOrigin:list){
			semiList.add(this.converterLocationOriginToSemi(efLocationOrigin));
		}
		return semiList;
	}
	
	private EfLocationSemi converterLocationOriginToSemi(EfLocationOrigin efLocationOrigin){
		EfLocationSemi efLocationSemi = new EfLocationSemi();
		efLocationSemi.setDeviceId(efLocationOrigin.getDeviceId());
		efLocationSemi.setFlag(EfLocationSemi.Flag.UNHANDLE.getValue());//未处理
		efLocationSemi.setLocation(efLocationOrigin.getLocation());
		efLocationSemi.setRemark(efLocationOrigin.getRemark());
		efLocationSemi.setSource(efLocationOrigin.getSource());
		efLocationSemi.setSourceId(efLocationOrigin.getId());
		efLocationSemi.setStatus(YesNo.YES.getValue());//可用
		efLocationSemi.setTime(efLocationOrigin.getTime());
		efLocationSemi.setType(efLocationOrigin.getType());
		efLocationSemi.setUserId(efLocationOrigin.getUserId());
		efLocationSemi.setTimeEnd(efLocationOrigin.getTime());
		efLocationSemi.setTimeBegin(efLocationOrigin.getTime());
		efLocationSemi.setCity(efLocationOrigin.getCity());
		efLocationSemi.setAddress(efLocationOrigin.getRemark());
		return efLocationSemi;
	}
	
	@Override
	public void doProcessOrigin() throws BizException{
		try{
			final int days=9;	//7天之内 根据实际有意义的轨迹要求(24小时)++天数
			Date timeTo=DateUtils.currentDate();
			Date timeFrom=DateUtils.addDays(timeTo, -1*days);
			
			List<Long> deviceIds = efComLocationOriginDaoImpl.getLocationOriginDeviceId();
			Context ctx = new Context();
			ctx.set("userId", -1);
			deviceIds.clear();
			deviceIds.add(371L);
			//List<List<Long>> deviceIdSplits = LocationUtil.getSplitsDeviceIds(deviceIds);
			for(Long deviceId:deviceIds){
				List<Long> deviceList = new ArrayList<Long>();
				deviceList.add(deviceId);
				List<EfLocationOrigin> list = efComLocationOriginDaoImpl.getLocationOriginByDevice(deviceList);
				
				if(list==null||list.size()==0){
					continue;
				}
				for(EfLocationOrigin efLocationOrigin:list){
					efLocationOrigin.setFlag(YesNo.YES.getValue());
				}
				efLocationOriginService.save(ctx, list);
				
				*//**
				 * 1.处理当前未处理的list
				 * 2.走数据库处理(所有坐标点)
				 *//*
				List<EfLocationSemi> locationSemiList=getInitLocationSemi(list);
				log.debug("filterLocationSemiTmp start.deviceId="+deviceId);
				filterLocationSemiTmp(ctx,locationSemiList);
				log.debug("aggregationTmp start.deviceId="+deviceId);
				aggregationTmp(ctx,locationSemiList);
				log.debug("distinctLocationSemiTmp start.deviceId="+deviceId);
				//踢除同一时间点 同一source的记录
				distinctLocationSemiTmp(ctx,locationSemiList);
				log.debug("save start.deviceId="+deviceId);
				save(ctx, locationSemiList);
				
				log.debug("filterLocationSemi start.deviceId="+deviceId);
				filterLocationSemi(ctx,deviceId,timeFrom,timeTo);
				log.debug("distinctLocationSemi start.deviceId="+deviceId);
				//踢除同一时间点 同一source的记录
				distinctLocationSemi(ctx,deviceId,timeFrom,timeTo);
				log.debug("linkLocationSemi start.deviceId="+deviceId);
				//linkLocationSemi(ctx,deviceId,timeFrom,timeTo);	//去掉链表维护
				 		
				log.debug("aggregationLocationSemi start.deviceId="+deviceId);
				//按指定的距离聚合数据
				aggregationLocationSemi(ctx,deviceId,timeFrom,timeTo);
			}
		}catch(Exception e){
			log.error("",e);
			throw new BizException(e);
		}
	}
	
	*//**
	 * 聚合处理（未处理的点）
	 * @param ctx
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @throws BizException
	 *//*
	protected void aggregationLocationSemi(Context ctx,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			for(EfLocationSemi locationSemi:locationSemiList){
				//可能在前点处理后发生了变化
				locationSemi=get(locationSemi.getId());
				if(locationSemi.getFlag().intValue()==EfLocationSemi.Flag.FILTERED.getValue()){
					aggregation(ctx,locationSemi,false);
				}
			}
		}
	}
	@Override
	public void aggregation(Context ctx,EfLocationSemi locationSemi,boolean isSecd) throws BizException{
		if(null!=locationSemi){
			final int count=10;
			List<EfLocationSemi> nearList =new ArrayList<EfLocationSemi>();
			List<EfLocationSemi> aggList =new ArrayList<EfLocationSemi>(); 			
			//获取该点待聚合的点集合（前后10个点） 
			nearList=getValidPreNextListByIdAndCount(locationSemi.getId(),count);
			nearList.add(locationSemi);
			//找到要进行真正聚合的点
			aggList=getAggregationList(ctx, locationSemi, nearList, LocationUtil.JUHEDISTANCE);
			if(null!=aggList && aggList.size()>1){
				//聚合处理
				EfLocationSemi aggLocation=getAggregationLocation(ctx,aggList);
				aggLocation.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, aggLocation);
				//删除被聚合的点
				disposeAggregationList(ctx,aggLocation,aggList);
			}else{
				if(!isSecd){	//首次聚合则更新状态
					locationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
					save(ctx, locationSemi);
				}
			}			
		}
	}
	
	protected void disposeAggregationList(Context ctx,EfLocationSemi newLocation,List<EfLocationSemi> aggList) throws BizException{
		if(null!=aggList && aggList.size()>1){	// 包含了自己  大于1才有必要处理
			for(EfLocationSemi locationSemi:aggList){
				locationSemi.setRemark("聚合删除 newId="+newLocation.getId());
				replace(ctx, locationSemi, newLocation, aggList);
			}
		}
	}
	//@Override
	*//** 新点替换旧点
	 * @param ctx
	 * @param locationSemi
	 * @param locationSemiNew
	 * @param aggList
	 * @throws BizException
	 *//*
	public void replace(Context ctx,EfLocationSemi locationSemi,EfLocationSemi locationSemiNew,List<EfLocationSemi> aggList)
			throws BizException {
		try{
			EjlLocation location=ejlComLocationService.getBySourceId(locationSemi.getId());
			if(null!=location){
				ejlComLocationService.remove(ctx, location.getId());
			}
			EfLocationSemi locationSemiPre=getPreviousById(locationSemi);
			if(null!=locationSemiPre && !isContain(aggList,locationSemiPre)){
				locationSemiPre.setNextId(locationSemiNew.getId());
				save(ctx, locationSemiPre);
			}
			EfLocationSemi locationSemiNext=getNextById(locationSemi);
			if(null!=locationSemiNext && !isContain(aggList,locationSemiNext)){
				locationSemiNext.setPreviousId(locationSemiNew.getId());
				locationSemiNext.setDistance(calcDistance(locationSemiNew,locationSemiNext));
				locationSemiNext.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, locationSemiNext);
			}
			locationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
			save(ctx, locationSemi);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	protected boolean isContain(List<EfLocationSemi> locationSemiList,EfLocationSemi locationSemi){
		if(null!=locationSemiList){
			for(EfLocationSemi lc:locationSemiList){
				if(lc.getId().longValue()==locationSemi.getId().longValue()){
					return true;
				}
			}
		}
		return false;
	}
	*//**
	 * 就数据库进行过滤
	 * @param ctx
	 * @param locationSemiList
	 * @throws BizException
	 *//*
	protected void filterLocationSemi(Context ctx,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		*//**
		 * 1.MovableWifi
		 * 2.GPS>WIFI>BTS
		 *//*
		
		List<EfLocationSemi> locationSemiList=getUnhandledListByDeviceIdAndTimeFromTo(deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			filterMovableWifi(ctx,locationSemiList);
			filterPriority(ctx,locationSemiList);
			
			for(EfLocationSemi locationSemi:locationSemiList){
				locationSemi.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
			}
			save(ctx, locationSemiList);
		}
	}
	*//**
	 * 踢除同一时间点同一source的坐标点
	 * @param ctx
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @throws BizException
	 *//*
	protected void distinctLocationSemi(Context ctx,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		*//**
		 * 没连接的才有可能需要去重
		 *//*
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			for(EfLocationSemi locationSemi:locationSemiList){
				List<EfLocationSemi> dupList=getSameList(locationSemi);
				if(null!=dupList && dupList.size()>0){
					locationSemi.setRemark("同一时间和类型去重");
					deleteLocationSemi(ctx, locationSemi);
				}
			}
		}
	}
	
	*//**
	 * 连接前后点
	 * @param ctx
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @throws BizException
	 *//*
	protected void linkLocationSemi(Context ctx,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true); 
			for(EfLocationSemi locationSemi:locationSemiList){
				*//**
				 * 1.pre=null && next=null  ---->insert  位置：队首 队中 队尾
				 * 2.pre=null && next!=null  --不应存在   队首也无需处理
				 * 3.pre!=null && next==null --应由下一节点处理
				 * 4.pre!=null && next!=null --不应存在
				 *//*
				locationSemi=get(locationSemi.getId());
				if(null==locationSemi.getPreviousId() && null==locationSemi.getNextId()){
					insertLocationSemi(ctx,locationSemi);
				}
			}
		}		
	}
	*//**
	 * 就当前list进行过滤
	 * @param ctx
	 * @param locationSemiList
	 * @throws BizException
	 *//*
	protected void filterLocationSemiTmp(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		filterMovableWifiTmp(ctx,locationSemiList);
		filterPriorityTmp(ctx,locationSemiList);
	}
	protected void aggregationTmp(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){ 
			List<EfLocationSemi> aggList=new ArrayList<EfLocationSemi>();
			Set<EfLocationSemi> addList=new HashSet<EfLocationSemi>();
			Set<EfLocationSemi> rmList=new HashSet<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(aggList.contains(locationSemi)) continue;
				aggList=getAggregationList(ctx, locationSemi, locationSemiList, LocationUtil.JUHEDISTANCE);
				if(null!=aggList && aggList.size()>1){
					EfLocationSemi aggLocation=getAggregationLocation(ctx,aggList);
					if(null!=aggLocation){
						addList.add(aggLocation);
						rmList.addAll(aggList);
					}
				}
			}
			locationSemiList.removeAll(rmList);
			locationSemiList.addAll(addList);
		}
	}
	protected void distinctLocationSemiTmp(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			List<EfLocationSemi> rmList=new  ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				List<EfLocationSemi> duplist=getSameListTmp(locationSemi,locationSemiList);
				if(null!=duplist && duplist.size()>0){
					rmList.addAll(duplist);
				}
			}
			locationSemiList.removeAll(rmList);
		}
	}
	protected List<EfLocationSemi> getSameListTmp(EfLocationSemi locationSemi,List<EfLocationSemi> locationSemiList){
		if(null!=locationSemiList){
			List<EfLocationSemi> dupList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi lc:locationSemiList){
				if(DateUtils.calcSecondsBetween(lc.getTime(), locationSemi.getTime())==0 && lc.getSource().intValue()==locationSemi.getSource().intValue()
						&& !lc.equals(locationSemi)){
					dupList.add(lc);
				}
			}
			return dupList;
		}
		return null;
	}
	*//**
	 * 聚合点
	 * @param ctx
	 * @param locationSemiList
	 * @return
	 *//*
	protected EfLocationSemi getAggregationLocation(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			if(locationSemiList.size()==1){
				return locationSemiList.get(0);
			}else{	//包含了自己  大于1才有必要处理
				double lat=0,lon=0,avLat=0,avLon=0;
				int size=locationSemiList.size();
				Date timeBegin=null,timeEnd=null;
				Long previousId=locationSemiList.get(0).getPreviousId();
				Long nextId=locationSemiList.get(locationSemiList.size()-1).getNextId();
				String remark="聚合生成：sourceIds=";
				for(EfLocationSemi locationSemi:locationSemiList){
					lat+=locationSemi.getLatitude();
					lon+=locationSemi.getLongitude();
					if(null==timeBegin){
						timeBegin=locationSemi.getTime();
					}else{
						if(!DateUtils.isBefore(timeBegin, locationSemi.getTime())){
							timeBegin=locationSemi.getTime();
						}
					}
					if(null==timeEnd){
						timeEnd=locationSemi.getTime();
					}else{
						if(DateUtils.isBefore(timeEnd, locationSemi.getTime())){
							timeEnd=locationSemi.getTime();
						}
					}
					if(remark.length()<200){
						remark+=(null==locationSemi.getSourceId())?"":locationSemi.getSourceId()+" ";
					}
				}
				avLat=lat/size;
				avLon=lon/size;
				//avTime=DateUtils.average(timeBegin, timeEnd);		//不取平均时间 取最早点的时间
				try {
					EfLocationSemi newLocation= locationSemiList.get(0).clone();
					newLocation.setId(null);
					newLocation.setSourceId(null);
					newLocation.setLongitude(avLon);
					newLocation.setLatitude(avLat);
					newLocation.setTime(timeBegin);
					newLocation.setTimeBegin(timeBegin);
					newLocation.setTimeEnd(timeEnd);
					newLocation.setPreviousId(previousId);
					newLocation.setNextId(nextId);
					newLocation.setRemark(remark);
					return newLocation;
				} catch (Exception e) {
					log.error("getAggregationLocation clone error",e);
					throw new BizException(e);
				}
			}
		}
		return null;
	}
	*//**
	 * 找到要进行真正聚合的点
	 * @param ctx
	 * @param locationSemi
	 * @param locationSemiList
	 * @param distance
	 * @return
	 *//*
	protected List<EfLocationSemi> getAggregationList(Context ctx,EfLocationSemi locationSemi,List<EfLocationSemi> locationSemiList,double distance){
		//包含自己
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			List<EfLocationSemi>  aggList=new ArrayList<EfLocationSemi>();
			LatLng llA=new LatLng(locationSemi.getLatitude(), locationSemi.getLongitude());
			boolean passSelf=false;
			for(EfLocationSemi lc:locationSemiList){
				if(locationSemi.equals(lc)){
					passSelf=true;
				}
				LatLng llB=new LatLng(lc.getLatitude(), lc.getLongitude());
				
				if(LocationUtil.getDistance(llA, llB)<=distance){
					aggList.add(lc);
				}else{	//超出距离且是后续的点则中断  否则清空列表之后 继续
					if(passSelf){
						break;
					}else{
						aggList.clear();
					}
				}
			}
			return aggList;
		}
		return null;
	}
	@Deprecated
	protected List<EfLocationSemi> getNearListByLatLon(Context ctx,EfLocationSemi locationSemi,List<EfLocationSemi> locationSemiList,double offset,double distance,boolean includeSelf){
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			List<EfLocationSemi> nearList=new ArrayList<EfLocationSemi>();
			
			double lat=LocationUtil.format(locationSemi.getLatitude(),LocationUtil.PREC_LATLON);
			double lon=LocationUtil.format(locationSemi.getLongitude(),LocationUtil.PREC_LATLON);
			
			double longitudeFrom=lon-offset;
			double longitudeTo=lon+offset;
			double latitudeFrom=lat-offset;
			double latitudeTo=lat+offset;			
			for(EfLocationSemi lc:locationSemiList){
				if(!includeSelf && lc.equals(locationSemi)) continue;
				if(lc.getLongitude()>longitudeFrom && lc.getLongitude()<longitudeTo
						&& lc.getLatitude()>latitudeFrom && lc.getLatitude()<latitudeTo){
					nearList.add(lc);
				}
			}
			return nearList;
		}
		return null;
	}
	protected void filterPriorityTmp(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			int mins=LocationUtil.POINT_TIME_SPLIT;
			Set<EfLocationSemi> rmList=new HashSet<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(rmList.contains(locationSemi)) continue;
				List<EfLocationSemi> nearList=getNearList(ctx,locationSemi,locationSemiList,mins,false);
				if(locationSemi.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
					rmList.addAll(nearList);
				}else if(locationSemi.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
					rmList.addAll(getBtsList(nearList));
				}
			}
			locationSemiList.removeAll(rmList);
		}
	}
	protected void filterPriority(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			int mins=LocationUtil.POINT_TIME_SPLIT;
			Set<EfLocationSemi> rmSet=new HashSet<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(rmSet.contains(locationSemi)) continue;
				List<EfLocationSemi> nearListWithSelf=getListNearByDeviceIdAndTimeAndOffset(locationSemi.getDeviceId(), locationSemi.getTime(), mins);
				List<EfLocationSemi> nearList=getExcludeSelfById(locationSemi,nearListWithSelf);
				if(null!=nearList && nearList.size()>0){
					if(locationSemi.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
						rmSet.addAll(getExcludeGpsList(nearList));
					}else if(locationSemi.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
						rmSet.addAll(getBtsList(nearList));
					}else if(locationSemi.getSource().intValue()==EjlLocation.Source.TOWER.getCode()){
						for(EfLocationSemi s:nearList){
							if(s.getId().longValue()==locationSemi.getId().longValue()) continue;
							if(s.getSource().intValue()==EjlLocation.Source.WIFI.getCode() 
									|| s.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
								rmSet.add(locationSemi);
								break;
							}
						}
					}
				}
			}
			//要先移除 
			locationSemiList.removeAll(rmSet);
			for(EfLocationSemi s:rmSet){
				s.setRemark("优先级过滤");
				deleteLocationSemi(ctx, s);
			}
		}
	}
	*//**
	 * 排除自身点 根据ID
	 * @param locationSemi
	 * @param list
	 * @return
	 *//*
	private List<EfLocationSemi> getExcludeSelfById(EfLocationSemi locationSemi,List<EfLocationSemi> list){
		if(null!=list){
			List<EfLocationSemi> aList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi s:list){
				if(locationSemi.getId().longValue()==s.getId().longValue()) continue;
				aList.add(s);
			}
			return aList;
		}
		return null;
	}
	protected List<EfLocationSemi> getBtsList(List<EfLocationSemi> locationSemiList){
		if(null!=locationSemiList){
			List<EfLocationSemi> btsList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi lc:locationSemiList){
				if(lc.getSource().intValue()==EjlLocation.Source.TOWER.getCode()){
					btsList.add(lc);
				}
			}
			return btsList;
		}
		return null;
	}
	protected List<EfLocationSemi> getExcludeGpsList(List<EfLocationSemi> locationSemiList){
		if(null!=locationSemiList){
			List<EfLocationSemi> list=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi lc:locationSemiList){
				if(lc.getSource().intValue()!=EjlLocation.Source.GPS.getCode()){
					list.add(lc);
				}
			}
			return list;
		}
		return null;
	}
	protected void filterMovableWifiTmp(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			List<EfLocationSemi> rmList=new ArrayList<EfLocationSemi>();
			List<EfLocationSemi> btsList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(locationSemi.getSource().intValue()==EjlLocation.Source.TOWER.getCode()){
					btsList.add(locationSemi);
				}
			}			
			for(EfLocationSemi locationSemi:locationSemiList){ 
				if(locationSemi.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
					List<EfLocationSemi> btsNearList=getNearList(ctx,locationSemi,btsList,LocationUtil.OFFSET_NEAR_MINS,false);
					if(isMovableWifi(ctx,locationSemi,btsNearList)){
						rmList.add(locationSemi);
					}
				}
			}
			locationSemiList.removeAll(rmList);
		}
	}
	protected boolean isMovableWifi(Context ctx,EfLocationSemi locationSemi,List<EfLocationSemi> btsNearList)  throws BizException{
		if(null!=btsNearList){		
			double distance=0;
			LatLng l1 = new LatLng(locationSemi.getLatitude(),locationSemi.getLongitude());
			int count=btsNearList.size();
			for(EfLocationSemi bts:btsNearList){
				LatLng l2 = new LatLng(bts.getLatitude(),bts.getLongitude());
				distance+=LocationUtil.getDistance(l1, l2);
			}
			double avarDistance=distance/count;
			if(avarDistance>LocationUtil.DYNAMICWIFT_DISTANCE){
				locationSemi.setRemark("移动wifi，基站平均距离："+avarDistance);
				return true;
			}
		}
		return false;
	}
	protected List<EfLocationSemi> getNearList(Context ctx,EfLocationSemi locationSemi,List<EfLocationSemi> locationSemiList,int offset,boolean includeSelf){
		if(null!=locationSemiList){
			List<EfLocationSemi> nearList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi lc:locationSemiList){
				if(!includeSelf && lc.equals(locationSemi)) continue;
				int mins=(int)DateUtils.calcMinutesBetween(locationSemi.getTime(),lc.getTime());
				if(Math.abs(mins)<offset){
					nearList.add(lc);
				}
			}
			return nearList;
		}
		return null;
	}
	*//**
	 * 过滤移动wifi
	 * @param locationSemi
	 * @throws BizException
	 *//*
	protected void filterMovableWifi(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			List<EfLocationSemi> rmList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(locationSemi.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
					List<EfLocationSemi> btsNearList=getBtsListNearByDeviceIdAndTimeAndOffset(locationSemi.getDeviceId(), locationSemi.getTime(),LocationUtil.OFFSET_NEAR_MINS);
					if(isMovableWifi(ctx, locationSemi, btsNearList)){
						locationSemi.setRemark("移动wifi");
						deleteLocationSemi(ctx,locationSemi);
						rmList.add(locationSemi);
					}
				}
			}
			locationSemiList.remove(rmList);
		}
	}
	protected void insertLocationSemi(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationSemi locationSemiPre=getPrevious(locationSemi);
		EfLocationSemi locationSemiNext=getNext(locationSemi);
		if(null!=locationSemiPre){
			insertAfter(ctx, locationSemi, locationSemiPre, locationSemiNext);
		}else{
			//队首
			if(null!=locationSemiNext){
				insertBefore(ctx, locationSemi, locationSemiNext);
			}
			locationSemi.setPreviousId(null);
			locationSemi.setFlag(EfLocationSemi.Flag.LINKED.getValue()); 
			save(ctx, locationSemi);
		}
	}
	private void insertAfter(Context ctx, EfLocationSemi locationSemi,
			EfLocationSemi locationSemiPre, EfLocationSemi locationSemiNext)
			throws BizException {
		EfLocationSemi locationSemiPreNext=getNextById(locationSemiPre);
		//队中
		if(null!=locationSemiPreNext){
			insertBefore(ctx, locationSemi, locationSemiPreNext);
		}
		//else 队尾
		locationSemiPre.setNextId(locationSemi.getId());
		save(ctx, locationSemiPre);
		locationSemi.setPreviousId(locationSemiPre.getId());
		locationSemi.setFlag(EfLocationSemi.Flag.LINKED.getValue()); 
		save(ctx, locationSemi);
		//直到 前一节点的下一节点 正是当前点的下一节点 
		if(null!=locationSemiNext && (null==locationSemiPreNext 
				|| locationSemiPreNext.getId().longValue()!=locationSemiNext.getId().longValue())){
			insertLocationSemi(ctx, locationSemiNext);
		}
	}
	private void insertBefore(Context ctx, EfLocationSemi locationSemi,
		EfLocationSemi locationSemiNext) throws BizException {
	
		EjlLocation location=ejlComLocationService.getBySourceId(locationSemiNext.getId());
		if(null!=location){
			ejlComLocationService.remove(ctx, location.getId());
		}
		locationSemiNext.setPreviousId(locationSemi.getId());
		locationSemiNext.setFlag(EfLocationSemi.Flag.LINKED.getValue());
		save(ctx, locationSemiNext);
		locationSemi.setNextId(locationSemiNext.getId());
	}
	
	protected boolean isLinked(int flag){
		return flag!=EfLocationSemi.Flag.UNHANDLE.getValue() && flag!=EfLocationSemi.Flag.FILTERED.getValue();
	}
	protected void deleteLocationSemi(Context ctx,EfLocationSemi locationSemi) throws BizException{
		int flagBefore=locationSemi.getFlag().intValue();
		if(getValidFlags().contains(flagBefore)){
			//FILTERED之后的标识 才进行处理
			if(isLinked(flagBefore)){				
				EfLocationSemi locationSemiPre=getPrevious(locationSemi);	//getPreviousById
				EfLocationSemi locationSemiNext=getNext(locationSemi);	//getNextById
				if(null!=locationSemiPre){
					EjlLocation location=ejlComLocationService.getBySourceId(locationSemiPre.getId());
					if(null!=location){
						ejlComLocationService.remove(ctx, location.getId());
					}
					locationSemiPre.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
					locationSemiPre.setNextId(null);
					save(ctx, locationSemiPre);
				}
				if(null!=locationSemiNext){
					EjlLocation location=ejlComLocationService.getBySourceId(locationSemiNext.getId());
					if(null!=location){
						ejlComLocationService.remove(ctx, location.getId());
					}
					locationSemiNext.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
					locationSemiNext.setPreviousId(null);
					save(ctx, locationSemiNext);
				}
			}
			locationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
			save(ctx, locationSemi);
		}
	}
	*//**
	 * 过滤坐标（根据规则）
	 * @param list
	 * @throws Exception
	 *//*
	protected void filterLocationSemiByRule(Context ctx,List<EfLocationSemi> locationSemiList) throws Exception{
		*//**
		 * 1.GPS>WIFI>BTS
		 *//*
		if(null!=locationSemiList){
			for(EfLocationSemi locationSemi:locationSemiList){
				
			}
		}
	}
	
}


*/