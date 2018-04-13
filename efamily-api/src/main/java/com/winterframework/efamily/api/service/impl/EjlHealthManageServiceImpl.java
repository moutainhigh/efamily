package com.winterframework.efamily.api.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.winterframework.efamily.api.dao.IEjlHealthSleepDao;
import com.winterframework.efamily.api.dto.HealthyMonitorDateSleepResponse;
import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.service.IEjlHealthManageService;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.IEJLComHealthSleepDao;
import com.winterframework.efamily.dao.IEjlComDeviceDao;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlHealthSleep;
import com.winterframework.efamily.entity.HealthyMonitorDataDeep;
import com.winterframework.efamily.entity.HealthyMonitorDataWake;
import com.winterframework.efamily.enums.Precision;



/**
 * @ClassName: EjlHealthManageServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 你的名字
 * @date 2015-5-5 下午5:20:22
 * 
 */
@Service("ejlHealthManageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlHealthManageServiceImpl implements IEjlHealthManageService{
	protected Logger log = LoggerFactory.getLogger(EjlHealthManageServiceImpl.class);
	
	@Resource(name = "ejlComHealthSleepDaoImpl")
	private IEJLComHealthSleepDao ejlComHealthSleepDaoImpl;
	
	@Resource(name = "ejlComDeviceDaoImpl")
	private IEjlComDeviceDao ejlComDeviceDaoImpl;
	
	@Resource(name = "ejlHealthSleepDaoImpl")
	private IEjlHealthSleepDao ejlHealthSleepDaoImpl;
	
	
	public Double getWakeTime(EjlHealthSleep ejlHealthSleep) {
		BigDecimal wakeTime = new BigDecimal(0.000000000);
		List<HealthyMonitorDataWake> wakeList = ejlHealthSleep.getWakes();
		for (HealthyMonitorDataWake healthyMonitorDataWake : wakeList) {
			wakeTime = wakeTime.add(new BigDecimal(DateUtils.calcHoursDouble(
					DateUtils.convertLong2Date(healthyMonitorDataWake
							.getFromTimeWake()), DateUtils
							.convertLong2Date(healthyMonitorDataWake
									.getToTimeWake()))));
		}
		return wakeTime.doubleValue();// Double.valueOf(new
										// java.text.DecimalFormat("#.0").format(wakeTime));
	}

	public Double getDeepTime(EjlHealthSleep ejlHealthSleep) {
		BigDecimal deepTime = new BigDecimal(0.0000000000);
		for (HealthyMonitorDataDeep healthyMonitorDataDeep : ejlHealthSleep
				.getDeeps()) {
			deepTime = deepTime.add(new BigDecimal(DateUtils.calcHoursDouble(
					DateUtils.convertLong2Date(healthyMonitorDataDeep
							.getFromTimeDeep()), DateUtils
							.convertLong2Date(healthyMonitorDataDeep
									.getToTimeDeep()))));
		}
		return deepTime.doubleValue();// Double.valueOf(new
										// java.text.DecimalFormat("#.0").format(deepTime));
	}

	
	public List<HealthyMonitorDateSleepResponse> getMonitorDataSleepById(
			String imei,Long fromTime,Long toTime) throws Exception {
		EjlDevice  ejlDeivce = ejlComDeviceDaoImpl.getByImei(imei);
		if(null==ejlDeivce){
			throw new BizException(ResultCode.IMEI_INVALID.getCode(),new String[]{imei});
		}
		List<HealthyMonitorDateSleepResponse> resultList = new ArrayList<HealthyMonitorDateSleepResponse>();
		Date startDate = DateUtils.convertLong2Date(fromTime);
		Date endDate = DateUtils.convertLong2Date(toTime);
		EjlHealthSleep ejlHealthSleepQuery = new EjlHealthSleep();
		ejlHealthSleepQuery.setDeviceId(ejlDeivce.getId());
		ejlHealthSleepQuery.setUserId(ejlDeivce.getUserId());
		ejlHealthSleepQuery.setFromTime(startDate);
		ejlHealthSleepQuery.setToTime(endDate);
		List<EjlHealthSleep> list =null;
		try{
		list =  ejlHealthSleepDaoImpl.getSleepsByTime(ejlHealthSleepQuery);
		}catch(Exception e){
			log.error("",e);
			throw new BizException(ResultCode.DB_ERROR.getCode(),e);
		}
		if (!list.isEmpty()) {
			for(EjlHealthSleep ejlHealthSleep:list){
				HealthyMonitorDateSleepResponse result = convert2SleepResponse(ejlHealthSleep);
				resultList.add(result);
			}
			
		}
		return resultList;
	}

	@Override
	public HealthyMonitorDateSleepResponse getLastSleepBetweenTime(String imei) throws Exception {
		EjlDevice  ejlDeivce = ejlComDeviceDaoImpl.getByImei(imei);
		if(null==ejlDeivce){
			throw new BizException(ResultCode.IMEI_INVALID.getCode(),new String[]{imei});
		}
		EjlHealthSleep ejlHealthSleepQuery = new EjlHealthSleep();
		ejlHealthSleepQuery.setDeviceId(ejlDeivce.getId());
		ejlHealthSleepQuery.setUserId(ejlDeivce.getUserId());
		EjlHealthSleep ejlHealthSleep =null;
		HealthyMonitorDateSleepResponse result = null;
		try{
			ejlHealthSleep =  ejlComHealthSleepDaoImpl.getLastSleepByAttribute(ejlHealthSleepQuery);
		}catch(Exception e){
			log.error("",e);
			throw new BizException(ResultCode.DB_ERROR.getCode(),e);
		}
		if (ejlHealthSleep!=null) {
			result=convert2SleepResponse(ejlHealthSleep);
		}
		return result;
	}
	private HealthyMonitorDateSleepResponse convert2SleepResponse(
			EjlHealthSleep ejlHealthSleep) {
		HealthyMonitorDateSleepResponse result = new HealthyMonitorDateSleepResponse();
		result.setStartTime(DateUtils.convertDate2Long(ejlHealthSleep.getFromTime()));
		result.setEndTime(DateUtils.convertDate2Long(ejlHealthSleep.getToTime()));
		BigDecimal totalTime = new BigDecimal(DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime()));
		totalTime=processPrecision(totalTime);
		BigDecimal deepSleepTime = new BigDecimal(this.getDeepTime(ejlHealthSleep));
		deepSleepTime=processPrecision(deepSleepTime);
		BigDecimal wakeTime = new BigDecimal(this.getWakeTime(ejlHealthSleep));
		wakeTime=processPrecision(wakeTime);
		BigDecimal lightSleepTime=totalTime.subtract(deepSleepTime).subtract(wakeTime);
		lightSleepTime=processPrecision(lightSleepTime);
		//BigDecimal ligthTime = totalTime.subtract(deepSleepTime).subtract(wakeTime);//new BigDecimal(DateUtils.calcHoursDouble(ejlHealthSleep.getFromTime(), ejlHealthSleep.getToTime())).subtract(new BigDecimal(this.getDeepTime(ejlHealthSleep))).subtract(new BigDecimal(this.getWakeTime(ejlHealthSleep)));
		result.setDeepSleepTime(deepSleepTime.doubleValue());
		result.setWakeSleepTime(wakeTime.doubleValue());
		result.setTotalTime(totalTime.doubleValue());
		result.setLightSleepTime(lightSleepTime.doubleValue());
		return result;
	}
	
	
	private BigDecimal processPrecision(BigDecimal b){
		return b.setScale(Precision.HEALTH.getValue(), BigDecimal.ROUND_HALF_UP);
	}
	
}
