package com.zw.zwpp.service;

import com.zw.zwpp.entity.User;

public interface UserService {

	User findByName(String string);

	User insert(User u);
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	Integer updateUser(User user);

	/**
	 * 修改账户余额
	 * @param u
	 * @return
	 */
	Integer updateMoneyByUser(User u);

	/**修改账号代练的地址
	 * @param user
	 * @return
	 */
	Integer updateUserAddress(User user);

}
