package com.winterframework.efamily.api.service.impl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.efamily.api.service.IEfOrgService;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.entity.EfOrg;
import com.winterframework.efamily.service.impl.EfComOrgServiceImpl;

@Service("efOrgServiceImpl")
@Transactional(rollbackFor = BizException.class)
public class EfOrgServiceImpl  extends EfComOrgServiceImpl  implements IEfOrgService{

	@Override
	public String register(EfOrg entity) throws BizException {
		String iKey = DigestUtils.md5Hex(entity.getName()+entity.getCity()+entity.getPhone()+DateUtils.currentDate().getTime());
		Context ctx =  new Context();
		ctx.set("userId", -1);
		entity.setIkey(iKey);
		entity.setNumber(String.valueOf(this.getEntityDao().getCount()+1));
		this.save(ctx, entity);
		return iKey;
	}
}
