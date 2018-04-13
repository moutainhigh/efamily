/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

/**
 * 健康工具类
 * @ClassName
 * @Description
 * @author ibm
 * 2015年8月23日
 */
public class HealthUtil {
	private static final int THOUSANDS=1000;	
	 
	/**
	 * 时间间隔（秒）
	 * @param timeTo
	 * @param timeFrom
	 * @return
	 */
	public static int getTimeSpan(Long timeTo,Long timeFrom) {
		return (int)Math.ceil((timeTo-timeFrom)*1.0/THOUSANDS);
	}
	
	public static void main(String[] args){
		System.out.println(getTimeSpan(100901L, 100000L));
	}
}
