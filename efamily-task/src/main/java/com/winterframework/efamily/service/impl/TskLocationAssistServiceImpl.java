/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.Separator;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfLocationAssist;
import com.winterframework.efamily.entity.EfLocationSemi;
import com.winterframework.efamily.entity.EjlLocation;
import com.winterframework.efamily.entity.LatLng;
import com.winterframework.efamily.service.IEjlComLocationService;
import com.winterframework.efamily.service.ITskLocationAssistService;
import com.winterframework.efamily.service.ITskLocationSemiService;
import com.winterframework.efamily.utils.LocationUtil;



/**
 * 定位辅助服务
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
@Service("tskLocationAssistServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class TskLocationAssistServiceImpl extends EfLocationAssistServiceImpl implements ITskLocationAssistService {
	@Resource(name = "tskLocationSemiServiceImpl")
	private ITskLocationSemiService tskLocationSemiService;
	@Resource(name = "ejlComLocationServiceImpl")
	private IEjlComLocationService ejlComLocationService;
	
	
	
	
}
