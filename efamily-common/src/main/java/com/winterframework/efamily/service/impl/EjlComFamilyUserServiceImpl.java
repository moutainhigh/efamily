package com.winterframework.efamily.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlComFamilyUserDao;
import com.winterframework.efamily.entity.EjlFamilyUser;
import com.winterframework.efamily.entity.EjlFamilyUser.Position;
import com.winterframework.efamily.service.IEjlComFamilyUserService;



@Service("ejlComFamilyUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlComFamilyUserServiceImpl extends BaseServiceImpl<IEjlComFamilyUserDao, EjlFamilyUser> implements
		IEjlComFamilyUserService {
	@Resource(name = "ejlComFamilyUserDaoImpl")
	private IEjlComFamilyUserDao dao;

	@Override
	protected IEjlComFamilyUserDao getEntityDao() {
		return dao;
	}
	
	public int updateStatusAndManageType(EjlFamilyUser familyUser) throws BizException {
		return dao.updateStatusAndManageType( familyUser);
	}
	@Override
	public List<EjlFamilyUser> getByUserId(Long userId) throws BizException {
		try {
			return dao.getByUserId(userId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	@Override
	public EjlFamilyUser getEffectiveByUserId(Long userId) throws BizException {
		List<EjlFamilyUser> familyUserList=getByUserId(userId);
		if(null!=familyUserList){
			Iterator<EjlFamilyUser> iter=familyUserList.iterator();
			while(iter.hasNext()){
				EjlFamilyUser familyUser=iter.next();
				if(familyUser.getStatus()==EjlFamilyUser.Status.PRESENT.getValue()){
					return familyUser;
				}
			}
		}		
		return null;
	}
	@Override
	public List<EjlFamilyUser> getByFamilyId(Long familyId) throws BizException {
		try {
			return dao.getByFamilyId(familyId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	
	@Override
	public List<EjlFamilyUser> getEffectiveByFamilyId(Long familyId)
			throws BizException {
		List<EjlFamilyUser> familyUserList=getByFamilyId(familyId);
		if(null!=familyUserList){
			Iterator<EjlFamilyUser> iter=familyUserList.iterator();
			while(iter.hasNext()){
				EjlFamilyUser familyUser=iter.next();
				if(familyUser.getStatus()==EjlFamilyUser.Status.ABSENT.getValue()){
					iter.remove();
				}
			}
		}		
		return familyUserList;
	}
	
	@Override
	public List<EjlFamilyUser> getListByFamilyId(Long familyId)
			throws BizException {
		try{
			return getEntityDao().getListByFamilyId(familyId);
		}catch(Exception e){
			log.error("dao error(getListByFamilyId): familyId="+familyId,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	@Override
	public List<EjlFamilyUser> getValidListByFamilyId(Long familyId)
			throws BizException {
		List<EjlFamilyUser> familyUserList=getListByFamilyId(familyId);
		return getValidList(familyUserList);
	}
	@Override
	public List<EjlFamilyUser> getListByFamilyIdAndType(Long familyId,
			Integer type) throws BizException {
		try{
			return getEntityDao().getListByFamilyIdAndType(familyId, type);
		}catch(Exception e){
			log.error("dao error(getListByFamilyIdAndType): familyId="+familyId+",type="+type,e);
			throw new BizException(StatusCode.DAO_ERROR.getValue());
		}
	}
	
	@Override
	public List<EjlFamilyUser> getValidListByFamilyIdAndType(Long familyId,
			Integer type) throws BizException {
		List<EjlFamilyUser> familyUserList=getListByFamilyIdAndType(familyId, type);
		return getValidList(familyUserList);
	}

	private List<EjlFamilyUser> getValidList(List<EjlFamilyUser> familyUserList) {
		if(null!=familyUserList){
			Iterator<EjlFamilyUser> iter=familyUserList.iterator();
			while(iter.hasNext()){
				EjlFamilyUser familyUser=iter.next();
				if(familyUser.getStatus().longValue()==YesNo.YES.getValue()){
					familyUserList.remove(familyUser);
				}
			}
		}
		return familyUserList;
	}
	
	public Long getFamilyHostUserId(Long familyId) throws BizException{
		Long hostUserId = null;
		EjlFamilyUser ejlFamilyUser = new EjlFamilyUser();
		ejlFamilyUser.setFamilyId(familyId);
		ejlFamilyUser.setPosition(Position.HOST.getValue());
		ejlFamilyUser =  getEntityDao().selectOneObjByAttribute(ejlFamilyUser);
		if(ejlFamilyUser != null){
			hostUserId = ejlFamilyUser.getUserId();
		}
		return hostUserId;
	}
	
}

