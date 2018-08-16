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

}
