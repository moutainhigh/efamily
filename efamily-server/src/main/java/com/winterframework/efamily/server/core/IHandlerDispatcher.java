/**   
* @Title: IEventDispatcher.java 
* @Package com.winterframework.firefrog.event.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 上午9:12:12 
* @version V1.0   
*/
package com.winterframework.efamily.server.core;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;

 
/**
 * 处理器分发
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月16日
 */
public interface IHandlerDispatcher { 
	FmlResponse dispatch(Context ctx,FmlRequest req) throws ServerException;

}
