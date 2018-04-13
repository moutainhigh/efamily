/**   
* @Title: IDeviceAddressListService.java 
* @Package com.winterframework.efamily.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-14 下午4:25:02 
* @version V1.0   
*/
package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlDeviceAddressList;

/** 
* @ClassName: IDeviceAddressListService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-14 下午4:25:02 
*  
*/
public interface IEjlDeviceAddressListService extends IBaseService<EjlDeviceAddressList>{
	public List<EjlDeviceAddressList> getAddressListByUser(Long userId) throws BizException;
	public int deleteByUserId( Long userId) throws BizException;
}
