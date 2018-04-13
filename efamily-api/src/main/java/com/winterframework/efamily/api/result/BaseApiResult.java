 package com.winterframework.efamily.api.result;


/**
 * API结果基类
 * @ClassName
 * @Description
 * @author ibm
 * 2016年6月24日
 */
public class BaseApiResult{
	private Integer resultCode;	//结果码
	private String errMsg;	//错误信息
	
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}

