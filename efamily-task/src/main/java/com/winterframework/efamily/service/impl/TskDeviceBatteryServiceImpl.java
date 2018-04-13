/**   
* @Title: DeviceBatteryRecordServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-15 下午4:52:33 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.thread.BizMultiThread;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.DeviceBatteryRecord;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.service.IEjlComDeviceService;
import com.winterframework.efamily.service.IFrequencySetService;
import com.winterframework.efamily.service.ITskDeviceBatteryService;

 
@Service("tskDeviceBatteryServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class TskDeviceBatteryServiceImpl extends DeviceBatteryRecordServiceImpl implements ITskDeviceBatteryService {
	@Resource(name="ejlComDeviceServiceImpl")
	private IEjlComDeviceService ejlComDeviceService; 
	@Resource(name="tskDeviceBatteryServiceImpl")
	private ITskDeviceBatteryService tskDeviceBatteryService; 
	@Resource(name="frequencySetServiceImpl")
	private IFrequencySetService frequencySetService; 
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(6);
	
	@Override
	public void processFrequency() throws BizException {
		List<EjlDevice> deviceList=ejlComDeviceService.getByType(EjlDevice.Type.WATCH.getValue());
		if(null!=deviceList){
			for(EjlDevice device:deviceList){
				final Long userId=device.getUserId();
				final Long deviceId=device.getId();
				if(userId == null || userId==0){
					continue;
				}
				new BizMultiThread(threadPool) {
					protected void doBiz() throws BizException {
						log.debug("move2still process userId"+userId+" deviceId="+deviceId);
						tskDeviceBatteryService.doProcessFrequency(userId,deviceId);
					};
				}.start();
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class,readOnly = false)
	public void doProcessFrequency(Long userId, Long deviceId) throws BizException {
		/**
		 * 1.获取有效设备（分批）
		 * 2.获取设备当前时间30分钟内最后一条电量数据(task执行要小于该分钟数）
		 * 3.判断电量区间，改变相应频率
		 */
		final int BF_MIN=30;	//当前时间60分钟内
		Long time=DateUtils.addMinutes(DateUtils.currentDate(), BF_MIN*-1).getTime(); //1小时前数据
		DeviceBatteryRecord deviceBatteryRecord = getLastDeviceBatteryRecordByDeviceId(deviceId,time);
		if(null!=deviceBatteryRecord){
			frequencySetService.batterySet(deviceBatteryRecord);
		}
				 
	}
}
