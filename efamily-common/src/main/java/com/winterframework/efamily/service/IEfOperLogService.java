package com.winterframework.efamily.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EfOperLog;

public interface IEfOperLogService extends IBaseService<EfOperLog> { 
	
	/**
	 * 功能：转换command
	 * @param command
	 * @param data
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String switchCommand(String command,String data) throws JsonParseException, JsonMappingException, IOException;
}
