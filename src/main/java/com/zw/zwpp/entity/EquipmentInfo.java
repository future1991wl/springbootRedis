package com.zw.zwpp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 设备详情表
 * @author lailai
 *
 */
@Entity
public class EquipmentInfo {
	@Column(name="id",columnDefinition="int(30) COMMENT '主键id'") 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="equipmentCode",columnDefinition="varchar(30) COMMENT '机器编号'") 
	private String equipmentCode;
	@Column(name="simulatorNum",columnDefinition="int(30) COMMENT '配置模拟器个数'") 
	private Integer simulatorNum;
	@Column(name="address",columnDefinition="varchar(1000) COMMENT '序号地址'") 
	private String address;
	@Column(name="state",columnDefinition="varchar(30) COMMENT '状态'") 
	private String state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public Integer getSimulatorNum() {
		return simulatorNum;
	}
	public void setSimulatorNum(Integer simulatorNum) {
		this.simulatorNum = simulatorNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
}
