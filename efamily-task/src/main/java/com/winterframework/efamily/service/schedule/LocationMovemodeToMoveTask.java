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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.dao.IEfComLocationMovemodeDao;
import com.winterframework.efamily.entity.EfLocationMovemode;
import com.winterframework.efamily.service.IEfLocationMovemodeService;


/** 
* @ClassName: LocationMovemodeToMoveTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LocationMovemodeToMoveTask {

	private Logger log = LoggerFactory.getLogger(LocationMovemodeTask.class);

	@Resource(name = "efLocationMovemodeServiceImpl")
	private IEfLocationMovemodeService efLocationMovemodeServiceImpl;
	
	@Resource(name="efComLocationMovemodeDaoImpl")
	private IEfComLocationMovemodeDao dao;
	
	public void execute() throws Exception {
		log.debug("LocationMovemodeToMoveTask start");
		try{
			doExecute();
		}catch(Exception e){
			log.error("LocationMovemodeToMoveTask failed.",e);
		}
		log.debug("LocationMovemodeToMoveTask end");
	}

	public void doExecute() throws Exception {
		List<EfLocationMovemode> list = dao.getStilDeviceList();
		if(list!=null){
			for(EfLocationMovemode efLocationMovemode :list){
				try{
				efLocationMovemodeServiceImpl.doMovement(efLocationMovemode);
				}catch(Exception e){
					log.error("to move error"+efLocationMovemode.getDeviceId(), e);
				}
			}
		}
	}
	
	
}
