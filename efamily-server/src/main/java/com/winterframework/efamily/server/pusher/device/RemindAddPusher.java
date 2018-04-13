/**   
* @Title: RemindAddPusher.java 
* @Package com.winterframework.efamily.server.pusher.device 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-7 下午1:45:24 
* @version V1.0   
*/
package com.winterframework.efamily.server.pusher.device;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.server.core.AbstractPusher;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.FmlRequest;

/** 
* @ClassName: RemindAddPusher 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-7 下午1:45:24 
*  
*/
@Service("remindAddPusher")
public class RemindAddPusher extends AbstractPusher{
	@Override
	protected Map<String, String> getData(Context ctx, FmlRequest req) throws ServerException {
		Map<String,String> data=new HashMap<String,String>();
		data.put("data", req.getValue("remind"));
		return data;
	}

}
