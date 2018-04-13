package com.winterframework.efamily.api.web.controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.winterframework.efamily.api.enums.ResultCode;

import com.winterframework.efamily.api.util.ValidaUtil;


import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Response;

import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.dto.ComOrgReq;
import com.winterframework.efamily.dto.Contact;
import com.winterframework.efamily.dto.ContactReq;
import com.winterframework.efamily.dto.WatchDeviceManageRequest;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.utils.HttpUtil;

import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("institutionContactsConfigController")
@RequestMapping("/api")
public class InstitutionContactsConfigController {

	private static final Logger log = LoggerFactory.getLogger(InstitutionContactsConfigController.class);

	@PropertyConfig("server.url.app")
	private String serverUrl;
	@PropertyConfig("app.server.setWatchBookList")
	private String urlPath;
	@Resource(name = "httpUtil")
	protected HttpUtil httpUtil;

	@RequestMapping(value = "contacts", method = RequestMethod.POST)
	@ResponseBody
	protected Object contacts(@RequestBody ComOrgReq<List<ContactReq>> req,String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JsonMapper om=new JsonMapper();
		try {
			List<ContactReq> data = (List<ContactReq>)req.getData();
			//String key = req.getKey();
			
			if (data == null) {
				throw new BizException(ResultCode.PARAM_EMPTY.getCode());
			}
			List<WatchDeviceManageRequest> request = new ArrayList<WatchDeviceManageRequest>();
			for(ContactReq contactReq:data){
				if(contactReq.getImei()==null||contactReq.getContacts()==null){
					throw new BizException(ResultCode.PARAM_EMPTY.getCode());
				}
				if(contactReq.getImei().length()!=15){
					throw new BizException(ResultCode.IMEI_INVALID.getCode());
				}
				EjlDeviceAddressList[] addressBook = new EjlDeviceAddressList[contactReq.getContacts().size()];
				int i=0;
				for(Contact contact:contactReq.getContacts()){
					if(contact.getNickName() == null || contact.getPhoneNumber() == null || contact.getIsSos()==null){
						throw new BizException(ResultCode.PARAM_EMPTY.getCode());
					}
					/*if(!ValidaUtil.isMobile(contact.getPhoneNumber())){
						throw new BizException(ResultCode.PHONE_INVALID.getCode());
					}*/
					EjlDeviceAddressList phoneAddressBookStruc = new EjlDeviceAddressList();
					phoneAddressBookStruc.setName(contact.getNickName());
					phoneAddressBookStruc.setPhoneNumber(contact.getPhoneNumber());
					phoneAddressBookStruc.setIsSos(Long.valueOf(contact.getIsSos()));
					phoneAddressBookStruc.setHeadImage("");
					addressBook[i++] = phoneAddressBookStruc;
				}
				if(addressBook.length>0){
					WatchDeviceManageRequest wd = new WatchDeviceManageRequest();
					wd.setParameterContext(om.toJson(addressBook));
					wd.setParameterIndex(contactReq.getImei());
					request.add(wd);
				}
			}
			Context ctx = new Context();
			ctx.set("orgKey", key);
			ctx.set("userId", -1);
			Response<Object> bizRes = httpUtil.http(serverUrl,urlPath, ctx,request, Object.class);
			map.put("resultCode", bizRes.getStatus().getCode());
			map.put("errMsg", ResultCode.getResultCode(bizRes.getStatus().getCode()));
		} catch (BizException e) {
			ResultCode resultCode = ResultCode.getResultCode(e.getCode());
			log.error(e.getCode() + " " + resultCode.getMsg(), e);
			map.put("resultCode", e.getCode() + "");
			map.put("errMsg", resultCode.getMsg());
		} catch (Exception e1) {
			map.put("resultCode", "-1");
			map.put("errMsg", "unknown error.");
			log.error(e1.getMessage());
		}
		return map;
	}
}
