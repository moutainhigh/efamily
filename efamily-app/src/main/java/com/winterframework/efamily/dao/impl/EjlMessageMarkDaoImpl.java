package com.winterframework.efamily.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.dao.IEjlMessageMarkDao;
import com.winterframework.efamily.dto.MessageCountInfo;
import com.winterframework.efamily.dto.MessageNoReadInfoResponse;
import com.winterframework.efamily.entity.EjlMessageMark;

@Repository("ejlMessageMarkDaoImpl")
public class EjlMessageMarkDaoImpl extends EjlComMessageMarkDaoImpl<EjlMessageMark> implements IEjlMessageMarkDao {
	
	public List<MessageNoReadInfoResponse> getMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getMessageByChatObj"), ejlMessageMark);
	}
	
	public List<MessageNoReadInfoResponse> getLashMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getLashMessageByChatObj"), ejlMessageMark);
	}
	
	public List<MessageCountInfo> getNoReadMessageCount(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getNoReadMessageCount"), ejlMessageMark);
	}
	
	public List<MessageNoReadInfoResponse> getP2PMessageByChatObj(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getMessageByChatObj"), ejlMessageMark);
	}
	
	public List<MessageCountInfo> getP2PNoReadMessageCount(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getP2PNoReadMessageCount"), ejlMessageMark);
	}
	
	public long updateMessageMarkStatus(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.update(this.getQueryPath("updateMessageMarkStatus"), ejlMessageMark);
	}
	public Long getTowMessageIdInternalCount(EjlMessageMark ejlMessageMark) throws BizException {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getTowMessageIdInternalCount"), ejlMessageMark);
	}
	
}