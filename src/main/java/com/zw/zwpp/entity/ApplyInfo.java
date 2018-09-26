package com.zw.zwpp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 代练详情表
 * @author lailai
 *
 */
@Entity
public class ApplyInfo {
	@Column(name="id",columnDefinition="int(30) COMMENT '主键id'") 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="name",columnDefinition="varchar(30) COMMENT '账号'") 
	private String name;
	@Column(name="applyTime",columnDefinition="varchar(100) COMMENT '代练时长'") 
	private String applyTime;
	@Column(name="equipmentCode",columnDefinition="varchar(30) COMMENT '机器编号'") 
	private String equipmentCode;
	@Column(name="equipmentAddress",columnDefinition="varchar(30) COMMENT '机器地址'") 
	private String equipmentAddress;
	@Column(name="runState",columnDefinition="varchar(30) COMMENT '运行状态'") 
	private String runState;
	@Column(name="gameVersion",columnDefinition="varchar(50) COMMENT '游戏版本'") 
	private String gameVersion;
	@Column(name="gameID",columnDefinition="varchar(50) COMMENT '游戏账号'") 
	private String gameID;
	@Column(name="gamePwd",columnDefinition="varchar(50) COMMENT '游戏密码'") 
	private String gamePwd;
	@Column(name="runLog",columnDefinition="varchar(100) COMMENT '运行日志'") 
	private String runLog;
	@Column(name="yield",columnDefinition="varchar(100) COMMENT '收益'") 
	private String yield;
	@Column(name="runTime",columnDefinition="varchar(100) COMMENT '运行时长'") 
	private String runTime;
	@Column(name="startTime",columnDefinition="varchar(100) COMMENT '开始时间'") 
	private String startTime;
	@Column(name="armSet",columnDefinition="varchar(100) COMMENT '练兵设置'") 
	private String armSet;
	@Column(name="otherSet",columnDefinition="varchar(100) COMMENT '其他设置'") 
	private String otherSet;
	@Column(name="updateSet",columnDefinition="varchar(100) COMMENT '设置改变'") 
	private String updateSet;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentAddress() {
		return equipmentAddress;
	}
	public void setEquipmentAddress(String equipmentAddress) {
		this.equipmentAddress = equipmentAddress;
	}
	public String getRunState() {
		return runState;
	}
	public void setRunState(String runState) {
		this.runState = runState;
	}
	public String getGameVersion() {
		return gameVersion;
	}
	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getGamePwd() {
		return gamePwd;
	}
	public void setGamePwd(String gamePwd) {
		this.gamePwd = gamePwd;
	}
	public String getRunLog() {
		return runLog;
	}
	public void setRunLog(String runLog) {
		this.runLog = runLog;
	}
	public String getYield() {
		return yield;
	}
	public void setYield(String yield) {
		this.yield = yield;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getArmSet() {
		return armSet;
	}
	public void setArmSet(String armSet) {
		this.armSet = armSet;
	}
	public String getOtherSet() {
		return otherSet;
	}
	public void setOtherSet(String otherSet) {
		this.otherSet = otherSet;
	}
	public String getUpdateSet() {
		return updateSet;
	}
	public void setUpdateSet(String updateSet) {
		this.updateSet = updateSet;
	}

	
}
