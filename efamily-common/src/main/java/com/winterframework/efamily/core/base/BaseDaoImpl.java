package com.winterframework.efamily.core.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.orm.ibatis3.SqlSessionCallback;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


public class BaseDaoImpl<E extends FmlBaseEntity> extends BaseIbatis3Dao<E> implements IBaseDao<E> {
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	private static final String SELECT_ATTRIBUTE = "getObjByAttribute";
	
	@Override
	protected void prepareObjectForSaveOrUpdate(E o, boolean isSave) {
	}
	@Override
	public int insertBatch(final List<E> entitys) throws BizException{
		return sqlSessionTemplate.execute(new SqlSessionCallback<Integer>() {
			@Override
			public Integer doInSqlSession(SqlSession sqlSession) throws Exception {
				for (E entity : entitys) {
					prepareObjectForSaveOrUpdate(entity, true);
					sqlSession.insert(getQueryPath(INSERT), entity);
				}
				return entitys.size();
			}
		}, ExecutorType.BATCH);
	}
	@Override
	public int updateBatch(final List<E> entitys) throws BizException{			
		return sqlSessionTemplate.execute(new SqlSessionCallback<Integer>() {
			@Override
			public Integer doInSqlSession(SqlSession sqlSession) throws Exception {
				for (E entity : entitys) {
					prepareObjectForSaveOrUpdate(entity, true);
					sqlSession.insert(getQueryPath(UPDATE), entity);
				}
				return entitys.size();
			}
		}, ExecutorType.BATCH);
		
	}
	@Override
	public E selectOneObjByAttribute(E entity) throws BizException {
		return  sqlSessionTemplate.selectOne(this.getQueryPath(SELECT_ATTRIBUTE), entity);
	}

	@Override
	public List<E> selectListObjByAttribute(E entity) throws BizException {
		if (entity == null) {
			return new ArrayList<E>();
		}
		return sqlSessionTemplate.selectList(this.getQueryPath(SELECT_ATTRIBUTE), entity);
	}
}
