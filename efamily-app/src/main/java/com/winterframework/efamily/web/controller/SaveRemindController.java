package com.winterframework.efamily.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.dto.SaveRemindRequest;
import com.winterframework.efamily.dto.SaveRemindResponse;
import com.winterframework.efamily.entity.EjlRemind;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlRemindService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 保存提醒信息handler
 * @author floy
 *
 */
@Controller("saveRemindController")
@RequestMapping("/server")
public class SaveRemindController {

	@Resource(name = "ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(SaveUserBaseInfoController.class);

	@RequestMapping("/saveRemind")
	@ResponseBody
	protected Response<SaveRemindResponse> doHandle(@RequestBody Request<SaveRemindRequest> request) throws BizException {
		Response<SaveRemindResponse> fmlResponse = new Response<SaveRemindResponse>(request);
		SaveRemindResponse saveRemindResponse = new SaveRemindResponse();
		
		Long userId = request.getUserId();
		EjlUser sendUser = ejlUserServiceImpl.getUserByUserId(request.getData().getDeliverUserId());
		EjlRemind entity = new EjlRemind();
		entity.setId(request.getData().getRemindId().longValue() !=-1 ? request.getData().getRemindId() : null);
		if (request.getData().getRemindText() != null) {
			entity.setContent(request.getData().getRemindText());
		} else {
			entity.setContent(request.getData().getContent());
		}
		entity.setDeleteStatus(0l);
		entity.setGmtCreated(new Date());
		entity.setName(request.getData().getRemindName()==null?"提醒":request.getData().getRemindName());
		entity.setSendMethod(request.getData().getRemindType());
		entity.setSendPeriod(request.getData().getRemindRepeatType());
		entity.setSendTime(request.getData().getDeliverTime() != null ? DateUtils.convertLong2Date(request.getData().getDeliverTime()) : null);
		entity.setSendTimeEnd(request.getData().getDeliverDeadTime() != null ? DateUtils.convertLong2Date(request.getData().getDeliverDeadTime()) : DateUtils.addYears(DateUtils.currentDate(), 30));
		entity.setSendType(sendUser.getType());
		entity.setSendUserId(request.getData().getDeliverUserId());
		entity.setSentStatus(0l);
		entity.setUserId(userId);
		entity.setRemindState(0l);
		entity.setDurationTime(request.getData().getDurationTime()==null?null:request.getData().getDurationTime());
		
		ejlRemindServiceImpl.saveOrUpdate(request.getCtx(),entity);
		fmlResponse.setData(saveRemindResponse);
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		return fmlResponse;
	}
}
