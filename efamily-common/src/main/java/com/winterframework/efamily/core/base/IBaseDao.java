package com.winterframework.efamily.core.base;

import java.util.List;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.orm.dal.ibatis3.BaseDao;


public interface IBaseDao<E extends FmlBaseEntity> extends BaseDao<E>{
	int insertBatch(List<E> entitys) throws BizException;
	int updateBatch(List<E> entitys) throws BizException;
	E selectOneObjByAttribute(E entity) throws BizException;
	List<E> selectListObjByAttribute(E entity) throws BizException;
}
