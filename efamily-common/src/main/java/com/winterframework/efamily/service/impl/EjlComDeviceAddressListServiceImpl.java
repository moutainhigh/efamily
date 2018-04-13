/**   
* @Title: DeviceAddressListServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午4:26:43 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComDeviceAddressListDao;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.service.IEjlComDeviceAddressListService;

/** 
* @ClassName: DeviceAddressListServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午4:26:43 
*  
*/
@Service("ejlComDeviceAddressListServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComDeviceAddressListServiceImpl extends BaseServiceImpl<IEjlComDeviceAddressListDao, EjlDeviceAddressList>
		implements IEjlComDeviceAddressListService {

	@Resource(name = "ejlComDeviceAddressListDaoImpl")
	private IEjlComDeviceAddressListDao ejlComDeviceAddressListDaoImpl;

	/**
	* Title: getEntityDao
	* Description:
	* @return 
	* @see com.winterframework.efamily.base.BaseServiceImpl#getEntityDao() 
	*/
	@Override
	protected IEjlComDeviceAddressListDao getEntityDao() {
		return ejlComDeviceAddressListDaoImpl;
	}
	
	/**
	* Title: getAddressListByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlComDeviceAddressListService#getAddressListByUser(java.lang.Long) 
	*/
	@Override
	public List<EjlDeviceAddressList> getAddressListByUser(Long userId) throws BizException {
		EjlDeviceAddressList ejlDeviceAddressList = new EjlDeviceAddressList();
		ejlDeviceAddressList.setUserId(userId);
		return ejlComDeviceAddressListDaoImpl.selectListObjByAttribute(ejlDeviceAddressList);
	}

}
