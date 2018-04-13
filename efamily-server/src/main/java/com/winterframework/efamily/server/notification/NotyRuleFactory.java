package com.winterframework.efamily.server.notification;

import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyQueue;
import com.winterframework.efamily.base.model.NotyRule;

public class NotyRuleFactory {
	public static NotyRule getInstance(NotyMessage.Type type){
		/*
		 *  OPER:ONE
			SETT:TWO
			ALARM:THREE
			REMIND:FOUR
			MSG:FIVE
			NOTICE:SIX
		 */
		NotyRule rule=new NotyRule();
		if(NotyMessage.Type.OPER.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*7);	//7days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue()); 
			rule.setIndex(NotyQueue.Index.ONE.getValue());
		}else if(NotyMessage.Type.SETT.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*7);	//7days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.TWO.getValue());
		}else if(NotyMessage.Type.ALARM.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(60*2);	//2hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.THREE.getValue());
		}else if(NotyMessage.Type.REMIND.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(60*6);	//6hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.FOUR.getValue());
		}else if(NotyMessage.Type.MSG.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*30);	//30days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.FIVE.getValue());
		}else if(NotyMessage.Type.NOTICE.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*7);	//7days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.SIX.getValue());
		}else{
			rule.setOffline(false);
			rule.setHsn(false);
			rule.setExpireTime(60);	//1hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.ZERO.getValue());
		}
		return rule;
	}
}
