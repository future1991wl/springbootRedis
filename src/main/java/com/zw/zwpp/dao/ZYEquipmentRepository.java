package com.zw.zwpp.dao;

import java.util.List;

import com.zw.zwpp.base.BaseRepository;
import com.zw.zwpp.entity.ApplyInfo;
import com.zw.zwpp.entity.EquipmentInfo;

public interface ZYEquipmentRepository extends BaseRepository<EquipmentInfo, Integer> {

	List<EquipmentInfo> findByState(String string);
}
