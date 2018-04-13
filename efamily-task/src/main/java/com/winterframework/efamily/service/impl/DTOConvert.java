/**   
* @Title: DTOConvert.java 
* @Package com.winterframework.efamily.server.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-10 下午3:27:32 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.RemindStruc;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUser;

/** 
* @ClassName: DTOConvert 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-10 下午3:27:32 
*  
*/
public class DTOConvert {

	public static RemindStruc convertEntityToRemindStruc(EjlRemind entity,String userName) {
		if (entity != null) {
			RemindStruc struc = new RemindStruc();
			struc.setRemindId(entity.getId());
			struc.setDeliverDeadTime(DateUtils.convertDate2Long(entity.getSendTimeEnd()));
			struc.setDeliverTime(DateUtils.convertDate2Long(entity.getSendTime()));
			struc.setDeliverUserId(entity.getUserId());
			struc.setRemindContent(entity.getContent());
			struc.setRemindName(entity.getName());
			struc.setRemindRepeatType(entity.getSendPeriod());
			struc.setRemindType(entity.getSendMethod());
			struc.setDurationTime(entity.getDurationTime()==null?0l:entity.getDurationTime());
			struc.setDeliverUserName(userName);
			return struc;
		}
		return null;
	}
}
