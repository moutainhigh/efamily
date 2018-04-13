package com.winterframework.efamily.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.api.enums.ResultCode;
import com.winterframework.efamily.api.interceptor.annatation.ApiAuthAnnotation;
import com.winterframework.efamily.api.service.IApiBindService;
import com.winterframework.efamily.api.service.IApiDeviceService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfKey;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEfKeyService;
import com.winterframework.efamily.service.impl.EjlComLocationServiceImpl;
 

@Service("apiBindServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class ApiBindServiceImpl  extends EjlComLocationServiceImpl  implements IApiBindService{
	@Resource(name="efKeyServiceImpl")
	private IEfKeyService efKeyService;
	@Resource(name="apiDeviceServiceImpl")
	private IApiDeviceService apiDeviceService;
	
	@Override
	@ApiAuthAnnotation
	public List<EjlDevice> getBindListByKeyAndTime(Context ctx, Integer type,Date time) throws BizException {
		String key=(String)ctx.get("key");
		long days=DateUtils.calcDateBetween(time, DateUtils.currentDate());
		if(days>90){
			throw new BizException(ResultCode.TIME_TOO_LONG.getCode());
		}
		EfKey efKey=efKeyService.getByKey(key);
		if(null==efKey){
			throw new BizException(ResultCode.KEY_INVALID.getCode());
		}
		Long customerId=efKey.getCustomerId();
		Integer status=-1;
		if(type==1){
			status=EjlDevice.STATUS.BIND.getValue();
		}else{
			status=EjlDevice.STATUS.UNBIND.getValue();
		}
		return apiDeviceService.getListByCustomerIdAndStatusAndTime(ctx, customerId, status, time);
	}
	
	
}
