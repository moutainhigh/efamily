/**   
* @Title: BaiduLocationResult.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-11 下午6:03:34 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

import java.util.List;

/** 
* @ClassName: BaiduLocationResult 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-11 下午6:03:34 
*  
*/
public class BaiduLocationResult {
	private int status;
	private CityJsonObject result;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public CityJsonObject getResult() {
		return result;
	}

	public void setResult(CityJsonObject result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
