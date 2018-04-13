package com.winterframework.efamily.dao;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseDao;
import com.winterframework.efamily.entity.EjlThirdPartyLogin;
import com.winterframework.efamily.entity.ThirdPartyLoginStruc;

public interface IEjlComThirdPartyLoginDao extends IBaseDao<EjlThirdPartyLogin> {

	public List<ThirdPartyLoginStruc> getThirdPartyLoginStruc(Long userId);
}
