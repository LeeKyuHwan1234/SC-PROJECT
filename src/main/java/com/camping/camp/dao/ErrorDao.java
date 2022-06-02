package com.camping.camp.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.camping.camp.dto.ErrorDto;


@Mapper
@Repository
public interface ErrorDao {
	
	List<ErrorDto> getErrorCorrection();
	void insertErrorCorrection(HashMap<String, Object> ajaxdata);
}
