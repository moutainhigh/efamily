package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.UpdateOperateLogRequest;
import com.winterframework.efamily.entity.EfCommandInfo;
import com.winterframework.efamily.entity.EfOperLog;
import com.winterframework.efamily.service.IEfComCommandInfoService;
import com.winterframework.efamily.service.IEfOperLogService;


	/**
	 * 
	* @ClassName: UpdateOperateLogHandler 
	* @Description: TODO(记录操作日志) 
	* @author jason 
	* @date 2015-6-24 下午1:59:48 
	*
	 */
	@Controller("updateOperateLogController")
	@RequestMapping("/server")
	public class UpdateOperateLogController  {
		//private static final Logger logger = LoggerFactory.getLogger(UpdateOperateLogController.class);
		
		@Resource(name="efOperLogServiceImpl")
		private IEfOperLogService efOperLogServiceImpl;
		
		@Resource(name="efComCommandInfoServiceImpl")
		private IEfComCommandInfoService efComCommandInfoServiceImpl;
		
		@RequestMapping("/updateOperateLog")
		@ResponseBody
		protected Response<UpdateOperateLogRequest> doHandle(@RequestBody Request<UpdateOperateLogRequest> request) throws Exception {
			Response<UpdateOperateLogRequest> fmlResponse = new Response<UpdateOperateLogRequest>(request);
			UpdateOperateLogRequest createChatGroupResponse = new UpdateOperateLogRequest();
			
			Long userId = request.getData().getUserId();
			Long deviceId = request.getData().getDeviceId();
			Long time = request.getData().getTime();
			String command = request.getData().getCommand();
			String data = request.getData().getData();
			String operation = command;
			EfCommandInfo commandInfo = new EfCommandInfo();
			commandInfo.setCommand(command);
			commandInfo = efComCommandInfoServiceImpl.selectOneObjByAttribute(request.getCtx(), commandInfo);
			if(commandInfo!=null){
				operation = commandInfo.getOperation();
			}
			String commandSwitch = efOperLogServiceImpl.switchCommand(command, data);
			EfOperLog efOperLog = new EfOperLog();
			efOperLog.setUserId(userId);
			efOperLog.setDeviceId(deviceId);
			efOperLog.setTime(DateUtils.getDate(time));
			efOperLog.setRemark(data);
			efOperLog.setCommand(commandSwitch);
			efOperLog.setOperation(operation);
			efOperLogServiceImpl.save(request.getCtx(), efOperLog);
			
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(createChatGroupResponse);
			
			return fmlResponse;
		}
	}
