package com.winterframework.efamily.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.UpdateWatchOwnerRequest;
import com.winterframework.efamily.dto.UpdateWatchOwnerResponse;
import com.winterframework.efamily.service.IEjlDeviceConfigService;
import com.winterframework.efamily.service.IEjlUserService;

@Controller("updateWatchOwnerController")
@RequestMapping("/server")
public class UpdateWatchOwnerController {
	private static final Logger log = LoggerFactory.getLogger(UpdateWatchOwnerController.class); 
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	@Resource(name="deviceConfigServiceImpl")
	private IEjlDeviceConfigService deviceConfigServiceImpl;
	@RequestMapping("/updateWatchOwner")
	@ResponseBody
	protected Response<UpdateWatchOwnerResponse> doHandle(@RequestBody Request<UpdateWatchOwnerRequest> request) throws Exception {
		Response<UpdateWatchOwnerResponse> fmlResponse = new Response<UpdateWatchOwnerResponse>(request);
		UpdateWatchOwnerResponse updateWatchOwnerResponse = new UpdateWatchOwnerResponse();
		
        String userIdWatch = request.getData().getUserId();
        
        //1:手表换用户  2:用户换手表  3:手表换一个新的用户  4:解除绑定
        String oprType = request.getData().getOprType();
        //1:跟其他人 换一块手表            
        String userIdExchange = request.getData().getUserIdExchange();
        //2:换一块新的手表
        String deviceCode = request.getData().getDeviceCode();
        //3:把手表给一个新的成员
        String newUserName = request.getData().getNewUserName();
        //新增手表的电话号码
        String phoneNumber = request.getData().getPhoneNumber();

        if(StringUtils.isNotBlank(userIdWatch)&& StringUtils.isNotBlank(oprType)){
        	if(oprType.equals( EfamilyConstant.UPDATE_WATCH_SWITCH_WATCH) && StringUtils.isBlank(deviceCode)){
        		log.info("人换表时，设备号不存在： deviceCode = "+deviceCode);
        		throw new Exception("人换表时，设备号不存在： deviceCode = "+deviceCode);
        	}
            if(StringUtils.isBlank(deviceCode)){
        		deviceCode = "0";
        	}
        	
            if(oprType.equals( EfamilyConstant.UPDATE_WATCH_SWITCH_USER) ){
            	if(StringUtils.isBlank(userIdExchange)){
            		log.info("表换人时，被换表人的ID不存在 。 userIdExchange = "+userIdExchange);
            		throw new Exception("表换人时，被换表人的ID不存在 。 userIdExchange = "+userIdExchange);
            	}
        	}
            if(StringUtils.isBlank(userIdExchange)){
        		userIdExchange = "0";
        	}
        	// *** 需要做数据的同步和锁操作
            log.info("【更换用户使用者接口  开始】request = "+request.getClass().toString()+" 1:手表换用户  2:用户换手表  3:手表换一个新的用户  4:解除绑定  oprType = "+oprType+" ; userIdExchange = "+userIdExchange+" ; deviceCode = "+deviceCode+" ; newUserName = "+newUserName+" ; phoneNumber = "+phoneNumber);
            Map<String, String> paramMapResult = deviceConfigServiceImpl.updateWatchOwner(request.getCtx(),request.getUserId(), Long.valueOf(deviceCode),Long.valueOf(userIdWatch),Long.valueOf(userIdExchange),Long.valueOf(oprType),newUserName,phoneNumber);
            log.info("【更换用户使用者接口 结束】 request = "+request.getClass().toString());
            updateWatchOwnerResponse.setParamMapResult(paramMapResult);
            fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(updateWatchOwnerResponse);
			//*** 推送给APP 用户  有手表更换         *********************
        	ejlUserServiceImpl.notifyForUpdateWatchOwner(paramMapResult, request.getUserId());
			//*** 推送给设备用户  有手表更换         *********************
        	ejlUserServiceImpl.notifyForUpdateWatchOwnerForDevice(oprType, paramMapResult, request.getUserId());
        }else{
        	log.warn("修改手表拥有者时，参数不完整，操作失败。 userIdOperate = "+request.getUserId()+" ; userId="+userIdWatch+" ; deviceCode = "+deviceCode );
        	fmlResponse.setStatus(new Status(StatusCode.UNKNOW.getValue()));
        }
		return fmlResponse;
	}
	
	public static void main(String[] args) {
		String t = "1";
		if(t.equals(EfamilyConstant.UPDATE_WATCH_SWITCH_WATCH+"")){
			System.out.println("============");
		}
	}
}