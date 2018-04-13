package com.winterframework.efamily.web.controller;

import java.util.List;

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
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.GetUserBarrierListRequest;
import com.winterframework.efamily.dto.GetUserBarrierListResponse;
import com.winterframework.efamily.entity.UserBarrierStruc;
import com.winterframework.efamily.service.IEjlUserBarrierService;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("getUserBarrierListController")
@RequestMapping("/server")
public class GetUserBarrierListController {
	private static final Logger log = LoggerFactory.getLogger(GetUserBarrierListController.class);
	@Resource(name = "ejlUserBarrierServiceImpl")
	private IEjlUserBarrierService ejlUserBarrierServiceImpl;

	@RequestMapping("/getUserBarrierList")
	@ResponseBody
	protected Response<GetUserBarrierListResponse> doHandle(
			@RequestBody Request<GetUserBarrierListRequest> request)
			throws BizException {
		Response<GetUserBarrierListResponse> fmlResponse = new Response<GetUserBarrierListResponse>(request);
		GetUserBarrierListResponse getDeviceListResponse = new GetUserBarrierListResponse();
		List<UserBarrierStruc> userBarrierStrucList = ejlUserBarrierServiceImpl.getEjlUserBarrierList(request.data.getUserId());
		if(userBarrierStrucList!=null){
			getDeviceListResponse.setFenceList(JsonUtils.toJson(userBarrierStrucList));
		}else{
			getDeviceListResponse.setFenceList("");
		}
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(getDeviceListResponse);
		return fmlResponse;
	}
}
