package com.winterframework.efamily.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.dao.IEjlVerifyCodeDao;
import com.winterframework.efamily.entity.EjlVerifyCode;
import com.winterframework.efamily.service.IEjlVerifyCodeService;
import com.winterframework.efamily.thirdparty.sms.ISmsService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("ejlVerifyCodeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlVerifyCodeServiceImpl extends EjlComVerifyCodeServiceImpl implements
		IEjlVerifyCodeService {
	
	private static final Logger log = LoggerFactory.getLogger(EjlVerifyCodeServiceImpl.class); 

	@Resource(name = "ejlVerifyCodeDaoImpl")
	private IEjlVerifyCodeDao ejlVerifyCodeDaoImpl;
	
	@Resource(name="CLVerifyCodeSmsServiceImpl")
	private ISmsService smsService;
	
	@PropertyConfig(value="sms.message.template")
	private String messageTemplate;

	
	protected IEjlVerifyCodeDao getEntityDao() {
		// TODO Auto-generated method stub
		return ejlVerifyCodeDaoImpl;
	}

	/**
	* Title: getInvitedUserByFamily
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlComVerifyCodeService#getInvitedUserByFamily() 
	*/
	@Override
	public List<EjlVerifyCode> getInvitedUserByFamily(Long familyId) throws BizException {
		return ejlVerifyCodeDaoImpl.getInvitedUserByFamily(familyId);
	}

	@Override
	public EjlVerifyCode getVerifyCode(String phoneNumber, String verifyCode) {
		return ejlVerifyCodeDaoImpl.getVerifyCode(phoneNumber,verifyCode);
		
	}
	@Override
	public EjlVerifyCode getEffectiveVerifyCode(String phoneNumber,int type) {
		return ejlVerifyCodeDaoImpl.getEffectiveVerifyCode(phoneNumber,type);
		
	}
	
	@Override
	public Integer getVerifyCode(Context ctx,String phoneNumber) throws BizException {
		//生成100000-999999之间的六位随机数
		Random random = new Random();
		int verifyCode = random.nextInt(8999);
		verifyCode = verifyCode+1000;
		String content=StringUtils.replace(messageTemplate, "{1}", verifyCode+"");
		String code="error";
		EjlVerifyCode evc=new EjlVerifyCode();
		evc.setPhoneNumber(phoneNumber);
		evc.setGmtCreated(new Date());
		evc.setFamilyId(0L);
		evc.setTimeOut(10L);
		evc.setUserId(0L);
		evc.setType(1L);
		evc.setVerifyCode(verifyCode+"");
		evc.setStatus(1L);
		try {
			code = smsService.send(phoneNumber, content);
			evc.setMessageCode(code);
			if(code!=null){//短信返回判断是否发送成功
				String temp[]=code.split(",");
				if(temp.length<2){
					evc.setStatus(0L);
					log.error("短信返回格式错误:"+code);
				}else{
					if(!temp[1].startsWith("0")){//不为0表明发送失败
						evc.setStatus(0L);//设置验证码无效
					}
				}
			}
		} catch (Exception e) {
			log.error("短信发送错误:"+ e);
		}
		try {
			this.save(ctx,evc);
		} catch (Exception e) {
			log.error("保存验证码信息错误",e);
		}
		log.error("===================================="+code);
		return 0;
	}
	
}
