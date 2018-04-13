package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IDeviceQrcodeService;
import com.winterframework.efamily.service.IEfComCustomerService;

@Service("deviceQrcodeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class DeviceQrcodeServiceImpl  extends QrcodeServiceImpl implements IDeviceQrcodeService {
	private static final Logger log= LoggerFactory.getLogger(DeviceQrcodeServiceImpl.class);
	
	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerServiceImpl;
	
	
	@Override
	public int upload(Context ctx,String imei, String imsi,String model,Integer type,Integer simStatus) throws BizException {
		Qrcode qrcode=getByImei(imei);
		if(null!=qrcode){
			//throw new BizException(StatusBizCode.DEVICE_MOBILE_IMPORTED.getValue(),new String[]{imei});
			return 1;
		}
		qrcode=new Qrcode();
		qrcode.setImei(imei);
		qrcode.setImsi(imsi); 
		qrcode.setModel(model);
		if(type != null) {
			qrcode.setType(type);
		}else {
			qrcode.setType(Qrcode.Type.WATCH.getValue());
		}
		if(simStatus != null) {
			qrcode.setSimStatus(simStatus);
		}else {
			qrcode.setSimStatus(YesNo.NO.getValue());
		}
		qrcode.setStatus(YesNo.NO.getValue());	//默认不生效
		return save(ctx,qrcode);
	}
	
	public String getQrcodeUrl(String imei) throws BizException{
		if(imei == null){
			log.warn("获取QRCODE参数为空: imei = "+imei);
			return null;
		}
		String url = null;
		Qrcode qrcode=getByImei(imei);
		if(qrcode !=null ){
			EfCustomer customer = efComCustomerServiceImpl.getEfCustomerBy(qrcode.getCustomerNumber());
			if(customer != null){
				url = customer.getQrcodeUrl();
			}else{
				//代理商不存在
				log.error("设备对应的客户不存在: imei = "+imei);
				throw new BizException(StatusBizCode.DEVICE_CUSTOMER_NO_EXIST.getValue());
			}
		}else{
			//imei对应设备未入库 imei无效
			log.error("imei对应设备未入库 imei无效: imei = "+imei);
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		return url;
	}
	
	public int updateQrcodeResourceIdBy(Context ctx,String imei,String resourceId) throws BizException{
		if(StringUtils.isBlank(imei)||StringUtils.isBlank(resourceId)){
			log.error("updateQrcodeResourceIdBy 参数为空: imei = "+imei+" ;resourceId = "+resourceId+" ; " );
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		Qrcode qrcode=getByImei(imei);
		if(qrcode==null){
			log.error("imei对应设备未入库 imei无效: imei = "+imei);
			throw new BizException(StatusBizCode.DEVICE_IMEI_INVALID.getValue());
		}
		//更新qrcoderesourceId
		qrcode.setQrcodeResourceId(resourceId);
		return  save(ctx,qrcode);
	}
}
