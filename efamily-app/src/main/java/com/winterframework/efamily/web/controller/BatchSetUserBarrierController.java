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
import com.winterframework.efamily.dto.SaveFenceReq;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlUserBarrierService;

	@Controller("batchSetUserBarrierController")
	@RequestMapping("/server")
	public class BatchSetUserBarrierController {
		private static final Logger log = LoggerFactory.getLogger(BatchSetUserBarrierController.class);
		@Resource(name = "ejlUserBarrierServiceImpl")
		private IEjlUserBarrierService ejlUserBarrierServiceImpl;

		@RequestMapping("/batchSetUserBarrier")
		@ResponseBody
		protected Response<Object> doHandle(
				@RequestBody Request<List<SaveFenceReq>> request)
				throws BizException {
			Response<Object> fmlResponse = new Response<Object>(request);
			try{
			ejlUserBarrierServiceImpl.batchSetUserBarrier(request.getCtx(), request.getData(), String.valueOf(request.getCtx().get("orgKey")), Integer.valueOf(String.valueOf(request.getCtx().get("orgTag"))));
			fmlResponse.setStatus(new Status(StatusCode.OK.getValue()));
			}catch(BizException e){
				log.error("batchSetUserBarrierController error", e);
				fmlResponse.setStatus(new Status(e.getCode()));
			}catch(Exception e){
				log.error("batchSetUserBarrierController error", e);
				fmlResponse.setStatus(new Status(StatusBizCode.UNKNOW.getValue()));
			}
			return fmlResponse;
		}
	}
