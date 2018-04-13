package com.winterframework.efamily.server.web.result;

/**
 * 服务器HTTP请求结果基类
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月1日
 */
public class BaseHttpResult {
	private Integer status; //状态码
	private String errMsg;	//错误信息
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
