/**   
* @Title: IEjlHealthManageService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-5 下午5:01:35 
* @version V1.0   
*/
package com.winterframework.efamily.service;


import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlHealthHeartRate;

/** 
* @ClassName: IEjlHealthManageService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午5:01:35 
*  
*/
public interface IEjlComHealthHeartRateService extends IBaseService<EjlHealthHeartRate>{

	public int update(Context ctx,EjlHealthHeartRate entity) throws BizException;
	
	public List<EjlHealthHeartRate> getUserHeartRateByTime(Long userId, Long fromTime,Long endTime);
	
}
