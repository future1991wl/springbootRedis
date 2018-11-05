package com.zw.zwpp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.utils.StringUtils;
import com.zw.zwpp.base.BaseResponse;
import com.zw.zwpp.dao.ZYApplyRepository;
import com.zw.zwpp.dao.ZYEquipmentRepository;
import com.zw.zwpp.entity.ApplyInfo;
import com.zw.zwpp.entity.EquipmentInfo;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;
import com.zw.zwpp.service.ZYApplyService;
@Service
public class ZYApplyServiceImpl implements ZYApplyService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZYApplyRepository applyRepository;
	@Autowired
	private ZYEquipmentRepository equipmentRepository;
	@Autowired
	private UserService userService;
	@Override
	public ApplyInfo findById(int id) {
		return applyRepository.findOne(id);
	}

	@Override
	public ApplyInfo save(ApplyInfo apply) {
		return applyRepository.save(apply);
	}
	@Transactional
	@Override
	public BaseResponse applyGameLeveling(ApplyInfo reqApply) {
		logger.info("申请代练入参：{}",reqApply);
		BaseResponse res = new BaseResponse();
		String version = reqApply.getGameVersion();
		//获取所有在线设备
		List<EquipmentInfo> list = equipmentRepository.findByState("1");
		for (EquipmentInfo equipmentInfo : list) {
			String address = equipmentInfo.getAddress();
			Integer simulatorNum = equipmentInfo.getSimulatorNum();
			//将地址放到集合中
			List<List<String>> oldList = new ArrayList<>(simulatorNum);
			List<String> newList = null;
				String[] split = address.replace(" ", "").split("\\]\\,\\[");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].replace("[", "").replace("]", "").split(",");
					newList = new ArrayList<>();
						for (int j = 0; j < split2.length; j++) {
							if(StringUtils.isNotEmpty(split2[j])) {
								newList.add(split2[j]);
							}
					}
						oldList.add(newList);
				}
			logger.info("地址集合长度：{}",oldList.size());
			if(oldList.size()<simulatorNum) {
				int num = simulatorNum-oldList.size();
				for (int i = 0; i < num; i++) {
					newList = new ArrayList<>();
					oldList.add(newList);
				}
			}
			logger.info("地址集合："+oldList);
			boolean flag = false;
			for (int i = 0; i < oldList.size(); i++) {
				List<String> addressCodeList = oldList.get(i);
				//判断当前模拟器是否存满，或当前申请代练存完后直接跳出循环
				if(addressCodeList.size() >= 5 || flag) {
					continue;
				}
				//当前版本如果是腾讯只能存4个，百度2个
				Integer tencent = 0;
				Integer baidu = 0;
				for (int j = 0; j < addressCodeList.size(); j++) {
					int id = Integer.parseInt(addressCodeList.get(j));
					ApplyInfo applyInfo = findById(id);
					if("tencent".equals(applyInfo.getGameVersion())) {
						tencent += 1;
					}else if("baidu".equals(applyInfo.getGameVersion())) {
						baidu += 1;
					}
				}
				logger.info("当前模拟器腾讯版本数量：{}个，百度版本数量：{}个",tencent,baidu);
				//判断当前模拟器是否可以申请代码
				if(tencent<4&&"tencent".equals(version)||baidu<2&&"baidu".equals(version)) {
					User user = userService.findByName(reqApply.getName());
					//落地申请信息
					ApplyInfo saveApply = save(reqApply);
					//修改此模拟器中的地址集合
					if(saveApply!=null) {
						logger.info("落地申请信息成功");
						addressCodeList.add(saveApply.getId()+"");
						//修改账号下的代练游戏账号地址
						String userOldAddress = user.getAddress();
						if(StringUtils.isEmpty(userOldAddress)) {
							userOldAddress = "";
						}
						String userAddress = userOldAddress+"&"+saveApply.getId();
						user.setAddress(userAddress);
						Integer userNum =userService.updateUserAddress(user);
						if(userNum != null) {
							logger.info("修改用户代练信息地址成功");
							flag = true;
						}
					}
				}else {
					continue;
				}
			}
			if(!oldList.toString().equals(address)) {
				equipmentInfo.setAddress(oldList.toString());
				EquipmentInfo equipment = equipmentRepository.save(equipmentInfo);
				if(equipment!=null) {
					res.setCode(0);
					logger.info("修改设备中的地址集合成功：返回状态码：0");
					break;
				}
			}
		}
		return res;
	}

	@Override
	public ApplyInfo getGameLevelingInfo(String gameID, String gameVersion) {
		return applyRepository.getGameLevelingInfo(gameID,gameVersion);
	}
}
