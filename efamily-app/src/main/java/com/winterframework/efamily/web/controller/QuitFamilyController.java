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
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.dto.QuitFamilyRequest;
import com.winterframework.efamily.dto.QuitFamilyResponse;
import com.winterframework.efamily.dto.UnbindDeviceInfo;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlFamily;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlFamilyService;
import com.winterframework.efamily.service.IEjlFamilyUserService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 退出家庭handler
 * @author floy
 *
 */
@Controller("quitFamilyController")
@RequestMapping("/server")
public class QuitFamilyController {
	private static final Logger logger = LoggerFactory.getLogger(QuitFamilyController.class);
	
	@Resource(name = "ejlFamilyUserServiceImpl")
	private IEjlFamilyUserService ejlFamilyUserServiceImpl;
	
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@RequestMapping("/quitFamily")
	@ResponseBody
	protected Response<QuitFamilyResponse> doHandle(@RequestBody Request<QuitFamilyRequest> request) throws BizException {
		Response<QuitFamilyResponse> fmlResponse = new Response<QuitFamilyResponse>(request);
		QuitFamilyResponse quitFamilyResponse = new QuitFamilyResponse();
		
		Long familyId = request.getData().getFamilyId();
		String userIdStr = request.getData().getUserIdStr();
		Long manageType = request.getData().getManageType();
		Integer isAttentionOldFamilyDevice = request.getData().getIsAttentionOldFamilyDevice();//1关注  2不关注
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		EjlFamily ejlFamily = ejlFamilyServiceImpl.get(request.getData().getFamilyId());
		if(ejlFamily != null){
			if(EfamilyConstant.MANAGE_TYPE_QUIT == manageType.longValue()){
				userIdStr = request.getUserId()+"";
				List<UnbindDeviceInfo>  unbindDeviceInfoList = ejlFamilyUserServiceImpl.unbindFamilyAllDeviceByMemberQuit(request.getCtx(), request.getUserId());
				ejlFamilyUserServiceImpl.quitFamily(request.getCtx(),request.getUserId(),request.getData().getFamilyId(),isAttentionOldFamilyDevice );
				quitFamilyResponse.setFamilyId(familyId);
				quitFamilyResponse.setUnbindDevicedInfo(unbindDeviceInfoList);
				fmlResponse.setData(quitFamilyResponse);
			}else if(EfamilyConstant.MANAGE_TYPE_DELETE == manageType.longValue()){
				//*** 判断当前操作人是否家庭群主
				if(ejlFamilyUserServiceImpl.checkIsFamilyHost(request.getUserId(), familyId)){
					ejlFamilyUserServiceImpl.deleteUserFromFamily(request.getCtx(), userIdStr, familyId);
				}else{
					logger.error("用户不是家庭群主，不能删除家庭成员，操作失败。 manageType = "+manageType+" ; userIdStr = "+userIdStr);
					throw new BizException(StatusBizCode.USER_UN_FAMILY_HOST.getValue());
				}
			}
			
			//------ 1 申请同意,2 拒绝,3 申请,4 申请加入临时家庭,并创建临时家庭,5 邀请,6  退出 ,7 邀请同意 ,8 删除家庭成员
          	ejlUserServiceImpl.notifyForManageFamilyQuitAndDelete(userIdStr, request.getUserId(), ejlFamily.getId(), NoticeType.MANAGE_FAMILY_USER_DELETE_QUIT,manageType,request.getUserId());
		}else{
			fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
			logger.error("退出家庭时，退出的家庭不存在。 familyId = "+request.getData().getFamilyId()+" ; userId = "+request.getUserId());
		}
			
		return fmlResponse;
	}
}
