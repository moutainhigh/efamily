package com.winterframework.efamily.web.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.PhoneAddressBookStruc;
import com.winterframework.efamily.dto.UploadAddressBookRequest;
import com.winterframework.efamily.dto.UploadAddressBookResponse;
import com.winterframework.efamily.dto.UserNotice.NoticeType;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.service.IEjlUserFriendService;
import com.winterframework.efamily.service.IEjlUserService;

/**
 * 
* @ClassName: UploadAddressBookHandler 
* @Description: TODO(上传通讯录,如果通讯录中存在系统用户，则添加为好友) 
* @author jason 
* @date 2015-6-30 下午5:53:08 
*
 */
@Controller("uploadAddressBookController")
@RequestMapping("server")
public class UploadAddressBookController{
	private static final Logger logger = LoggerFactory.getLogger(UploadAddressBookController.class);
	
	@Resource(name="ejlUserFriendServiceImpl")
	private IEjlUserFriendService ejlUserFriendServiceImpl;
	@Resource(name="ejlUserServiceImpl")
	private IEjlUserService ejlUserServiceImpl;
	
	@RequestMapping("/uploadAddressBook")
	@ResponseBody
	protected Response<UploadAddressBookResponse> doHandle(@RequestBody Request<UploadAddressBookRequest> request) throws Exception {
		Response<UploadAddressBookResponse> fmlResponse = new Response<UploadAddressBookResponse>(request);
		UploadAddressBookResponse uploadAddressBookResponse = new UploadAddressBookResponse();
		String contactsData = request.getData().getContactsData();
		if(StringUtils.isNotBlank(contactsData)){
			ObjectMapper om=new ObjectMapper();
			PhoneAddressBookStruc[] addressBook = om.readValue(contactsData, PhoneAddressBookStruc[].class);  
			List<EjlUser> userFriendList = ejlUserFriendServiceImpl.systemAddUserFriend(request.getCtx(),request.getUserId(), addressBook);
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			fmlResponse.setData(uploadAddressBookResponse);
			
			EjlUser user = ejlUserServiceImpl.get(request.getUserId());
			Map<String, String> paramMap = new HashMap<String,String>();
          	paramMap.put("userId", String.valueOf(request.getUserId()));
          	paramMap.put("userName", user.getNickName());
          	paramMap.put("icon", user.getHeadImg());
          	paramMap.put("phoneNumber", user.getPhone());
          	NoticeType noticeType = NoticeType.MANAGE_FRIEND_ADDRESSlIST;

          	ejlUserServiceImpl.notifyForAddressFriendShip(paramMap, request.getUserId(),userFriendList, noticeType);
			
			}else{
				fmlResponse.setStatus(new Status(StatusCode.FAILED.getValue()));
				logger.info("遍历通讯录时参数不完整： contactsData = "+contactsData);
			}
			
		return fmlResponse;
	}
}
