/**   
* @Title: LunarInitTask.java 
* @Package com.winterframework.efamily.service.schedule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-9-15 下午1:41:20 
* @version V1.0   
*/
package com.winterframework.efamily.service.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfLunar;
import com.winterframework.efamily.entity.JuheLunarData;
import com.winterframework.efamily.service.IEfComLunarService;

import com.winterframework.efamily.utils.JuheGetLunarUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: LunarInitTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-9-15 下午1:41:20 
*  
*/
public class LunarInitTask {

	private Logger log = LoggerFactory.getLogger(LunarInitTask.class);

	@Resource(name = "ejlComLunarServiceImpl")
	private IEfComLunarService ejlComLunarServiceImpl;
	
	@PropertyConfig(value = "juhe.lunar.key")
	private String key;

	public void execute() throws Exception {
		if(ejlComLunarServiceImpl.getEfLunarByDate(DateUtils.parse(DateUtils.format( DateUtils.addDays(new Date(), 10))))!=null){
			return;
		}
		for (int i = 0; i < 1200; i++) {
			Date currentD = DateUtils.addDays(new Date(), i);
			
			EfLunar ef = ejlComLunarServiceImpl.getEfLunarByDate(DateUtils.parse(DateUtils.format(currentD)));
			if (ef == null) {
				JuheLunarData juheLunarData = null;
				try {
					juheLunarData = JuheGetLunarUtil.getJuheLunarData(currentD,key);

					if (juheLunarData != null) {
						EfLunar efLunar = new EfLunar();
						if (juheLunarData.getAvoid() != null) {
							String avoid = juheLunarData.getAvoid().replace(".", " ");
							efLunar.setAvoid(avoid);
						}

						efLunar.setCreateTime(new Date());
						efLunar.setGanzhi(juheLunarData.getLunarYear());
						efLunar.setLunarDate(juheLunarData.getLunar());
						efLunar.setSolarDate(DateUtils.parse(juheLunarData.getDate()));
						if(juheLunarData.getSuit() != null){
							String suit = juheLunarData.getSuit().replace(".", " ");
							efLunar.setSuit(suit);
						}
						
						efLunar.setTerm(null);
						efLunar.setZodiac(juheLunarData.getAnimalsYear());
						efLunar.setWeek(juheLunarData.getWeekday().equals("星期一") ? 1 : juheLunarData.getWeekday()
								.equals("星期二") ? 2 : juheLunarData.getWeekday().equals("星期三") ? 3 : juheLunarData
								.getWeekday().equals("星期四") ? 4 : juheLunarData.getWeekday().equals("星期五") ? 5
								: juheLunarData.getWeekday().equals("星期六") ? 6 : 7);

						ejlComLunarServiceImpl.saveOne(efLunar);
					} else {
						continue;
					}
				} catch (Exception e) {
					log.info("查询失败", e);
					continue;
				}
			}
		}
	}
	
	
}
