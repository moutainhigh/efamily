package com.winterframework.efamily.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Request;
import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.base.model.Status;
import com.winterframework.efamily.dto.SearchFamilyRequest;
import com.winterframework.efamily.dto.SearchFamilyResponse;
import com.winterframework.efamily.service.IEjlFamilyService;

/**
 * 获取身份列表handler
 * @author david
 *
 */
@Controller("searchFamilyController")
@RequestMapping("/server")
public class SearchFamilyController{
	
	@Resource(name="ejlFamilyServiceImpl")
	private IEjlFamilyService ejlFamilyServiceImpl;
	
	@RequestMapping("/searchFamily")
	@ResponseBody
	protected Response<SearchFamilyResponse> doHandle(@RequestBody Request<SearchFamilyRequest> request) throws BizException {
		Response<SearchFamilyResponse> fmlResponse = new Response<SearchFamilyResponse>(request);
		SearchFamilyResponse searchFamilyResponse = new SearchFamilyResponse();
		
		//*** 此方法被废弃了  暂时不需要 查询家庭的功能
/*		SearchFamilyResponse response=ejlFamilyServiceImpl.searchFamily(request.getData().getKey());
		searchFamilyResponse.setResultList(response.getResultList());*/
		fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
		fmlResponse.setData(searchFamilyResponse);
		return fmlResponse;
	}
}
