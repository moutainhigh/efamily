/**   
* @Title: EjlHealthManageServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:20:22 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEJLComHealthSleepDao;
import com.winterframework.efamily.dao.IEJLDeviceConfigDao;
import com.winterframework.efamily.dao.IEjlComUserExtendInfoDao;
import com.winterframework.efamily.dao.IEjlDeviceDao;
import com.winterframework.efamily.dao.IEjlHealthHeartRateDao;
import com.winterframework.efamily.dao.IEjlHealthStepCountDao;
import com.winterframework.efamily.dao.IEjlUserDao;
import com.winterframework.efamily.dto.HealthyMonitorDataClimbing;
import com.winterframework.efamily.dto.HealthyMonitorDataHeart;
import com.winterframework.efamily.dto.HealthyMonitorDataSteps;
import com.winterframework.efamily.dto.HealthyMonitorDataStruc;
import com.winterframework.efamily.dto.HealthyMonitorDataSwingOrCycle;
import com.winterframework.efamily.dto.HealthyMonitorDataUnitStruc;
import com.winterframework.efamily.dto.HealthyMonitorDateSleep;
import com.winterframework.efamily.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.dto.HealthyProfileStruc;
import com.winterframework.efamily.dto.LocationStruc;
import com.winterframework.efamily.dto.QueryMonitorDataRequest;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthBloodPressure;
import com.winterframework.efamily.entity.EjlHealthHeartRate;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.entity.EjlHealthStepCount;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserExtendInfo;
import com.winterframework.efamily.entity.HealthyMonitorDataDeep;
import com.winterframework.efamily.entity.HealthyMonitorDataWake;
import com.winterframework.efamily.service.IComEjlHealthBloodPressureService;
import com.winterframework.efamily.service.IEjlComUserNoticeService;
import com.winterframework.efamily.service.IEjlHealthManageService;
import com.winterframework.efamily.service.IEjlLocationService;
import com.winterframework.efamily.service.IEjlUserDeviceService;
import com.winterframework.efamily.utils.FormulaUtils;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: EjlHealthManageServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:20:22 
*  
*/
@Service("ejlHealthManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlHealthManageServiceImpl extends BaseServiceImpl<IEjlHealthStepCountDao, EjlHealthStepCount> implements
		IEjlHealthManageService {

	@Resource(name = "ejlHealthHeartRateDaoImpl")
	private IEjlHealthHeartRateDao ejlHealthHeartRateDao;

	@Resource(name = "ejlHealthStepCountDaoImpl")
	private IEjlHealthStepCountDao ejlHealthStepCountDao;

	@Resource(name = "ejlUserDaoImpl")
	private IEjlUserDao ejlUserDaoImpl;
	
	@Resource(name = "ejlComHealthSleepDaoImpl")
	private IEJLComHealthSleepDao 	ejlComHealthSleepDaoImpl;
	
	@Resource(name = "ejlLocationServiceImpl")
	private IEjlLocationService ejlLocationServiceImpl;
	
	@Resource(name = "ejlDeviceDaoImpl")
	private IEjlDeviceDao ejlDeviceDaoImpl;
	
	@Resource(name = "ejlUserDeviceServiceImpl")
	private IEjlUserDeviceService 	ejlUserDeviceServiceImpl;
	
	@Resource(name="deviceConfigDaoImpl")
	private IEJLDeviceConfigDao deviceConfigDaoImpl;
	
	@Resource(name="ejlComUserExtendInfoDaoImpl")
	private IEjlComUserExtendInfoDao ejlComUserExtendInfoDaoImpl;
	
	
	@Resource(name = "ejlComHealthBloodPressureServiceImpl")
	private IComEjlHealthBloodPressureService ejlComHealthBloodPressureServiceImpl;

	/**
	* Title: getHealthyProfilesByFamilyId
	* Description:
	* @param ownerMobileNumber
	* @param familyId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlHealthManageService#getHealthyProfilesByFamilyId(java.lang.String, java.lang.Long) 
	*/
	@Override
	public HealthyProfileStruc getHealthyProfilesByFamilyId(Long deviceId, Long userId)
			throws BizException {
		HealthyProfileStruc healthyProfileStruc= new HealthyProfileStruc();
		EjlHealthHeartRate ejlHealthHeartRate = ejlHealthHeartRateDao.getLastUserHeartRate(
				userId, deviceId);
		healthyProfileStruc.setHeartRate(ejlHealthHeartRate == null ? 0 : ejlHealthHeartRate.getRate());
		healthyProfileStruc.setUserId(userId);
		healthyProfileStruc.setDeviceId(deviceId);
		
		Long stepsWalk = ejlHealthStepCountDao.getAllDayStepsByUser(healthyProfileStruc.getUserId(), DateUtils.currentDate(),deviceId,1);
		healthyProfileStruc.setWalkStep(stepsWalk==null?0:stepsWalk);
		Long runningStep = ejlHealthStepCountDao.getAllDayStepsByUser(healthyProfileStruc.getUserId(), DateUtils.currentDate(),deviceId,2);
		healthyProfileStruc.setRunningStep(runningStep==null?0:runningStep);
		EjlUser user = ejlUserDaoImpl.getUserByUserId(userId);
		healthyProfileStruc.setRunningDistance(FormulaUtils.getMileageBySteps(user.getHeight()==null?0:user.getHeight(), healthyProfileStruc.getRunningStep()).longValue());
		Long climbingStep = ejlHealthStepCountDao.getAllDayStepsByUser(healthyProfileStruc.getUserId(), DateUtils.currentDate(),deviceId,3);
		healthyProfileStruc.setClimbingStep(climbingStep==null?0:climbingStep/100);
		
		//获取当天骑行的时间段 begin
		Date startDate = DateUtils.getStartTimeOfCurrentDate();
		Date endDate = DateUtils.getEndTimeOfCurrentDate();
		PageRequest<QueryMonitorDataRequest> pageRequest = new PageRequest<QueryMonitorDataRequest>(1,
				Integer.MAX_VALUE);
		QueryMonitorDataRequest request = new QueryMonitorDataRequest();
		request.setUserId(userId);
		request.setDeviceId(deviceId);
		request.setStartQueryTime(startDate);
		request.setEndQueryTime(endDate);
		request.setMonitorDataType(4);
		request.setSortColumns("BEGINTIME asc");
		pageRequest.setSearchDo(request);
		List<EjlHealthStepCount> list = ejlHealthStepCountDao.getEjlHealthStepCountsByUserAndDate(pageRequest,false);
		//获取当天骑行的时间段end
		//计算骑行的所有时段的卡路里 begin
		Double ridingCalorie = 0d;
		if(!list.isEmpty()){
			for(EjlHealthStepCount e:list){
				ridingCalorie += this.getCycleCalorie(userId, deviceId, e.getBegintime(), e.getEndtime());
			}
		}
		healthyProfileStruc.setRidingCalorie(ridingCalorie.longValue());
		//计算骑行的所有时段的卡路里 end
		
		
		
		startDate =DateUtils.getStartTimeOfCurrentDate();
		endDate = DateUtils.getEndTimeOfCurrentDate();
		EjlHealthSleep ejlHealthSleepQuery = new EjlHealthSleep();
		ejlHealthSleepQuery.setUserId(userId);
		ejlHealthSleepQuery.setDeviceId(deviceId);
		ejlHealthSleepQuery.setFromTime(startDate);
		ejlHealthSleepQuery.setToTime(endDate);
		List<EjlHealthSleep> listSleep = ejlComHealthSleepDaoImpl.getSleepsByAttribute(ejlHealthSleepQuery);
		if(!listSleep.isEmpty()){
			Double total =0d;
			for(EjlHealthSleep ejlHealthSleep : listSleep){
				total+=DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime());
			}
			healthyProfileStruc.setSleepTime(Float.valueOf(new java.text.DecimalFormat("#.0").format(total)));
		}else{
			healthyProfileStruc.setSleepTime(0.0f);
		}

		/*EjlHealthSleep ejlHealthSleep=ejlComHealthSleepDaoImpl.getLastSleepByAttribute(ejlHealthSleepQuery);
		if(ejlHealthSleep != null){
			Double wakeTime = this.getWakeTime(ejlHealthSleep);
			Double total = DateUtils.calcHoursDoubleBetween(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime());
			healthyProfileStruc.setSleepTime(Float.valueOf(new java.text.DecimalFormat("#.0").format(total-wakeTime)));
		}*/
		if(user.getHeight() !=null && user.getWeight() !=null && user.getHeight().intValue() !=0 && user.getWeight().intValue() !=0){
			healthyProfileStruc.setSetHealthConfig(1l);
		}

		if(deviceId == null){
		deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(userId);}
		EjlDevice ejlDevice = ejlDeviceDaoImpl.getById(deviceId);
		healthyProfileStruc.setSleepLockStatus(ejlDevice.getSleeplockStatus());
		try {
			EjlUserExtendInfo entity = new EjlUserExtendInfo();
			entity.setUserId(userId);
			EjlUserExtendInfo ejlUserExtendInfo = ejlComUserExtendInfoDaoImpl.selectOneObjByAttribute(entity);
			healthyProfileStruc.setSitTime(ejlUserExtendInfo==null||ejlUserExtendInfo.getSitTime()==null?0f:ejlUserExtendInfo.getSitTime());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		EjlHealthBloodPressure ejlHealthBloodPressure = new EjlHealthBloodPressure();
		ejlHealthBloodPressure.setUserId(userId);
		ejlHealthBloodPressure.setDeviceId(deviceId);
		ejlHealthBloodPressure.setFromTime(DateUtils.convertDate2Long(startDate));
		ejlHealthBloodPressure.setToTime(DateUtils.convertDate2Long(endDate));
		try {
			EjlHealthBloodPressure bloodPressure=ejlComHealthBloodPressureServiceImpl.getLastBloodPressure(ejlHealthBloodPressure);
			if(bloodPressure != null){
				healthyProfileStruc.setSystolicPressure(bloodPressure.getHigh());
				healthyProfileStruc.setDiastolicPressure(bloodPressure.getLow());
				healthyProfileStruc.setBloodRate(bloodPressure.getRate());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  healthyProfileStruc;
					
	}
	
	@Override
	public Integer getSleepLockStatus(Long userId,Long deviceId) throws BizException{
		if(deviceId == null){
			deviceId = ejlUserDeviceServiceImpl.getUserUseingDeviceId(userId);
		}
		EjlDevice ejlDevice = ejlDeviceDaoImpl.getById(deviceId);
		return ejlDevice.getSleeplockStatus();
	}

	/**
	* Title: deleteHealthyProfileByFamilyId
	* Description:
	* @param ownerMobileNumber
	* @param userId
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlHealthManageService#deleteHealthyProfileByFamilyId(java.lang.String, java.lang.Long) 
	*/
	@Override
	public void deleteHealthyProfileByUserId(String ownerMobileNumber, Long userId, Long deviceId) throws BizException {
		ejlHealthHeartRateDao.deleteUserHealthHeartRate(userId, deviceId);
		ejlHealthStepCountDao.deleteUserHealthStepCount(userId, deviceId);
	}

	@Override
	public HealthyMonitorDataStruc getMonitorHeartDataById(
			QueryMonitorDataRequest request) throws Exception {
		List<HealthyMonitorDataUnitStruc> unitDatas = new ArrayList<HealthyMonitorDataUnitStruc>();
		HealthyMonitorDataStruc result = new HealthyMonitorDataStruc();
		result.setUnitDatas(unitDatas);
		result.setTotalTime(0l);
		Map<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> map = new HashMap<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc>();
		PageRequest<QueryMonitorDataRequest> pageRequest = new PageRequest<QueryMonitorDataRequest>(request.getCurrentPage(),
				request.getPerPageSize());
		request.setSortColumns("create_time DESC");
		pageRequest.setSearchDo(request);
		List<EjlHealthHeartRate> list = ejlHealthHeartRateDao.getEjlHealthHeartRatesByUserAndDate(pageRequest);
		for (EjlHealthHeartRate ejlHealthHeartRate : list) {
			HealthyMonitorDataUnitStruc healthyMonitorDataUnitStruc = new HealthyMonitorDataUnitStruc();
			healthyMonitorDataUnitStruc.setUserId(ejlHealthHeartRate.getUserId());
			healthyMonitorDataUnitStruc.setStartTime(ejlHealthHeartRate.getToTime());	//用结束时间
			HealthyMonitorDataHeart healthyMonitorDataHeart = new HealthyMonitorDataHeart();
			healthyMonitorDataHeart.setRate(ejlHealthHeartRate.getRate());
			healthyMonitorDataHeart.setTimeSpan(ejlHealthHeartRate.getTimeSpan());
			healthyMonitorDataUnitStruc.setContent(healthyMonitorDataHeart);
			unitDatas.add(healthyMonitorDataUnitStruc);
		}
		
		pageRequest = new PageRequest<QueryMonitorDataRequest>(1,
				Integer.MAX_VALUE);
		request.setSortColumns("create_time DESC");
		pageRequest.setSearchDo(request);
		List<EjlHealthHeartRate> listAll = ejlHealthHeartRateDao.getEjlHealthHeartRatesByUserAndDate(pageRequest);
		if(listAll!=null&&listAll.size()>0){
			List<Long> allRateList  = new ArrayList<Long>();
			Long allRate= 0l;
			for(EjlHealthHeartRate ejlHealthHeartRate:listAll){
				allRateList.add(ejlHealthHeartRate.getRate());
				allRate = allRate+ejlHealthHeartRate.getRate();
			}
			Collections.sort(allRateList);
			result.setTopValue(allRateList.get(allRateList.size()-1));
			result.setBottomValue(allRateList.get(0));
			result.setMiddleValue(allRate/allRateList.size());
		}
		return result;
	}
	
	
	private void putWalkDateMapFromHealthStepCount(Map<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> map,HealthyMonitorDataUnitStruc key,EjlUser user,EjlHealthStepCount ejlHealthStepCount ){
		if (map.containsKey(key)) {
			
			HealthyMonitorDataSteps steps = (HealthyMonitorDataSteps) map.get(key).getContent();
			steps.setSteps(steps.getSteps() + ejlHealthStepCount.getSteps());
			//steps.setCalorie(steps.getCalorie()+ejlHealthStepCount.getCalorie());
			if(user.getHeight()!=null){
			steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps()).longValue());
			}

			if (user.getHeight() != null) {
				steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps()).longValue());
				if (user.getWeight() != null) {
					steps.setCalorie(FormulaUtils.getCalorie(user.getWeight(),
									(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps())))
									.longValue());
				}
			}
		} else {
			HealthyMonitorDataUnitStruc value = new HealthyMonitorDataUnitStruc();
			value.setStartTime(key.getStartTime());
			value.setEndTime(key.getEndTime());
			value.setUserId(user.getId());
			HealthyMonitorDataSteps steps = new HealthyMonitorDataSteps();
			steps.setSteps(ejlHealthStepCount.getSteps());
			//steps.setCalorie(ejlHealthStepCount.getCalorie());
			if(user.getHeight()!=null){
				steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps()).longValue());
			}

			if (user.getHeight() != null) {
				steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps())
						.longValue());
				if (user.getWeight() != null) {
					steps.setCalorie(FormulaUtils.getCalorie(user.getWeight(),
							(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps())))
							.longValue());
				}
			}
			value.setContent(steps);
			map.put(key, value);
		}
	}
	
	private void putClimbingDateMapFromHealthStepCount(Map<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> map,HealthyMonitorDataUnitStruc key,EjlUser user,EjlHealthStepCount ejlHealthStepCount ){
		if(map.containsKey(key)){
			HealthyMonitorDataClimbing climbing = (HealthyMonitorDataClimbing)map.get(key).getContent();
			climbing.setHeight(climbing.getHeight()+(ejlHealthStepCount.getHeight()==null?0:ejlHealthStepCount.getHeight()));
			climbing.setSteps(climbing.getSteps()+ejlHealthStepCount.getSteps());
			//climbing.setCalorie(climbing.getCalorie()+ejlHealthStepCount.getCalorie());
			climbing.setEndTime(DateUtils.convertDate2Long(ejlHealthStepCount.getEndtime()));
			if (user.getHeight() != null) {
				if (user.getWeight() != null) {
					climbing.setCalorie(FormulaUtils.getCalorie(user.getWeight(),
									(FormulaUtils.getMileageBySteps(user.getHeight(), climbing.getHeight()==null?0:climbing.getHeight()/100)))
									.longValue()*10);
				}
			}
		}else{
			HealthyMonitorDataUnitStruc value = new HealthyMonitorDataUnitStruc();
			value.setStartTime(key.getStartTime());
			value.setEndTime(key.getEndTime());
			value.setUserId(user.getId());
			HealthyMonitorDataClimbing climbing = new HealthyMonitorDataClimbing();
			//climbing.setCalorie(ejlHealthStepCount.getCalorie());
			climbing.setHeight(ejlHealthStepCount.getHeight()==null?0:ejlHealthStepCount.getHeight());
			climbing.setSteps(ejlHealthStepCount.getSteps());
			climbing.setStartTime(DateUtils.convertDate2Long(ejlHealthStepCount.getBegintime()));
			climbing.setEndTime(DateUtils.convertDate2Long(ejlHealthStepCount.getEndtime()));
			if (user.getHeight() != null) {
				if (user.getWeight() != null) {
					climbing.setCalorie(FormulaUtils.getCalorie(user.getWeight(),
									(FormulaUtils.getMileageBySteps(user.getHeight(), climbing.getHeight()==null?0:climbing.getHeight()/100)))
									.longValue()*10);
				}
			}
			value.setContent(climbing);
			map.put(key, value);
		}
	}
	
	
	private void putSwingOrCycleDateMapFromHealthStepCount(Map<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> map,HealthyMonitorDataUnitStruc key,EjlUser user,EjlHealthStepCount ejlHealthStepCount ){
		if(map.containsKey(key)){
			HealthyMonitorDataSwingOrCycle cycle = (HealthyMonitorDataSwingOrCycle)map.get(key).getContent();
			//cycle.setCalorie(cycle.getCalorie()+ejlHealthStepCount.getCalorie());
			cycle.setEndTime(DateUtils.convertDate2Long(ejlHealthStepCount.getEndtime()));
		
			cycle.setCalorie(cycle.getCalorie()+this.getCycleCalorie(ejlHealthStepCount.getUserId(), ejlHealthStepCount.getDeviceId(), ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime()).longValue());
			cycle.setMileage(cycle.getMileage() + this.getCycleDistance(ejlHealthStepCount.getUserId(), ejlHealthStepCount.getDeviceId(), ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime()).longValue());
		}else{
			HealthyMonitorDataUnitStruc value = new HealthyMonitorDataUnitStruc();
			value.setStartTime(key.getStartTime());
			value.setEndTime(key.getEndTime());
			value.setUserId(user.getId());
			HealthyMonitorDataSwingOrCycle cycle  = new HealthyMonitorDataSwingOrCycle();
			cycle.setStartTime(DateUtils.convertDate2Long(ejlHealthStepCount.getBegintime()));
			cycle.setEndTime(DateUtils.convertDate2Long(ejlHealthStepCount.getEndtime()));
			//cycle.setCalorie(ejlHealthStepCount.getCalorie());

			cycle.setCalorie(this.getCycleCalorie(ejlHealthStepCount.getUserId(), ejlHealthStepCount.getDeviceId(), ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime()).longValue());
			cycle.setMileage(this.getCycleDistance(ejlHealthStepCount.getUserId(), ejlHealthStepCount.getDeviceId(), ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime()).longValue());
			value.setContent(cycle);
			map.put(key, value);
		}
		
	}
	/**
	* Title: getMonitorDataById
	* Description:
	* @param request
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlHealthManageService#getMonitorDataById(com.winterframework.efamily.server.dto.QueryMonitorDataRequest) 
	*/
	@Override
	public HealthyMonitorDataStruc getMonitorDataById(QueryMonitorDataRequest request) throws Exception {
		Date startDate = DateUtils.convertLong2Date(request.getStartDateTime());
		Date endDate = DateUtils.convertLong2Date(request.getEndDateTime());
		PageRequest<QueryMonitorDataRequest> pageRequest = new PageRequest<QueryMonitorDataRequest>(1,
				Integer.MAX_VALUE);
		boolean isPage = false;
		/*if(request.getMonitorDataType().intValue()==1 ||request.getMonitorDataType().intValue()==2){
			isPage = false;
		}else{
			pageRequest = new PageRequest<QueryMonitorDataRequest>(request.getCurrentPage(),
					request.getPerPageSize());
		}*/
		request.setStartQueryTime(startDate);
		request.setEndQueryTime(endDate);
		request.setSortColumns("begintime");
		pageRequest.setSearchDo(request);

		List<HealthyMonitorDataUnitStruc> unitDatas = new ArrayList<HealthyMonitorDataUnitStruc>();
		HealthyMonitorDataStruc result = new HealthyMonitorDataStruc();
		result.setUnitDatas(unitDatas);
		
		Map<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> map = new HashMap<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc>();
		List<List<Date>> dateSplit = new ArrayList<List<Date>>();
		dateSplit = this.dateSplit(startDate, endDate, request.getDateType());
		List<EjlHealthStepCount> list = ejlHealthStepCountDao.getEjlHealthStepCountsByUserAndDate(pageRequest,isPage);
		long totalTime = 0l;

			EjlUser user = ejlUserDaoImpl.getUserByUserId(request.getUserId());

			for (EjlHealthStepCount ejlHealthStepCount : list) {
				totalTime += DateUtils.calcSecondsBetween(ejlHealthStepCount.getBegintime(), ejlHealthStepCount.getEndtime());
				HealthyMonitorDataUnitStruc key = new HealthyMonitorDataUnitStruc();
				for (List<Date> dateList : dateSplit) {
					if (ejlHealthStepCount.getBegintime().compareTo(dateList.get(0)) >= 0
							&& ejlHealthStepCount.getBegintime().compareTo(DateUtils.addSeconds(dateList.get(1), 1)) <= 0) {
						key.setStartTime(DateUtils.convertDate2Long(dateList.get(0)));
						key.setEndTime(DateUtils.convertDate2Long(dateList.get(1)));
						break;
					}
				}
				
					//散步和跑步
					if(request.getMonitorDataType().intValue()==1){
						if (key.getStartTime() != null) {
						this.putWalkDateMapFromHealthStepCount(map, key, user, ejlHealthStepCount);}
					}else if(request.getMonitorDataType().intValue()==2){
						if( request.getDateType().intValue()==1){
							key = new HealthyMonitorDataUnitStruc();
							key.setStartTime(DateUtils.convertDate2Long(ejlHealthStepCount.getBegintime()));
							key.setEndTime(DateUtils.convertDate2Long(ejlHealthStepCount.getEndtime()));
							HealthyMonitorDataUnitStruc value = new HealthyMonitorDataUnitStruc();
							value.setStartTime(key.getStartTime());
							value.setEndTime(key.getEndTime());
							value.setUserId(user.getId());
							HealthyMonitorDataSteps steps = new HealthyMonitorDataSteps();
							steps.setSteps(ejlHealthStepCount.getSteps());
							/*if(ejlHealthStepCount.getCalorie() != null&&ejlHealthStepCount.getCalorie().longValue()!=0l){
								steps.setCalorie(ejlHealthStepCount.getCalorie());
								if(user.getHeight()!=null){
									steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps()).longValue());
								}
							}else*/
							if (user.getHeight() != null) {
								steps.setMileage(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps())
										.longValue());
								if (user.getWeight() != null) {
									steps.setCalorie(FormulaUtils.getCalorie(user.getWeight(),
											(FormulaUtils.getMileageBySteps(user.getHeight(), steps.getSteps())))
											.longValue());
								}
							}
							value.setContent(steps);
							unitDatas.add(value);
						}else{
							if (key.getStartTime() != null) {
							this.putWalkDateMapFromHealthStepCount(map, key, user, ejlHealthStepCount);}
						}
					}
					//登山
					else if(request.getMonitorDataType().intValue() == 3){
						if(key.getStartTime() != null){
							this.putClimbingDateMapFromHealthStepCount(map , key, user, ejlHealthStepCount);
						}
					}else if(request.getMonitorDataType().intValue()==4||request.getMonitorDataType().intValue()==5){
						if(key.getStartTime() != null){
							this.putSwingOrCycleDateMapFromHealthStepCount(map , key, user, ejlHealthStepCount);
						}
					}
				}
			
			result.setTotalTime(totalTime/60);
			
			if(!(request.getMonitorDataType().intValue()==2&&request.getDateType().intValue()==1)){
			for (List<Date> dateList : dateSplit) {
				boolean isExist = false;
				for (Entry<HealthyMonitorDataUnitStruc, HealthyMonitorDataUnitStruc> entry : map.entrySet()) {
					if (entry.getKey().getStartTime().longValue() == DateUtils.convertDate2Long(dateList.get(0))) {
						if(request.getMonitorDataType().intValue()==3){
							HealthyMonitorDataClimbing healthyMonitorDataClimbing=(HealthyMonitorDataClimbing)entry.getValue().getContent();
							healthyMonitorDataClimbing.setHeight(healthyMonitorDataClimbing.getHeight()==null?0:healthyMonitorDataClimbing.getHeight()/100);
						}
						unitDatas.add(entry.getValue());
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					HealthyMonitorDataUnitStruc value = new HealthyMonitorDataUnitStruc();
					value.setStartTime(DateUtils.convertDate2Long(dateList.get(0)));
					value.setEndTime(DateUtils.convertDate2Long(dateList.get(1)));
					value.setUserId(request.getUserId());
					if(request.getMonitorDataType().intValue()==1||request.getMonitorDataType().intValue()==2){
						HealthyMonitorDataSteps steps = new HealthyMonitorDataSteps();
						steps.setSteps(0l);
						steps.setCalorie(0l);
						steps.setMileage(0l);
						value.setContent(steps);
						unitDatas.add(value);
					}else if(request.getMonitorDataType().intValue()==3){
						HealthyMonitorDataClimbing climbing = new HealthyMonitorDataClimbing();
						climbing.setCalorie(0l);
						climbing.setHeight(0l);
						climbing.setSteps(0l);
						climbing.setStartTime(0l);
						climbing.setEndTime(0l);
						value.setContent(climbing);
						unitDatas.add(value);
					}else if(request.getMonitorDataType().intValue()==4||request.getMonitorDataType().intValue()==5){
						HealthyMonitorDataSwingOrCycle cycle  = new HealthyMonitorDataSwingOrCycle();
						cycle.setCalorie(0l);
						cycle.setStartTime(0l);
						cycle.setEndTime(0l);
						cycle.setMileage(0l);
						value.setContent(cycle);
						unitDatas.add(value);
					}
				}
			}}
		return result;
	}

	private List<List<Date>> dateSplit(Date startDate, Date endDate, int dateType) throws Exception {
		List<List<Date>> splitList = new ArrayList<List<Date>>();
		Date begin = startDate;
		if (dateType == 1) {
			while (begin.compareTo(endDate) < 0) {
				List<Date> list = new ArrayList<Date>();
				list.add(begin);
				list.add(DateUtils.addSeconds(DateUtils.addHours(begin, 1), -1));
				begin = DateUtils.addHours(begin, 1);
				splitList.add(list);
			}
		} else if (dateType == 2) {
			while (begin.compareTo(endDate) < 0) {
				List<Date> list = new ArrayList<Date>();
				list.add(begin);
				list.add(DateUtils.addSeconds(DateUtils.addDays(begin, 1),-1));
				begin = DateUtils.addDays(begin, 1);
				splitList.add(list);
			}
		} else if (dateType == 3) {
			while (begin.compareTo(endDate) < 0) {
				List<Date> list = new ArrayList<Date>();
				list.add(begin);
				begin = DateUtils.addDays(begin, 7);
				if(begin.compareTo(endDate) >= 0){
					list.add(endDate);
					splitList.add(list);
					break;
				}else{
					list.add(DateUtils.addSeconds(begin,-1));
					splitList.add(list);
				}
				
				
			}
		} else if (dateType == 4) {
			while (begin.compareTo(endDate) < 0) {
				List<Date> list = new ArrayList<Date>();
				list.add(begin);
				list.add(DateUtils.addSeconds(DateUtils.addMonths(begin, 1),-1));
				begin = DateUtils.addMonths(begin, 1);
				splitList.add(list);
			}
		}
		return splitList;
	}

	/**
	* Title: getEntityDao
	* Description:
	* @return 
	* @see com.winterframework.efamily.base.BaseServiceImpl#getEntityDao() 
	*/
	@Override
	protected IEjlHealthStepCountDao getEntityDao() {
		return ejlHealthStepCountDao;
	}

	/**
	* Title: getAllDayStepsByUser
	* Description:
	* @param userId
	* @param queryDate
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlHealthManageService#getAllDayStepsByUser(java.lang.Long, java.util.Date) 
	*/
	@Override
	public Long getAllDayStepsByUser(Long userId, Date queryDate) throws BizException {
		return null;
	}
	
	public  Double getWakeTime(EjlHealthSleep ejlHealthSleep){
		BigDecimal wakeTime = new BigDecimal(0.000000000) ;
		List<HealthyMonitorDataWake> wakeList = ejlHealthSleep.getWakes();
		for(HealthyMonitorDataWake healthyMonitorDataWake:wakeList){
			HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
			dateSleep.setType(2l);
			dateSleep.setStartTime(healthyMonitorDataWake.getFromTimeWake());
			dateSleep.setEndTime(healthyMonitorDataWake.getToTimeWake());
			dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataWake.getFromTimeWake()), DateUtils.convertLong2Date(healthyMonitorDataWake.getToTimeWake())));
			wakeTime=wakeTime.add(new BigDecimal(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataWake.getFromTimeWake()), DateUtils.convertLong2Date(healthyMonitorDataWake.getToTimeWake()))));
		}
		return wakeTime.doubleValue();//Double.valueOf(new java.text.DecimalFormat("#.0").format(wakeTime));
	}
	
	
	
	
	public  Double getDeepTime(EjlHealthSleep ejlHealthSleep){
		BigDecimal deepTime = new BigDecimal(0.0000000000);
		for(HealthyMonitorDataDeep healthyMonitorDataDeep:ejlHealthSleep.getDeeps()){
			HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
			dateSleep.setType(1l);
			dateSleep.setStartTime(healthyMonitorDataDeep.getFromTimeDeep());
			dateSleep.setEndTime(healthyMonitorDataDeep.getToTimeDeep());
			dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataDeep.getFromTimeDeep()), DateUtils.convertLong2Date(healthyMonitorDataDeep.getToTimeDeep())));
			deepTime =deepTime.add(new BigDecimal(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataDeep.getFromTimeDeep()), DateUtils.convertLong2Date(healthyMonitorDataDeep.getToTimeDeep()))));
		}
		return deepTime.doubleValue();//Double.valueOf(new java.text.DecimalFormat("#.0").format(deepTime));
	}

	@Override
	public HealthyMonitorDateSleepResponse getMonitorDataSleepById(
			QueryMonitorDataRequest request) throws Exception {
		HealthyMonitorDateSleepResponse response = new HealthyMonitorDateSleepResponse();
		Date startDate =DateUtils.convertLong2Date(request.getStartDateTime());
		Date endDate = DateUtils.convertLong2Date(request.getEndDateTime());
		EjlHealthSleep ejlHealthSleepQuery = new EjlHealthSleep();
		ejlHealthSleepQuery.setUserId(request.getUserId());
		ejlHealthSleepQuery.setDeviceId(request.getDeviceId());
		ejlHealthSleepQuery.setFromTime(startDate);
		ejlHealthSleepQuery.setToTime(endDate);
		List<EjlHealthSleep> list = ejlComHealthSleepDaoImpl.getSleepsByAttribute(ejlHealthSleepQuery);
		if(!list.isEmpty()){
			response.setStartTime(DateUtils.convertDate2Long(list.get(0).getFromTime()));
			response.setEndTime(DateUtils.convertDate2Long(list.get(list.size()-1).getToTime()));
			response.setTotalTime(DateUtils.calcHoursDouble(list.get(0).getFromTime() , list.get(list.size()-1).getToTime()));
		}else{
			response.setStartTime(0l);
			response.setEndTime(0l);
			response.setTotalTime(0d);
		}
		List<HealthyMonitorDateSleep> unitDatas = new ArrayList<HealthyMonitorDateSleep>();
		response.setUnitDatas(unitDatas);
		response.setDeepSleepTime(0d);
		response.setWakeSleepTime(0d);
		BigDecimal totalTime = new BigDecimal(0);
		if(request.getDateType().intValue() ==1){
			if(!list.isEmpty()){
				HealthyMonitorDateSleep last = null;
				for(EjlHealthSleep ejlHealthSleep : list){
					List<HealthyMonitorDateSleep> sleepList = new ArrayList<HealthyMonitorDateSleep>();
					List<HealthyMonitorDataDeep> deepList = ejlHealthSleep.getDeeps();
					List<HealthyMonitorDataWake> wakeList = ejlHealthSleep.getWakes();
					for(HealthyMonitorDataDeep healthyMonitorDataDeep:deepList){
						HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
						dateSleep.setType(1l);
						dateSleep.setStartTime(healthyMonitorDataDeep.getFromTimeDeep());
						dateSleep.setEndTime(healthyMonitorDataDeep.getToTimeDeep());
						dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataDeep.getFromTimeDeep()), DateUtils.convertLong2Date(healthyMonitorDataDeep.getToTimeDeep())));
						dateSleep.setTimeSpanDeep(0d);
						dateSleep.setTimeSpanWake(0d);
						//deepTime = deepTime.add(new BigDecimal(dateSleep.getTimeSpan()));
						sleepList.add(dateSleep);
					}
					for(HealthyMonitorDataWake healthyMonitorDataWake:wakeList){
						HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
						dateSleep.setType(3l);
						dateSleep.setStartTime(healthyMonitorDataWake.getFromTimeWake());
						dateSleep.setEndTime(healthyMonitorDataWake.getToTimeWake());
						dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(healthyMonitorDataWake.getFromTimeWake()), DateUtils.convertLong2Date(healthyMonitorDataWake.getToTimeWake())));
						dateSleep.setTimeSpanDeep(0d);
						dateSleep.setTimeSpanWake(0d);
						//wakeTime =wakeTime.add(new BigDecimal(dateSleep.getTimeSpan()));
						sleepList.add(dateSleep);
					}
					Collections.sort(sleepList);
					Date beginTime = ejlHealthSleep.getFromTime();
					Date endTime = ejlHealthSleep.getToTime();
					HealthyMonitorDateSleep  begin =sleepList.get(0);
					if(DateUtils.convertDate2Long(beginTime)<begin.getStartTime()){
						HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
						dateSleep.setStartTime(DateUtils.convertDate2Long(beginTime));
						dateSleep.setEndTime(begin.getStartTime());
						dateSleep.setType(2l);
						dateSleep.setTimeSpanDeep(0d);
						dateSleep.setTimeSpanWake(0d);
						dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
						unitDatas.add(dateSleep);
						begin = dateSleep;
					}
					
					for(int i=0;i<sleepList.size()-1;i++){
						HealthyMonitorDateSleep firstSleep = sleepList.get(i);
						unitDatas.add(firstSleep);
						HealthyMonitorDateSleep lastSleep = sleepList.get(i+1);
							if(firstSleep.getEndTime().longValue()<lastSleep.getStartTime().longValue()){
								HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
								dateSleep.setStartTime(firstSleep.getEndTime());
								dateSleep.setEndTime(lastSleep.getStartTime());
								dateSleep.setType(2l);
								dateSleep.setTimeSpanDeep(0d);
								dateSleep.setTimeSpanWake(0d);
								dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
								unitDatas.add(dateSleep);
							}
						
					}
					
					unitDatas.add(sleepList.get(sleepList.size()-1));
					HealthyMonitorDateSleep  end =sleepList.get(sleepList.size()-1);
					if(end.getEndTime().longValue()<DateUtils.convertDate2Long(endTime)){
						HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
						dateSleep.setStartTime(end.getEndTime());
						dateSleep.setEndTime(DateUtils.convertDate2Long(endTime));
						dateSleep.setType(2l);
						dateSleep.setTimeSpanDeep(0d);
						dateSleep.setTimeSpanWake(0d);
						dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
						unitDatas.add(dateSleep);
					}
					
					
					
					if(last !=null){
						if(last.getEndTime().longValue()<begin.getStartTime()){
							HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
							dateSleep.setStartTime(last.getEndTime().longValue());
							dateSleep.setEndTime(begin.getStartTime());
							dateSleep.setType(4l);
							dateSleep.setTimeSpanDeep(0d);
							dateSleep.setTimeSpanWake(0d);
							dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
							unitDatas.add(dateSleep);
						}
					}
					
					last = unitDatas.get(unitDatas.size()-1);
				}
				
				
				/*HealthyMonitorDateSleep  begin =sleepList.get(0);
				if(response.getStartTime().longValue()<begin.getStartTime()){
					HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
					dateSleep.setStartTime(response.getStartTime());
					dateSleep.setEndTime(begin.getStartTime());
					dateSleep.setType(2l);
					dateSleep.setTimeSpanDeep(0d);
					dateSleep.setTimeSpanWake(0d);
					dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
					unitDatas.add(dateSleep);
				}
				for(int i=0;i<sleepList.size()-1;i++){
					HealthyMonitorDateSleep firstSleep = sleepList.get(i);
					unitDatas.add(firstSleep);
					HealthyMonitorDateSleep lastSleep = sleepList.get(i+1);
						if(firstSleep.getEndTime().longValue()<lastSleep.getStartTime().longValue()){
							HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
							dateSleep.setStartTime(firstSleep.getEndTime());
							dateSleep.setEndTime(lastSleep.getStartTime());
							dateSleep.setType(2l);
							dateSleep.setTimeSpanDeep(0d);
							dateSleep.setTimeSpanWake(0d);
							dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
							unitDatas.add(dateSleep);
						}
					
				}
				unitDatas.add(sleepList.get(sleepList.size()-1));
				HealthyMonitorDateSleep  end =sleepList.get(sleepList.size()-1);
				if(end.getEndTime().longValue()<response.getEndTime().longValue()){
					HealthyMonitorDateSleep dateSleep = new HealthyMonitorDateSleep();
					dateSleep.setStartTime(end.getEndTime());
					dateSleep.setEndTime(response.getEndTime());
					dateSleep.setType(2l);
					dateSleep.setTimeSpanDeep(0d);
					dateSleep.setTimeSpanWake(0d);
					dateSleep.setTimeSpan(DateUtils.calcHoursDouble(DateUtils.convertLong2Date(dateSleep.getStartTime()),DateUtils.convertLong2Date(dateSleep.getEndTime())));
					unitDatas.add(dateSleep);
				}*/
				Collections.sort(unitDatas);
				BigDecimal deepTime =new BigDecimal(0);
				BigDecimal wakeTime =new BigDecimal(0);
				for(HealthyMonitorDateSleep dateSleep:unitDatas){
					if(dateSleep.getType().intValue()!=4){
						totalTime = totalTime.add(new BigDecimal(dateSleep.getTimeSpan()));
					}
					if(dateSleep.getType().intValue()==1){
						deepTime = deepTime.add(new BigDecimal(dateSleep.getTimeSpan()));
					}else if(dateSleep.getType().intValue()==2){
						wakeTime = wakeTime.add(new BigDecimal(dateSleep.getTimeSpan()));
					}
				}
				response.setDeepSleepTime(deepTime.doubleValue());
				response.setWakeSleepTime(wakeTime.doubleValue());
				response.setTotalTime(totalTime.doubleValue());
				//response.setUnitDatas(unitDatas);
			}
		}else{
			List<List<Date>> dateSplit = new ArrayList<List<Date>>();
			dateSplit = this.dateSplit(DateUtils.convertLong2Date(request.getStartDateTime()), DateUtils.convertLong2Date(request.getEndDateTime()), request.getDateType());
			Map<HealthyMonitorDateSleep, HealthyMonitorDateSleep> map = new HashMap<HealthyMonitorDateSleep, HealthyMonitorDateSleep>();
			if(!list.isEmpty()){
				for(EjlHealthSleep ejlHealthSleep : list){
					HealthyMonitorDateSleep key = new HealthyMonitorDateSleep();
					for (List<Date> dateList : dateSplit) {
						if (ejlHealthSleep.getFromTime().compareTo(dateList.get(0)) >= 0
								&& ejlHealthSleep.getFromTime().compareTo(DateUtils.addSeconds(dateList.get(1), 1)) <= 0) {
							key.setStartTime(DateUtils.convertDate2Long(dateList.get(0)));
							key.setEndTime(DateUtils.convertDate2Long(dateList.get(1)));
							break;
						}
					}
					if(key.getStartTime() != null){
						if (map.containsKey(key)) {
							HealthyMonitorDateSleep struc = (HealthyMonitorDateSleep)map.get(key);
							struc.setTimeSpanDeep(struc.getTimeSpanDeep()+this.getDeepTime(ejlHealthSleep));
							Double timeWake_ok = DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime())-this.getDeepTime(ejlHealthSleep)-this.getWakeTime(ejlHealthSleep);
							struc.setTimeSpanWake(struc.getTimeSpanWake()+timeWake_ok);
							struc.setTotalTime(struc.getTotalTime()+DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime()));
							
						} else {
							HealthyMonitorDateSleep struc = new HealthyMonitorDateSleep();
							struc.setStartTime(key.getStartTime());
							struc.setEndTime(key.getEndTime());
							struc.setTimeSpanDeep(this.getDeepTime(ejlHealthSleep));
							Double timeWake_ok = DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime())-this.getDeepTime(ejlHealthSleep)-this.getWakeTime(ejlHealthSleep);
							struc.setTimeSpanWake(timeWake_ok<0?0:timeWake_ok);
							struc.setTimeSpan(0d);
							struc.setType(0l);
							struc.setTotalTime(DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime()));
							map.put(key, struc);
						}
					}
				}
			}
			BigDecimal deepSleepTime =new BigDecimal(0);
			BigDecimal wakeSleepTime =new BigDecimal(0);
			for (List<Date> dateList : dateSplit) {
				boolean isExist = false;
				for (Entry<HealthyMonitorDateSleep, HealthyMonitorDateSleep> entry : map.entrySet()) {
					if (entry.getKey().getStartTime().longValue() == DateUtils.convertDate2Long(dateList.get(0))) {
						unitDatas.add(entry.getValue());
						deepSleepTime = deepSleepTime.add(new BigDecimal(entry.getValue().getTimeSpanDeep()));
						wakeSleepTime = wakeSleepTime.add(new BigDecimal(entry.getValue().getTimeSpanWake()));
						//entry.getValue().setTotalTime(Double.valueOf(new java.text.DecimalFormat("#.0").format(entry.getValue().getTotalTime())));
						//entry.getValue().setTimeSpanDeep(Double.valueOf(new java.text.DecimalFormat("#.0").format(entry.getValue().getTimeSpanDeep())));
						//entry.getValue().setTimeSpanWake(Double.valueOf(new java.text.DecimalFormat("#.0").format(entry.getValue().getTimeSpanWake())));
						totalTime =totalTime.add(new BigDecimal(entry.getValue().getTotalTime()));
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					HealthyMonitorDateSleep value = new HealthyMonitorDateSleep();
					value.setStartTime(DateUtils.convertDate2Long(dateList.get(0)));
					value.setEndTime(DateUtils.convertDate2Long(dateList.get(1)));
					value.setTimeSpanDeep(0d);
					value.setTimeSpanWake(0d);
					value.setTimeSpan(0d);
					value.setType(0l);
					value.setTotalTime(0d);
					unitDatas.add(value);
				}
			}
			//response.setUnitDatas(unitDatas);
			response.setDeepSleepTime(deepSleepTime.doubleValue());
			response.setWakeSleepTime(wakeSleepTime.doubleValue());
			response.setTotalTime(totalTime.doubleValue());
		}
		response.setStartTime(request.getStartDateTime());
		response.setEndTime(request.getEndDateTime());
		return response;
	}
	
	private Double getCycleDistance(Long userId,Long deviceId,Date beginTime,Date endTime){
		List<LocationStruc> list = ejlLocationServiceImpl.getUserLocationBetweenTime(userId, deviceId, beginTime, endTime);
		Double distance =0d;
		try{
			if(list != null&&list.size() >0){
				for(int i=0;i<list.size()-1;i++){
					LocationStruc begin = list.get(i);
					LocationStruc end = list.get(i+1);
					distance +=FormulaUtils.Distance(Double.valueOf((begin.getLocation().split(",")[1])),Double.valueOf((begin.getLocation().split(",")[0])),
							Double.valueOf((end.getLocation().split(",")[1])),Double.valueOf((end.getLocation().split(",")[0])));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return distance;
	}
	
	private Double getCycleCalorie(Long userId,Long deviceId,Date beginTime,Date endTime) {
		try{
			EjlUser user = ejlUserDaoImpl.getUserByUserId(userId);
			Double distance = this.getCycleDistance(userId, deviceId, beginTime, endTime);
			if(user.getWeight() == null){
				return 0d;
			}else{
				return FormulaUtils.getCalorie(user.getWeight(),distance);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0d;
	}
	
}
