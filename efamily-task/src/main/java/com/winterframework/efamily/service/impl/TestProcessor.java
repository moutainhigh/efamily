package com.winterframework.efamily.service.impl;

import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.service.ITskLocationSemiService;
import com.winterframework.modules.utils.SpringContextHolder;

public class TestProcessor implements Runnable {

	private ITskLocationSemiService tskLocationSemiService2=SpringContextHolder.getBean("tskLocationSemiServiceImpl");
	
	@Override
	public void run() { 
		try{
			System.out.println("KKK"+tskLocationSemiService2);
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA"); 
			//tskLocationSemiService2.process(new Context(), 1L, 1L,null,null);
			//process2(ctx, userId, deviceId,timeFrom,timeTo); 
		//	test(ctx, userId, deviceId,timeFrom,timeTo);
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA2");
		}catch(Exception e){
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA4");
		}
	}

}
