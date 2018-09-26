package com.zw.zwpp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户账号表
 * @author lailai
 *
 */
@Entity
public class User {
	@Column(name="id",columnDefinition="int(30) COMMENT '主键id'") 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="name",columnDefinition="varchar(30) COMMENT '姓名'") 
	private String name;
	@Column(name="password",columnDefinition="varchar(50) COMMENT '密码'") 
	private String password;
	@Column(name="money",columnDefinition="double(10,2) default '0.00' COMMENT '余额'") 
	private Double money;
	@Column(name="address",columnDefinition="varchar(255) COMMENT '序号地址'") 
	private String address;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
}
