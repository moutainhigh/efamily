/**   
* @Title: IDeviceAddressListDao.java 
* @Package com.winterframework.efamily.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午3:35:13 
* @version V1.0   
*/
package com.winterframework.efamily.dao;

import com.winterframework.efamily.base.exception.BizException;


/** 
* @ClassName: IDeviceAddressListDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午3:35:13 
*  
*/
public interface IEjlDeviceAddressListDao extends IEjlComDeviceAddressListDao {
	public int deleteByUserId( Long userId) throws BizException ;
}
