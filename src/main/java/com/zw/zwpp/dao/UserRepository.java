package com.zw.zwpp.dao;

import com.zw.zwpp.base.BaseRepository;
import com.zw.zwpp.entity.User;

public interface UserRepository extends BaseRepository<User, Integer> {
	User findByName(String name);
}
