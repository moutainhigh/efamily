package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlRemind;


public interface IEjlRemindService extends IBaseService<EjlRemind> {
	public List<EjlRemind> queryAllDisposableRemindForTask() throws BizException;
	public List<EjlRemind> queryAllRepeatRemindForTask() throws BizException;
	public void saveOrUpdate(Context ctx,EjlRemind ejlRemind) throws BizException;
	public List<EjlRemind> queryAllRemindsForUser(Long userId) throws BizException;
	public List<EjlRemind> queryAllReceiveRemindsForUser(Long userId) throws BizException;
	public EjlRemind queryReminById(Long id,Long type) throws BizException;
	public void deleteById(Context ctx,Long id,Long type) throws BizException;
	public List<EjlRemind> queryAllDeviceRemindForTask() throws BizException;
	public void insertRecord(EjlRemind ejlRemind) throws BizException;
	public void updateRecord(EjlRemind ejlRemind) throws BizException;
	
}
