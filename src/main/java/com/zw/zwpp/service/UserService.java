package com.zw.zwpp.service;

import com.zw.zwpp.entity.User;

public interface UserService {

	User findByName(String string);

	User insert(User u);

}