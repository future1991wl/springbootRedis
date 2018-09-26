package com.zw.zwpp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zw.zwpp.dao.ZYEquipmentRepository;
import com.zw.zwpp.service.ZYEquipmentService;
@Service
public class ZYEquipmentServiceImpl implements ZYEquipmentService {
	@Autowired
	private ZYEquipmentRepository equipmentRepository;

}
