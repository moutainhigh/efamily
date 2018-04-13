/**   
* @Title: IEfComWeatherService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 下午4:12:51 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.util.Date;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfLunar;


/** 
* @ClassName: IEfComWeatherService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 下午4:12:51 
*  
*/
public interface IEfComLunarService extends IBaseService<EfLunar> {
	public EfLunar getEfLunarByDate(Date date) throws Exception;
	
	public void saveOne(EfLunar efLunar) throws Exception;
}
