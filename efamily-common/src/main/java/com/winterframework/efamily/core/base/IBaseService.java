package com.winterframework.efamily.core.base; 

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;

public interface IBaseService<E extends FmlBaseEntity>{	 
	E get(Long id) throws BizException;
	int save(Context ctx,E entity) throws BizException;
	int save(Context ctx,List<E> entityList) throws BizException;
	int remove(Context ctx,Long id) throws BizException;
	public E selectOneObjByAttribute(Context ctx,E entity) throws BizException ;
	public List<E> selectListObjByAttribute(Context ctx,E entity) throws BizException ;
}
