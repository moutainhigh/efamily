package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class EjlUserFence  extends FmlBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//alias
		public static final String TABLE_ALIAS = "EjlUserFence";
		public static final String ALIAS_FENCE_ID = "围栏ID";
		public static final String ALIAS_USER_ID = "用户ID";
		public static final String ALIAS_STATUS = "状态 0：关闭  1：打开 ";
		public static final String ALIAS_REMARK = "备注";
		public static final String ALIAS_CREATOR_ID = "creatorId";
		public static final String ALIAS_CREATE_TIME = "创建时间";
		public static final String ALIAS_UPDATOR_ID = "updatorId";
		public static final String ALIAS_UPDATE_TIME = "更新时间";

		//columns START
		private Long barrierId;
		private Long fenceId;
		private Long userId;
		private Integer status;

		//columns END

		public EjlUserFence(){
		}

		public EjlUserFence(
			Long id
		){
			this.id = id;
		}

		
		public Long getBarrierId() {
			return barrierId;
		}

		public void setBarrierId(Long barrierId) {
			this.barrierId = barrierId;
		}

		public Long getFenceId() {
			return fenceId;
		}

		public void setFenceId(Long fenceId) {
			this.fenceId = fenceId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "EjlUserFence [barrierId=" + barrierId + ", fenceId="
					+ fenceId + ", userId=" + userId + ", status=" + status
					+ "]";
		}


}
