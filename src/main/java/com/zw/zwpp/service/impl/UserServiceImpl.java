package com.zw.zwpp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zw.zwpp.dao.UserRepository;
import com.zw.zwpp.entity.User;
import com.zw.zwpp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public User findByName(String string) {
		return userRepository.findByName(string);
	}
	@Override
	public User insert(User u) {
		User save = userRepository.save(u);
		return save;
		
	}
	@Override
	public Integer updateUser(User user) {
		return userRepository.updateUser(user.getPassword(),user.getId());
	}
	@Override
	public Integer updateMoneyByUser(User u) {
		return userRepository.updateMoneyByUser(u.getMoney(),u.getId());
	}
	@Override
	public Integer updateUserAddress(User u) {
		return userRepository.updateUserAddress(u.getAddress(), u.getId());
	}

}
