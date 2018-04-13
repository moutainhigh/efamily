package com.winterframework.efamily.service.impl;

import com.winterframework.efamily.enums.DeviceOperation;
import com.winterframework.modules.utils.SpringContextHolder;

public class DeviceOperationStrategyFactory {
	 
	public static AbstractDeviceOperationStrategy getInstance(int operation){
		if(operation==DeviceOperation.CHARGE.getCode()){
			return SpringContextHolder.getBean("deviceOperationChargeStrategy");
		}else if (operation==DeviceOperation.ONFF.getCode()){
			return SpringContextHolder.getBean("deviceOperationOnffStrategy");
		}else if (operation==DeviceOperation.SOS.getCode()){
			return SpringContextHolder.getBean("deviceOperationSosStrategy");
		}else if (operation==DeviceOperation.LOCATION.getCode()){
			return SpringContextHolder.getBean("deviceOperationLocationStrategy");
		}else if (operation==DeviceOperation.RESET.getCode()){
			return SpringContextHolder.getBean("deviceOperationResetStrategy");
		}else if(operation==DeviceOperation.SOFTWARE.getCode()){
			return SpringContextHolder.getBean("deviceOperationSoftwareStrategy");
		}else{
			return null;
		}
	}
}
