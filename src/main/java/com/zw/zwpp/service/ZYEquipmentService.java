package com.zw.zwpp.service;

import com.zw.zwpp.entity.EquipmentInfo;

public interface ZYEquipmentService {

	/**
	 * 新增或修改模拟器
	 * @param equipmentInfo
	 * @return
	 */
	EquipmentInfo saveAndUpdateEquipment(EquipmentInfo equipmentInfo);
}
