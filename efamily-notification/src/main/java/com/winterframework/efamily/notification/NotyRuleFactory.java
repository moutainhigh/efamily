package com.winterframework.efamily.notification;

import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.notification.model.NotyQueue;
import com.winterframework.efamily.notification.model.NotyRule;

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
			rule.setSendType(NotyRule.SendType.ON_LINE.getValue());
			
		}else if(NotyMessage.Type.SETT.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*7);	//7days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.TWO.getValue());
			rule.setSendType(NotyRule.SendType.OFF_LINE.getValue());
			
		}else if(NotyMessage.Type.ALARM.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(60*2);	//2hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.THREE.getValue());
			rule.setSendType(NotyRule.SendType.ONFF_LINE.getValue());
			
		}else if(NotyMessage.Type.REMIND.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(60*6);	//6hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.FOUR.getValue());
			rule.setSendType(NotyRule.SendType.OFF_LINE.getValue());
			
		}else if(NotyMessage.Type.MSG.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*30);	//30days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.FIVE.getValue());
			rule.setSendType(NotyRule.SendType.OFF_LINE.getValue());
			
		}else if(NotyMessage.Type.NOTICE.equals(type)){
			rule.setOffline(true);
			rule.setHsn(false);
			rule.setExpireTime(1440*7);	//7days
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.SIX.getValue());
			rule.setSendType(NotyRule.SendType.ON_LINE.getValue());
			
		}else{
			rule.setOffline(false);
			rule.setHsn(false);
			rule.setExpireTime(60);	//1hours
			rule.setPriority(NotyQueue.Priority.FIVE.getValue());
			rule.setIndex(NotyQueue.Index.ZERO.getValue());
			rule.setSendType(NotyRule.SendType.ON_LINE.getValue());
			
		}
		return rule;
	}
}
