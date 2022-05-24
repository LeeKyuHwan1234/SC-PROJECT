package com.camping.camp.service;

import java.util.HashMap;
import java.util.List;

import com.camping.camp.dto.CampDto;

public interface CampService {
	List<CampDto> getOpenApi();
	List<CampDto> getPlaceDetail(String encurl);
	List<CampDto> getSearchCamp(HashMap<String, Object> ajaxdata);
	List<CampDto> getSearchCamp2(HashMap<String, Object> ajaxdata);
	List<CampDto> getSearchDo(HashMap<String, String> ajaxdata);
	List<CampDto> getSearchSigungu(String encurl);
	List<CampDto> getDoCategory();
	List<CampDto> getSigunguCategory(String encurl);
	List<CampDto> getSigunguCamp(HashMap<String, Object> ajaxdata);
	List<CampDto> getSearchDoCount(HashMap<String, String> ajaxdata);
	List<CampDto> getSearchSigunguCount(HashMap<String, Object> ajaxdata);
	List<CampDto> getTotalCamp();
	List<CampDto> getTotalCampCount();
}
