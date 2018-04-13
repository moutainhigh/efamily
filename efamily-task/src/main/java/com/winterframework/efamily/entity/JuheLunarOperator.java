/**   
* @Title: JuheLunarOperator.java 
* @Package com.winterframework.efamily.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午1:56:16 
* @version V1.0   
*/
package com.winterframework.efamily.entity;

/** 
* @ClassName: JuheLunarOperator 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:56:16 
*  
*/
public class JuheLunarOperator {
	private int error_code;
	private String reason;
	private JuheLunarResult result;
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public JuheLunarResult getResult() {
		return result;
	}
	public void setResult(JuheLunarResult result) {
		this.result = result;
	}
	
}
