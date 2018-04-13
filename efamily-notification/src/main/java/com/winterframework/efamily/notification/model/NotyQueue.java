package com.winterframework.efamily.notification.model;


/**
 * 消息队列
 * @ClassName
 * @Description
 * @author ibm
 * 2015年9月4日
 */
public class NotyQueue {  
	public static final String QUEUE_USER="queue_user_new";
	public static final String QUEUE_USER_OFFLINE="queue_user_offline_new";
	private final String QUEUE_ZERO="queue_app0";
	private final String QUEUE_ONE="queue_app1";
	private final String QUEUE_TWO="queue_app2";
	private final String QUEUE_THREE="queue_app3";
	private final String QUEUE_FOUR="queue_app4";
	private final String QUEUE_FIVE="queue_app5";
	private final String QUEUE_SIX="queue_app6";
	private final String QUEUE_SEVEN="queue_app7";
	
	private final String QUEUE_ZERO_DEVICE="queue_device0";
	private final String QUEUE_ONE_DEVICE="queue_device1";
	private final String QUEUE_TWO_DEVICE="queue_device2";
	private final String QUEUE_THREE_DEVICE="queue_device3";
	private final String QUEUE_FOUR_DEVICE="queue_device4";
	private final String QUEUE_FIVE_DEVICE="queue_device5";
	private final String QUEUE_SIX_DEVICE="queue_device6";
	private final String QUEUE_SEVEN_DEVICE="queue_device7";
	
	private int index;
	private int priority;
	private boolean isDevice;	//是否是设备
	private boolean isOffline;	//是否离线
	
	public enum Index{
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7); 
		private int _value=1;
		Index(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	public enum Priority{
		ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5); 
		private int _value=1;
		Priority(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	private NotyQueue(int index,boolean isDevice,boolean isOffline){
		this.index=index;
		this.isDevice=isDevice;
		this.isOffline=isOffline;
	}
	public String getName() {
		String queueName=null;
		if(isDevice){
			if(index==1){
				queueName= QUEUE_ONE_DEVICE;
			}else if(index==2){
				queueName= QUEUE_TWO_DEVICE;
			}else if(index==3){
				queueName= QUEUE_THREE_DEVICE;
			}else if(index==4){
				queueName= QUEUE_FOUR_DEVICE;
			}else if(index==5){
				queueName= QUEUE_FIVE_DEVICE;
			}else if(index==6){
				queueName= QUEUE_SIX_DEVICE;
			}else if(index==7){
				queueName= QUEUE_SEVEN_DEVICE;
			}else if(index==0){
				queueName= QUEUE_ZERO_DEVICE;
			}
		}else{
			if(index==1){
				queueName= QUEUE_ONE;
			}else if(index==2){
				queueName= QUEUE_TWO;
			}else if(index==3){
				queueName= QUEUE_THREE;
			}else if(index==4){
				queueName= QUEUE_FOUR;
			}else if(index==5){
				queueName= QUEUE_FIVE;
			}else if(index==6){
				queueName= QUEUE_SIX;
			}else if(index==7){
				queueName= QUEUE_SEVEN;
			}else if(index==0){
				queueName= QUEUE_ZERO;
			}
		}
		if(isOffline){
			queueName+="_offline";
		}
		return queueName;
	}
	
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public static NotyQueue getQueue(int index,boolean isDevice,boolean isOffline){
		return new NotyQueue(index,isDevice,isOffline);
	}
}
