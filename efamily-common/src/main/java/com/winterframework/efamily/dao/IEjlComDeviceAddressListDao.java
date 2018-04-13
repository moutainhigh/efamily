/**   
* @Title: IDeviceAddressListDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午3:35:13 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlDeviceAddressList;

/** 
* @ClassName: IDeviceAddressListDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午3:35:13 
*  
*/
public interface IEjlComDeviceAddressListDao extends IBaseDao<EjlDeviceAddressList> {
	public List<EjlDeviceAddressList> getAddressListByUser(Long userId) throws BizException;
	
}
