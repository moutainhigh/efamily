package com.winterframework.efamily.service;

import java.util.List;



import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.entity.EjlRemind;


public interface IEjlRemindService extends IEjlComRemindService {
	public List<EjlRemind> queryAllDisposableRemindForTask() throws Exception;
	public List<EjlRemind> queryAllRepeatRemindForTask() throws Exception;
	public void saveOrUpdate(Context ctx,EjlRemind ejlRemind) throws Exception;
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws Exception;
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws Exception;
	public EjlRemind queryReminById(Long id,Long type) throws Exception;
	public void deleteById(Context ctx,Long id,Long type) throws Exception;
	public List<EjlRemind> queryAllDeviceRemindForTask() throws Exception;
	public void insertRecord(EjlRemind ejlRemind) throws Exception;
	public void updateRecord(EjlRemind ejlRemind) throws Exception;
	
}
