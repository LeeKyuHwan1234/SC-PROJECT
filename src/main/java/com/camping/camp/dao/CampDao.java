package com.camping.camp.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.camping.camp.dto.CampDto;

@Mapper
@Repository
public interface CampDao {
	List<CampDto> getManyTodoList();
	List<CampDto> getOpenApi();
	List<CampDto> getPlaceDetail(String id);
}
