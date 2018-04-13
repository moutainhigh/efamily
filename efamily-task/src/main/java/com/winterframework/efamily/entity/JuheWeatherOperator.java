/**   
* @Title: JuheWeatherOperator.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-14 上午11:52:21 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

/** 
* @ClassName: JuheWeatherOperator 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-14 上午11:52:21 
*  
*/
public class JuheWeatherOperator<T> {

	private String reason;
	private JuheWeatherResult<T> result;
	private int errorCode;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public JuheWeatherResult<T> getResult() {
		return result;
	}
	public void setResult(JuheWeatherResult<T> result) {
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
