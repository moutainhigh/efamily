package com.winterframework.efamily.service;

import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.core.base.IBaseService;
import com.winterframework.efamily.dto.CreateFamilyResponse;
import com.winterframework.efamily.dto.GetFamilyAttentionResponse;
import com.winterframework.efamily.dto.ManageJoinFamilyCheck;
import com.winterframework.efamily.dto.ManageJoinFamilyResponse;
import com.winterframework.efamily.dto.SearchFamilyResponse;
import com.winterframework.efamily.entity.EjlFamily;


public interface IEjlFamilyService extends IBaseService<EjlFamily> {
	/** 创建家庭
	 * @param userId 用户ID
	 * @param familyName 家庭名称
	 */
	public CreateFamilyResponse createFamily(Context ctx,Long userId, String familyName) throws BizException ;
	
	/**加入家庭
	 * @param headImg头像
	 * @param nickName昵称
	 * @param familyId 家庭id
	 * @param roleId角色id
	 */
	public ManageJoinFamilyResponse manageFamily(Context ctx,Long userId, Long applyUserid, Long familyId,String manageType) throws BizException ;
	
	/**
	 * 功能：保存或者修改家庭信息
	 * @param ejlFamily
	 * @return
	 */
	public Long saveOrUpdateFamily(Context ctx,EjlFamily ejlFamily,Long userId)  throws BizException;
	
	/**
	 * 功能：根据家庭编码或者ID获取家庭信息
	 * @param code
	 * @return
	 */
	public EjlFamily getEjlFamilyByCodeOrFamilyId(String manageType,String code,String familyId);
	
	/**
	 * 管理家庭操作之前：进行参数的判断和处理
	 * @param userId
	 * @param applyUseridX
	 * @param familyId
	 * @param familyCode
	 * @param manageType
	 * @param isPhoneNumber
	 * @return
	 * @throws BizException
	 */
	public ManageJoinFamilyCheck checkManageFamily(Long userId, String applyUseridX, String familyId,String familyCode,String manageType, Long isPhoneNumber) throws BizException;
	
	/**
	 * 管理家庭之后进行相应的推送操作
	 * @param optfamilyId
	 * @param oldFamilyId
	 * @param userId
	 * @param applyUserid
	 * @param familyCode
	 * @param manageType
	 * @throws BizException
	 */
	public void notifyForManageJoinFamily(Long optfamilyId,Long oldFamilyId,Long userId, Long applyUserid,String familyCode,String manageType)throws BizException;
	
	public GetFamilyAttentionResponse getFamilyAttentionDetail(Long userId,Long familyId) throws BizException;

}
