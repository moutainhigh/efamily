/**   
* @Title: JuheGetLunarUtil.java 
* @Package com.winterframework.efamily.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午2:15:28 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

import java.util.Date;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.JuheLunarData;
import com.winterframework.efamily.entity.JuheLunarOperator;

/** 
* @ClassName: JuheGetLunarUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午2:15:28 
*  
*/
public class JuheGetLunarUtil {
	public static JuheLunarData getJuheLunarData(Date date,String key) throws Exception{
		String url = "http://japi.juhe.cn/calendar/day?key="+key+"&date="+DateUtils.format(date).replace("-0", "-");
		String result = BaiDuGetCityNameUtil.getResultByUrl(url);
		JuheLunarOperator juheLunarOperator = JsonUtils.fromJson(result, JuheLunarOperator.class);
		if(juheLunarOperator!=null&&juheLunarOperator.getResult()!=null){
			return juheLunarOperator.getResult().getData();
		}else{
			return null;
		}
	}
}
