/**   
* @Title: EjlRemindServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-5 下午4:30:28 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.model.Notification;
import com.winterframework.efamily.base.model.NotyMessage;
import com.winterframework.efamily.base.model.NotyTarget;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.entity.EfCustomerAlarmSetting;
import com.winterframework.efamily.entity.EfDeviceAlarmTarget;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEfCustomerAlarmSettingService;
import com.winterframework.efamily.service.ITskDeviceAlarmTargetService;
import com.winterframework.efamily.utils.HttpUtil;
import com.winterframework.efamily.utils.NotificationUtil;


/**
 *TskDeviceAlarmServiceImpl
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月12日
 */
@Service("tskDeviceAlarmTargetServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class TskDeviceAlarmTargetServiceImpl extends EfDeviceAlarmTargetServiceImpl implements ITskDeviceAlarmTargetService {
	private Logger log = LoggerFactory.getLogger(TskDeviceAlarmTargetServiceImpl.class);
	
	@Resource(name="efCustomerAlarmSettingServiceImpl")
	protected IEfCustomerAlarmSettingService efCustomerAlarmSettingService;
	@Resource(name="notificationUtil")
	protected NotificationUtil notificationUtil;
	@Resource(name="httpUtil")
	protected HttpUtil httpUtil;
	
	 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(80);
	
	@Override
	public void doProcess() throws BizException {
		
		List<EfDeviceAlarmTarget> alarmTargetList=getByStatus(YesNo.NO.getValue());
		if(null!=alarmTargetList){
			for(final EfDeviceAlarmTarget alarmTarget:alarmTargetList){
				new BizMultiThread(threadPool) {
					protected void doBiz() throws BizException {
						process(alarmTarget);
					};
				}.start();
			}
		}
	}
	public void process(EfDeviceAlarmTarget alarmTarget){
		boolean isCustomer=alarmTarget.getIsCustomer().intValue()==YesNo.YES.getValue()?true:false;
		try{
			int status=EfDeviceAlarmTarget.Status.INIT.getValue();
			try{
				noty(alarmTarget, isCustomer);
				status=EfDeviceAlarmTarget.Status.SUCCESS.getValue();
			}catch(Exception e){
				log.error("noty failed at first time when processing device alarm target. alarmTarget_id="+alarmTarget.getId(),e);
				try{
					noty(alarmTarget, isCustomer);
				}catch(Exception e2){
					status=EfDeviceAlarmTarget.Status.FAILED.getValue();
					log.error("noty failed at second time when processing device alarm target. alarmTarget_id="+alarmTarget.getId(),e2);
				}
			}
			alarmTarget.setStatus(status);
			Context ctx=new Context();
			ctx.set("userId",-1);
			save(ctx, alarmTarget);
		}catch(BizException e){
			log.error("process device alarm target failed.alarmTarget_id="+alarmTarget.getId(),e);
		}
		
	}
	protected void noty(EfDeviceAlarmTarget alarmTarget,boolean isCustomer) throws BizException{
		if(!isCustomer){
			addNoty(alarmTarget);
		}else{
			addCall(alarmTarget);
		}
	}
	
	protected void addNoty(EfDeviceAlarmTarget alarmTarget) throws BizException{
		 Notification notification = new Notification();
		 Map<String,String> data=(Map<String,String>)JsonUtils.fromJson(alarmTarget.getValue(),Map.class);
		 
		 NotyTarget target = new NotyTarget(alarmTarget.getTargetId(),null);	//推送目标 
		 NotyMessage message = new NotyMessage();	//推送消息
		 message.setCommand(EfamilyConstant.PUSH);// 更换：20105  解除：20106
		 message.setData(data);
		 message.setType(NotyMessage.Type.ALARM);
		 notification.setRealTime(false); //是否实时推送
		 notification.setTarget(target);
		 notification.setMessage(message);
		 notificationUtil.notification(notification);
	}
	
	/**
	 * 考虑 单独项目请求第三方
	 * @param alarmTarget
	 * @throws BizException
	 */
	protected void addCall(EfDeviceAlarmTarget alarmTarget) throws BizException{
		Long customerId=alarmTarget.getTargetId();
		
		EfCustomerAlarmSetting alarmSett=efCustomerAlarmSettingService.getValidByCustomerIdAndAlarmType(customerId,alarmTarget.getAlarmType());
		if(null==alarmSett){
			//log.error("没有配置血压报警地址的 默认为成功： customerId = "+customerId+" ; alarmType = "+alarmTarget.getAlarmType()+" ; " );
			//return;
			throw new BizException(StatusBizCode.CUSTOMER_ALARM_SETT_MISSING.getValue());
		}
		if(alarmSett.getMethod()==null||alarmSett.getMethod().equalsIgnoreCase("GET")){
			String param=alarmTarget.getValue();
			String alarmUrl=alarmSett.getAlarmUrl();
			String result=httpUtil.httpGet(alarmUrl, param);
			log.info("HTTP GET addCall 告警请求：Url = "+alarmUrl+"; param ="+param+" ; 告警返回结果：result = "+result+" ; ");
			if(null!=result && result.contains("resultCode")){
				//JsonUtils.fromJson(result, BaseResult)
			}
		}else{
			Map<String,String> paramMap=(Map<String,String>)JsonUtils.fromJson(alarmTarget.getValue(), Map.class);
			if(customerId == 10 && alarmTarget.getAlarmType().intValue()==1){//赑勰SOS告警接口
				paramMap = getParamMapForBiXie(paramMap);
				//log.info("HTTP POST addCall  告警请求 paramMap: "+paramMap.get("imei")+" ; "+paramMap.get("location")+" ; "+paramMap.get("time")+" ; paramMap = "+paramMap.size()+" ; ");
			}
			String result = httpUtil.httpPost(alarmSett.getAlarmUrl(), paramMap);
			log.info("HTTP POST addCall  告警请求：Url = "+alarmSett.getAlarmUrl()+"; param ="+alarmTarget.getValue()+" ; 告警返回结果：result = "+result);
			Map map = JsonUtils.fromJson(result, Map.class);
			String status = "1";
			if(map.get("resultCode")!=null){
				status = map.get("resultCode").toString();
			}else if(map.get("status")!=null){
				status = map.get("status").toString();
			}
			
			String message = "unknow error";
			if(map.get("message")!=null){
				message = map.get("message").toString();
			}else if(map.get("errMsg")!=null){
				message = map.get("errMsg").toString();
			}		
					
			if(!"0".equals(status)){
				throw new BizException(message);
			}
		}
	}
	
	public Map<String,String> getParamMapForBiXie(Map<String,String> map){
		Map<String,String> mapResult = new HashMap<String,String>();
		mapResult.put("imei", map.get("imei"));
		mapResult.put("location", map.get("alatitude")+","+map.get("alongitude"));
		mapResult.put("time", map.get("time"));
		
		return mapResult;
	}
	
}
