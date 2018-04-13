/**   
* @Title: DeviceAddressListDaoImpl.java 
* @Package com.winterframework.efamily.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午4:04:07 
* @version V1.0   
*/
package com.winterframework.efamily.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComDeviceAddressListDao;
import com.winterframework.efamily.entity.EjlDeviceAddressList;

/** 
* @ClassName: DeviceAddressListDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午4:04:07 
*  
*/
@Repository("ejlComDeviceAddressListDaoImpl")
public class EjlComDeviceAddressListDaoImpl<E extends EjlDeviceAddressList> extends BaseDaoImpl<EjlDeviceAddressList> implements IEjlComDeviceAddressListDao{

	

	/**
	* Title: getAddressListByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IDeviceAddressListDao#getAddressListByUser(java.lang.Long) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<EjlDeviceAddressList> getAddressListByUser(Long userId) throws BizException {
		List<EjlDeviceAddressList> ejlDeviceAddressList =   this.sqlSessionTemplate.selectList(this.getQueryPath("getAddressListByUser"), userId);
		return ejlDeviceAddressList==null?Collections.EMPTY_LIST:ejlDeviceAddressList;
	}

	
}
