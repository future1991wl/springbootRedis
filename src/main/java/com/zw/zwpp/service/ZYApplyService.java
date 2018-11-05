package com.zw.zwpp.service;

import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.entity.ApplyInfo;

/**
 * @author lailai
 *
 */
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

	/**
	 * 通过游戏账号和游戏版本获取账号代练信息
	 * @param gameID 游戏账号
	 * @param gameVersion 游戏版本
	 * @return
	 */
	ApplyInfo getGameLevelingInfo(String gameID, String gameVersion);
}
