/**   
* @Title: IEjlFileUploadService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-12 上午10:52:59 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.io.IOException;

/** 
* @ClassName: IEjlFileUploadService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-12 上午10:52:59 
*  
*/
public interface IEjlFileUploadService {
	public String uploadByteArrays(String type, byte[] datas) throws IOException;
}
