package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlThirdPartyLogin;
import com.winterframework.efamily.entity.ThirdPartyLoginStruc;

public interface IEjlComThirdPartyLoginService  extends IBaseService<EjlThirdPartyLogin>{

	public List<ThirdPartyLoginStruc> getThirdPartyLoginStruc(Long userId);
}
