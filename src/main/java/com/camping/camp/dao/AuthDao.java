package com.camping.camp.dao;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.camping.camp.dto.AuthDto;
import com.camping.camp.dto.CampDto;

@Mapper
@Repository
public interface AuthDao {
	void insertUser(HashMap<String, Object> UserInfo);
	List<AuthDto> selectUser(HashMap<String, Object> UserInfo);
}
