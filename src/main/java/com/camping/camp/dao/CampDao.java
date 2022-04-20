package com.camping.camp.dao;
import java.util.HashMap;
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
	List<CampDto> getSearchCamp(String encurl);
	List<CampDto> getSearchDo(String encurl);
	List<CampDto> getSearchSigungu(String encurl);
	List<CampDto> getDoCategory();
	List<CampDto> getSigunguCategory(String encurl);
	List<CampDto> getSigunguCamp(HashMap<String, Object> ajaxdata);
	List<CampDto> getSearchDoCount(String encurl);
}
