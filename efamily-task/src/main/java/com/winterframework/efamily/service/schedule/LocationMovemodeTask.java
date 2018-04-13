/**   
* @Title: LunarInitTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午1:41:20 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dao.ITskLocationSemiDao;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.service.IEfLocationMovemodeService;


/** 
* @ClassName: LocationMovemodeTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LocationMovemodeTask {

	private Logger log = LoggerFactory.getLogger(LocationMovemodeTask.class);

	@Resource(name = "efLocationMovemodeServiceImpl")
	private IEfLocationMovemodeService efLocationMovemodeService;
	
	@Resource(name="tskLocationSemiDaoImpl")
	private ITskLocationSemiDao tskLocationSemiDaoImpl;
	
	public void execute() throws Exception {
		log.debug("LocationMovemode start");
		
		efLocationMovemodeService.processMoveMode();
		//doExecute();
		
		log.debug("LocationMovemode end");
	}

	public void doExecute() throws Exception {
		final int days=1;
		final Date timeTo=DateUtils.addMinutes(DateUtils.currentDate(),-1);
		final Date timeFrom=DateUtils.addDays(timeTo, -1*days);
		List<Integer> flags=getValidFlags();
		List<Map<String,Long>> deviceIds = tskLocationSemiDaoImpl.getDeviceIdListByFlags(timeFrom, timeTo, flags);
		if(null!=deviceIds){
			for(Map<String,Long> device:deviceIds){
				try{
					efLocationMovemodeService.doGetMovemode(device);
				}catch(Exception e){
					log.error("save mode error deivceId:"+device.get("deviceId"), e);
				}
			}
		}
	}
	
	/**
	 * 获取有效点标识值
	 * @return
	 */
	protected List<Integer> getValidFlags(){
		List<Integer> flags=new ArrayList<Integer>();
		List<EfLocationSemi.Flag> sf=Arrays.asList(EfLocationSemi.Flag.values());
		for(EfLocationSemi.Flag f:sf){
			if(!f.equals(EfLocationSemi.Flag.DELETE) && !f.equals(EfLocationSemi.Flag.DISPOSE)&&f.getValue()>EfLocationSemi.Flag.SUSPECT.getValue()) {
				flags.add(f.getValue());
			}
		}
		return flags;
	}
	
}
