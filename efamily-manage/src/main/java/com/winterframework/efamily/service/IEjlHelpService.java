package com.winterframework.efamily.service;

import java.util.List;

import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.entity.EjlHelp;

public interface IEjlHelpService extends IBaseService<EjlHelp> {

	List<EjlHelp> getAll() throws Exception;
}
