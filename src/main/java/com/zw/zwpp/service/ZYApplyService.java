package com.zw.zwpp.service;

import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.ApplyInfo;

public interface ZYApplyService {

	/**
	 * 通过id查代练详情
	 * @param id
	 * @return
	 */
	ApplyInfo findById(int id);

	/**
	 * 存储代练详情
	 * @param apply
	 */
	ApplyInfo save(ApplyInfo apply);
	
	/**
	 * 申请代练业务
	 * @param reqApply
	 * @return
	 */
	BaseResponse applyGameLeveling(ApplyInfo reqApply);


}
