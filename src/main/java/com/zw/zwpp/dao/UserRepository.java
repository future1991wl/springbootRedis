package com.zw.zwpp.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zw.zwpp.base.BaseRepository;
import com.zw.zwpp.entity.User;

public interface UserRepository extends BaseRepository<User, Integer> {
	User findByName(String name);
	@Transactional
	@Modifying
	@Query(value  = "update User u set u.password = ?1 where u.id = ?2",nativeQuery = true)
	int updateUser(String password, Integer id);
}
