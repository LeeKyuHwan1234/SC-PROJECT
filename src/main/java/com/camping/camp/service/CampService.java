package com.camping.camp.service;

import java.util.HashMap;
import java.util.List;

import com.camping.camp.dto.CampDto;

public interface CampService {
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
