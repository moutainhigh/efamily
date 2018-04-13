/**   
* @Title: LunarInitTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午1:41:20 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;



import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.dao.IEfComLocationMovemodeDao;
import com.winterframework.efamily.entity.EfLocationMovemode;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEfLocationMovemodeService;
import com.winterframework.efamily.service.IEjlComDeviceService;


/** 
* @ClassName: LocationMovemodeToStillTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LocationMovemodeToStillTask {

	private Logger log = LoggerFactory.getLogger(LocationMovemodeTask.class);

	@Resource(name = "efLocationMovemodeServiceImpl")
	private IEfLocationMovemodeService efLocationMovemodeServiceImpl;
	
	@Resource(name="efComLocationMovemodeDaoImpl")
	private IEfComLocationMovemodeDao dao;
	
	public void execute(){
		log.debug("LocationMovemodeToStillTask start");
		try{
			doExecute();
		}catch(Exception e){
			log.error("LocationMovemodeToStillTask failed.",e);
		}
		log.debug("LocationMovemodeToStillTask end");
	}
	
	protected void doExecute()  throws Exception{
		List<EfLocationMovemode> list = dao.getMoveDeviceList();
		if(list!=null){
			for(EfLocationMovemode efLocationMovemode:list){
				try{
				efLocationMovemodeServiceImpl.doStill(efLocationMovemode);
				}catch(Exception e){
					log.error("to still error"+efLocationMovemode.getDeviceId(), e);
				}
			}
		}
	}
}
