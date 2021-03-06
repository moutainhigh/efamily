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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.base.thread.BizMultiThread;
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
import com.winterframework.efamily.service.ITskLocationSemiServiceNew;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.efamily.utils.Constant;
import com.winterframework.efamily.utils.LocationUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("tskLocationSemiServiceImplFiveSpotAlgorithm")
@Transactional(rollbackFor = Exception.class)
public class TskLocationSemiFiveSpotAlgorithmServiceImpl extends EfComLocationSemiServiceImpl implements ITskLocationSemiServiceNew
{
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
	@Resource(name = "tskLocationSemiServiceImpl")
	private ITskLocationSemiService tskLocationSemiService;
	
	@Resource(name = "RedisClient")
	private RedisClient redisClient;   
	
	@PropertyConfig(value = "Juhe.weight.gps")
	private String  gpsWeight;
	
	@PropertyConfig(value = "Juhe.weight.wifi")
	private String 	wifiWeight;
	
	@PropertyConfig(value = "Juhe.weight.cell")
	private String  cellWeight;
	
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	
	private static final ExecutorService threadPool1 = Executors.newFixedThreadPool(80);
	
	@Override
	public List<Map<String,Long>> getUnhandledDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.UNHANDLE.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getUnhandledMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(userId,deviceId, EfLocationSemi.Flag.UNHANDLE.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getUnhandledListByDeviceIdAndTimeFromTo(
			Long userId,Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.UNHANDLE.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	public List<EfLocationSemi> getNearListByDeviceIdAndTimeFromTo(
			Long userId,Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.NEIGHBORING.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	public List<EfLocationSemi> getInitListByDeviceIdAndTimeFromTo(
			Long userId,Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.INITJUHE.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getValidListAfterAggredByUserIdAndDeviceIdAndTimeFromTo(
			Long userId, Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<EfLocationSemi> getFilteredListByDeviceIdAndTimeFromTo(Long userId,Long deviceId, Date timeFrom, Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.FILTERED.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getLinkedListByDeviceIdAndTimeFromTo(Long userId,Long deviceId, Date timeFrom, Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.LINKED.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Map<String,Long>> getAggregationDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{	
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.JUHEFINISH.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getAggregationMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(userId,deviceId, EfLocationSemi.Flag.JUHEFINISH.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	public Map<Date, Date> getInitJuheMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(userId,deviceId, EfLocationSemi.Flag.INITJUHE.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<EfLocationSemi> getAggregationListByDeviceIdAndTimeFromTo(Long userId,
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.JUHEFINISH.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	
	public List<EfLocationSemi> getInitJuheListByDeviceIdAndTimeFromTo(Long userId,
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.INITJUHE.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<Map<String,Long>> getDetailedDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.DETAIL.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Map<String,Long>> getAggAndDetailedDeviceIdList(Date timeFrom, Date timeTo)
			throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.JUHEFINISH.getValue());
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			return tskLocationSemiDao.getDeviceIdListByFlags( timeFrom,timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<Map<String, Long>> getNeedHandleDeviceList(Date timeFrom,
			Date timeTo) throws BizException {
		try{
			List<Integer> flags=getValidFlags();
			flags.remove(new Integer(EfLocationSemi.Flag.FINISH.getValue()));
			return tskLocationSemiDao.getDeviceIdListByFlags( timeFrom,timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getSameList(EfLocationSemi locationSemi) throws BizException {
		try{
			return tskLocationSemiDao.getSameListById(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getDetailedMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(userId,deviceId, EfLocationSemi.Flag.DETAIL.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getDetailedListByDeviceIdAndTimeFromTo(Long userId,
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.DETAIL.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	@Override
	public List<Map<String,Long>> getRetainedDeviceIdList(Date timeFrom,Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getDeviceIdListByFlag(EfLocationSemi.Flag.RETAIN.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public Map<Date, Date> getRetainedMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlag(userId,deviceId, EfLocationSemi.Flag.RETAIN.getValue(), timeFrom,timeTo);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getRetainedListByDeviceIdAndTimeFromTo(Long userId,
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlag(userId,deviceId, timeFrom, timeTo, EfLocationSemi.Flag.RETAIN.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getRetainedListNearByDeviceIdAndLatLngAndOffset(Long userId,
			Long deviceId, double longitude, double latitude, double offset)
			throws BizException {
		try{
			double longitudeFrom=longitude-offset;
			double longitudeTo=longitude+offset;
			double latitudeFrom=latitude-offset;
			double latitudeTo=latitude+offset;
			return tskLocationSemiDao.getListNearByDeviceIdAndLatLngAndFlag(userId,deviceId, longitudeFrom, longitudeTo, latitudeFrom, latitudeTo, EfLocationSemi.Flag.RETAIN.getValue());
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	public List<EfLocationSemi> getRetailedAndFinishedListNearByDeviceIdAndLatLngAndOffset(Long userId,
			Long deviceId, double longitude, double latitude, double offset)
			throws BizException {
		try{
			double longitudeFrom=longitude-offset;
			double longitudeTo=longitude+offset;
			double latitudeFrom=latitude-offset;
			double latitudeTo=latitude+offset;
			List<Integer> flags=getFlagsRetainedAndFinished();
			return tskLocationSemiDao.getListNearByDeviceIdAndLatLngAndFlags(userId,deviceId, longitudeFrom, longitudeTo, latitudeFrom, latitudeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
		
	}
	@Override
	public List<EfLocationSemi> getValidListNearByDeviceIdAndLatLngAndOffset(Long userId,
			Long deviceId, double longitude, double latitude, double offset)
			throws BizException {
		try{
			double longitudeFrom=longitude-offset;
			double longitudeTo=longitude+offset;
			double latitudeFrom=latitude-offset;
			double latitudeTo=latitude+offset;
			List<Integer> flags=getValidFlags();
			return tskLocationSemiDao.getListNearByDeviceIdAndLatLngAndFlags(userId,deviceId, longitudeFrom, longitudeTo, latitudeFrom, latitudeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getValidPreNextListByIdAndCount(EfLocationSemi locationSemi, int count) throws BizException {
		try{ 
			List<Integer> flags=getValidFlags();
			Long userId=locationSemi.getUserId();
			Long deviceId=locationSemi.getDeviceId();
			Long id =locationSemi.getId();
			return tskLocationSemiDao.getPreNextListByIdAndCountAndFlags(userId,deviceId,id,count, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getBtsListNearByDeviceIdAndTimeAndOffset(Long userId,
			Long deviceId, Date time, int offset) throws BizException {
		try{
			List<EfLocationSemi> locationSemiList=getListNearByDeviceIdAndTimeAndOffset(userId,deviceId, time, offset);
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
	public List<EfLocationSemi> getListNearByDeviceIdAndTimeAndOffset(Long userId,
			Long deviceId, Date time, int offset) throws BizException {
		try{
			Date timeFrom=DateUtils.addSeconds(DateUtils.addMinutes(time, -1*offset),1);
			Date timeTo=DateUtils.addSeconds(DateUtils.addMinutes(time, offset),-1);
			return tskLocationSemiDao.getListNearByDeviceIdAndTime(userId,deviceId, timeFrom, timeTo);
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
	public List<Map<String,Long>> getDetailedAndSuspectDeviceIdList(Date timeFrom,Date timeTo)
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
	public Map<Date, Date> getDetailedAndSuspectMaxMinTimeByDeviceId(Long userId,Long deviceId,
			Date timeFrom,Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getMaxMinTimeByDeviceIdAndFlags(userId,deviceId, timeFrom,timeTo,flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getRetainedFirstByDeviceId(Long userId,Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.RETAIN.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(userId,deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getDetailedAndSuspectFirstByDeviceId(Long userId,Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(userId,deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getFilterFirstByDeviceId(Long userId,Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.FILTERED.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(userId,deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getLinkedFirstByDeviceId(Long userId,Long deviceId, Date timeFrom,
			Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.LINKED.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(userId,deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public EfLocationSemi getUnhandledFirstByDeviceId(Long userId,Long deviceId, Date timeFrom,
			Date timeTo) throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.UNHANDLE.getValue());
			return tskLocationSemiDao.getFirstByDeviceIdAndFlags(userId,deviceId, timeFrom, timeTo, flags);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public List<EfLocationSemi> getDetailedAndSuspectListByDeviceIdAndTimeFromTo(Long userId,
			Long deviceId, Date timeFrom, Date timeTo)
			throws BizException {
		try{
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.DETAIL.getValue());
			flags.add(EfLocationSemi.Flag.SUSPECT.getValue());
			return tskLocationSemiDao.getListByDeviceIdAndTimeFromToAndFlags(userId,deviceId, timeFrom, timeTo,flags);
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
				locationSemiPre=getPreviousAfterAggred(locationSemi);	//getPreviousById
			}
			if(null==locationSemiNext){
				locationSemiNext=getNextAfterAggred(locationSemi);	//getNextById
			}
			if(null!=locationSemiPre){
				//去掉前后关系设置 
				 * if(null!=locationSemiNext){
					locationSemiPre.setNextId(locationSemiNext.getId());
				}else{
					locationSemiPre.setNextId(null);
				}
				locationSemiPre.setTimeEnd(locationSemi.getTimeEnd());
				//如果前点已经处理完成 则需要重新经过后续的逻辑 以便触发ejl_location的更新
				if(getFlagsRetainedAndFinished().contains(locationSemiPre.getFlag())){
					//无需更改运动模式
					locationSemiPre.setFlag(EfLocationSemi.Flag.RETAIN.getValue());	//前点无需再计算详情
				}
				save(ctx, locationSemiPre);
			}
			//next的处理依赖当前点 要先于处理
			locationSemi.setFlag(isInitial?EfLocationSemi.Flag.DELETE.getValue():EfLocationSemi.Flag.DISPOSE.getValue());
			save(ctx, locationSemi);
			if(null!=locationSemiNext){
				//去掉前后关系设置 locationSemiNext.setPreviousId(locationSemiPre.getId());
				locationSemiNext.setDistance(calcDistance(locationSemiPre,locationSemiNext));
				locationSemiNext.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, locationSemiNext); 
				processDetail(ctx, locationSemiNext);
			}
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	@Override
	public void doProcessNew() throws BizException {
		final int days=1;	//1天之内 根据实际有意义的轨迹要求(24小时)++天数
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		log.debug("process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		List<Map<String,Long>> deviceList=getNeedHandleDeviceList(timeFrom,timeTo);
		deviceList=getDeviceId(deviceList);
		if(null!=deviceList){
			final Context ctx=new Context();
			ctx.set("userId", -1);
			for(Map<String,Long> device:deviceList){
				final Long userId=device.get("userId");
				final Long deviceId=device.get("deviceId"); 
				final String target=userId+Separator.vertical+deviceId;
				new BizMultiThread(threadPool,Constant.LOCATION_LOCK+target,60*30) {
					@Override
					protected void doBiz() throws BizException {
						log.debug("process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
						//***  处理临界点  异常点调整 
						process(ctx, userId, deviceId,timeFrom,timeTo);
						log.debug("doProcessAggregationLatLon start.timeFrom="+timeFrom+ " timeTo="+timeTo);
						//***  聚合经纬度  区域处理
						doProcessAggregationLatLon(ctx, userId, deviceId,timeFrom, timeTo);
						log.debug("doProcessLocation start.timeFrom="+timeFrom+ " timeTo="+timeTo);
						//***  从ef_location_semi ==> ejl_location 此时状态修改为 99 
						doProcessLocation(ctx, userId, deviceId,timeFrom,timeTo);
					}
				}.start();
			}
		}
	} 
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void process(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo)
			throws BizException {
		log.debug("filterLocationSemi start.deviceId="+deviceId);
		//*** 处理完临界点
		filterLocationSemi(ctx,userId,deviceId,timeFrom,timeTo);
		
		log.debug("aggregationLocationSemi start.deviceId="+deviceId);
		//按指定的距离聚合临界点数据 : 聚合因点被删 
		aggregationLocationSemi(ctx,userId,deviceId,timeFrom,timeTo);
		
		//把初始聚合的数据改为聚合结束 :全部为聚合结束状态
		doProcessChangeFlagToJuhe(ctx,userId,deviceId,timeFrom,timeTo);
		
		log.debug("doProcessDetail start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		//*** 计算详情 :方向速度
		doProcessDetail(ctx,userId,deviceId,timeFrom,timeTo);
		log.debug("doProcessAdjust start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		//*** 往前聚合  删除自己 往后聚合 
		doProcessAdjust(ctx,userId,deviceId,timeFrom,timeTo);
		log.debug("doProcessAggregation start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		//*** 经过调整后 重新聚合：因为doProcessAdjust会删除点
		doProcessAggregation(ctx,userId,deviceId,timeFrom, timeTo);
		//若还有聚合的点需要处理 则递归处理
		if(isNeedProcess(ctx,userId,deviceId,timeFrom, timeTo)){
			log.debug("again process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
			process(ctx,userId,deviceId,timeFrom, timeTo);
		}
	}
	protected void doProcessLocation(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo)throws BizException{
		List<EfLocationSemi> locationSemiList=getRetainedListByDeviceIdAndTimeFromTo(userId, deviceId, timeFrom, timeTo);
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
				location.setRadius(locationSemi.getRadius());
				location.setTime(locationSemi.getTime());
				location.setStatus(YesNo.YES.getValue());
				location.setRemark(locationSemi.getRemark());
				location.setTimeStay((int)DateUtils.calcSecondsBetween(locationSemi.getTimeBegin(),locationSemi.getTimeEnd()));
				location.setAddress(locationSemi.getAddress());
				location.setCity(locationSemi.getCity());
				locationList.add(location);
				locationSemi.setFlag(EfLocationSemi.Flag.FINISH.getValue());
			} 
			ejlComLocationService.save(ctx, locationList);
			tskLocationSemiService.save(ctx, locationSemiList); 
		}
	}
	protected boolean isNeedProcess(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		//List<Long> deviceIdList=getAggAndDetailedDeviceIdList(timeFrom, timeTo);
		List<EfLocationSemi> locationSemiList=getAggregationListByDeviceIdAndTimeFromTo(userId, deviceId, timeFrom, timeTo);
		
		return null!=locationSemiList && locationSemiList.size()>0;
	}
	public void doProcessDetail(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo)throws BizException{
		final int interval=6*60;	//30分钟
		Map<Date,Date> dateMap=getAggregationMaxMinTimeByDeviceId(userId,deviceId, timeFrom,timeTo);
		if(null!=dateMap && dateMap.size()>0){
			Date minTime=dateMap.get("minTime");
			Date maxTime=dateMap.get("maxTime");
			Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
			int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
			for(int i=0;i<count;i++){				
				Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
				Date timeTo2=DateUtils.addMinutes(timeFrom2,interval);
				processDetail(ctx,userId,deviceId,timeFrom2,timeTo2);
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doProcessChangeFlagToJuhe(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo)throws BizException{
		final int interval=6*60;	//30分钟
		Map<Date,Date> dateMap=getInitJuheMaxMinTimeByDeviceId(userId,deviceId, timeFrom,timeTo);
		if(null!=dateMap && dateMap.size()>0){
			Date minTime=dateMap.get("minTime");
			Date maxTime=dateMap.get("maxTime");
			Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
			int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
			boolean isExist = false;
			for(int i=0;i<count;i++){				
				Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
				Date timeTo2=DateUtils.addMinutes(timeFrom2,interval);
				List<EfLocationSemi> locationSemiList=getInitJuheListByDeviceIdAndTimeFromTo(userId,deviceId,timeFrom2,timeTo2);
				if(locationSemiList != null) {
					for(EfLocationSemi efLocationSemi:locationSemiList){
						//*** 遇到最新临界点   修改状态终止
						if(efLocationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue())
						{
							isExist = true;
							break;
						}
						efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
					}
					tskLocationSemiService.save(ctx, locationSemiList);
					if(isExist){
						break;
					}
				}
			}
		}
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void processAdjust(Context ctx, Long userId, Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		EfLocationSemi locationSemi=getDetailedAndSuspectFirstByDeviceId(userId,deviceId, timeFrom,timeTo);
		while(null!=locationSemi){	
			if(locationSemi.getTime().getTime()>timeTo.getTime()) break;
			processAdjustNew(ctx,locationSemi);
			locationSemi=getNextAfterAggred(locationSemi);
		}
	}
	public void doProcessAdjust(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException {
		final int totalCount=2000;	//一次处理记录数 
		int count=0;	//处理数 
		//*** 获取  DETAIL  SUSPECT 点 
		EfLocationSemi locationSemi=getDetailedAndSuspectFirstByDeviceId(userId,deviceId, timeFrom,timeTo);
		while(null!=locationSemi){	
			if(locationSemi.getTime().getTime()>timeTo.getTime()) break;
			processAdjustNew(ctx,locationSemi);
			count++;
			if(count>totalCount){
				break;
			}
			//*** 获取下一个有效点
			locationSemi=getNextAfterAggred(locationSemi);

		}
	}
	

	
	public void tranToRetainForMoreSpotAndLongStay(Context ctx,EfLocationSemi locationSemi) throws BizException{
		if(locationSemi.getAggrCount()>2 || isLongStay(locationSemi)){
			locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
		}else if(locationSemi.getFlag().intValue()<EfLocationSemi.Flag.SUSPECT.getValue()){
			locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
		}else{
			return;
		}
		save(ctx, locationSemi);
	}
	
	public void tranToRetailForFirstAndSecondSpot(Context ctx,EfLocationSemi locationSemi) throws BizException{
		locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
		save(ctx, locationSemi);
	}
	
	public boolean isLongStay(EfLocationSemi locationSemi){
		return locationSemi.getTimeEnd().getTime()-locationSemi.getTimeBegin().getTime()/1000>LocationUtil.TIME_STAY*60;
	}
	
	public EfLocationAssist getEfLocationAssistBy(EfLocationSemi locationSemi) throws BizException{
		EfLocationAssist efLocationAssist = new EfLocationAssist();
		if(locationSemi!=null && locationSemi.getId()!=null){
			efLocationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
		}
		return efLocationAssist;
	}
	
	
	protected void processAdjustNew(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationSemi locationSemiPre=null; 
		EfLocationSemi locationSemiPrePre=null; 
		EfLocationAssist locationAssistPrePre=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistTmp=null;
		EfLocationSemi lcPre=getPreviousAfterAggred(locationSemi);
		if(null==lcPre){	//locationSemi.getPreviousId()
			locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
		}else{
			boolean isPreRetained=false;
			locationSemiPre=getPreviousAfterAggred(locationSemi);	//getPreviousById
			locationAssistPre=tskLocationAssistService.getByLocationId(locationSemiPre.getId());
			locationAssist=tskLocationAssistService.getByLocationId(locationSemi.getId());
			if(locationSemiPre.getFlag().intValue()==EfLocationSemi.Flag.SUSPECT.getValue()){
				//可疑点必有前点
				locationSemiPrePre=getPreviousAfterAggred(locationSemiPre);	//getPreviousById
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
				locationAssistTmp.setRemark(getRemark(locationAssistTmp.getRemark()+"last info:speed="+locationAssist.getSpeed()
						+",slopeDegree="+locationAssist.getSlopeDegree()
						+",moveMode="+locationAssist.getMoveMode()
						+",direction="+locationAssist.getDirection()  
						));
				
				boolean isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,locationAssistTmp);
				boolean isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,locationAssistTmp,!isDegreeTooBigP200);
				boolean isAccTooBigP200=isAccTooBig(locationAssistTmp);
				boolean isDegreeTooBigP201=isDegreeTooBig(locationAssistPre,locationAssistTmp);
				boolean isSpeedTooBigP201=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssistTmp,!isDegreeTooBigP201);
				
				if((isSpeedTooBigP200  || isDegreeTooBigP200) && !isSpeedTooBigP201 && !isDegreeTooBigP201 && !isAccTooBigP200){
					isPreRetained=true;
					locationSemiPre.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
					//可疑点得到排除 则不用更新当前点的运动模式
					locationSemiPre.setRemark(getRemark(locationSemiPre.getRemark()+"A可疑点处理P1：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200
							+ " isSpeedTooBigP201"+isSpeedTooBigP201+ " isDegreeTooBigP201"+isDegreeTooBigP201+ " isAccTooBigP200"+isAccTooBigP200));
					save(ctx, locationSemiPre);
				}else if ((!isSpeedTooBigP200 && !isDegreeTooBigP200) || isSpeedTooBigP201 || isDegreeTooBigP201 || isAccTooBigP200){
					locationSemiPre.setRemark(getRemark(locationSemiPre.getRemark()+ "B可疑点处理P1：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200
							+ " isSpeedTooBigP201"+isSpeedTooBigP201+ " isDegreeTooBigP201"+isDegreeTooBigP201+ " isAccTooBigP200"+isAccTooBigP200));
					dispose(ctx, locationSemiPrePre,locationSemiPre,locationSemi,false);
					
					EfLocationAssist lassist=tskLocationAssistService.getByLocationId(locationSemi.getId());
					//以下if逻辑对于当前点停留时间较长 则不起作用
					if((locationSemi.getTimeEnd().getTime()-locationSemi.getTimeBegin().getTime())/1000<LocationUtil.TIME_STAY*60){
						
						//重新计算后一点的坐标详情--即保存locationAssistTmp
						tskLocationAssistService.save(ctx, locationAssistTmp);
						//重新设置前后点关系
						locationSemi.setDistance(calcDistance(locationSemiPrePre,locationSemi));
						save(ctx, locationSemiPrePre);
						isDegreeTooBigP200=isDegreeTooBig(locationAssistPrePre,lassist);
						isSpeedTooBigP200=isSpeedTooBig(locationSemiPrePre,locationSemi,locationAssistPrePre,lassist,!isDegreeTooBigP200);
						isAccTooBigP200=isAccTooBig(lassist); 
						
						if(isSpeedTooBigP200 && isAccTooBigP200){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"C可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isAccTooBigP200="+isAccTooBigP200));
							dispose(ctx, locationSemiPrePre,locationSemi,null, false);
						}else if(isSpeedTooBigP200||isDegreeTooBigP200){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"D可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200));
							locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
							//next
						}else if(!isSpeedTooBigP200 && !isDegreeTooBigP200){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"E可疑点处理P2：isSpeedTooBigP200="+isSpeedTooBigP200+" isDegreeTooBigP200="+isDegreeTooBigP200));
							locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
							//直接保留 moveMove等于前点运动模式
							lassist.setMoveMode(locationAssistPrePre.getMoveMode());
							lassist.setRemark(getRemark("直接保留 moveMove等于前点运动模式"));
							tskLocationAssistService.save(ctx, lassist);
						}
					}else{	//当前点停留时间较长 则保留
						locationSemi.setRemark(getRemark(locationSemi.getRemark()+"F直接保留P2."));
						locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
						//停留保存 则更新运动模式为停留
						lassist.setMoveMode(EfLocationAssist.MoveMode.STAY.getValue());
						lassist.setRemark(getRemark("停留保存 则更新运动模式为停留"));
						tskLocationAssistService.save(ctx, lassist);
					}				
				}
				
			}
			//当前点停留时间较长 则保留
			if((locationSemi.getTimeEnd().getTime()-locationSemi.getTimeBegin().getTime())/1000<LocationUtil.TIME_STAY*60){
				//当前点如果是保留点 且 前点处理后变成保留 则不处理当前点
				if(!(isPreRetained && isLocationOk(locationSemi.getFlag().intValue()))){
					if(isLocationOk(locationSemiPre.getFlag().intValue())){
						boolean isDegreeTooBigP21=isDegreeTooBig(locationAssistPre,locationAssist);
						boolean isSpeedTooBigP21=isSpeedTooBig(locationSemiPre,locationSemi,locationAssistPre,locationAssist,!isDegreeTooBigP21);
						boolean isAccTooBigP21=isAccTooBig(locationAssist);
						if(isSpeedTooBigP21 && isAccTooBigP21){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"F保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isAccTooBigP21="+isAccTooBigP21));
							dispose(ctx, locationSemiPre,locationSemi,null,false);
						}else if(isSpeedTooBigP21 || isDegreeTooBigP21){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"G保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isDegreeTooBigP21="+isDegreeTooBigP21));
							locationSemi.setFlag(EfLocationSemi.Flag.SUSPECT.getValue());
						}else if(!isSpeedTooBigP21 && !isDegreeTooBigP21){
							locationSemi.setRemark(getRemark(locationSemi.getRemark()+"H保留点处理P2：isSpeedTooBigP21="+isSpeedTooBigP21+" isDegreeTooBigP21="+isDegreeTooBigP21));
							locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
							//直接保留 moveMove等于前点运动模式
							locationAssist.setMoveMode(locationAssistPre.getMoveMode());
							tskLocationAssistService.save(ctx, locationAssist);
						}
					}
				}
			}else{
				locationSemi.setRemark(getRemark(locationSemi.getRemark()+"G直接保留P2."));
				locationSemi.setFlag(EfLocationSemi.Flag.RETAIN.getValue());
				//停留保存 则更新运动模式为停留
				locationAssist.setMoveMode(EfLocationAssist.MoveMode.STAY.getValue());
				locationAssist.setRemark(getRemark("停留保存 则更新运动模式为停留"));
				tskLocationAssistService.save(ctx, locationAssist);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void processDetail(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		List<EfLocationSemi> locationSemiList=getAggregationListByDeviceIdAndTimeFromTo(userId,deviceId,timeFrom,timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			for(EfLocationSemi locationSemi:locationSemiList){ 
				processDetail(ctx, locationSemi);
			}
		}
		
	}
	protected void processDetail(Context ctx, EfLocationSemi locationSemi)
			throws BizException {
		EfLocationAssist locationAssist=null;
		EfLocationAssist locationAssistPre=null;
		EfLocationSemi locationSemiPre=null; 
		locationSemiPre=getPreviousAfterAggred(locationSemi);	//getPreviousById
		
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
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void processAggregation(Context ctx, Long userId, Long deviceId,
			Date timeFrom, Date timeTo) throws BizException {
		List<EfLocationSemi> locationSemiList=getRetainedListByDeviceIdAndTimeFromTo(userId,deviceId,timeFrom,timeTo);
		if(locationSemiList != null){
			sort(locationSemiList, true);
			for(EfLocationSemi efLocationSemi:locationSemiList){
				EfLocationSemi efLocationSemiDb = this.get(efLocationSemi.getId());
				if(efLocationSemiDb.getFlag().intValue() == EfLocationSemi.Flag.RETAIN.getValue()){
					aggregationNearLocationSemi(ctx,1,efLocationSemi,0,0);
				}
			}
		}
	}
	public void doProcessAggregation(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException {
		final int interval=6*60;	//30分钟
		Map<Date,Date> dateMap=getRetainedMaxMinTimeByDeviceId(userId,deviceId, timeFrom,timeTo);
		if(null!=dateMap && dateMap.size()>0){
			Date minTime=dateMap.get("minTime");
			Date maxTime=dateMap.get("maxTime");
			Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
			int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
			for(int i=0;i<count;i++){				
				Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
				Date timeTo2=DateUtils.addMinutes(timeFrom2,interval); 
				processAggregation(ctx, userId, deviceId, timeFrom2, timeTo2);
			}
		}
	}
	public void doProcessAggregationLatLon(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException {
		final int interval=6*60;	//30分钟
		Map<Date,Date> dateMap=getRetainedMaxMinTimeByDeviceId(userId,deviceId, timeFrom,timeTo);
		if(null!=dateMap && dateMap.size()>0){
			Date minTime=dateMap.get("minTime");
			Date maxTime=dateMap.get("maxTime");
			Long minutes=DateUtils.calcMinutesBetween(minTime, maxTime);
			int count=minutes==0L?1:((int)Math.ceil(minutes*1.0/interval));
			for(int i=0;i<count;i++){				
				Date timeFrom2=DateUtils.addMinutes(minTime,(interval*i));
				Date timeTo2=DateUtils.addMinutes(timeFrom2,interval); 
				processAggregationLatLon(ctx,userId,deviceId,timeFrom2,timeTo2);
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void processAggregationLatLon(Context ctx, Long userId,
			Long deviceId, Date timeFrom, Date timeTo) throws BizException {
		
		 * 升序点集合
		 * map中寻找经纬度，若无则往前找到最近一个与P点坐标接近的点 (第3位小数加减3~=500m)，map记录经纬度
		 * 			          若有则更新经纬度
		 * 
		 
		//*** RETAIN(32) 把保留点都拿出来
		List<EfLocationSemi> locationSemiList=getRetainedListByDeviceIdAndTimeFromTo(userId,deviceId,timeFrom,timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			Map<String,LatLng> latlngMap=new HashMap<String,LatLng>();
			List<EfLocationSemi> locationSemiTmpList =new ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				double lat=LocationUtil.format(locationSemi.getLatitude(),LocationUtil.PREC_LATLON);
				double lon=LocationUtil.format(locationSemi.getLongitude(),LocationUtil.PREC_LATLON);
				double offset=getLatLonOffSet();//区域 半径经纬度精确半径
				String key=lat+Separator.comma+lon;
				
				LatLng latlng=null;
				//*** 拿出该点半径区域之前的点 
				List<EfLocationSemi> locationSemiPreList=getRetailedAndFinishedListNearByDeviceIdAndLatLngAndOffset(locationSemi.getUserId(),locationSemi.getDeviceId(), lon, lat, offset);
				if(null!=locationSemiPreList){
					sort(locationSemiPreList, true);
					for(EfLocationSemi locationSemiPre:locationSemiPreList){
						if(locationSemiPre.getId().equals(locationSemi.getId())) {
							continue;
						}
						//*** 距离小于80米
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
			tskLocationSemiService.save(ctx, locationSemiTmpList);
		}
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void processAggregationLatLon(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		
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
				double offset=getLatLonOffSet();
				String key=lat+Separator.comma+lon;
				
				LatLng latlng=null;
				List<EfLocationSemi> locationSemiPreList=getRetailedAndFinishedListNearByDeviceIdAndLatLngAndOffset(locationSemi.getUserId(),locationSemi.getDeviceId(), lon, lat, offset);
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
			tskLocationSemiService.save(ctx, locationSemiTmpList);
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
	private List<Map<String,Long>> getDeviceId(List<Map<String,Long>> l){
		List<Map<String,Long>> m=new ArrayList<Map<String,Long>>();
		Map<String,Long> mm=new HashMap<String,Long>();
		mm.put("userId",1470L);
		mm.put("deviceId",376L);
		m.add(mm);
		boolean f=false;
		for(Map<String,Long> k:l){
			if(k.get("userId").equals(m.get(0).get("userId")) && k.get("deviceId").equals(m.get(0).get("deviceId"))){
				f=true;
				break;
			}
		}
		if(!f){
			m.clear();
		}
		return l;
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
		double respeed=getRespeed(locationSemiPre, locationSemi, locationAssistPre, locationAssist);
		double speedSpan=locationAssist.getSpeed()-respeed;
		//速度=距离/(第2坐标的最早时间-第1坐标的最晚时间)		
		long timeSpan=locationSemi.getTimeBegin().getTime()-locationSemiPre.getTimeEnd().getTime();
		if(getMoveMode(respeed)==EfLocationAssist.MoveMode.STAY.getValue()){
			timeSpan=timeSpan/2;
		}
		return LocationUtil.calcAcceleration(speedSpan, timeSpan);
	}
	private double getRespeed(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		////距离<速度*停留时间 && 停留时间>3min 则速度置为0
		//停留时间>3min 则速度置为0
		double speedReal=LocationUtil.MIN_VALUE;
		if(null!=locationSemiPre){
			int timeStay=(int)DateUtils.calcMinutesBetween(locationSemiPre.getTimeBegin(), locationSemiPre.getTimeEnd());
			double averSpeed=(locationAssistPre.getSpeed()+locationAssist.getSpeed())/2;
			speedReal=locationAssistPre.getSpeed();
			//if(timeStay>LocationUtil.TIME_STAY && (averSpeed*timeStay*1.0/60*1000)>locationSemi.getDistance()){
			if(timeStay>LocationUtil.TIME_STAY){
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
					}else{
						if(o1.getTime().equals(o2.getTime())){
							return 0;
						}
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
	
	private boolean isSpeedTooBig(EfLocationSemi locationSemiPre,EfLocationSemi locationSemi,EfLocationAssist locationAssistPre,EfLocationAssist locationAssist,boolean isApproxDirection){
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
			//mMode=calcMoveMode(locationSemi);
		}
		if(isApproxDirection){	//如果方向相似 则不区分运动模式速度下限
			int value=locationAssistPre.getMoveMode();	//取前点的运动模式
			EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(value);
			double SMALL=moveMode.getPrecision();
			return locationAssist.getSpeed()-realSpeedPre>SMALL;	//只要后者速度不偏大（可以偏小）--大于前者运动模式（平均速度）的精度值
		}else{
			int value=locationAssist.getMoveMode()>mMode?mMode:locationAssist.getMoveMode();	//取小的运动模式 才能分辨是否运动模式可能发生了变化
			EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(value);
			double SMALL=moveMode.getPrecision();
			return Math.abs(locationAssist.getSpeed()-realSpeedPre)>SMALL;	//如果方向不相似 则计算后者速度偏大偏小--大于小于前者运动模式（平均速度）的精度值
		}
		//return locationAssist.getSpeed()-realSpeedPre>SMALL;	//只计算后者速度偏大  大于前者运动模式（平均速度）的精度值
		//return Math.abs(locationAssistPrePre.getMoveMode()-locationAssist.getMoveMode())>0;
	}
	private int calcMoveMode(EfLocationSemi locationSemi){
		*//**
		 * 取前一保留点
		 *//*
	}
	private boolean isAccTooBig(EfLocationAssist locationAssist){ 
		EfLocationAssist.MoveMode moveMode=EfLocationAssist.MoveMode.getMoveMode(locationAssist.getMoveMode());
		final double ACC=moveMode.getAcceleration();
		//return !(ACC/2 <Math.abs(locationAssist.getAcceleration()) && Math.abs(locationAssist.getAcceleration())<ACC*3/2);
		return !(locationAssist.getAcceleration()<ACC*1.5);	//只计算后者加速度偏大
	}
	private boolean isDegreeTooBig(EfLocationAssist locationAssistPre,EfLocationAssist locationAssist){
		final int SMALL=30;
		final int MIDDLE=45;
		final int LARGE=60;
		final int FULL=90;
		final int HALFC=180;
		String directionPre=locationAssistPre.getDirection();
		String direction=locationAssist.getDirection();
		//(ES WS & EN WN) || (ES WN & EN WN)
		if((directionPre.substring(1).equals(direction.substring(1)) && !directionPre.substring(0,1).equals(direction.substring(0,1)))
				|| (!directionPre.substring(1).equals(direction.substring(1)) && !directionPre.substring(0,1).equals(direction.substring(0,1)))){
			return (HALFC-Math.abs(locationAssistPre.getSlopeDegree()-locationAssist.getSlopeDegree()))>MIDDLE;
		}
		return Math.abs(locationAssistPre.getSlopeDegree()-locationAssist.getSlopeDegree())>MIDDLE;
	}
	
	
	////////////////////////////优先级、聚合逻辑////////////////////////////////
	private List<EfLocationSemi> getInitLocationSemi(List<EfLocationOrigin> list) throws BizException{
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
		efLocationSemi.setRadius(efLocationOrigin.getRadius());
		efLocationSemi.setRemark("");
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
	
	private static Integer EACH_PRO_MINUTES=360;
	
	
	public void handlerLocationSemi(final Long userId, final Long deviceId,List<List<Date>> dateSplitList,Context ctx) throws BizException{
		for(List<Date> dateList : dateSplitList){
			List<EfLocationOrigin> list=null;
			try {
				list = efComLocationOriginDaoImpl.getLocationOriginByDevice(userId,deviceId,dateList.get(0),dateList.get(1));
			} catch (Exception e) {
				log.error("query location origin error",e);
			}
			
			if(list==null||list.size()==0){
				continue;
			}
			for(EfLocationOrigin efLocationOrigin:list){
				efLocationOrigin.setFlag(YesNo.YES.getValue());
			}
			
			
			*//**
			 * 1.处理当前未处理的list
			 * 2.走数据库处理(所有坐标点)
			 *//*
			
			//***  EfLocationOrigin ==> EfLocationSemi  (简单转换)
			List<EfLocationSemi> locationSemiList=getInitLocationSemi(list);
			//*** 获取本批次最近的一条定位的时间
			Date maxTime=getMaxTime(locationSemiList);
			
			log.debug("filterLocationSemiTmp start.deviceId="+deviceId);
			//*** 过滤移动WIFI  过滤优先级
			filterLocationSemiTmp(ctx,locationSemiList);
			
			log.debug("aggregationTmp start.deviceId="+deviceId);

			//*** 聚合点

			//踢除同一时间点 同一source的记录
			distinctLocationSemiTmp(ctx,locationSemiList);

			aggregationTmp(ctx,locationSemiList);
			
			log.debug("distinctLocationSemiTmp start.deviceId="+deviceId);
			
			
			if(locationSemiList != null && locationSemiList.size()>0){
				sort(locationSemiList, true);
				for(EfLocationSemi efLocationSemi:locationSemiList){
					efLocationSemi.setFlag(EfLocationSemi.Flag.INITJUHE.getValue());
				}
				locationSemiList.get(0).setFlag(EfLocationSemi.Flag.NEIGHBORING.getValue());
				locationSemiList.get(locationSemiList.size()-1).setTimeEnd(maxTime);
			}else{
				try{
					EfLocationSemi locationSemi=tskLocationSemiService.getLastLocation(userId, deviceId);
					if(null!=locationSemi){
						locationSemi.setTimeEnd(maxTime);
						save(ctx, locationSemi);
					}
				}catch(Exception e){
					log.error("处理最近更新时间异常",e);
				}
			}
			log.debug("save start.deviceId="+deviceId);
			tskLocationSemiService.save(ctx, locationSemiList);
			
			efLocationOriginService.save(ctx, list);
			
			try{
				EfLocationSemi locationSemi=tskLocationSemiService.getLastLocation(userId, deviceId);
				if(null!=locationSemi){
					locationSemi.setTimeEnd(maxTime);
					save(ctx, locationSemi);
				}
			}catch(Exception e){
				log.error("处理最近更新时间异常",e);
			}
		}
	}
	
	@Override
	public void doProcessOrigin(Long userId,Long deviceId) throws BizException{
		final int days=1;	//7天之内 根据实际有意义的轨迹要求(24小时)++天数
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		final List<List<Date>> dateSplitList = LocationUtil.dateSplit(timeFrom, timeTo, EACH_PRO_MINUTES);
		final String target=userId+Separator.vertical+deviceId;
		final Context ctx = new Context();
		ctx.set("userId", -1);
		String lockKey = Constant.LOCATION_SEMI_LOCK+target;
		if(!redisClient.lock(lockKey, 60*30)){
			return;	//拿锁失败 说明前一个任务执行中 则本次不执行
		}
		try{
			this.handlerLocationSemi(userId, deviceId, dateSplitList, ctx);
		}catch(Exception e){
			log.error("query user location doProcessOrigin error", e);
			throw new BizException(e);
		}finally{
			redisClient.unlock(lockKey);
		}
	}
	
	@Override
	public void doProcessOrigin() throws BizException{
		*//**
		 * origin--入库semi的tmp处理时 不处理time_end
		 *//*
		try{
			final int days=1;	//7天之内 根据实际有意义的轨迹要求(24小时)++天数
			final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
			final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
			
			List<Map<String,Long>> deviceIds = efComLocationOriginDaoImpl.getLocationOriginDeviceId(timeFrom,timeTo);
			final Context ctx = new Context();
			ctx.set("userId", -1);
			if(null!=deviceIds){
				for(Map<String,Long> device:deviceIds){
					final Long userId=device.get("userId");
					final Long deviceId=device.get("deviceId");
					final List<List<Date>> dateSplitList = LocationUtil.dateSplit(timeFrom, timeTo, EACH_PRO_MINUTES);
					final String target=userId+Separator.vertical+deviceId;
					new BizMultiThread(threadPool1,Constant.LOCATION_SEMI_LOCK+target,60*30) {
						@Override
						protected void doBiz() throws BizException {
							handlerLocationSemi(userId, deviceId, dateSplitList, ctx);
						}
					}.start();
				}
			}
		}catch(Exception e){
			log.error("",e);
			throw new BizException(e);
		}
	}
	protected Date getMaxTime(List<EfLocationSemi> locationSemiList){
		if(null!=locationSemiList && locationSemiList.size()>0){
			Date time=locationSemiList.get(0).getTimeEnd();
			for(EfLocationSemi lc:locationSemiList){
				if(DateUtils.isBefore(time, lc.getTimeEnd())){
					time=lc.getTimeEnd();
				}
			}
			return time;
		}
		return null;
	}
	*//**
	 * 聚合处理（未处理的点）
	 * @param ctx
	 * @param deviceId
	 * @param timeFrom
	 * @param timeTo
	 * @throws BizException
	 *//*
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void aggregationLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(userId,deviceId, timeFrom, timeTo);		
		if(locationSemiList != null){
			sort(locationSemiList, true);
			for(EfLocationSemi efLocationSemi:locationSemiList){
				EfLocationSemi efLocationSemiDb = this.get(efLocationSemi.getId());
				if(efLocationSemiDb.getFlag().intValue() == EfLocationSemi.Flag.FILTERED.getValue()){
					aggregationNearLocationSemi(ctx,1,efLocationSemi,0,0);
				}
			}
		}
	}
	
	private void aggregationNearLocationSemi(Context ctx,int direction,EfLocationSemi efLocationSemi,int first,int num) throws BizException{
		//聚合大于40次直接结束
		if(num>40){
			return;
		}else{
			num++;
		}
		if(direction==1){//向前移动
			//找到前面一个点
			EfLocationSemi efLocationSemiPre = this.getPreviousAfterFiltered(efLocationSemi);
			//没有前一个点
			if(efLocationSemiPre == null){
				//第一次或者从后面聚合完成转向前面直接结束   //不是第一次并且不是从后面聚合完成转向前面,转向后面聚合
				if(first==2){
					if(efLocationSemi.getId()!=null && efLocationSemi.getFlag().intValue() != EfLocationSemi.Flag.RETAIN.getValue()){
						efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
						this.save(ctx, efLocationSemi);
					}
					return;
				}else{
					aggregationNearLocationSemi(ctx,2,efLocationSemi,1,num);
				}
			}else{//有前面一个点，聚合两个点
				EfLocationSemi newEfLocationSemi = aggregationTwoPoint(efLocationSemiPre , efLocationSemi);
				if(newEfLocationSemi == null){//不需要聚合
					if(first==2){//第一次或者从后面聚合完成转向前面直接结束
						if(efLocationSemi.getId()!=null && efLocationSemi.getFlag().intValue() != EfLocationSemi.Flag.RETAIN.getValue()){
							efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
							this.save(ctx, efLocationSemi);
						}
						return;
					}else{//否则向后面聚合
						aggregationNearLocationSemi(ctx,2,efLocationSemi,1,num);
					}
				}else{
					//聚合2个点
					this.replace(ctx, efLocationSemiPre, efLocationSemi, newEfLocationSemi);
					//从新点继续向前聚合
					aggregationNearLocationSemi(ctx,1,newEfLocationSemi,1,num);
				}
			}
		}else{//向后聚合
			EfLocationSemi efLocationSemiLast = this.getNextAfterFiltered(efLocationSemi);
			if(efLocationSemiLast == null){//没有后面的点
				if(first == 0 || first==1){//第一次或者前面聚合完成转成后面聚合 直接退出
					if(efLocationSemi.getId()!=null && efLocationSemi.getFlag().intValue() != EfLocationSemi.Flag.RETAIN.getValue()){
						efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
						this.save(ctx, efLocationSemi);
					}
					return;
				}
				else{
					//转向前面聚合
					aggregationNearLocationSemi(ctx,1,efLocationSemi,2,num);
				}
			}else{
				EfLocationSemi newEfLocationSemi = aggregationTwoPoint(efLocationSemi , efLocationSemiLast);
				if(newEfLocationSemi == null){//没有聚合 
					if(first==0||first==1){//第一次或者前面聚合完成转向后面，结束
						if(efLocationSemi.getId()!=null && efLocationSemi.getFlag().intValue() != EfLocationSemi.Flag.RETAIN.getValue()){
							efLocationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
							this.save(ctx, efLocationSemi);
						}
						return;
					}else{
						//转向前面聚合
						aggregationNearLocationSemi(ctx,1,efLocationSemi,2,num);
					}
				}else{//聚合两个点
					this.replace(ctx, efLocationSemi, efLocationSemiLast, newEfLocationSemi);
					//继续向后
					aggregationNearLocationSemi(ctx,2,newEfLocationSemi,2,num);
				}
			}
		}
	}
	
	
	private EfLocationSemi aggregationTwoPoint(EfLocationSemi efLocationSemiA ,EfLocationSemi efLocationSemiB) throws BizException{
		try{
			LatLng llA=new LatLng(efLocationSemiA.getLatitude(), efLocationSemiA.getLongitude());
			LatLng llB=new LatLng(efLocationSemiB.getLatitude(), efLocationSemiB.getLongitude());
			if(LocationUtil.getDistance(llA, llB)<=LocationUtil.JUHEDISTANCE){
				EfLocationSemi newLocation= efLocationSemiA.clone();
				newLocation.setId(null);
				newLocation.setSourceId(null);
				List<EfLocationSemi> list = new ArrayList<EfLocationSemi>();
				list.add(efLocationSemiA);
				list.add(efLocationSemiB);
				newLocation.setSource(this.getJuheSource(list));
				double lat=0,lon=0;
				double size=0;
				for(EfLocationSemi efLocationSemi:list){
					double weight = 1;
					if(efLocationSemi.getSource().intValue() == EjlLocation.Source.GPS.getCode()){
						weight = Double.valueOf(gpsWeight);
					}else if(efLocationSemi.getSource().intValue() == EjlLocation.Source.WIFI.getCode()){
						weight = Double.valueOf(wifiWeight);
					}else if(efLocationSemi.getSource().intValue() == EjlLocation.Source.TOWER.getCode()){
						weight = Double.valueOf(cellWeight);
					}
					
					lat+=efLocationSemi.getLatitude()*weight;
					lon+=efLocationSemi.getLongitude()*weight;
					size += 1*weight;
				}
				newLocation.setLongitude(lon/size);
				newLocation.setLatitude(lat/size);
				newLocation.setTime(efLocationSemiA.getTime());
				newLocation.setTimeBegin(efLocationSemiA.getTime());
				Date timeEndA = efLocationSemiA.getTimeEnd();
				Date timeEndB = efLocationSemiB.getTimeEnd();
				if(timeEndA!=null && timeEndB!=null){
					if(timeEndA.after(timeEndB)){
						newLocation.setTimeEnd(efLocationSemiA.getTimeEnd());
					}else{
						newLocation.setTimeEnd(efLocationSemiB.getTimeEnd());
					}
				}else{
					newLocation.setTimeEnd(efLocationSemiB.getTimeEnd()==null?efLocationSemiB.getTime():efLocationSemiB.getTimeEnd());
				}
				newLocation.setRemark(getRemark("临近聚合生成: " +efLocationSemiA.getId()+" " + efLocationSemiB.getId()));
				newLocation.setFlag(EfLocationSemi.Flag.INITJUHE.getValue());
				return newLocation;
			}
		}catch(Exception e){
			log.error("getAggregationLocation clone error",e);
			throw new BizException(e);
		}
		return null;
	}
	
	@Override
	public void aggregation(Context ctx,EfLocationSemi locationSemi,boolean isSecd) throws BizException{
		if(null!=locationSemi){
			final int count=10;
			List<EfLocationSemi> nearList =getValidPreNextListByIdAndCount(locationSemi,count);
			//获取该点待聚合的点集合（前后10个点） 
			if(null==nearList){
				nearList=new ArrayList<EfLocationSemi>();
			}
			nearList.add(locationSemi);
			//找到要进行真正聚合的点
			List<EfLocationSemi> aggList =getAggregationList(ctx, locationSemi, nearList, LocationUtil.JUHEDISTANCE); 
			aggList=filterAgg(aggList,isSecd);
			if(null!=aggList && aggList.size()>1){
				//聚合处理
				EfLocationSemi aggLocation=getAggregationLocation(ctx,aggList,isSecd);
				aggLocation.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, aggLocation);
				//删除被聚合的点
				disposeAggregationList(ctx,aggLocation,aggList,isSecd);
			}else{
				if(!isSecd){	//首次聚合则更新状态
					locationSemi.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
					save(ctx, locationSemi);
				}
			}
		}
	}
	private List<Integer> getFlags(boolean isSecd){
		return isSecd?getFlagsRetainedAndFinished():getFlagsFiltered();
	}
	protected List<EfLocationSemi> filterAgg(List<EfLocationSemi> aggList,boolean isSecd){
		if(null!=aggList){
			sort(aggList,true);
			List<EfLocationSemi> newAggList=new ArrayList<EfLocationSemi>();
			List<Integer> flags=getFlags(isSecd);
			for(EfLocationSemi lc:aggList){
				if(flags.contains(lc.getFlag())){
					newAggList.add(lc);
				}
			}
			return newAggList;
		}
		return null;
	}
	protected void disposeAggregationList(Context ctx,EfLocationSemi newLocation,List<EfLocationSemi> aggList,boolean isSecd) throws BizException{
		if(null!=aggList && aggList.size()>1){	// 包含了自己  大于1才有必要处理
			for(EfLocationSemi locationSemi:aggList){
				locationSemi.setRemark(getRemark(locationSemi.getRemark()+"聚合删除 newId="+newLocation.getId()));
				replace(ctx, locationSemi, newLocation, aggList,isSecd);
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
	public void replace(Context ctx,EfLocationSemi locationSemi,EfLocationSemi locationSemiNew,List<EfLocationSemi> aggList,boolean isSecd)
			throws BizException {
		try{
			EjlLocation location=ejlComLocationService.getBySourceId(locationSemi.getId());
			if(null!=location){
				ejlComLocationService.remove(ctx, location.getId());
			}
			EfLocationSemi locationSemiPre=getPrevious(locationSemi,isSecd);	//getPreviousById
			if(null!=locationSemiPre && !isContain(aggList,locationSemiPre)){
				//去掉前后关系设置 locationSemiPre.setNextId(locationSemiNew.getId());
				save(ctx, locationSemiPre);
			}
			EfLocationSemi locationSemiNext=getNext(locationSemi,isSecd);	//getNextById
			if(null!=locationSemiNext && !isContain(aggList,locationSemiNext)){
				//去掉前后关系设置 locationSemiNext.setPreviousId(locationSemiNew.getId());
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
	
	
	*//** 新点替换旧点
	 * @param ctx
	 * @param locationSemi
	 * @param locationSemiNew
	 * @param aggList
	 * @throws BizException
	 *//*
	public void replace(Context ctx,EfLocationSemi locationSemiPre,EfLocationSemi locationSemiNext,EfLocationSemi locationSemiNew)
			throws BizException {
		try{
			if(locationSemiPre.getId() == null){
				List<EfLocationSemi> locationSemiPres = tskLocationSemiDao.getSameList(locationSemiPre.getUserId(), locationSemiPre.getDeviceId(), locationSemiPre.getSource(), locationSemiPre.getTime(),locationSemiPre.getRemark());
				if(locationSemiPres != null && locationSemiPres.size()>0){
					locationSemiPre = locationSemiPres.get(0);
				}
				
			}
			
			if(locationSemiNext.getId() == null){
				List<EfLocationSemi> locationSemiNexts = tskLocationSemiDao.getSameList(locationSemiNext.getUserId(), locationSemiNext.getDeviceId(), locationSemiNext.getSource(), locationSemiNext.getTime(),locationSemiNext.getRemark());
				if(locationSemiNexts != null && locationSemiNexts.size()>0){
					locationSemiNext = locationSemiNexts.get(0);
				}
				
			}
			
			EjlLocation location=ejlComLocationService.getBySourceId(locationSemiPre.getId());
			if(null!=location){
				ejlComLocationService.remove(ctx, location.getId());
			}
			location=ejlComLocationService.getBySourceId(locationSemiNext.getId());
			if(null!=location){
				ejlComLocationService.remove(ctx, location.getId());
			}
			EfLocationSemi locationSemiAfter=this.getNextAfterFiltered(locationSemiNext);
			if(null!=locationSemiAfter){
				//去掉前后关系设置 locationSemiNext.setPreviousId(locationSemiNew.getId());
				locationSemiAfter.setDistance(calcDistance(locationSemiNew,locationSemiAfter));
				locationSemiAfter.setFlag(EfLocationSemi.Flag.JUHEFINISH.getValue());
				save(ctx, locationSemiAfter);
			}			
			locationSemiPre.setFlag(EfLocationSemi.Flag.DELETE.getValue());
			locationSemiPre.setRemark(this.getRemark(locationSemiPre.getRemark()+" 临近点聚合删除:"+locationSemiNext.getId()));
			locationSemiNext.setFlag(EfLocationSemi.Flag.DELETE.getValue());
			locationSemiNext.setRemark(this.getRemark(locationSemiNext.getRemark()+ " 临近点聚合删除"+locationSemiPre.getId()));
			locationSemiNew.setRemark("临近点聚合生成"+locationSemiNext.getId() +" "+ locationSemiPre.getId());
			save(ctx, locationSemiPre);
			save(ctx, locationSemiNext);
			save(ctx, locationSemiNew);
		}catch(Exception e){
			log.error("dao error.",e);
			throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
		}
	}
	
	protected EfLocationSemi getPrevious(EfLocationSemi locationSemi,boolean isSecd) throws BizException {
		List<Integer> flags=getFlags(isSecd);
		return tskLocationSemiDao.getPrevious(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
	}
	protected EfLocationSemi getNext(EfLocationSemi locationSemi,boolean isSecd) throws BizException {
		List<Integer> flags=getFlags(isSecd);
		return tskLocationSemiDao.getNext(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void filterLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		*//**
		 * 1.MovableWifi
		 * 2.GPS>WIFI>BTS
		 * 
		 * 待改进：
		 * locationSemiList 处理出 时间段衔接的点（判断中间是否有已经处理的点）
		 *//*

		List<EfLocationSemi> unHandlerList = getUnhandledListByDeviceIdAndTimeFromTo(userId,deviceId, timeFrom, timeTo);
		for(EfLocationSemi efLocationSemi:unHandlerList){
			efLocationSemi.setFlag(EfLocationSemi.Flag.INITJUHE.getValue());
			this.save(ctx, efLocationSemi);
		}
		//*** 获取时间段内的所有临界点
		List<EfLocationSemi> locationSemiList=getNearListByDeviceIdAndTimeFromTo(userId,deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			List<EfLocationSemi> deleteList = new ArrayList<EfLocationSemi>();//被删除的临界点
			for(EfLocationSemi locationSemi : locationSemiList){
				if(!deleteList.contains(locationSemi)){//如果此点已被删除 就不需要进行过滤操作
					//*** 临界点 前后2分钟的点
					List<EfLocationSemi> nearList=getListNearByDeviceIdAndTimeAndOffset(locationSemi.getUserId(),locationSemi.getDeviceId(), locationSemi.getTime(),LocationUtil.OFFSET_NEAR_MINS);
					//*** 去除移动wifi   ： nearList 被删除的移动wifi并且还是临界点
					this.filterMovableWifi(ctx, nearList);//过滤移动wift
					//
					deleteList.addAll(nearList);//被删除的零界点
					if(!deleteList.contains(locationSemi)){//如果当前点已经被删除了 就不需要进行优先级过滤了
						this.fiterNearPriority(ctx, 1, locationSemi, 0);//优先级过滤
					}
				}
			}
		}
	}
	
	
	
	
	
	private int fiterForTwoPoint(EfLocationSemi efLocationSemiPre,EfLocationSemi efLocationSemi){
		if(efLocationSemiPre.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
			if(efLocationSemi.getSource().intValue() != EjlLocation.Source.GPS.getCode()){
				return 2;//删除后面一个点
			}else{
				return 0;//都不要删除
			}
		}else if(efLocationSemiPre.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
			if(efLocationSemi.getSource().intValue() == EjlLocation.Source.WIFI.getCode()){
				return 0;//都不要删除
			}else if(efLocationSemi.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
				return 1;//删除前一个点
			}else{
				return 2;//删除后一个点
			}
		}else{
			if(efLocationSemi.getSource().intValue() == EjlLocation.Source.WIFI.getCode() || efLocationSemi.getSource().intValue()==EjlLocation.Source.GPS.getCode()){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	
	
	
	private void fiterNearPriority(Context ctx,int direction,EfLocationSemi efLocationSemi,int count) throws BizException{
		count ++;
		if(count>10){
			return;
		}
		int mins=LocationUtil.POINT_TIME_SPLIT;
		//向前走
		if(direction==1){
			EfLocationSemi efLocationSemiPre = this.getPreviousAfterFiltered(efLocationSemi);
			
			if(efLocationSemiPre==null || DateUtils.calcMinutesBetween(efLocationSemiPre.getTimeEnd()==null?efLocationSemiPre.getTime():efLocationSemiPre.getTimeEnd(), efLocationSemi.getTime()) >mins){
				if(efLocationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
					efLocationSemi.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
					this.save(ctx, efLocationSemi);
				}
				return;
			}else{
				int tag = fiterForTwoPoint(efLocationSemiPre,efLocationSemi);
				if(tag == 0){
					if(efLocationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
						efLocationSemi.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
						this.save(ctx, efLocationSemi);
					}
					return;
				}else if(tag ==1){
					efLocationSemiPre.setRemark(getRemark(efLocationSemiPre.getRemark()+"优先级过滤"));
					this.deleteLocationSemi(ctx, efLocationSemiPre);
					this.fiterNearPriority(ctx, 1, efLocationSemi,count);
				}else{
					efLocationSemi.setRemark(getRemark(efLocationSemi.getRemark()+"优先级过滤"));
					this.deleteLocationSemi(ctx, efLocationSemi);
					this.fiterNearPriority(ctx, 2, efLocationSemiPre,count);
				}
			}
		}else{
			EfLocationSemi efLocationSemiNext = this.getNextAfterFiltered(efLocationSemi);
			if(efLocationSemiNext == null || DateUtils.calcMinutesBetween(efLocationSemi.getTimeEnd()==null?efLocationSemi.getTime():efLocationSemi.getTimeEnd(), efLocationSemiNext.getTime()) >mins||efLocationSemiNext.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
				if(efLocationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
					efLocationSemi.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
					this.save(ctx, efLocationSemi);
				}
				return;
			}else{
				int tag = fiterForTwoPoint(efLocationSemi,efLocationSemiNext);
				if(tag == 0){
					if(efLocationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
						efLocationSemi.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
						this.save(ctx, efLocationSemi);
					}
					return;
				}else if(tag ==1){
					efLocationSemi.setRemark(getRemark(efLocationSemi.getRemark()+"优先级过滤"));
					this.deleteLocationSemi(ctx, efLocationSemi);
					this.fiterNearPriority(ctx, 1, efLocationSemiNext,count);
				}else{
					efLocationSemiNext.setRemark(getRemark(efLocationSemiNext.getRemark()+"优先级过滤"));
					this.deleteLocationSemi(ctx, efLocationSemiNext);
					this.fiterNearPriority(ctx, 2, efLocationSemi,count);
				}
			}
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
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void distinctLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		*//**
		 * 没连接的才有可能需要去重
		 *//*
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(userId,deviceId, timeFrom, timeTo);
		if(null!=locationSemiList){
			for(EfLocationSemi locationSemi:locationSemiList){
				List<EfLocationSemi> dupList=getSameList(locationSemi);
				if(null!=dupList && dupList.size()>0){
					locationSemi.setRemark(getRemark(locationSemi.getRemark()+"同一时间和类型去重"));
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
	protected void linkLocationSemi(Context ctx,Long userId,Long deviceId,Date timeFrom,Date timeTo) throws BizException{
		List<EfLocationSemi> locationSemiList=getFilteredListByDeviceIdAndTimeFromTo(userId,deviceId, timeFrom, timeTo);
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
			List<EfLocationSemi> otherList = new ArrayList<EfLocationSemi>();
			otherList.addAll(locationSemiList);
			for(EfLocationSemi locationSemi:locationSemiList){
				locationSemi.setFlag(EfLocationSemi.Flag.INITJUHE.getValue());
				if(aggList.contains(locationSemi)) continue;
				otherList.removeAll(aggList);
				aggList=getAggregationList(ctx, locationSemi, otherList, LocationUtil.JUHEDISTANCE);
				if(null!=aggList && aggList.size()>1){
					EfLocationSemi aggLocation=getAggregationLocation(ctx,aggList,false);
					if(null!=aggLocation){
						aggLocation.setFlag(EfLocationSemi.Flag.INITJUHE.getValue());
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
				if(rmList.contains(locationSemi)){
					continue;
				}
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
	protected EfLocationSemi getAggregationLocation(Context ctx,List<EfLocationSemi> locationSemiList,boolean isSecd) throws BizException{
		if(null!=locationSemiList){
			if(locationSemiList.size()==1){
				return locationSemiList.get(0);
			}else{	//包含了自己  大于1才有必要处理
				double lat=0,lon=0,avLat=0,avLon=0;
				double size=0;
				Date timeBegin=null,timeEnd=null;
				String remark="聚合生成("+isSecd+")：";
				for(EfLocationSemi locationSemi:locationSemiList){
					double weight = 1;
					if(locationSemi.getSource().intValue() == EjlLocation.Source.GPS.getCode()){
						weight = Double.valueOf(gpsWeight);
					}else if(locationSemi.getSource().intValue() == EjlLocation.Source.WIFI.getCode()){
						weight = Double.valueOf(wifiWeight);
					}else if(locationSemi.getSource().intValue() == EjlLocation.Source.TOWER.getCode()){
						weight = Double.valueOf(cellWeight);
					}
					
					lat+=locationSemi.getLatitude()*weight;
					lon+=locationSemi.getLongitude()*weight;
					size += 1*weight;
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
					if(isSecd){
						remark+=(null==locationSemi.getId())?"-1":locationSemi.getId()+" ";
					}else{
						remark+=(null==locationSemi.getSourceId())?"-1":locationSemi.getSourceId()+" ";
					}
				}
				avLat=lat/size;
				avLon=lon/size;
				//avTime=DateUtils.average(timeBegin, timeEnd);		//不取平均时间 取最早点的时间
				try {
					EfLocationSemi newLocation= locationSemiList.get(0).clone();
					newLocation.setId(null);
					newLocation.setSourceId(null);
					newLocation.setSource(this.getJuheSource(locationSemiList));
					newLocation.setLongitude(avLon);
					newLocation.setLatitude(avLat);
					newLocation.setTime(timeBegin);
					newLocation.setTimeBegin(timeBegin);
					newLocation.setTimeEnd(timeEnd);
					//去掉前后关系设置 newLocation.setPreviousId(previousId);
					//去掉前后关系设置 newLocation.setNextId(nextId);
					newLocation.setRemark(getRemark(newLocation.getRemark()+remark));
					return newLocation;
				} catch (Exception e) {
					log.error("getAggregationLocation clone error",e);
					throw new BizException(e);
				}
			}
		}
		return null;
	}
	
	private int getJuheSource(List<EfLocationSemi> list){
		for(EfLocationSemi efLocationSemi : list){
			if(efLocationSemi.getSource().intValue() == EjlLocation.Source.GPS.getCode()){
				return EjlLocation.Source.GPS.getCode();
			}
			if(efLocationSemi.getSource().intValue() == EjlLocation.Source.WIFI.getCode()){
				return EjlLocation.Source.WIFI.getCode();
			}
			if(efLocationSemi.getSource().intValue() == EjlLocation.Source.TOWER.getCode()){
				return EjlLocation.Source.TOWER.getCode();
			}
		}
		return EjlLocation.Source.TOWER.getCode();
	}
	
	
	private String getRemark(String remark){
		if(null!=remark && remark.length()>200){
			return remark.substring(remark.length()-200);
		}
		return remark;
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
	
	
	
	protected void filterNearPriority(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		if(null!=locationSemiList){
			sort(locationSemiList, true);
			for(EfLocationSemi efLocationSemi : locationSemiList){
				if(this.get(efLocationSemi.getId()).getFlag().intValue() != EfLocationSemi.Flag.DELETE.getValue()){
					this.fiterNearPriority(ctx, 1, efLocationSemi, 0);
				}
			}
		}
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
				locationSemi.setRemark(getRemark(locationSemi.getRemark()+"移动wifi，基站平均距离："+avarDistance));
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
				int mins=0;
				if(locationSemi.getTime().before(lc.getTime()))
				{
					if(locationSemi.getTimeEnd() != null && lc.getTimeBegin()!=null){
						mins=(int)DateUtils.calcMinutesBetween(locationSemi.getTimeEnd(),lc.getTimeBegin());
					}else{
						mins=(int)DateUtils.calcMinutesBetween(locationSemi.getTime(),lc.getTime());
					}
				}else{
					if(lc.getTimeEnd() != null && locationSemi.getTimeBegin()!=null){
						mins=(int)DateUtils.calcMinutesBetween(lc.getTimeEnd(),locationSemi.getTimeBegin());
					}else{
						mins=(int)DateUtils.calcMinutesBetween(locationSemi.getTime(),lc.getTime());
					}
				}
				
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
	protected List<EfLocationSemi> filterMovableWifi(Context ctx,List<EfLocationSemi> locationSemiList) throws BizException{
		List<EfLocationSemi> newNearList = new ArrayList<EfLocationSemi>();//新的临近点
		List<EfLocationSemi> rmList=new ArrayList<EfLocationSemi>();//被删除的临近点
		if(null!=locationSemiList){
			List<EfLocationSemi> btsList=new ArrayList<EfLocationSemi>();
			for(EfLocationSemi locationSemi:locationSemiList){
				if(locationSemi.getSource().intValue()==EjlLocation.Source.TOWER.getCode() || locationSemi.getSource().intValue()==EjlLocation.Source.GPS.getCode() ){
					btsList.add(locationSemi);
				}
			}			
			for(EfLocationSemi locationSemi:locationSemiList){ 
				if(locationSemi.getSource().intValue()==EjlLocation.Source.WIFI.getCode()){
					//btsNearList 是基站 和  gps点  并且两分钟之内有wifi点  
					List<EfLocationSemi> btsNearList=getNearList(ctx,locationSemi,btsList,LocationUtil.OFFSET_NEAR_MINS,false);
					//如果当前点locationSemi 距离   btsNearList 所有点的平均距离 大于约定距离  则认为当前点 locationSemi 为移动wifi
					if(isMovableWifi(ctx,locationSemi,btsNearList)){//是否是移动wifi： 距离每个点的平均距离>约定距离
						locationSemi.setRemark(getRemark(locationSemi.getRemark()+"移动wifi"));
						EfLocationSemi newNear = deleteLocationSemi(ctx,locationSemi);//删除之后产生新的临近点  删除当前点 然后把前后两点修改为filtered点
						if(newNear != null&&!newNearList.contains(newNear)){
							newNearList.add(newNear);
						}
						if(locationSemi.getFlag().intValue() == EfLocationSemi.Flag.NEIGHBORING.getValue()){
							rmList.add(locationSemi);//被删除的临近点
						}
					}
				}
			}
		}
		locationSemiList.clear();
		locationSemiList.addAll(rmList);//如果是临界点  并且被删除了 就记录下来
		return newNearList;
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
			//去掉前后关系设置 locationSemi.setPreviousId(null);
			locationSemi.setFlag(EfLocationSemi.Flag.LINKED.getValue()); 
			save(ctx, locationSemi);
		}
	}
	private void insertAfter(Context ctx, EfLocationSemi locationSemi,
			EfLocationSemi locationSemiPre, EfLocationSemi locationSemiNext)
			throws BizException {
		EfLocationSemi locationSemiPreNext=getNextAfterFiltered(locationSemiPre);	//getNextById
		//队中
		if(null!=locationSemiPreNext){
			insertBefore(ctx, locationSemi, locationSemiPreNext);
		}
		//else 队尾
		//去掉前后关系设置 locationSemiPre.setNextId(locationSemi.getId());
		save(ctx, locationSemiPre);
		//去掉前后关系设置 locationSemi.setPreviousId(locationSemiPre.getId());
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
		//去掉前后关系设置 locationSemiNext.setPreviousId(locationSemi.getId());
		locationSemiNext.setFlag(EfLocationSemi.Flag.LINKED.getValue());
		save(ctx, locationSemiNext);
		//去掉前后关系设置 locationSemi.setNextId(locationSemiNext.getId());
	}
	
	protected boolean isAfterFiltered(int flag){
		return flag!=EfLocationSemi.Flag.UNHANDLE.getValue() && flag!=EfLocationSemi.Flag.FILTERED.getValue();
	}
	*//**
	 * 获取过滤后标识值
	 * @return
	 *//*
	protected List<Integer> getFlagsAfterFiltered(){
		List<Integer> flags=new ArrayList<Integer>();
		//List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(Integer f:getValidFlags()){
			if(f!=EfLocationSemi.Flag.UNHANDLE.getValue() && f!=EfLocationSemi.Flag.FILTERED.getValue()) {
				flags.add(f);
			}
		}
		return flags;
	}
	
	protected List<Integer> getFlagsAfterAggred(){
		List<Integer> flags=new ArrayList<Integer>();
		//List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(Integer f:getFlagsAfterFiltered()){
			if(f!=EfLocationSemi.Flag.JUHEFINISH.getValue()&&f!=EfLocationSemi.Flag.INITJUHE.getValue()&&f!=EfLocationSemi.Flag.NEIGHBORING.getValue()) {
				flags.add(f);
			}
		}
		return flags;
	}
	protected List<Integer> getFlagsAgged(){
		List<Integer> flags=new ArrayList<Integer>();
		flags.add(EfLocationSemi.Flag.JUHEFINISH.getValue());
		return flags;
	}
	protected List<Integer> getFlagsFiltered(){
		List<Integer> flags=new ArrayList<Integer>();
		flags.add(EfLocationSemi.Flag.FILTERED.getValue());
		return flags;
	}
	protected List<Integer> getFlagsRetainedAndFinished(){
		List<Integer> flags=new ArrayList<Integer>();
		flags.add(EfLocationSemi.Flag.RETAIN.getValue());
		flags.add(EfLocationSemi.Flag.FINISH.getValue());
		return flags;
	}
	
	
	@Override
	public EfLocationSemi getPreviousAfterFiltered(EfLocationSemi locationSemi)
			throws BizException {
		if(locationSemi==null){
			return null;
		}
		List<Integer> flags=getValidFlags();
		if(locationSemi.getId()!= null){
			return tskLocationSemiDao.getPrevious(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
		}else{
			return tskLocationSemiDao.getPreviousByTime(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getTime(), flags);
		}
	}
	public EfLocationSemi getPreviousAfterAggred(EfLocationSemi locationSemi)
			throws BizException {
		if(locationSemi==null){
			return null;
		}
		List<Integer> flags=getFlagsAfterAggred();
		//*** locationSemi.getId() 为空的原因是 某些点是聚合的 还未入库 这个方法多处调用
		if(locationSemi.getId()!= null){
			return tskLocationSemiDao.getPrevious(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
		}else{
			return tskLocationSemiDao.getPreviousByTime(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getTime(), flags);
		}
	}
	public EfLocationSemi getNextAfterFiltered(EfLocationSemi locationSemi)
			throws BizException {
		if(locationSemi==null){
			return null;
		}
		List<Integer> flags=getValidFlags();
		if(locationSemi.getId()!= null){
			return tskLocationSemiDao.getNext(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
		}else{
			return tskLocationSemiDao.getNextByTime(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getTime(), flags);
		}
	}
	public EfLocationSemi getNextAfterAggred(EfLocationSemi locationSemi)
			throws BizException {
		if(locationSemi==null){
			return null;
		}
		List<Integer> flags=getFlagsAfterAggred();
		if(locationSemi.getId()!= null){
			return tskLocationSemiDao.getNext(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getId(), flags);
		}else{
			return tskLocationSemiDao.getNextByTime(locationSemi.getUserId(),locationSemi.getDeviceId(),locationSemi.getTime(), flags);
		}
	}
	
	*//**
	 * 功能：  删除当前点，并把前后点设置为filter状态
	 * @param ctx
	 * @param locationSemi
	 * @return
	 * @throws BizException
	 *//*
	protected EfLocationSemi deleteLocationSemi(Context ctx,EfLocationSemi locationSemi) throws BizException{
		int flagBefore=locationSemi.getFlag().intValue();
		EfLocationSemi ef = null;
		if(getValidFlags().contains(flagBefore)){
			//FILTERED之后的标识 才进行处理
			if(isAfterFiltered(flagBefore)){				
				EfLocationSemi locationSemiPre=getPreviousAfterFiltered(locationSemi);	//getPreviousById
				EfLocationSemi locationSemiNext=getNextAfterFiltered(locationSemi);	//getNextById
				if(null!=locationSemiPre){
					EjlLocation location=ejlComLocationService.getBySourceId(locationSemiPre.getId());
					if(null!=location){
						ejlComLocationService.remove(ctx, location.getId());
					}
					locationSemiPre.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
					//去掉前后关系设置 locationSemiPre.setNextId(null);
					save(ctx, locationSemiPre);
				}
				if(null!=locationSemiNext){
					EjlLocation location=ejlComLocationService.getBySourceId(locationSemiNext.getId());
					if(null!=location){
						ejlComLocationService.remove(ctx, location.getId());
					}
					if(locationSemiNext.getFlag().intValue() != EfLocationSemi.Flag.NEIGHBORING.getValue()){
						locationSemiNext.setFlag(EfLocationSemi.Flag.FILTERED.getValue());
						ef = locationSemiNext;
					}
					//去掉前后关系设置 locationSemiNext.setPreviousId(null);
					save(ctx, locationSemiNext);
				}
			}
			locationSemi.setFlag(EfLocationSemi.Flag.DELETE.getValue());
			save(ctx, locationSemi);
		}
		return ef;
	}
	
	
	public static void main(String[] args) throws Exception{
		List<EfLocationSemi> locationSemiList = new ArrayList<EfLocationSemi>();
		EfLocationSemi e1 = new EfLocationSemi();
		e1.setLatitude(22.5487687);
		e1.setLongitude(113.9442418);
		e1.setSource(1);
		e1.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		
		EfLocationSemi e6 = new EfLocationSemi();
		e6.setLatitude(22.5487687);
		e6.setLongitude(113.9442418);
		e6.setSource(1);
		e6.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		EfLocationSemi e8 = new EfLocationSemi();
		e8.setLatitude(22.5487687);
		e8.setLongitude(113.9442418);
		e8.setSource(1);
		e8.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		
		EfLocationSemi e7 = new EfLocationSemi();
		e7.setLatitude(22.5487687);
		e7.setLongitude(113.9442418);
		e7.setSource(1);
		e7.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		
		EfLocationSemi e9 = new EfLocationSemi();
		e9.setLatitude(22.5487687);
		e9.setLongitude(113.9442418);
		e9.setSource(1);
		e9.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		
		
		EfLocationSemi e2 = new EfLocationSemi();
		e2.setLatitude(22.5488687);
		e2.setLongitude(113.9442418);
		e2.setSource(3);
		e2.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		
		
		EfLocationSemi e3 = new EfLocationSemi();
		e3.setLatitude(22.5490687);
		e3.setLongitude(113.9442418);
		e3.setSource(2);
		e3.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		locationSemiList.add(e1);
		locationSemiList.add(e2);
		locationSemiList.add(e3);
		
		EfLocationSemi e4 = new EfLocationSemi();
		e4.setLatitude(22.5790687);
		e4.setLongitude(113.9442418);
		e4.setSource(2);
		e4.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		locationSemiList.add(e1);
		locationSemiList.add(e2);
		locationSemiList.add(e3);
		
		EfLocationSemi e5 = new EfLocationSemi();
		e5.setLatitude(22.5890687);
		e5.setLongitude(113.9442418);
		e5.setSource(2);
		e5.setTime(DateUtils.addMinutes(DateUtils.currentDate(),-1));
		locationSemiList.add(e1);
		locationSemiList.add(e2);
		locationSemiList.add(e3);
		locationSemiList.add(e4);
		locationSemiList.add(e5);
		locationSemiList.add(e6);
		locationSemiList.add(e7);
		locationSemiList.add(e8);
		locationSemiList.add(e9);
		TskLocationSemiFiveSpotAlgorithmServiceImpl ad = new TskLocationSemiFiveSpotAlgorithmServiceImpl();
		EfLocationSemi efLocationSemi = ad.getAggregationLocation(null, locationSemiList, false);
		System.out.println(efLocationSemi.getLongitude()+","+efLocationSemi.getLatitude());
		
		EfLocationSemi efLocationSemi1 =ad.aggregationTwoPoint(e1,e2);
		System.out.println(efLocationSemi1.getLongitude()+","+efLocationSemi1.getLatitude());
		
	}
	//****************************************五点算法开始***************************************************************
	//*******************************************************************************************************
	//*******************************************************************************************************
	
	class EfLocationSemiThree {
//      P-1 ____P0____P1____P2____P3
	EfLocationSemi pf1;//P-1
	EfLocationSemi p0;//P0
	EfLocationSemi p1;//P1
	
	EfLocationAssist af1;//P-1
	EfLocationAssist a0;//P0
	
	EfLocationAssist a1;//P1
	public static final int NUMBER_SPOT_PF1=-1;
	public static final int NUMBER_SPOT_P0=0;
	public static final int NUMBER_SPOT_P1=1;
	
	public EfLocationSemiThree(EfLocationSemi p) throws BizException {
		this.p1 = p;
		p0=getPreviousAfterAggred(p1);
		pf1=getPreviousAfterAggred(p0);
		
		af1=getEfLocationAssistBy(pf1);
		a0=getEfLocationAssistBy(p0);
		a1=getEfLocationAssistBy(p1);
	}
	
	public EfLocationSemiThree pushEfLocationSemi(EfLocationSemi p) throws BizException {
		
		this.pf1=p0;
		this.p0=p1;
		this.p1=p;
		
		this.af1=a0;
		this.a0=a1;
		this.a1=getEfLocationAssistBy(p);
		
		return this;
	}
	
	
	public EfLocationSemi getPf1() {
		return pf1;
	}


	public void setPf1(EfLocationSemi pf1) {
		this.pf1 = pf1;
	}


	public EfLocationSemi getP0() {
		return p0;
	}


	public void setP0(EfLocationSemi p0) {
		this.p0 = p0;
	}


	public EfLocationSemi getP1() {
		return p1;
	}


	public void setP1(EfLocationSemi p1) {
		this.p1 = p1;
	}



	public EfLocationAssist getAf1() {
		return af1;
	}


	public void setAf1(EfLocationAssist af1) {
		this.af1 = af1;
	}


	public EfLocationAssist getA0() {
		return a0;
	}


	public void setA0(EfLocationAssist a0) {
		this.a0 = a0;
	}


	public EfLocationAssist getA1() {
		return a1;
	}


	public void setA1(EfLocationAssist a1) {
		this.a1 = a1;
	}

	}
	
	class EfLocationSemiFive {
//             P-1 ____P0____P1____P2____P3
		EfLocationSemi pf1;//P-1
		EfLocationSemi p0;//P0
		EfLocationSemi p1;//P1
		EfLocationSemi p2;//P2
		EfLocationSemi p3;//P3
		
		EfLocationAssist af1;//P-1
		EfLocationAssist a0;//P0
		EfLocationAssist a1;//P1
		EfLocationAssist a2;//P2
		EfLocationAssist a3;//P3
		
		public static final int NUMBER_SPOT_PF1=-1;
		public static final int NUMBER_SPOT_P0=0;
		public static final int NUMBER_SPOT_P1=1;
		public static final int NUMBER_SPOT_P2=2;
		public static final int NUMBER_SPOT_P3=3;
		
		public EfLocationSemiFive(EfLocationSemi p) throws BizException {
			p3 =p;
			a3=getEfLocationAssistBy(p3);

			p2 =getPreviousAfterAggred(p3);
			if(isDistanceOrTimeTooLong(p2, p3)){
				p2=null;
				return;
			}
			a2=getEfLocationAssistBy(p2);
			
			
			p1 =getPreviousAfterAggred(p2);
			if(isDistanceOrTimeTooLong(p1, p2)){
				p1=null;
				return;
			}
			a1=getEfLocationAssistBy(p1);

			p0 =getPreviousAfterAggred(p1);
			if(isDistanceOrTimeTooLong(p0, p1)){
				p0=null;
				return;
			}
			a0=getEfLocationAssistBy(p0);

			pf1=getPreviousAfterAggred(p0);
			if(isDistanceOrTimeTooLong(pf1, p0)){
				pf1=null;
				return;
			}
			af1=getEfLocationAssistBy(pf1);
		}
		
		public boolean deleteP1AndRecombinationFiveSpot()  throws BizException {
			boolean flag = false;
			EfLocationSemi efLocationSemiTemp_P0 = getPreviousAfterAggred(pf1);
			EfLocationSemi efLocationSemiTemp_P1 = getPreviousAfterAggred(p0);
			if(efLocationSemiTemp_P0!=null && efLocationSemiTemp_P1!=null){
				p3=p2;
				p2=p0;
				p1=pf1;
				p0=efLocationSemiTemp_P0;
				pf1=efLocationSemiTemp_P1;
				
				a3=a2;
				a2=a0;
				a1=af1;
				a0=getEfLocationAssistBy(pf1);
				af1=getEfLocationAssistBy(p0);
				flag = true;
			}
			return flag;
		}
		
		
		public boolean pushEfLocationSemi() throws BizException {
			boolean flag = false;
			EfLocationSemi nextEfLocationSemi = getNextAfterFiltered(p3);
			if(nextEfLocationSemi != null){
				this.pf1=p0;
				this.p0=p1;
				this.p1=p2;
				this.p2=p3;
				this.p3=nextEfLocationSemi;
				
				this.af1=a0;
				this.a0=a1;
				this.a1=a2;
				this.a2=a3;
				this.a3=getEfLocationAssistBy(p3);
				flag = true;
			}
			return flag;
		}
		
		
		public void saveCalScoreForFive(){
			tskLocationSemiService.updateCalData(this.p3);
			tskLocationSemiService.updateCalData(this.p2);
		}
		
		
		public EfLocationSemi getPf1() {
			return pf1;
		}


		public void setPf1(EfLocationSemi pf1) {
			this.pf1 = pf1;
		}


		public EfLocationSemi getP0() {
			return p0;
		}


		public void setP0(EfLocationSemi p0) {
			this.p0 = p0;
		}


		public EfLocationSemi getP1() {
			return p1;
		}


		public void setP1(EfLocationSemi p1) {
			this.p1 = p1;
		}


		public EfLocationSemi getP2() {
			return p2;
		}


		public void setP2(EfLocationSemi p2) {
			this.p2 = p2;
		}


		public EfLocationSemi getP3() {
			return p3;
		}


		public void setP3(EfLocationSemi p3) {
			this.p3 = p3;
		}


		public EfLocationAssist getAf1() {
			return af1;
		}


		public void setAf1(EfLocationAssist af1) {
			this.af1 = af1;
		}


		public EfLocationAssist getA0() {
			return a0;
		}


		public void setA0(EfLocationAssist a0) {
			this.a0 = a0;
		}


		public EfLocationAssist getA1() {
			return a1;
		}


		public void setA1(EfLocationAssist a1) {
			this.a1 = a1;
		}


		public EfLocationAssist getA2() {
			return a2;
		}


		public void setA2(EfLocationAssist a2) {
			this.a2 = a2;
		}


		public EfLocationAssist getA3() {
			return a3;
		}


		public void setA3(EfLocationAssist a3) {
			this.a3 = a3;
		}


		public boolean isEnoughThreeSpot()throws BizException{
			boolean flag = false;
			if(p1!=null&&p2!=null&&p3!=null){
				flag = true;
			}
			return flag;
		}
		
		//***获取两个点之间的速度差********************************************************************************
		public double getSpeedSubP1_Pf1(){
			return calcSpeed(p1,pf1)-af1.getSpeed();
		}

		public double getSpeedSubP1_P0(){
			return a1.getSpeed()-a0.getSpeed();
		}
		public double getSpeedSubP20_P10(){
			return getSpeedSubThreeSpot(p2, p1, p0, a0);
		}
		public double getSpeedSubP31_P21(){
			return getSpeedSubThreeSpot(p3, p2, p1, a1);
		}
		public double getSpeedSubP2f1_P1f1(){
			return getSpeedSubThreeSpot(p2, p1, pf1, af1);
		}
		public double getSpeedSubP30_P20(){
			return getSpeedSubThreeSpot(p3, p2, p0, a0);
		}
		public double getSpeedSubP30_P10(){
			return getSpeedSubThreeSpot(p3, p1, p0, a0);
		}
		
		public double getSpeedSubP3f1_P1f1(){
			return getSpeedSubThreeSpot(p3, p1, pf1, af1);
		}
		
		//***获取P2 P1相对于P0的速度差
		public double getSpeedSubThreeSpot(EfLocationSemi p2,EfLocationSemi p1,EfLocationSemi p0,EfLocationAssist a0){
			double speedSubPB=calcSpeed(p0,p2)-a0.getSpeed();
			double speedSubPA=calcSpeed(p0,p1)-a0.getSpeed();
			
			return speedSubPB - speedSubPA;
		}
		
		
		//***获取两点之间的角度差********************************************************************************
		public int getSlopeDegreeSubThreeSpot(EfLocationSemi p2,EfLocationSemi p1,EfLocationSemi p0,EfLocationAssist a0){
			int slopeDegreePB=calcSlopeDegree(p0, p2)-a0.getSlopeDegree();
			int slopeDegreePA=calcSlopeDegree(p0, p1)-a0.getSlopeDegree();
			
			return slopeDegreePB - slopeDegreePA;
		}

		public int getSlopeDegreeSubP2_P1(){
			return a2.getSlopeDegree()-a1.getSlopeDegree();
		}
		public int getSlopeDegreeSubP2_P0(){
			return calcSlopeDegree(p2,p0)-a0.getSlopeDegree();
		}
		public int getSlopeDegreeSubP3_P2(){
			return a3.getSlopeDegree()-a2.getSlopeDegree();
		}
		public int getSlopeDegreeSubP3_P1(){
			return calcSlopeDegree(p3,p1)-a1.getSlopeDegree();
		}
		public int getSlopeDegreeSubP1_Pf1(){
			return calcSlopeDegree(p1,pf1)-af1.getSlopeDegree();
		}

		public int getSlopeDegreeSubP1_P0(){
			return a1.getSlopeDegree()-a0.getSlopeDegree();
		}
		
		public int getSlopeDegreeSubP31_P21(){
			return getSlopeDegreeSubThreeSpot(p3, p2, p1, a1);
		}
		public int getSlopeDegreeSubP20_P10(){
			return getSlopeDegreeSubThreeSpot(p2, p1, p0, a0);
		}
		
		public int getSlopeDegreeSubP2f1_P1f1(){
			return getSlopeDegreeSubThreeSpot(p2, p1, pf1, af1);
		}
		
		public int getSlopeDegreeSubP30_P20(){
			return getSlopeDegreeSubThreeSpot(p3, p2, p0, a0);
		}
		
		public int getSlopeDegreeSubP30_P10(){
			return getSlopeDegreeSubThreeSpot(p3, p1, p0, a0);
		}
		
		public int getSlopeDegreeSubP3f1_P1f1(){
			return getSlopeDegreeSubThreeSpot(p3, p1, pf1, af1);
		}
		//***获取两点之间的加速度********************************************************************************
		public double getAccelerationSubThreeSpot(EfLocationSemi p2,EfLocationSemi p1,EfLocationSemi p0,EfLocationAssist a2,EfLocationAssist a1,EfLocationAssist a0){
			double accelerationPB=calcAcceleration(p0, p2 ,a0 ,a2)-a0.getAcceleration();
			double accelerationPA=calcAcceleration(p0, p1 ,a0 ,a2)-a0.getAcceleration();
			
			return accelerationPB - accelerationPA;
		}
		
		public double getAccelerationSubP1_Pf1(){
			return calcSlopeDegree(p1,pf1)-af1.getSlopeDegree();
		}

		public double getAccelerationSubP1_P0(){
			return a1.getSlopeDegree()-a0.getSlopeDegree();
		}
		
		public double getAccelerationSubP20_P10(){
			return getAccelerationSubThreeSpot(p2, p1, p0, a2, a1, a0);
		}
		public double getAccelerationSubP31_P21(){
			return getAccelerationSubThreeSpot(p3, p2, p1, a3, a2, a1);
		}
		public double getAccelerationSubP2f1_P1f1(){
			return getAccelerationSubThreeSpot(p2, p1, pf1, a2, a1, af1);
		}
		public double getAccelerationSubP30_P20(){
			return getAccelerationSubThreeSpot(p3, p2, p0, a3, a2, a0);
		}
		public double getAccelerationSubP30_P10(){
			return getAccelerationSubThreeSpot(p3, p1, p0,  a2, a1, a0);
		}
		
		public double getAccelerationSubP3f1_P1f1(){
			return getAccelerationSubThreeSpot(p3, p1, pf1, a2, a1, af1);
		}
		
		//***计算三点之间的曲度：  前段 P-1 P0 P1：  中段 P0 P1 P2：  后段 P1 P2 P3 *****************************************
		public int getCurvatureSubThreeSpot(EfLocationAssist a0,EfLocationAssist a1,EfLocationAssist a2){
			int slopeDegreePA=a1.getSlopeDegree()-a0.getSlopeDegree();
			int slopeDegreePB=a2.getSlopeDegree()-a1.getSlopeDegree();
			
			return slopeDegreePB - slopeDegreePA;
		}
		
		//***  前段 P-1 P0 P1
		public int getCurvatureSubPf1_P0_P1(){
			return getCurvatureSubThreeSpot(af1,a0,a1);
		}
		
		//***  中段 P0 P1 P2
		public int getCurvatureSubP0_P1_P2(){
			return getCurvatureSubThreeSpot(a0,a1,a2);
		}
		
		//***  后段 P1 P2 P3 
		public int getCurvatureSubP1_P2_P3(){
			return getCurvatureSubThreeSpot(a1,a2,a3);
		}
		
		
		//***计算三点之间的平滑度**************************************************************************
		//SmoothDegree
		public int getSmoothDegreeSubThreeSpot(EfLocationAssist a0,EfLocationAssist a1){
			int slopeDegreePA=a1.getSlopeDegree()-a0.getSlopeDegree();
			
			return slopeDegreePA;
		}
		
		//***  前段 P-1 P0 P1
		public int getSmoothDegreeSubPf1_P0_P1(){
			return getSmoothDegreeSubThreeSpot(a0,a1);
		}
		
		//***  中段 P0 P1 P2
		public int getSmoothDegreeSubP0_P1_P2(){
			return getSmoothDegreeSubThreeSpot(a1,a2);
		}
		
		//***  后段 P1 P2 P3 
		public int getSmoothDegreeSubP1_P2_P3(){
			return getSmoothDegreeSubThreeSpot(a2,a3);
		}
		
		//**********获取当前点的等级可疑系数（越大证明可靠率越高）
		
		32：1
		31 0：0.8
		31 2：0.7
		31 4：0.6
		31 6：0.5
		31 8：0.4
		
		public double getRankSuspectRadio(EfLocationSemi efLocationSemi){
			double rankSuspectRadio = 0 ;
			double suspScore = efLocationSemi.getSuspScore();
			if(efLocationSemi.getFlag().intValue()==30 || efLocationSemi.getFlag().intValue()==32){
				rankSuspectRadio = 1 ;
			}else if(efLocationSemi.getFlag().intValue()==31){
				CalcScoreByRange calcScoreByRange =new CalcScoreByRange()
                .add(Double.MIN_VALUE, false, 0, true, 1)
                .add(0, false, 2, true, 0.8)
                .add(2, false, 4, true, 0.7)
                .add(4, false, 6, true, 0.6)
                .add(6, false, 8, true, 0.5)
                .add(8, false, Double.MAX_VALUE, false, 0.4);
				rankSuspectRadio=calcScoreByRange.calculate(suspScore);
			}
			return rankSuspectRadio;
		}
		//*************坐标源可疑系数（越小可疑率越低）
		public double getLocationSourceSuspectRadio(int source){
			double sourceSuspectRadio = 0 ;
			if(EjlLocation.Source.GPS.getCode()==source){
				sourceSuspectRadio = 0.2 ;
			}else if(EjlLocation.Source.WIFI.getCode()==source){
				sourceSuspectRadio = 0.8 ;
			}else if(EjlLocation.Source.TOWER.getCode()==source){
				sourceSuspectRadio = 1 ;
			}else{
				log.error("getLocationSourceSuspectRadio : 未定义  source = "+source+" ; ");
			}
			return sourceSuspectRadio;
		}
		//*************聚合系数（越小可疑率越低）：
		public double getAggregationSuspectRadio(int aggregationSpotNumber){
			double aggregationSuspectRadio = 0 ;
			if(aggregationSpotNumber==1 || aggregationSpotNumber==2){
				aggregationSuspectRadio =1;
			}else if(aggregationSpotNumber==3 || aggregationSpotNumber==4){
				aggregationSuspectRadio =0.9;
			}else if(aggregationSpotNumber==5){
				aggregationSuspectRadio =0.5;
			}else if(aggregationSpotNumber==6 || aggregationSpotNumber==7){	
				aggregationSuspectRadio =0.2;
			}else{
				aggregationSuspectRadio =0;
			}
			return aggregationSuspectRadio;
		}
		
		public double getSuspectRadio(EfLocationSemi efLocationSemi){
			return  getRankSuspectRadio(efLocationSemi)*getLocationSourceSuspectRadio(efLocationSemi.getSource())*getAggregationSuspectRadio(efLocationSemi.getAggrCount());
		}
		
		public EfLocationSemi getEfLocationSemiBy(int number){
			EfLocationSemi efLocationSemi = null;
			switch(number){
			case -1:
				efLocationSemi = pf1;
				break;
			case 0:
				efLocationSemi = p0;
				break;
			case 1:
				efLocationSemi = p1;
				break;
			case 2:
				efLocationSemi = p2;
				break;
			case 3:
				efLocationSemi = p3;
				break;
			default:
				efLocationSemi = null;
				break;
			}
			
			return efLocationSemi ;
		}
		
		public int getAlgorighmModel(){
			int model = 0;
			      if(p0==null&&pf1==null&&p1==null&&p2==null&&p3!=null){
				      model = 1;//只存在  p1 
			}else if(p0==null&&pf1==null&&p1==null&&p2!=null&&p3!=null){
				      model = 2;//只存在  p1 p2 
			}else if(p0==null&&pf1==null&&p1!=null&&p2!=null&&p3!=null){
				      model = 3;//只存在  p1 p2 p3
			}else if(p0==null&&pf1!=null&&p1!=null&&p2!=null&&p3!=null){
				      model = 4;//只存在  p0 p1
			}else if(p0!=null&&pf1!=null&&p1!=null&&p2!=null&&p3!=null){
				      model = 5;
			}
			return model;
		}

		//************************** 计算P3点 与前两点(P2 P1)的可疑系数
		public double calcSuspScoreForP3_P2P1(){
			double score_p1 = 0;
			double score_p2 = 0;

			//方向偏差  > 15度
			if(getSlopeDegreeSubP3_P1()>15){
				score_p1 += 2 ; 
			}

			//分值=分值 *P-1等级可疑系数
			score_p1 = score_p1*getSuspectRadio(p1);
			
			//曲线度 < 45度
			if(getCurvatureSubP1_P2_P3()<45){
				score_p1-=4;
			}else if(getSmoothDegreeSubP1_P2_P3()<30){
				//平滑度<30度   不满足曲线度减分要求  则计算平滑度
				score_p1-=4;
			}
			
			//***计算P2点对P3点的可疑分数
			if(getSlopeDegreeSubP3_P2()>15){
				score_p2 += 2 ; 
			}
			//分值=分值 *P2等级可疑系数
			score_p2 = score_p2*getSuspectRadio(p2);
			p3.setSuspScore(score_p1+score_p2);
			p3.setSuspScoreP0(score_p2);
			p3.setSuspScorePf1(score_p1);

			p3.setSuspScoreFlag(1);//*** 完成P-1 p1 点的计算
			return score_p1+score_p2;
		}
		
		
		public double calcSuspScoreForP3_P1(){
			double score_p1 = 0;

			//方向偏差  > 15度
			if(getSlopeDegreeSubP3_P1()>15){
				score_p1 += 2 ; 
			}

			//分值=分值 *P-1等级可疑系数
			score_p1 = score_p1*getSuspectRadio(p1);
			
			//曲线度 < 45度
			if(getCurvatureSubP1_P2_P3()<45){
				score_p1-=4;
			}else if(getSmoothDegreeSubP1_P2_P3()<30){
				//平滑度<30度   不满足曲线度减分要求  则计算平滑度
				score_p1-=4;
			}
			

			p3.addSuspScore(score_p1);
			p3.setSuspScorePf1(score_p1);

			p3.setSuspScoreFlag(-1);//*** 完成P-1 对  p1 点的计算
			return score_p1;
		}
		
		public double calcSuspScoreForP3_P2(){
			double score_p2 = 0;

			//***计算P2点对P3点的可疑分数
			if(getSlopeDegreeSubP3_P2()>15){
				score_p2 += 2 ; 
			}
			//分值=分值 *P2等级可疑系数
			score_p2 = score_p2*getSuspectRadio(p2);
			p3.addSuspScore(score_p2);
			p3.setSuspScoreP0(score_p2);


			p3.setSuspScoreFlag(0);//*** 完成P0 对 p1 点的计算
			return score_p2;
		}
		
		
	   //************************** 计算P2点 与前两点(P1 Pf1)的可疑系数
		public double calcSuspScoreForP2_P1P0(){
			double score_p0 = 0;
			double score_p1 = 0;

			//方向偏差  > 15度
			if(getSlopeDegreeSubP2_P0()>15){
				score_p0 += 2 ; 
			}

			//分值=分值 *P-1等级可疑系数
			score_p0 = score_p0*getSuspectRadio(p0);
			
			//曲线度 < 45度
			if(getCurvatureSubP0_P1_P2()<45){
				score_p0-=4;
			}else if(getSmoothDegreeSubP0_P1_P2()<30){
				//平滑度<30度   不满足曲线度减分要求  则计算平滑度
				score_p0-=4;
			}
			
			//***计算P2点对P3点的可疑分数
			if(getSlopeDegreeSubP2_P1()>15){
				score_p1 += 2 ; 
			}
			//分值=分值 *P2等级可疑系数
			score_p1 = score_p1*getSuspectRadio(p1);
			p2.setSuspScore(score_p0+score_p1);
			
			return score_p0+score_p1;
		}
		
		//************************** 根据速度  方向 加速度  平滑度  曲度  计算分值  **************************
		//---------- P-1点引起的分数
		public double getPf1Score(){
			double score = 0;
			//方向偏差  > 15度
			if(getSlopeDegreeSubP1_Pf1()>15){
				score += 2 ; 
			}
			//P10与P-1速度偏差	小	-0
			//if(getSpeedSubP1_Pf1()>100){ score += 2 ;  }
			
			//P10与P-1加速度偏差	小	-0
			//if(getAccelerationSubP1_Pf1()>100){ score += 2 ;  }

			//分值=分值 *P-1等级可疑系数
			score = score*getSuspectRadio(pf1);
			//曲线度 < 45度
			if(getCurvatureSubPf1_P0_P1()<45){
				score-=4;
			}else if(getSmoothDegreeSubPf1_P0_P1()<30){
				//平滑度<30度   不满足曲线度减分要求  则计算平滑度
				score-=4;
			}
			
			return score;
		}
		
		
		//---------- P0点引起的分数
		public double getP0Score(){
			double score = 0;
			//方向偏差  > 15度
			if(getSlopeDegreeSubP1_P0()>15){
				score += 2 ; 
			}
			//P10与P0速度偏差	小	-0
			//if(getSpeedSubP1_P0()>100){ score += 2 ;  }
			
			//P10与P0加速度偏差	小	-0
			//if(getAccelerationSubP1_P0()>100){ score += 2 ;  }
			
			//分值=分值 *P-1等级可疑系数
			score = score*getSuspectRadio(p0);
			return score;
		}
		
		
		//---------- P3点引起的分数   对P2点
		public double getP2Score_P3(){
			double score = 0;
			
			//*** 如果P2点是  32 99  确定点 则不需再算分数
			if(isProcessCompleteLocation(p2)){
				return 0;
			}
			
			//*** 如果P2点与P3点  相隔的距离或时间都过长则P3点与P2点 相互不产生影响
			if(isDistanceOrTimeTooLong(p2, p3)){
				return 0;
			}
			
			//方向偏差P31_P21
			CalcScoreByRange calcScoreByRange =new CalcScoreByRange()
						                      .add(0, true, 45, true, -2)
						                      .add(45, false, 90, true, 2)
						                      .add(90, false, 135, true, 3)
						                      .add(135, false,181, true, 4);
			score+=calcScoreByRange.calculate(getSlopeDegreeSubP31_P21());
			//方向偏差 P30_P20
			calcScoreByRange =new CalcScoreByRange()
					            .add(0, true, 30, true, -2)
					            .add(30, false, 90, true, 2)
					            .add(90, false, 135, true, 3)
					            .add(135, false,181, true, 4);
			score+=calcScoreByRange.calculate(getSlopeDegreeSubP30_P20());
			
			//速度偏差P31_P21
			double speedTemp = a1.getSpeed();
			calcScoreByRange =new CalcScoreByRange()
			            .add(speedTemp*0,  true,  speedTemp*3,  true, 0)
			            .add(speedTemp*3,  false, speedTemp*10, true, 1)
			            .add(speedTemp*10, false, speedTemp*30, true, 2)
			            .add(speedTemp*30, false, Double.MAX_VALUE, false, 3);
            score+=calcScoreByRange.calculate(getSpeedSubP31_P21());
			//速度偏差P20_P10
			speedTemp = a0.getSpeed();
			calcScoreByRange =new CalcScoreByRange()
			            .add(speedTemp*0,  true,  speedTemp*3,  true, 0)
			            .add(speedTemp*3,  false, speedTemp*10, true, 1)
			            .add(speedTemp*10, false, speedTemp*30, true, 2)
			            .add(speedTemp*30, false, Double.MAX_VALUE, false, 3);
            score+=calcScoreByRange.calculate(getSpeedSubP30_P20());
			
			//加速度偏差P31_P21
			double accelerationTemp = a1.getAcceleration();
			calcScoreByRange =new CalcScoreByRange()
            .add(accelerationTemp*0,  true,  accelerationTemp*3,  true, 0)
            .add(accelerationTemp*3,  false, accelerationTemp*10, true, 1)
            .add(accelerationTemp*10, false, accelerationTemp*30, true, 2)
            .add(accelerationTemp*30, false, Double.MAX_VALUE, false, 3);
			score+=calcScoreByRange.calculate(getAccelerationSubP31_P21());
			//加速度偏差P30_P20
			accelerationTemp = a0.getAcceleration();
			calcScoreByRange =new CalcScoreByRange()
			            .add(accelerationTemp*0,  true,  accelerationTemp*3,  true, 0)
			            .add(accelerationTemp*3,  false, accelerationTemp*10, true, 1)
			            .add(accelerationTemp*10, false, accelerationTemp*30, true, 2)
			            .add(accelerationTemp*30, false, Double.MAX_VALUE, false, 3);
			score+=calcScoreByRange.calculate(getAccelerationSubP30_P20());
            
			//分值=分值 *P-1等级可疑系数 :: 曲度和平滑度不需要可疑系数
			score = score*getSuspectRadio(p3);
			
			//曲线度 < 45度    平滑度<30度   不满足曲线度减分要求  则计算平滑度
			CalcScoreByRange calcScoreByRangeCurvature =new CalcScoreByRange()
			            .add(0, true, 45, true, -4)
			            .add(45, false,181, false, 0);
			CalcScoreByRange calcScoreByRangeSmoothDegree =new CalcScoreByRange()
			            .add(0, true, 30, true, -4)
			            .add(45, false,181, false, 0);
			score+=calcScoreByRangeCurvature.calculate(getCurvatureSubP1_P2_P3())<0?calcScoreByRangeCurvature.calculate(getCurvatureSubP1_P2_P3()):calcScoreByRangeSmoothDegree.calculate(getSmoothDegreeSubP1_P2_P3());
			
			p2.setSuspScoreP2(score);
			p2.setSuspScoreFlag(2);//*** 完成P2点的计算
			
			
			return score;
		}
		
		
		public  boolean isProcessCompleteLocation(EfLocationSemi efLocationSemi){
			boolean flag = false;
			List<Integer> flags=new ArrayList<Integer>();
			flags.add(EfLocationSemi.Flag.RETAIN.getValue());
			flags.add(EfLocationSemi.Flag.FINISH.getValue());
			if(flags.contains(efLocationSemi.getFlag().intValue())){
				flag = true;
			}
			return flag ;
		}
		
		public  boolean isDistanceOrTimeTooLong(EfLocationSemi aa,EfLocationSemi bb){
			boolean flag = false;
			if(aa==null || bb==null){
				return true;
			}
			long minutes = Math.abs(DateUtils.calcMinutesBetween(aa.getTimeEnd(), bb.getTimeBegin()));
			double distance = Math.abs(calcDistance(aa, bb));
			//*** 初步定为30分钟   1000米  调试的时候再修改
			if(minutes >30 || distance >1000){
				flag = true;
			}
			return flag;
		}
		
		public  boolean isTimeTooLongByCurTime(EfLocationSemi aa){
			boolean flag = false;
			if(aa==null){
				return true;
			}
			long minutes = Math.abs(DateUtils.calcMinutesBetween(aa.getTimeEnd(),new Date()));
			//*** 初步定为30分钟   1000米  调试的时候再修改
			if(minutes >30){
				flag = true;
			}
			return flag;
		}
		
		//---------- P3点引起的分数  对P1点
		public double getP1Score_P3(){
			double score = 0;
			
			//********* 某一个点在算完后   可能不需要计算4个点的数据  比如 算到第3个点的时候 就已经确定分数  ：删除或者保存
			//在没有计算P1点之前 先确定P1点是否已经满足 绝对删除或者保存
			//前后两点证明之后：31  abs(分值)>=2=33 否则=32 （P2之后31 <-6 =32 	31>12=33（不可能大于12））
			if(p1.getSuspScore()<-6){
				log.info("计算完P2点之后  P1点的分数已经小于-6 分  则不需计算P3点对P1的影响   直接保留P1点  : p1.id = "+p1.getId());
				return 0;
			}
			if(p1.getSuspScore()>12){
				log.info("计算完P2点之后  P1点的分数已经大于12分   则不需计算P3点对P1的影响   直接删除P1点  : p1.id = "+p1.getId());
				return 0;
			}
			
			//*** 如果P1点是  32 99  确定点 则不需再算分数
			if(isProcessCompleteLocation(p1)){
				return 0;
			}
			
			//*** 如果P1点与P3点  相隔的距离或时间都过长则P3点与P1点 相互不产生影响
			if(isDistanceOrTimeTooLong(p1, p3)){
				return 0;
			}
			
			
			//方向偏差 P30_P10
			CalcScoreByRange calcScoreByRange =new CalcScoreByRange()
						                      .add(0, true, 45, true, -2)
						                      .add(45, false, 90, true, 2)
						                      .add(90, false, 135, true, 3)
						                      .add(135, false,181, true, 4);
			score+=calcScoreByRange.calculate(getSlopeDegreeSubP30_P10());
			//方向偏差 P3f1_P1f1
			calcScoreByRange =new CalcScoreByRange()
					            .add(0, true, 30, true, -2)
					            .add(30, false, 90, true, 2)
					            .add(90, false, 135, true, 3)
					            .add(135, false,181, true, 4);
			score+=calcScoreByRange.calculate(getSlopeDegreeSubP3f1_P1f1());
			
			//速度偏差P30_P10
			double speedTemp = a0.getSpeed();
			calcScoreByRange =new CalcScoreByRange()
			            .add(speedTemp*0,  true,  speedTemp*3,  true, 0)
			            .add(speedTemp*3,  false, speedTemp*10, true, 1)
			            .add(speedTemp*10, false, speedTemp*30, true, 2)
			            .add(speedTemp*30, false, Double.MAX_VALUE, false, 3);
            score+=calcScoreByRange.calculate(getSpeedSubP30_P10());
			//速度偏差P3f1_P1f1
			speedTemp = af1.getSpeed();
			calcScoreByRange =new CalcScoreByRange()
			            .add(speedTemp*0,  true,  speedTemp*3,  true, 0)
			            .add(speedTemp*3,  false, speedTemp*10, true, 1)
			            .add(speedTemp*10, false, speedTemp*30, true, 2)
			            .add(speedTemp*30, false, Double.MAX_VALUE, false, 3);
            score+=calcScoreByRange.calculate(getSpeedSubP3f1_P1f1());
			
			//加速度偏差P30_P10
			double accelerationTemp = a0.getAcceleration();
			calcScoreByRange =new CalcScoreByRange()
            .add(accelerationTemp*0,  true,  accelerationTemp*3,  true, 0)
            .add(accelerationTemp*3,  false, accelerationTemp*10, true, 1)
            .add(accelerationTemp*10, false, accelerationTemp*30, true, 2)
            .add(accelerationTemp*30, false, Double.MAX_VALUE, false, 3);
			score+=calcScoreByRange.calculate(getAccelerationSubP30_P10());
			//加速度偏差P3f1_P1f1
			accelerationTemp = af1.getAcceleration();
			calcScoreByRange =new CalcScoreByRange()
			            .add(accelerationTemp*0,  true,  accelerationTemp*3,  true, 0)
			            .add(accelerationTemp*3,  false, accelerationTemp*10, true, 1)
			            .add(accelerationTemp*10, false, accelerationTemp*30, true, 2)
			            .add(accelerationTemp*30, false, Double.MAX_VALUE, false, 3);
			score+=calcScoreByRange.calculate(getAccelerationSubP3f1_P1f1());
            
			//分值=分值 *P-1等级可疑系数 :: 曲度和平滑度不需要可疑系数
			score = score*getSuspectRadio(p3);
			
			//曲线度 < 45度    平滑度<30度   不满足曲线度减分要求  则计算平滑度
			CalcScoreByRange calcScoreByRangeCurvature =new CalcScoreByRange()
			            .add(0, true, 45, true, -4)
			            .add(45, false,181, false, 0);
			CalcScoreByRange calcScoreByRangeSmoothDegree =new CalcScoreByRange()
			            .add(0, true, 30, true, -4)
			            .add(45, false,181, false, 0);
			score+=calcScoreByRangeCurvature.calculate(getCurvatureSubP1_P2_P3())<0?calcScoreByRangeCurvature.calculate(getCurvatureSubP1_P2_P3()):calcScoreByRangeSmoothDegree.calculate(getSmoothDegreeSubP1_P2_P3());
			p1.setSuspScoreP3(score);
			p1.addSuspScore(score);
			p1.setSuspScoreFlag(3);//*** 完成P3点的计算
			
			//****** 如果P1点的分数符合删除条件   此时需要考虑P1点 本身的  停留时间  聚合点数  源坐标类型  然后再进行是否删除
			if(p1.getSuspScore()>2 ){
				log.info("P1点的分数符合删除条件   此时需要考虑P1点 本身的  停留时间  聚合点数  源坐标类型  然后再进行是否删除 : p1.id = "+p1.getId());
				if(calcScoreMyself(p1)<-4){//分数都是需要调整的
					p1.setSuspScore(0);// 因为本身的条件保存下来的点
				}
			}
			
			return score;
         }
		
		//****** 如果P1点的分数符合删除条件   此时需要考虑P1点 本身的  停留时间  聚合点数  源坐标类型  然后再进行是否删除
		 public double calcScoreMyself(EfLocationSemi efLocationSemi){
			 double score = 0;
			 
			 //停留时间
			 CalcScoreByRange calcScoreByRange =new CalcScoreByRange()
									            .add(0,  true,  1,  true, 0)
									            .add(1,  false, 2,  true, -1)
									            .add(2,  false, 5,  true, -2)
									            .add(5,  false, 10, true, -5)
									            .add(10, false, 15, true, -10)
									            .add(15, false,Integer.MAX_VALUE, false, -100);
             double scoreStayTime =calcScoreByRange.calculate(DateUtils.calcMinutesBetween(efLocationSemi.getTimeBegin(), efLocationSemi.getTimeEnd()));
			 
			 //聚合点数
			 calcScoreByRange =new CalcScoreByRange()
									            .add(0,  true,  1,  true, 0)
									            .add(1,  true,  2,  true, -1)
									            .add(2,  true,  5,  true, -2)
									            .add(5,  false, 10, true, -5)
									            .add(10, false, 15, true, -10)
									            .add(15, false, Integer.MAX_VALUE, false, -100);
             double scoreAggrCount = calcScoreByRange.calculate(efLocationSemi.getAggrCount());
             
             
             

			 //源坐标类型
			 int gps   = EjlLocation.Source.GPS.getCode();
			 int tower = EjlLocation.Source.TOWER.getCode();
			 int wifi  = EjlLocation.Source.WIFI.getCode();
			 calcScoreByRange =new CalcScoreByRange()
									            .add(gps,   true,  gps,    true, -2)
									            .add(tower, true,  tower,  true, +1)
									            .add(wifi,  true,  wifi,   true, +1);
			 double scoreArr = calcScoreByRange.calculate(efLocationSemi.getSource());
             
			 score = scoreStayTime + scoreAggrCount + scoreArr ;
			 
			 return score;
		 }
		
		
		 public double getEfLocationSemiTotalScore(){
			 
			 //*** 计算P2 P3的可疑系数
			 calcSuspScoreForP3_P2P1();
			 calcSuspScoreForP2_P1P0();
			 
			 
			 //*** 获取点P1与其他四点的计算得分
			 return (getPf1Score()+getP0Score()+getP2Score_P3()+getP1Score_P3())*getAggregationSuspectRadio(p1.getAggrCount())*getLocationSourceSuspectRadio(p1.getSource());
		 }
		 
		
		 public double getEfLocationSemiPf1P0SuspScore(){
			 //*** 获取点P1与前面两点Pf1 P0的计算得分，
			 return (getPf1Score()+getP0Score())*getAggregationSuspectRadio(p1.getAggrCount())*getLocationSourceSuspectRadio(p1.getSource());
		 }
		 
		 
		 public void handler_one(){
			 //*** 算完一个P1点后   需确定 删除还是保存 如果保存需要把分数这些都保存了
			 
			 
			 
			 //*** 保存以后需要组建一个新的五点   也就是往后取一点
			 
			 //*** 删除以后也需要组建一个新的五点  并且清掉分数
			 
			 
		 }
		 
		
		class CalcScoreByRange{
			List<ScoreRange> scoreRangeList = new ArrayList<ScoreRange>();
			
			CalcScoreByRange add(double startVal,boolean isCloseStart,double endVal,boolean isCloseEnd,double score){
				scoreRangeList.add(new ScoreRange(startVal, isCloseStart, endVal, isCloseEnd, score));
				return this ;
			}
			
			double calculate(double value){
				double score = 0;
				for(ScoreRange scoreRangeTemp:scoreRangeList){
					if(scoreRangeTemp.isInScoreRange(value)){
						score = scoreRangeTemp.getScore();
						break;
					}
				}
				return score;
			}
	}
	
	
	class ScoreRange{
		double  startVal;//开始值
		boolean isCloseStart;//开始是否闭区间
		double  endVal;//结束值
		boolean isCloseEnd;//结束是否闭区间
		double  score;//所在区间的
		
		
		public ScoreRange(double startVal, boolean isCloseStart, double endVal,
				boolean isCloseEnd, double score) {
			super();
			this.startVal = startVal;
			this.isCloseStart = isCloseStart;
			this.endVal = endVal;
			this.isCloseEnd = isCloseEnd;
			this.score = score;
		}

		
		//double 的比较相等 需要更改  因为精度问题  非整型数，运算由于精度问题，可能会有误差，建议使用BigDecimal类型
		public boolean isInScoreRange(double value){
			boolean flag = false;
			if(startVal<value && value<endVal){
				flag = true;
			}else if(isCloseStart && value==startVal){
				flag = true;
			}else if(isCloseEnd && value==endVal){
				flag = true;
			}
			return flag ;
		}
		
		public double getStartVal() {
			return startVal;
		}
		public void setStartVal(double startVal) {
			this.startVal = startVal;
		}
		public boolean isCloseStart() {
			return isCloseStart;
		}
		public void setCloseStart(boolean isCloseStart) {
			this.isCloseStart = isCloseStart;
		}
		public double getEndVal() {
			return endVal;
		}
		public void setEndVal(double endVal) {
			this.endVal = endVal;
		}
		public boolean isCloseEnd() {
			return isCloseEnd;
		}
		public void setCloseEnd(boolean isCloseEnd) {
			this.isCloseEnd = isCloseEnd;
		}
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}
		@Override
		public String toString() {
			return "ScoreRange [startVal=" + startVal + ", isCloseStart="
					+ isCloseStart + ", endVal=" + endVal + ", isCloseEnd="
					+ isCloseEnd + ", score=" + score + "]";
		}
	  }
    }

	public void  handlerEfLocationSemiByScore111(Context ctx ,EfLocationSemiFive semiFive) throws BizException{
		
		//***** 算P3点 对P1点分数影响
		semiFive.getP1Score_P3();
		
		if(Math.abs(semiFive.p1.getSuspScore())<2){
			if(semiFive.p1.getFlag().intValue()!=EfLocationSemi.Flag.RETAIN.getValue() && semiFive.p1.getFlag().intValue()!=EfLocationSemi.Flag.FINISH.getValue()){
				//*** 保存当前点
				updateEfLocationSemiFlag(ctx,semiFive.p1, EfLocationSemi.Flag.RETAIN.getValue());
			}
			
			//*** 在P1点保存的情况下   计算P3点对P2点的影响分数
			semiFive.getP2Score_P3();
			
			//*** 算P3点 与前两点的可疑系数 ( 最开始没算P3点的分数  suspScore默认为 0 )
			semiFive.calcSuspScoreForP3_P2P1();
			
			if(!semiFive.pushEfLocationSemi()){
				//*** 后面已没有点  跳出
				return ;
			}
			
			//查出来的点有可能是：  1 未处理过的点    2 P2点   3 P3点
			
			
			
		}else{
			//*** 删除当前点会对前面的点有影响，需重新计算前两点
			updateEfLocationSemiFlag(ctx,semiFive.p1, EfLocationSemi.Flag.DISPOSE.getValue());
			
			//*** 删除P1点以后  需要重新计算P2点的  方向 速度 加速度等详细数据
			processDetail(ctx,semiFive.p2);
			
			if(!semiFive.deleteP1AndRecombinationFiveSpot()){
				//*** 删除当前点并重新组合五点时 不够五点
				return;
			} 

		}
		
		handlerEfLocationSemiByScore(ctx, semiFive);
	}
	
	public void t2(Context ctx ,EfLocationSemi efLocationSemi) throws BizException{
		//查出来的点有可能是：  1 未处理过的点    2 P2点   3 P3点
		EfLocationSemiFive semiFive =  null;
		if(efLocationSemi.getSuspScoreFlag()== null || efLocationSemi.getSuspScoreFlag().intValue() == -2){
			//未处理过的点
			semiFive = new EfLocationSemiFive(efLocationSemi);
			
		}else if(efLocationSemi.getSuspScoreFlag().intValue() == 0){
			//P3点   
			semiFive = new EfLocationSemiFive(efLocationSemi);
			
			//P3点往后面找点 如果有点 则正常处理   如果没有点 则按照规则处理：
			
			//1  如果没有点   但是P3点聚集很多点了
			//2  如果一直在聚集到P3点
			//3 P3点在确定下来之前要把P2点确定下来
			
			//********** 思考？？？  当前点  本身的   停留时间    聚合点数  以及   坐标来源 应该对当前点是否为正确的点  有很大的影响

			//********** 有没有下一点  只要P3点聚合点数发生改变  就需要重新计算 P2 点 (不用再算P1点 )
			if(efLocationSemi.getAggrCountLastCal().intValue() != efLocationSemi.getAggrCount().intValue()){
				
				//*** P3点的分数需要 重新计算   此处置 0 ，避免对P2点造成影响
				semiFive.p3.setSuspScore(0);
				
				//*** 计算P3点对P2点的影响分数
				semiFive.getP2Score_P3();
				
				//*** 算P3点 与P1 P2点的可疑系数 (可以分开算)
				semiFive.calcSuspScoreForP3_P2P1();
				
			}
			
			
			EfLocationSemi tt =getNextAfterAggred(efLocationSemi);
			if(tt != null){
				//有下一点 直接跳（定一个规则  只要后面有点  前面的点都由后面定 即使没有关系）
				return;
			}else{
				//没有下一点
				//规则：P3点的最后时间 距离当前时间超过20分钟  不来点  需要把P3点定下来
				
				//没有下一点   但是聚合点数增加   需要算前面一点P2   还有当前点P3
				
				if(semiFive.isTimeTooLongByCurTime(efLocationSemi)){
					//*** P2 P3点  在没有完全算完点的情况下  需要确定下来  分数是否需要调整  这个可能测试的时候给的数据更合理
					//  也就是此处就是一个断点了   直接保存P2 P3点
					updateEfLocationSemiFlag(ctx,semiFive.p2, EfLocationSemi.Flag.RETAIN.getValue());
					updateEfLocationSemiFlag(ctx,semiFive.p3, EfLocationSemi.Flag.RETAIN.getValue());
					
				}else{
					//*** P3点后面短时间内  还没来点  暂时不处理
					return;
				}
			}
			
		}else if(efLocationSemi.getSuspScoreFlag().intValue() == 2){
			// P2点
			// 1  如果是P2点 则跳到P3点进行计算（P2点后面肯定有P3点）
			 return;
			
			// 2 如果多次进来都是P2点 应该有标准把P2点 定下来
			// P2点定不下来的 原因是P3点后面没有来点
			// 如果这样  应该对P3点自己也有影响
			// 也就是说  如果P3点 后面不在来点  超过一定的时间  这时需要把 P2 P3按照规则定下来
			// 规则：P3点的最后时间 距离当前时间超过20分钟  不来点  需要把P3点定下来
			
		}else {
			log.error("存在未处理的点： ------------- ");
		}
		
		handlerEfLocationSemiByScore(ctx, semiFive);
	}
	
	//****** 计算当前点的得分  并根据得分   判断是保留还是删除当前点 ****************  
	public void  handlerEfLocationSemiByScore(Context ctx ,EfLocationSemiFive semiFive) throws BizException{
		
		//*** 进入不足 三点的算法   直接保留前两点
		if(!semiFive.isEnoughThreeSpot()){
			updateEfLocationSemiFlag(ctx,semiFive.p3, EfLocationSemi.Flag.RETAIN.getValue());
			if(semiFive.pushEfLocationSemi()){
				handlerEfLocationSemiByScore(ctx, semiFive);
			}else{
				return;
			}
		}
		
		//***** 算P3点 对P1点分数影响
		semiFive.getP1Score_P3();
		
		if(semiFive.p1.getSuspScore()<2){
			
			if(semiFive.p1.getFlag().intValue()!=EfLocationSemi.Flag.RETAIN.getValue() && semiFive.p1.getFlag().intValue()!=EfLocationSemi.Flag.FINISH.getValue()){
				//*** 保存当前点
				updateEfLocationSemiFlag(ctx,semiFive.p1, EfLocationSemi.Flag.RETAIN.getValue());
			}
			
			//*** 在P1点保存的情况下   计算P3点对P2点的影响分数
			semiFive.getP2Score_P3();
			
			//*** 算P3点 与前两点的可疑系数 ( 最开始没算P3点的分数  suspScore默认为 0 )
			semiFive.calcSuspScoreForP3_P2P1();
			
			if(!semiFive.pushEfLocationSemi()){
				//*** 退出时  需要保存每个点已经计算出来的分数
				semiFive.saveCalScoreForFive();
				return ;
			}
		}else{
			//*** 删除当前点会对前面的点有影响，需重新计算前两点
			updateEfLocationSemiFlag(ctx,semiFive.p1, EfLocationSemi.Flag.DISPOSE.getValue());
			
			//*** 删除P1点以后  需要重新计算P2点的  方向 速度 加速度等详细数据
			processDetail(ctx,semiFive.p2);
			
			//*** 删除当前点并重新组合五点时 不够五点
			if(!semiFive.deleteP1AndRecombinationFiveSpot()){
				//*** 退出时  需要保存每个点已经计算出来的分数
				semiFive.saveCalScoreForFive();
				return;
			} 
		}
		
		handlerEfLocationSemiByScore(ctx, semiFive);
	}
	

	
	
	
	public void processFiveSpotAlgorighm(Context ctx,EfLocationSemi locationSemi) throws BizException{
		EfLocationSemiFive semiFive = new EfLocationSemiFive(locationSemi);
		handlerEfLocationSemiByScore(ctx, semiFive);
	}
	
		public void processFiveSpotAlgorighm(Context ctx,EfLocationSemiFive semiFive) throws BizException{
		
		//*** 如果最后的两点  P2 P3 一直不确定  ：1  后面不在有点来   2 后面来的点一直是P3点的重复点   3 后面来的点距离时间或者距离过大
		
		if(semiFive.isEnoughThreeSpot()){
			
			if(!handlerEfLocationSemiByScore(ctx , semiFive)){
				return;
			}
			//*** 进入五点的算法   
			if(handlerEfLocationSemiByScore(ctx , semiFive)){
				semiFive.pushEfLocationSemi();
			}else{
				//*** 删除的时候 能移多少  算多少吧  要修改
				semiFive.deleteP1AndRecombinationFiveSpot();
			}
		}else{
			//*** 进入不足 五点的算法   直接保留前两点
			if(!handlerInitEfLocationSemi(ctx, semiFive)){
				return;
			}
			//*** 如果点都处理完了   则跳出递归
		}
		
		processFiveSpotAlgorighm(ctx, semiFive);
	}
	
	
		
	protected void processFiveSpotAlgorighm22(Context ctx,EfLocationSemi locationSemi) throws BizException{

		EfLocationSemiFive efLocationSemiFive = new EfLocationSemiFive(locationSemi);
		if(efLocationSemiFive.isEnoughThreeSpot()){
			//*** 进入五点的算法   
			handlerEfLocationSemiByScore(ctx , efLocationSemiFive);
			
		}else{
			//*** 进入不足 五点的算法
			handlerInitEfLocationSemi(ctx, efLocationSemiFive);
			
		}
		//processFiveSpotAlgorighm22(ctx, efLocationSemiFive);
	}
	
	public EfLocationSemi  updateEfLocationSemiFlag(Context ctx,EfLocationSemi efLocationSemi,int flag) throws BizException{
		efLocationSemi.setFlag(flag);
		tskLocationSemiService.updateCalData(efLocationSemi);
		return efLocationSemi;
	}
	
	//*******
	 
	//特殊点处理： 1  停留时间过长  2 与后面的点距离太长时间  
	//***判断是否不足五个点
	//***进入不足五点算法
	//***进入足五点算法
	1.方向
    2.速度
	3.加速度
	4.点前段平滑度、曲度
	5.点中段平滑度、曲度
	6.点后段平滑度、曲度1.方向
	2.速度
	3.加速度
	4.点前段平滑度、曲度
	5.点中段平滑度、曲度
	6.点后段平滑度、曲度(把这些打分的用流水日志记录下来分析，只分析需要分析的设备，并配置在数据库中)
	综合分：修改状态
	

	
	public void NotEnoughFiveSpotAlgorighm(Context ctx,EfLocationSemi locationSemi,EfLocationSemiFive efLocationSemiFive) throws BizException{
		int model = efLocationSemiFive.getAlgorighmModel();
		//*** 一个点停留超过几分钟 或者 聚合的点超过多少个 ，就当作保留点，这个需要那个方法来确定
		if(model == 1 || model == 2){//NO NO YES NO NO (前后都没有点的情况)
			tranToRetailForFirstAndSecondSpot(ctx, locationSemi);
		}else if(model == 3){//前面两个点
			//***必须把第一个点确定下来
			if(locationSemiPrePre.getFlag().intValue()<EfLocationSemi.Flag.RETAIN.getValue()){
				//方向 速度 加速度 角度 曲度 平滑度
				
			}
			//***
			
			
		}else if(model == 4){
			
		}

	}//********** end NotEnoughFiveSpotAlgorighm
	
	
	
}

*/