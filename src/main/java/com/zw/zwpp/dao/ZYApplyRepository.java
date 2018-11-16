package com.zw.zwpp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zw.zwpp.base.BaseRepository;
import com.zw.zwpp.entity.ApplyInfo;

public interface ZYApplyRepository extends BaseRepository<ApplyInfo, Integer> {
	@Query(value = "select * from ApplyInfo where gameID = ?1 and gameVersion = ?2", nativeQuery = true)
	ApplyInfo getGameLevelingInfo(String gameID, String gameVersion);
	
	@Modifying
	@Transactional
	@Query("delete from ApplyInfo where id = ?1")
	int deleteById(Integer id);
	
	List<ApplyInfo> findAllByName(String name);
}
