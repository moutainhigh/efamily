package com.winterframework.efamily.core.base;
 
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;

public abstract class BaseServiceImpl<DAO extends IBaseDao<E>, E extends FmlBaseEntity> implements IBaseService<E> { 
	protected Logger log = LoggerFactory.getLogger(getClass()); 
	
	protected abstract DAO getEntityDao();
	
  	public E get(Long id) throws BizException{
		return (E)getEntityDao().getById(id);
	}

	public int save(Context ctx,E entity) throws BizException {
		if(null!=entity){
			try{
				if(null==entity.getId()){
					entity.setCreatorId(ctx.getUserId());
					entity.setGmtCreated(DateUtils.currentDate());
					entity.setCreateTime(DateUtils.currentDate());
					return getEntityDao().insert(entity); 
				}else{
					entity.setUpdatorId(ctx.getUserId());
					entity.setUpdateTime(DateUtils.currentDate());
					entity.setGmtModified(DateUtils.currentDate());
					return getEntityDao().update(entity);
				}
			}catch(Exception e){
				log.error("save failed.",e);
				throw new BizException(StatusCode.DAO_ERROR.getValue());
			}
		}
		return 0;
	}
	@Override
	@Transactional
	public int save(Context ctx,List<E> entityList) throws BizException {
		if(null!=entityList){
			int count=0;
			try{
				List<E> insertList=new ArrayList<E>();
				List<E> updateList=new ArrayList<E>();
				for(E entity:entityList){
					if(null==entity.getId()){
						entity.setCreatorId(ctx.getUserId());
						entity.setGmtCreated(DateUtils.currentDate());
						entity.setCreateTime(DateUtils.currentDate());
						insertList.add(entity);
					}else{
						entity.setUpdatorId(ctx.getUserId());
						entity.setUpdateTime(DateUtils.currentDate());
						entity.setGmtModified(DateUtils.currentDate());
						updateList.add(entity);
					}
				}
				count= getEntityDao().insertBatch(insertList)+getEntityDao().updateBatch(updateList);
			}catch(Exception e){
				log.error("save failed.",e);
				throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
			}
			return count;
		}
		return 0;
	}

	public int remove(Context ctx,Long id) throws BizException {
		if(null!=id){
			try{
				return getEntityDao().delete(id);
			}catch(Exception e){
				log.error("remove failed.",e);
				throw new BizException(StatusCode.DAO_ERROR.getValue(),e);
			}
		}
		return 0;
	}
	
	public E selectOneObjByAttribute(Context ctx,E entity) throws BizException {
		return  getEntityDao().selectOneObjByAttribute(entity);
	}

	public List<E> selectListObjByAttribute(Context ctx,E entity) throws BizException {
		if (entity == null) {
			return new ArrayList<E>();
		}
		return getEntityDao().selectListObjByAttribute( entity);
	}
	
}
 