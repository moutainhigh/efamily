package com.winterframework.efamily.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlPublicDataDao;
import com.winterframework.efamily.entity.EjlPublicData;
import com.winterframework.efamily.service.IEjlPublicDataService;

@Service("ejlPublicDataServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlPublicDataServiceImpl extends BaseServiceImpl<IEjlPublicDataDao, EjlPublicData> implements IEjlPublicDataService {


	@Resource(name="ejlPublicDataDaoImpl")
	private IEjlPublicDataDao ejlPublicDataDao;
	@Override
	protected IEjlPublicDataDao getEntityDao() { 
		return ejlPublicDataDao;
	}
	
	public String getUpdateFlagByAppVersion(String appVersion,String updateFlagStr,byte clinetType){
		String updateFlag = "0";//0表示不更新
		if(appVersion==null){
			if(clinetType == 1){
				return "2";
			}else{
				return "3";
			}
		}
		if(updateFlagStr==null){
			return "0";
		}
		String[] updateFlagArr = updateFlagStr.trim().split("#");
		boolean flag = false;
		for(int i=0;i<updateFlagArr.length;i++){
			if(updateFlagArr[i].equals("")){
				continue;
			}
			String[] updateFlagArrTemp = updateFlagArr[i].split(",");
			String appVersionTemp = updateFlagArrTemp[0];
			String updateFlagTemp = updateFlagArrTemp[1];
			if(flag){
				if("3".equals(updateFlagTemp)){
					//***  3 表示强制更新
					updateFlag = "3";
					break;
				}else if("2".equals(updateFlagTemp)){
					//***  2表示一般更新
					updateFlag = "2";
				}
				
			}
			if(appVersion.equals(appVersionTemp)){
				flag = true;
			}

		}
		
		return updateFlag ;
	}
	
}
