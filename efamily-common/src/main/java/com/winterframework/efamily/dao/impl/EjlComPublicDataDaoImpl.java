package com.winterframework.efamily.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComPublicDataDao;
import com.winterframework.efamily.entity.EjlPublicData;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Repository("ejlComPublicDataDaoImpl")
public class EjlComPublicDataDaoImpl<E extends EjlPublicData> extends BaseDaoImpl<EjlPublicData> implements
		IEjlComPublicDataDao {
	private static final String SELECT_PAGE = "getByPage";
	private static final String SELECT_PAGE_COUNT = "getCountByPage";
	@Override
	public <R> Page<EjlPublicData> getAllByPage(PageRequest<R> pageRequest) {
		return this.pageQuery(pageRequest, null, null);
	}
	
	protected <R> Page<EjlPublicData> pageQuery(PageRequest<R> pageRequest, String prefix, String postfix) {
		String selectPageCount = SELECT_PAGE_COUNT;
		String selectPage = SELECT_PAGE;

		if (StringUtils.isNotBlank(prefix)) {
			selectPageCount = prefix + "_" + selectPageCount;
			selectPage = prefix + "_" + selectPage;
		}
		if (StringUtils.isNotBlank(postfix)) {
			selectPageCount = selectPageCount + "_" + postfix;
			selectPage = selectPage + "_" + postfix;
		}
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Number totalCount = (Number) sqlSessionTemplate.selectOne(this.getQueryPath(selectPageCount), param);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<EjlPublicData>(pageRequest, 0);
		}
		Page<EjlPublicData> page = new Page<EjlPublicData>(pageRequest, totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(param);
		List<EjlPublicData> list = sqlSessionTemplate.selectList(this.getQueryPath(selectPage), filters);
		page.setResult(list);
		return page;
	}
}