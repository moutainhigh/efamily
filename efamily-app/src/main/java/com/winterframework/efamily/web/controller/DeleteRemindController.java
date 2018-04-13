package com.winterframework.efamily.web.controller;

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
import com.winterframework.efamily.dto.DeleteRemindRequest;
import com.winterframework.efamily.dto.DeleteRemindResponse;
import com.winterframework.efamily.service.IEjlRemindService;

/**
 * 获取家庭用户健康列表handler
 * @author floy
 *
 */
@Controller("deleteRemindController")
@RequestMapping("/server")
public class DeleteRemindController {

	@Resource(name = "ejlRemindServiceImpl")
	private IEjlRemindService ejlRemindServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(DeleteRemindController.class);
	
	@RequestMapping("/deleteRemind")
	@ResponseBody
	protected Response<DeleteRemindResponse> doHandle(@RequestBody Request<DeleteRemindRequest> request) throws BizException {
		Response<DeleteRemindResponse> fmlResponse = new Response<DeleteRemindResponse>(request);
		DeleteRemindResponse createChatGroupResponse = new DeleteRemindResponse();
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		if(request.getData().getRemindId()!=null){
		ejlRemindServiceImpl.deleteById(request.getCtx(),request.getData().getRemindId(),
				request.getData().getType());
		}else if(request.getData().getRemindIds() != null){
			for(Long id:request.getData().getRemindIds()){
				ejlRemindServiceImpl.deleteById(request.getCtx(),id,
						request.getData().getType());
			}
		}
		fmlResponse.setData(createChatGroupResponse);
		return fmlResponse;
	}
}
