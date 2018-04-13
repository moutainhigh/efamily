package com.winterframework.efamily.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEfOperLogDao;
import com.winterframework.efamily.dto.DeviceOperationRequest;
import com.winterframework.efamily.entity.EfOperLog;
import com.winterframework.efamily.service.IEfOperLogService;

@Service("efOperLogServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EfOperLogServiceImpl  extends BaseServiceImpl<IEfOperLogDao,EfOperLog> implements IEfOperLogService {
	@Resource(name="efOperLogDaoImpl")
	private IEfOperLogDao dao;
	@Override
	protected IEfOperLogDao getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public String switchCommand(String command,String data) throws JsonParseException, JsonMappingException, IOException{
		String commandResult=command;
		//设备操作协议COMMAND=28999 
		if("28999".equals(command)){
			ObjectMapper om=new ObjectMapper();
			DeviceOperationRequest deviceOperationInfo = om.readValue(data, DeviceOperationRequest.class); 
			commandResult = String.valueOf(deviceOperationInfo.getCode());
		}
		return commandResult;
	}
	
	
}
