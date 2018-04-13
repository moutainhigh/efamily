package com.winterframework.efamily.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.efamily.base.exception.BizException;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.common.EfamilyConstant;
import com.winterframework.efamily.core.base.BaseServiceImpl;
import com.winterframework.efamily.dao.IEjlUserBarrierDao;
import com.winterframework.efamily.dao.IEjlUserDeviceDao;
import com.winterframework.efamily.dto.BatchSetOrgUserBarrierRequest;
import com.winterframework.efamily.dto.SaveFenceReq;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.EjlDeviceAddressList;
import com.winterframework.efamily.entity.EjlUser;
import com.winterframework.efamily.entity.EjlUserBarrier;
import com.winterframework.efamily.entity.EjlUserDevice;
import com.winterframework.efamily.entity.UserBarrierStruc;
import com.winterframework.efamily.enums.StatusBizCode;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IEjlUserBarrierService;
import com.winterframework.efamily.service.IEjlUserService;
import com.winterframework.efamily.service.IInstitutionUserService;

	@Service("ejlUserBarrierServiceImpl")
	@Transactional(rollbackFor = Exception.class)
	public class EjlUserBarrierServiceImpl extends BaseServiceImpl<IEjlUserBarrierDao,EjlUserBarrier> implements IEjlUserBarrierService {
	 
		private static final Logger log = LoggerFactory.getLogger(EjlUserBarrierServiceImpl.class);
		
		@Resource(name="ejlUserBarrierDaoImpl")
		private IEjlUserBarrierDao ejlUserBarrierDaoImpl;
		
		@Resource(name = "institutionUserServiceImpl")
		private IInstitutionUserService institutionUserServiceImpl;
		@Override
		protected IEjlUserBarrierDao getEntityDao() { 
			return ejlUserBarrierDaoImpl;
		}
		
		@Resource(name = "ejlUserServiceImpl")
		private IEjlUserService ejlUserServiceImpl;
		
		@Resource(name="ejlDeviceServiceImpl")
		private IEjlDeviceService ejlDeviceServiceImpl;
		
		@Resource(name="ejlUserDeviceDaoImpl")
		private IEjlUserDeviceDao ejlUserDeviceDaoImpl;
				
		public EjlUserBarrier getEjlUserBarrier(Long userId) throws BizException{

			return ejlUserBarrierDaoImpl.getOneBarrierByUserId(userId,0);
		}
		
		public List<UserBarrierStruc> getEjlUserBarrierList(Long userId) throws BizException{

			return ejlUserBarrierDaoImpl.getListBarrierByUserId(userId,0);
		}
		
		
		public boolean checkSetEjlUserBarrier(Long optUserId,Long barrierUserId) throws BizException{
			boolean flag = false;
			//*** 只有家人才能设置  手表  围栏 
			EjlUser optUser = ejlUserServiceImpl.get(optUserId);
			EjlUser barrierUser = ejlUserServiceImpl.get(barrierUserId);
			if(optUser.getFamilyId()!=null && barrierUser.getFamilyId()!=null && barrierUser.getFamilyId().longValue()==optUser.getFamilyId().longValue()){
				flag = true;
			}else{
				log.info("只有家人才能设置  手表  围栏  : ----- optUserId = "+optUserId+" =====>>>>  barrierUserId = "+barrierUserId);
				throw new BizException(StatusBizCode.USER_BARRIER_SET_ONLY_FAMILY_MEMBER.getValue());
			}
			return flag ;
		}
		
		private void setBatchBarrier(Context ctx,List<EjlUserBarrier> list,Integer orgTag,Long deviceUserId) throws BizException{
			if(orgTag == 1){
				ejlUserBarrierDaoImpl.deleteByUserAndOrgTag(deviceUserId, orgTag);
				for(EjlUserBarrier ejlUserBarrier:list){
					ejlUserBarrier.setOrgTag(1);
				}
				if(list.size()>0){
					this.save(ctx, list);
				}
			}
		}
		
		
		
		@Override
		public void batchSetUserBarrier(Context ctx,List<SaveFenceReq> list, String key,
				int orgTag) throws BizException {
			if(orgTag == 1){
				for(SaveFenceReq saveFenceReq:list){
					String imei = saveFenceReq.getImei();
					EjlDevice ejlDevice = ejlDeviceServiceImpl.getByImei(imei);
					if(ejlDevice == null){
						log.error("当前操作的设备没有使用。imei = "+imei);
						throw new BizException(StatusBizCode.DEVICE_KEY_INVALID.getValue());
					}
					Long deviceUserId = null;
					EjlUserDevice ejlUserDevice =new EjlUserDevice();
					ejlUserDevice.setDeviceId(ejlDevice.getId());
					ejlUserDevice.setStatus(EfamilyConstant.USER_DEVICE_STATUS_USING);
					ejlUserDevice = ejlUserDeviceDaoImpl.selectOneObjByAttribute(ejlUserDevice);
					if(ejlUserDevice == null){
						log.error("当前操作的设备没有用户使用。watchId = "+ejlDevice.getId());
						throw new BizException(StatusBizCode.USER_NOT_BIND_DEVICE.getValue());
					}
					if(!institutionUserServiceImpl.isImeiContainOrg(imei,key)){
						log.error("IMEI_UKEY_ORGUKEY_NOTSAME imei:"+imei);
						throw new BizException(StatusBizCode.IMEI_UKEY_ORGUKEY_NOTSAME.getValue());
					}
					deviceUserId = ejlUserDevice.getUserId();
					
					List<BatchSetOrgUserBarrierRequest> fenceInfo = saveFenceReq.getFenceInfo();
					List<EjlUserBarrier> bList = new ArrayList<EjlUserBarrier>();
					if(fenceInfo!=null){
						for(BatchSetOrgUserBarrierRequest batchSetOrgUserBarrierRequest:fenceInfo){
							EjlUserBarrier eb = new EjlUserBarrier();
							eb.setLocation(batchSetOrgUserBarrierRequest.getLocation().split(",")[1]+","+batchSetOrgUserBarrierRequest.getLocation().split(",")[0]);
							eb.setRadius(batchSetOrgUserBarrierRequest.getRadius());
							eb.setOrgTag(1);
							eb.setStatus(1);
							eb.setUserId(deviceUserId);
							eb.setType(batchSetOrgUserBarrierRequest.getType());
							bList.add(eb);
						}
					}
					this.setBatchBarrier(ctx, bList, orgTag, deviceUserId);
				}
			}
			
		}

		public int setEjlUserBarrier(Context ctx,Long fenceId,Long userId,Integer status,String location,Long radius,Integer type,String remark,int orgTag) throws BizException{
			EjlUserBarrier ejlUserBarrier = new EjlUserBarrier();
			ejlUserBarrier.setOrgTag(orgTag);
			if(fenceId<0){
				EjlUserBarrier ejlUserBarrierCheck = ejlUserBarrierDaoImpl.getOneBarrierByUserId(userId,orgTag);
				if(ejlUserBarrierCheck!=null){
					fenceId = ejlUserBarrierCheck.getId();
					ejlUserBarrier.setId(fenceId);
				}
				/*else{
					log.info("被修改的设备围栏已不存在 : ----- status = "+status+" =====>>>>  userId = "+userId);
					throw new BizException(StatusBizCode.USER_BARRIER_NOT_EXIST.getValue());
				}*/
			}else if(fenceId>0){
				ejlUserBarrier.setId(fenceId);
			}
			if(status.intValue()==1){//0 关闭  1打开
				ejlUserBarrier.setUserId(userId);
				ejlUserBarrier.setLocation(location);
				ejlUserBarrier.setRadius(radius);
				ejlUserBarrier.setType(type);
				ejlUserBarrier.setRemark(remark);
			}
			ejlUserBarrier.setStatus(status);
			return save(ctx,ejlUserBarrier);
		}
		
	}