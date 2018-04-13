package com.winterframework.efamily.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.ManageAttentionRequest;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlAttentionUserService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlUserService;


@Controller("manageAttentionController")
@RequestMapping("/server")
public class ManageAttentionController  {
	private static final Logger logger = LoggerFactory.getLogger(ManageAttentionController.class);
	
	@Resource(name="ejlAttentionUserServiceImpl")
	private IEjlAttentionUserService ejlAttentionUserServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;
	
	@RequestMapping("/manageAttention")
	@ResponseBody
	protected Response<ManageAttentionRequest> doHandle(@RequestBody Request<ManageAttentionRequest> request) throws Exception {
		Response<ManageAttentionRequest> fmlResponse = new Response<ManageAttentionRequest>(request);
		ManageAttentionRequest createChatGroupResponse = new ManageAttentionRequest();
		
        if(request.getData().getUserId()==null||request.getData().getOprType()==null||request.getData().getDeviceCode()==null){
        	logger.error("管理关注时，参数不完整，操作失败。 userOperate = "+request.getUserId()+" ; " +
        			"; userId = "+request.getData().getUserId()+            			
        			"; oprType = "+request.getData().getOprType()+            			
        			"; deviceCode = "+request.getData().getDeviceCode());
        	throw new BizException(StatusBizCode.PARAM_INCOMPLETE.getValue());
		}
        Long userId = request.getData().getUserId();
		Long oprType = request.getData().getOprType();//请求的类型，1:“申请关注”，2:“同意关注”，3:“取消关注”
		String deviceCode = request.getData().getDeviceCode();

		EjlUser user = ejlUserServiceImpl.get(request.getUserId());
		
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		createChatGroupResponse.setOprType(oprType);
		fmlResponse.setData(createChatGroupResponse);
		//*** 关注设备操作
		if(ejlAttentionUserServiceImpl.checkAttentionDevice(request.getCtx(), userId, oprType, deviceCode, request.getUserId())){
		     ejlAttentionUserServiceImpl.attentionDevice(request.getCtx(), userId, oprType, deviceCode, request.getUserId());
		}
		EjlDevice deviceObj = new EjlDevice();
		deviceObj.setCode(deviceCode);
		deviceObj = ejlDeviceServiceImpl.selectOneObjByAttribute(request.getCtx(), deviceObj);
		EjlUser userDevice = ejlUserServiceImpl.get(deviceObj.getUserId());
      	Map<String, String> paramMap = new HashMap<String,String>();
      	paramMap.put("userId", String.valueOf(request.getUserId()));
      	paramMap.put("userName", user.getNickName());
      	paramMap.put("icon", user.getHeadImg());
      	paramMap.put("type", String.valueOf(oprType));
      	paramMap.put("deviceId", deviceObj.getId()+"");
      	paramMap.put("deviceCode", deviceCode);
      	paramMap.put("deviceNick", userDevice.getNickName());
      	paramMap.put("time", DateUtils.getCurTime()+"");
      	NoticeType noticeType = null;
      	if(EfamilyConstant.ATTENTION_OPRTYPE_AGREE == Long.valueOf(oprType) ){
      		noticeType = NoticeType.ATTENTION_AGREE;
      	}else{
      		noticeType = NoticeType.ATTENTION_APPLY_CANCEL;
      	}
      	ejlUserServiceImpl.notifyForManageAttention(paramMap, userId, request.getUserId(), oprType, noticeType,deviceCode);
	
		return fmlResponse;
	}
}
