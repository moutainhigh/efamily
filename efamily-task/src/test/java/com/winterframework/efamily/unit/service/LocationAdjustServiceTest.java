package com.winterframework.efamily.unit.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.service.IEfLocationOriginService;
import com.winterframework.efamily.service.IEfLocationSemiService;
import com.winterframework.efamily.service.ITskLocationSemiServiceNew;
import com.winterframework.efamily.service.IWeatherService;
import com.winterframework.modules.test.BaseTestCase;

public class LocationAdjustServiceTest extends BaseTestCase {
	Logger log = LoggerFactory.getLogger(LocationAdjustServiceTest.class);

	@Resource(name = "tskLocationSemiServiceImplNew")
	private ITskLocationSemiServiceNew tskLocationSemiService;
	@Resource(name = "efLocationOriginServiceImpl")
	private IEfLocationOriginService efLocationOriginServiceImpl;
	@Resource(name = "efLocationSemiServiceImpl")
	private IEfLocationSemiService efLocationSemiServiceImpl;
	
	@Resource(name = "weatherServiceImpl")
	private IWeatherService weatherService; 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	
	@Test
	@Rollback(false)
	public void test() throws Exception { 
		//efLocationOriginServiceImpl.initLocationOrigin();
		//for(int i=0;i<13;i++){
		//	efLocationSemiServiceImpl.initLocationSemi();
		//}
		try{
		//tskLocationSemiService.doProcessOrigin();
		tskLocationSemiService.doProcessNew(); 
		}catch(Exception e){
			log.error("",e);
		}
		
		/*final int days=19;	//7天之内 根据实际有意义的轨迹要求(24小时)++天数
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		log.debug("process start.timeFrom="+timeFrom+ " timeTo="+timeTo);
		List<Map<String,Long>> deviceList=tskLocationSemiService.getNeedHandleDeviceList(timeFrom,timeTo);
		if(null!=deviceList){
			final Context ctx=new Context();
			ctx.set("userId", -1);
			for(Map<String,Long> device:deviceList){
				final Long userId=device.get("userId");
				final Long deviceId=device.get("deviceId"); 
				threadPool.execute(
						//new TestProcessor()
						new Runnable() {
					private ITskLocationSemiService tskLocationSemiService2=SpringContextHolder.getBean("tskLocationSemiServiceImpl");
					
					@Override
					public void run() { 
						try{
							System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
							log.error("AAAAAAAAAAAAAAAAAAAAAAAAAA");
							tskLocationSemiService2.process(ctx, userId, deviceId,timeFrom,timeTo);
							//process2(ctx, userId, deviceId,timeFrom,timeTo); 
						//	test(ctx, userId, deviceId,timeFrom,timeTo);
							System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA2");
						}catch(Exception e){
							log.error("定位处理出错.userId="+userId+" deviceId="+deviceId);
						}
					}
				}
						);
			}
		}*/
	}
	
	

}